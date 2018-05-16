package com.company;

import java.text.ParseException;

public class REcompile {

    private static String p;
    private static int j;

    public static void main(String[] args) {

        try {
            j = 0;
            p = "\"a**bc(a+b)d\"";
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

    private static boolean isVocab(char element) { return element != '(' && element != ')' && element != '*' && element != '+' && element != '\"'; }
}

