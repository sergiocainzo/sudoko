package br.com.game.input;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.Font;

import static java.awt.Font.PLAIN;

import br.com.game.model.Space;

public class NumberText extends JTextField{
    private final Space space;

    public NumberText(final Space space){
        this.space = space;
        var dimension = new Dimension(50,50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if (space.isFixed()) {
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {

            private void changeSpace(){
                if (getText().isEmpty()) {
                    space.clearSpace();
                    return;
                }

                space.setActual(Integer.parseInt(getText()));

            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                changeSpace();
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                changeSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                changeSpace();
            }
            
        });
    }
}
