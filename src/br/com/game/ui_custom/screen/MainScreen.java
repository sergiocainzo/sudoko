package br.com.game.ui_custom.screen;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.game.service.BoardService;
import br.com.game.ui_custom.button.CheckGameStatusButton;
import br.com.game.ui_custom.button.FinishGameButton;
import br.com.game.ui_custom.button.ResetButton;
import br.com.game.ui_custom.frame.MainFrame;
import br.com.game.ui_custom.panel.MainPanel;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static br.com.game.model.GameStatusEnum.INCOMPLETE;
import static br.com.game.model.GameStatusEnum.COMPLETE;
import static br.com.game.model.GameStatusEnum.NON_STARTED;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton finishGameButton;
    private JButton checkGameStatusButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig){
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(null,"Deseja limpar o jogo?","Limpar o jogo",YES_NO_OPTION,QUESTION_MESSAGE);
            if (dialogResult == 0) {
                boardService.reset();
            } 
        });

        
        mainPanel.add(resetButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus){
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasErrors ? " e contem erros":" e não contem erros";
            JOptionPane.showMessageDialog(null, message);
        });

        mainPanel.add(checkGameStatusButton);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()) {
                JOptionPane.showMessageDialog(null, "Parabéns, você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Seu jogo tem alguma inconsistência, ajuste e tente novamente.");
            }
        });
        mainPanel.add(finishGameButton);
    }
}
