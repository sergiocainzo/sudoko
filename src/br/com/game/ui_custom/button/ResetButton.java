package br.com.game.ui_custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ResetButton extends JButton {
    public ResetButton(final ActionListener actionsListener) {
        this.setText("Reiniciar jogo");
        this.addActionListener(actionsListener);
    }
}
