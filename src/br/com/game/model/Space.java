package br.com.game.model;

public class Space {

    // Atributos
    private Integer actual;
    private final int expected;
    private final boolean fixed;

    // Construtor
    public Space(int expected, boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if (fixed) {
            actual = expected;
        }
    }

    // Metodos Getters
    public Integer getActual() {
        return actual;
    }

    public void setActual(final Integer actual) {
        if (fixed)
            return;
        this.actual = actual;
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void clearSpace() {
        setActual(null);
    }

}
