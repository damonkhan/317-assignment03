package com.company;

public class State {
    private int state;
    private char ch;
    private State n1, n2;

    public State(int state, char ch,  State n1, State n2) {
        this.state = state;
        this.ch = ch;
        this.n1 = n1;
        this.n2 = n2;
    }

    public int getState() {
        return state;
    }

    public char getCh() {
        return ch;
    }

    public State getN1() {
        return n1;
    }

    public State getN2() {
        return n2;
    }
}
