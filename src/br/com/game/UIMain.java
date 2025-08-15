package br.com.game;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.JPanel;
import javax.swing.JFrame;

import br.com.game.ui_custom.frame.MainFrame;

import br.com.game.ui_custom.screen.MainScreen;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {

        var fullArgs = String.join(" ", args);

        final var gameConfig = Arrays.stream(fullArgs.split(" "))
                .collect(toMap(
                        k -> k.split(";")[0],
                        // v -> v.split(";")[1]
                        v -> v.substring(v.indexOf(";") + 1)));

        var mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();

    }
}
