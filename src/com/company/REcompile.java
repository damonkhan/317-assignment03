package com.company;

import com.sun.xml.internal.bind.v2.TODO;

import java.text.ParseException;

public class REcompile {

    private static String p;
    private static int j;
    private static char[] c;
    private static int[] n1;
    private static int[] n2;
    private static int currState; // the current state being built

    public static void main(String[] args) {

        try {

            j = 0;
            p = "\"(a*b+ac)d\""; // the regexp
            initialise(); // initialise arrays and state
            parse();

        } catch (ParseException ex) {
            System.err.println(ex);
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
            if(n1[f] == n2[f])
                n2[f] = currState;
            n1[f] = currState;
            f = currState - 1;
            j++;
            r = currState;
            currState++;
            t2 = term();
            setState(r, ' ', t1, t2);
            if (n1[f] == n2[f])
                n2[f] = currState;
            n1[f] = currState;
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
       c[s] = ch;
       n1[s] = next1;
       n2[s] = next2;

    }

    /*
    TODO: find way to make this method dynamic
    TODO: or let size arg be passed to method.
     */
    private static void initialise() {
        c = new char[100];
        n1 = new int[100];
        n2 = new int[100];
        currState = 0;
    }
}

