package br.com.game.ui_custom.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.game.input.NumberText;
import br.com.game.model.Space;
import br.com.game.service.BoardService;
import br.com.game.ui_custom.button.CheckGameStatusButton;
import br.com.game.ui_custom.button.FinishGameButton;
import br.com.game.ui_custom.button.ResetButton;
import br.com.game.ui_custom.frame.MainFrame;

import br.com.game.ui_custom.panel.SudokuSector;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static br.com.game.model.GameStatusEnum.INCOMPLETE;
import static br.com.game.model.GameStatusEnum.COMPLETE;
import static br.com.game.model.GameStatusEnum.NON_STARTED;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final Map<String, String> gameConfig;

    private JButton finishGameButton;
    private JButton checkGameStatusButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.gameConfig = gameConfig;
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen() {
        // Criando o painel principal com BorderLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Painel para o tabuleiro do jogo
        JPanel sudokuPanel = new JPanel();
        // GRID para os setores do jogo
        sudokuPanel.setLayout(new GridLayout(3, 3));
        // Ajuste de Dimensão
        sudokuPanel.setPreferredSize(new Dimension(600, 500));

        // Criar e adicionar os 9 setores do jogo
        for (int r = 0; r < 9; r += 3) {
            for (int c = 0; c < 9; c += 3) {
                var spaces = getSpacesFromSector(boardService.getSpaces(), r, r + 2, c, c + 2);
                JPanel section = generateSection(spaces);
                sudokuPanel.add(section);
            }
        }

        // Painel para os botoões
        JPanel buttonPanel = new JPanel();

        // Adicionando os botões
        addResetButton(buttonPanel);
        addCheckGameStatusButton(buttonPanel);
        addFinishGameButton(buttonPanel);

        // Adicionando painel para o painel principal
        mainPanel.add(sudokuPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Frame Principal
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.setVisible(true);
        mainFrame.revalidate();
        mainFrame.repaint();

    }

    private List<Space> getSpacesFromSector(List<List<Space>> spaces, final int initRow, final int endRow,
            final int initCol, final int endCol) {
        List<Space> spaceSector = new ArrayList<>();

        // Iteração para pegar os espaços do setor
        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                // Acessando o elemento na posição correta
                spaceSector.add(spaces.get(r).get(c));
            }
        }
        return spaceSector;

    }

    private JPanel generateSection(final List<Space> spaces) {
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        return new SudokuSector(fields);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(null, "Deseja limpar o jogo?", "Limpar o jogo",
                    YES_NO_OPTION, QUESTION_MESSAGE);
            if (dialogResult == 0) {
                boardService.reset();
                // Fecha a janela atual
                JFrame frame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(mainPanel);
                frame.dispose();
                // Cria uma nova tela
                new MainScreen(gameConfig).buildMainScreen();
            }
        });

        mainPanel.add(resetButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus) {
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasErrors ? " e contem erros" : " e não contem erros";
            JOptionPane.showMessageDialog(null, message);
        });

        mainPanel.add(checkGameStatusButton);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()) {
                JOptionPane.showMessageDialog(null, "Parabéns, você concluiu o jogo");
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
                resetButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Seu jogo tem alguma inconsistência, ajuste e tente novamente.");
            }
        });
        mainPanel.add(finishGameButton);
    }
}
