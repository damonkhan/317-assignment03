package com.company;

public class State {
    private int state;
    private char ch;
    private int n1, n2;

    public State(int state, char ch,  int n1, int n2) {
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

    public int getN1() {
        return n1;
    }

    public int getN2() {
        return n2;
    }
}
