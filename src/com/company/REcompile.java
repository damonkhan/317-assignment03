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

    //TODO: 4. implement + operator
    //TODO: 5. implement ? operator
    //TODO: 6. implement escaped operators '\'
    //TODO: 7. abstract out operator rules into seperate methods

    public static void main(String[] args) {

        try {

            j = 0; // pos in regexp
            p = args[0]; // the regexp
            initialise(); // initialise arrays and state
            parse();
            System.out.println(p); // print out RE
            print();

        } catch (ParseException ex) {
            System.err.println(ex);
        }
    }

    // prints out the FSM
    private static void print() {
        for (int i = 0; i < c.size(); i++) {
            System.out.println(i + " " + c.get(i) + " " + n1.get(i) + " " + n2.get(i));
        }
    }

    private static int expression() throws ParseException {
        int r;

        r = term();

        if (j >= p.length()) {
            return r;
        }
        if(isVocab(p.charAt(j)) || p.charAt(j) == '(' || p.charAt(j) == '|')
            expression();
        return r;
    }

    private static int term() throws ParseException {
        int result, term1, term2, finalState;

        finalState = state - 1;

        result=term1=factor();

        if (j >= p.length()) {
            return state -1;
        }

        if(p.charAt(j) == '*') {
            setState(state, ' ', term1, state +1);
            fixNext(n1, finalState, state);
            fixNext(n2, finalState, state);
            j++;
            state++;
            return state -1;
        }

        if (p.charAt(j) == '+') {
            setState(state, ' ', term1, state + 1);
            fixNext(n1, finalState, state);
            fixNext(n2, finalState, state);
            j++;
            state++;
            return state - 2;
        }

        if(p.charAt(j) == '|') {
            n2.set(finalState, state);
            // build the branching machine
            setState(state, ' ', term1 ,state + 1);
            j++;
            state++;
            // build the next state
            setState(state, p.charAt(j), state + 1, state + 1);

            if (finalState == 0) {
                n1.set(finalState, state-1);
                n1.set(term1, state + 1);
                n2.set(term1, state + 1);
                j++;
                state++;
                return state - 2;
            }
            n1.set(term1, state + 1);
            n2.set(term1, state + 1);
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
            // dummy start state
            setState(0, ' ', 0, 0);

            initial = expression();

            // set dummy start state to point to start state of FSM
            n1.set(0, initial);
            n2.set(0, initial);
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

    // reconfigure n1 and n2 for a given state so that the
    // state is pointing to the correct next state.
    public static void fixN1andN2(int finalState, int pos) {
        if (finalState == 0 || finalState == 1) {
            n1.set(finalState, pos);
            n2.set(finalState, pos);
        } else {
            n1.set(finalState - 1, pos);
            n2.set(finalState - 1, pos);
        }
        j++;
        state++;
    }

    public static void fixNext(ArrayList<Integer> list, int finalState, int pos) {
        if (finalState == 0 || finalState == 1)
            list.set(finalState, pos);
        else
            list.set(finalState - 1, pos);
    }


    private static void initialise() {
        c = new ArrayList<>();
        n1 = new ArrayList<>();
        n2 = new ArrayList<>();
        // leave state 0 to be dummy start state
        // String searcher knows where to start by reading from this start
        state = 1;

    }
}

