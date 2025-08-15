package br.com.game.ui_custom.panel;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import br.com.game.input.NumberText;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import static java.awt.Color.black;

public class SudokuSector extends JPanel {

    public SudokuSector(final List<NumberText> textFields) {
        var dimension = new Dimension(170, 170);
        this.setLayout(new GridLayout(3, 3));
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setBorder(new LineBorder(black, 2, true));
        this.setVisible(true);
        textFields.forEach(this::add);
    }

}
