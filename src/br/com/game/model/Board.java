package br.com.game.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static br.com.game.model.GameStatusEnum.COMPLETE;
import static br.com.game.model.GameStatusEnum.INCOMPLETE;
import static br.com.game.model.GameStatusEnum.NON_STARTED;

public class Board {

    // Atributos
    private final List<List<Space>> spaces;

    // Construtor
    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    // Metodo Get
    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))) {
            return NON_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getActual())) ? INCOMPLETE : COMPLETE;
    }

    // Metodo de erros
    public boolean hasErrors() {
        if (getStatus() == NON_STARTED) {
            return false;
        }

        // Iteração com a linha
        // Verificação se existe valor duplicado em cada linha
        for (int i = 0; i < 9; i++) {
            Set<Integer> numerosLinha = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                Integer valor = spaces.get(i).get(j).getActual();
                // Erro de duplicação na linha
                if (nonNull(valor) && !numerosLinha.add(valor)) {
                    return true;
                }
            }
        }

        // Verificação de existe valor duplicado em cada coluna
        for (int i = 0; i < 9; i++) {
            Set<Integer> numerosColuna = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                // Erro de duplicação na coluna
                Integer valor = spaces.get(i).get(j).getActual();
                if (nonNull(valor) && !numerosColuna.add(valor)) {
                    return true;
                }
            }
        }

        // Verificação de duplicidade em cada subgrid 3x3
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Set<Integer> numeros = new HashSet<>();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        Integer valor = spaces.get(row * 3 + i).get(col * 3 + j).getActual();
                        // Erro de duplicação no subgrid 3x3
                        if (nonNull(valor) && !numeros.add(valor)) {
                            return true;
                        }
                    }
                }
            }
        }

        // Verificar valor preenchido
        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual()) && s.getActual().intValue() != s.getExpected());
    }

    // Metodo para mudar valor
    public boolean changeValue(final int col, final int row, final int value) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.setActual(value);
        return true;
    }

    // Metodo para limpar valor
    public boolean clearValue(final int col, final int row) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }
        space.clearSpace();
        return true;
    }

    // Metodo de reset
    public void reset() {
        spaces.forEach(col -> col.forEach(Space::clearSpace));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus().equals(COMPLETE);
    }

}
