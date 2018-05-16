package com.company;

public class REcompile {

    private static String p;
    private static int j;

    public static void main(String[] args) {

        p = "hello";
    }

   private static void expression() {
        term();
        if(isVocab(p.charAt(j)) || p.charAt(j) == '(')
            expression();
    }

    private static void term() {
        factor();
        if(p.charAt(j) == '*')
            j++;
        if(p.charAt(j) == '+') {
            j++;
            term();
        }
    }

    private static void factor() {
        if(isVocab(p.charAt(j)))
            j++;
        else {
            if (p.charAt(j) == '(') {
                j++;
                expression();
                if (p.charAt(j) == ')')
                    j++;
                else
                    System.err.println("Unbalanced brackets - not W.F. expression.");
            }
            else {
                System.err.println("Not W.F. expression.");
            }
        }
    }

    private static void parse() {
        expression();
        if (p.charAt(j) != '\"')
            System.err.println("Error - expression not part of language.");
    }

    private static boolean isVocab(char element) {
        return true;
    }
}

