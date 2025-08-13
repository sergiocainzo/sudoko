package br.com.game;

import java.awt.Dimension;
import java.util.stream.Stream;

import javax.swing.JPanel;
import javax.swing.JFrame;

import br.com.game.ui_custom.frame.MainFrame;
import br.com.game.ui_custom.panel.MainPanel;
import br.com.game.ui_custom.screen.MainScreen;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        final var gameConfig = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]));
        
        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();



    }
}
