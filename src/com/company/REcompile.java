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
            p = "\"a*bc(a+b)d\"";
            initialise(); // initialise arrays and state
            parse();

        } catch (ParseException ex) {
            System.err.println(ex);
        }
    }

    private static void expression() throws ParseException {
        term();
        if(isVocab(p.charAt(j)) || p.charAt(j) == '(')
            expression();
    }

    private static void term() throws ParseException {
        factor();
        if(p.charAt(j) == '*')
            j++;
        if(p.charAt(j) == '+') {
            j++;
            term();
        }
    }

    private static void factor() throws ParseException {
        if(isVocab(p.charAt(j)))
            j++;
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
    }

    private static void parse() throws ParseException {
        if (p.charAt(j) == '\"') {
            j++;
            expression();
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
    }

    private static boolean isVocab(char element) {
        return element != '(' && element != ')' && element != '*' && element != '+' && element != '\"';
    }

    private static int setState(int s, char ch, int next1, int next2) {
       c[s] = ch;
       n1[s] = next1;
       n2[s] = next2;

       return currState = s + 1;
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

