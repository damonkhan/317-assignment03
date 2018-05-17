package com.company;

import java.text.ParseException;
import java.util.ArrayList;

public class REcompile {

    private static String p;
    private static int j;
    private static ArrayList<Character> c;
    private static ArrayList<Integer> n1;
    private static ArrayList<Integer> n2;
    private static int state; // the next state to be built

    public static void main(String[] args) {

        try {

            j = 0;
            p = args[0]; // the regexp
            initialise(); // initialise arrays and state
            parse();
            print();

        } catch (ParseException ex) {
            System.err.println(ex);
        }
    }

    private static void print() {
        for (int i = 0; i < c.size(); i++) {
            System.out.println(c.get(i) + " " + n1.get(i) + " " + n2.get(i));
        }
    }

    private static int expression() throws ParseException {
        int r;

        r = term();
        if(isVocab(p.charAt(j)) || p.charAt(j) == '(')
            expression();
        return r;
    }

    /*
    TODO: check why n1 and n2 are not being to correct position.
     */
    private static int term() throws ParseException {

        int r, t1, t2, f;

        f = state -1;
        r =t1=factor();
        if(p.charAt(j) == '*')
            setState(state, ' ', state +1, t1);
            j++;
            r= state;
            state++;
        if(p.charAt(j) == '+') {
            if(n1.get(f) == n2.get(f))
                n2.set(f, state);
            n1.set(f, state);
            f = state - 1;
            j++;
            r = state;
            state++;
            t2 = term();
            setState(r, ' ', t1, t2);
            if (n1.get(f) == n2.get(f))
                n2.set(f, state);
            n1.set(f, state);
        }
        return r;
    }

    /*
    TODO: check why n1 and n2 are not being to correct position.
     */
    private static int factor() throws ParseException {

        if(isVocab(p.charAt(j))) {
            setState(state, p.charAt(j), state +1, state + 1);
            state++;
            j++;
        }
        else {
            if (p.charAt(j) == '(') {
                j++;
                expression();
                if (p.charAt(j) == ')')
                    j++;
                else {
                    System.err.println("Unbalanced brackets - Expression is not well formed.");
                    throw new ParseException(p, j);
                }
            }
            else {
                System.err.println("Expression is not well formed.");
                throw new ParseException(p, j);
            }
        }
        return state-1;
    }

    private static void parse() throws ParseException {
        int initial;
        if (p.charAt(j) == '\"') {
            setState(0, '\"', state, state);
            j++;
            initial = expression();
        } else {
            System.err.println("Expression is not well formed.");
            throw new ParseException(p, j);
        }

        if (p.charAt(j) == '\"') {
            System.out.println("Expression is well formed.");
        } else {
            System.err.println("Error - Expression not part of language.");
            throw new ParseException(p, j);
        }
        setState(state, ' ', 0, 0);
    }

    private static boolean isVocab(char element) {
        return element != '(' && element != ')' && element != '*' && element != '+' && element != '\"';
    }

    private static void setState(int s, char ch, int next1, int next2) {
       c.add(ch);
       n1.add(next1);
       n2.add(next2);

    }

    private static void initialise() {
        c = new ArrayList<>();
        n1 = new ArrayList<>();
        n2 = new ArrayList<>();
        state = 1;
    }
}

