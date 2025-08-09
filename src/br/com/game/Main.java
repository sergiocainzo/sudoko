package br.com.game;

import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static br.com.game.util.BoardTemplate.BOARD_TEMPLATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.game.model.Board;
import br.com.game.model.Space;

public class Main {
    // Atritubos
    private final static Scanner scan = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]));

        var option = -1;
        while (true) {
            System.out.println("Selecione uma das opções a seguir:");
            System.out.println("1 - Iniciar um novo jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            option = scan.nextInt();

            switch (option) {
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 0 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu.");
            }

        }

    }

    private static void startGame(final Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("\nO jogo já foi iniciado.\n");
            return;
        }
        
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);

                spaces.get(i).add(currentSpace);
            }
        }
        board = new Board(spaces);
        System.out.println("\nO jogo está pronto para começar!!\n");
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("\nO jogo ainda não já foi iniciado.\n");
            return;
        }

        System.out.print("\nInforme a 'coluna': ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a 'linha': ");
        var row = runUntilGetValidNumber(0, 8);
        System.out.print("Informe um número: [1-9] ");
        var value = runUntilGetValidNumber(1, 9);
        if (!board.changeValue(col, row, value)) {
            System.out.printf("A posição [Coluna: %s, Linha: %s] tem um valor fixo.\n\n", col, row);
        }
        System.out.println("");
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("\nO jogo ainda não já foi iniciado.\n");
            return;
        }

        System.out.print("\nInforme a 'coluna': ");
        var col = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a 'linha': ");
        var row = runUntilGetValidNumber(0, 8);
        // System.out.print("Informe um número: ");
        // var value = runUntilGetValidNumber(1, 9);
        if (!board.clearValue(col, row)) {
            System.out.printf("A posição [Coluna: %s, Linha: %s] tem um valor fixo.\n\n", col, row);
        } else {
            System.out.println("Numero removido");
        }
        // System.out.println("");
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("\nO jogo ainda não já foi iniciado.\n");
            return;
        }

        var args = new Object[BOARD_LIMIT * BOARD_LIMIT];
        var argPos = 0;

        // Iteração entre as linhas e colunas
        for (int i = 0; i < BOARD_LIMIT; i++) {
            // for (var col : board.getSpaces()) {
            // args[argPos++] = " " + ((isNull(col.get(i).getActual())) ? " " :
            // col.get(i).getActual());
            // }
            for (int j = 0; j < BOARD_LIMIT; j++) {
                // Acesso a espaço na posição Linha e Coluna
                var space = board.getSpaces().get(i).get(j);
                args[argPos++] = " " + ((isNull(space.getActual())) ? " " : space.getActual());
            }
        }
        System.out.println("\nSeu jogo se encontra da seguinte forma:");
        System.out.printf((BOARD_TEMPLATE) + "%n", args);
        System.out.println("");
    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println("\nO jogo ainda não já foi iniciado.\n");
            return;
        }
        System.out.printf("\nO jogo atualmente se encontra no status %s\n", board.getStatus().getLabel());
        if (board.hasErrors()) {
            System.out.println("O jogo contém erros\n");
        } else {
            System.out.println("O jogo não contém erros\n");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("\nO jogo ainda não já foi iniciado.\n");
            return;
        }

        String confirm = "";
        while (!confirm.equalsIgnoreCase("s") && !confirm.equalsIgnoreCase("n")) {
            System.out.print("\nDeseja reiniciar o jogo? [s-'Sim'| n-'Não'] ");
            confirm = scan.next().toLowerCase();
        }

        if (!confirm.equalsIgnoreCase("s") && !confirm.equalsIgnoreCase("n")) {
            System.out.println("Opção inválida. Digite 's' para sim e 'n' para não");
        }

        if (confirm.equalsIgnoreCase("s")) {
            System.out.println("\nO jogo foi resetado!\n");
            board.reset();
        }

    }

    private static void finishGame() {
        if (isNull(board)) {
            System.out.println("\nO jogo ainda não já foi iniciado.\n");
            return;
        }

        if (board.gameIsFinished()) {
            System.out.println("Parabéns, você concluiu o jogo\n");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contem erro, verifique seu 'board' e ajuste-o\n");
        } else {
            System.out.println("Você ainda precisa preencher algum espaço\n");
        }

    }

    private static int runUntilGetValidNumber(final int min, final int max) {
        var current = scan.nextInt();
        while (current < min || current > max) {
            System.out.printf("Informe um número entre %s e %s\n", min, max);
            current = scan.nextInt();
        }

        return current;
    }

}
