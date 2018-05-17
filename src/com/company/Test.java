package com.company;

public class Test {
     public static void main(String[] args) {

         if (args.length != 1) {
             System.out.println("err");
             return;
         }
         String p = args[0];
         System.out.println(p);
     }

}
