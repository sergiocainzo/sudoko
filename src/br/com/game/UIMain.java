package br.com.game;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;

import br.com.game.ui_custom.frame.MainFrame;
import br.com.game.ui_custom.panel.MainPanel;

public class UIMain {

    public static void main(String[] args) {
        var dimension = new Dimension(600, 600);
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
