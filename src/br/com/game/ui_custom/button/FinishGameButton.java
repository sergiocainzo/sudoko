package br.com.game.ui_custom.button;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class FinishGameButton extends JButton {

    public FinishGameButton(final ActionListener actionsListener) {
        this.setText("Concluir jogo");
        this.addActionListener(actionsListener);
    }

}
