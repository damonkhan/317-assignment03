package com.company;

import com.sun.xml.internal.bind.v2.TODO;

import java.text.ParseException;
import java.util.ArrayList;

public class REcompile {

    private static String p;
    private static int j;
    private static ArrayList<Character> c;
    private static ArrayList<Integer> n1;
    private static ArrayList<Integer> n2;
    private static int currState; // the current state being built

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

        f = currState-1;
        r =t1=factor();
        if(p.charAt(j) == '*')
            setState(currState, ' ', currState+1, t1);
            j++;
            r=currState;
            currState++;
        if(p.charAt(j) == '+') {
            if(n1.get(f) == n2.get(f))
                n2.set(f, currState);
            n1.set(f, currState);
            f = currState - 1;
            j++;
            r = currState;
            currState++;
            t2 = term();
            setState(r, ' ', t1, t2);
            if (n1.get(f) == n2.get(f))
                n2.set(f, currState);
            n1.set(f, currState);
        }
        return r;
    }

    /*
    TODO: check why n1 and n2 are not being to correct position.
     */
    private static int factor() throws ParseException {
        int r = 0;

        if(isVocab(p.charAt(j))) {
            setState(currState, p.charAt(j),currState+1, currState+1);
            j++;
            r = currState;
            currState++;
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
        return r;
    }

    private static void parse() throws ParseException {
        int initial;
        if (p.charAt(j) == '\"') {
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
        setState(currState, ' ', 0, 0);
    }

    private static boolean isVocab(char element) {
        return element != '(' && element != ')' && element != '*' && element != '+' && element != '\"';
    }

    private static void setState(int s, char ch, int next1, int next2) {
       c.add(ch);
       n1.add(next1);
       n2.add(next2);

    }

    /*
    TODO: find way to make this method dynamic
    TODO: or let size arg be passed to method.
     */
    private static void initialise() {
        c = new ArrayList<>();
        n1 = new ArrayList<>();
        n2 = new ArrayList<>();
        currState = 0;
    }
}

