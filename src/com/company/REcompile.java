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
            System.out.println(i + " " + c.get(i) + " " + n1.get(i) + " " + n2.get(i));
        }
    }

    private static int expression() throws ParseException {
        int r;

        r = term();
        if(isVocab(p.charAt(j)) || p.charAt(j) == '(')
            expression();
        return r;
    }

    private static int term() throws ParseException {

        int result, term1, term2, finalState;

        finalState = state - 1;
        result=term1=factor();
        if(p.charAt(j) == '*') {
            setState(state, ' ', state +1, term1);
            if (finalState > 0) {
                n1.set(finalState - 1, state);
                n2.set(finalState - 1, state);
            } else {
                n1.set(finalState, state);
                n2.set(finalState, state);
            }
            j++;
            state++;
            return state -1;
        }
        if(p.charAt(j) == '+') {
            // build the branching machine
            setState(state, ' ', term1, state + 1);
            j++;
            state++;
            // build the next state
            setState(state, p.charAt(j), state + 1, state + 1);
            if (finalState > 0) {
                n1.set(finalState - 1, state - 1);
            } else {
                n1.set(finalState, state-1);
                n1.set(term1-1, state + 1);
                n2.set(term1-1, state + 1);
                j++;
                state++;
                return state - 2;
            }
            n1.set(term1-1, state + 1);
            n2.set(term1-1, state + 1);
            j++;
            state++;
            return state -1;
        }
        return result;
    }

    private static int factor() throws ParseException {


        if(isVocab(p.charAt(j))) {
            setState(state, p.charAt(j), state + 1, state + 1);
            state++;
            j++;
        }
        else {
            if (p.charAt(j) == '(') {
                j++;
                int r = expression();
                if (p.charAt(j) == ')') {
                    j++;
                    return r;
                }
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
            j++;
            initial = expression();
            // the dummy start state
            setState(0, ' ', initial, initial);
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

        // the finishing state
        setState(state, ' ', 0, 0);
    }

    // Checks if the element being read is a literal character
    private static boolean isVocab(char element) {
        return element != '(' && element != ')' && element != '*' && element != '+' && element != '\"';
    }

    /*
    TODO: Fix this so indexes at correct position
     */
    private static void setState(int s, char ch, int next1, int next2) {

        // check if state is within arrayList range
        if (s < c.size()) {
            c.add(s, ch);
            n1.add(s, next1);
            n2.add(s, next2);
        } else {
            // if not, tack new state on to the end of the arrayList
            c.add(ch);
            n1.add(next1);
            n2.add(next2);
        }

    }

    private static void initialise() {
        c = new ArrayList<>();
        n1 = new ArrayList<>();
        n2 = new ArrayList<>();
        state = 1; // leave state 0 to be dummy start state
        // String searcher knows to where to start by reading from this start
    }
}

