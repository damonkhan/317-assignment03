package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class REsearch {
    static int arraySize = 20;
    static DEqueue search = new DEqueue();
    static String[] data = new String[arraySize];
    static State s;
    static int pos = 0;
    public static void main(String[] args) {
        try
        {
            if(args.length != 1)
            {
                System.out.println("Not enough arguments passed, need a filename to read");
            }

            //declerations for file handling, io, etc
            InputStreamReader isRead = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isRead);
            State scan = new State(-1,  '/', -1,-1);
            //filepath
            String filename = "D:/Intellij/317assignment03/src/com/company/"+args[0];
            FileReader fr = new FileReader(filename);
            BufferedReader b = new BufferedReader(fr);
            String line;
            search.put(scan);
            int lineNum = 0;
            boolean matchFound;


            int incrementer;
            while((line = b.readLine())!=null) {
                matchFound = false;
                //System.out.println("Line: " + lineNum);
                lineNum++;
                //used to remember last start position of search to ensure no patterns are missed
                incrementer = 0;
                while (incrementer < line.length()) {
                    initialise(br, incrementer);
                    pos = incrementer;
                    DEqueue.Node tmp;
                    tmp = search.pop();
                    State nxt1,nxt2;
                        String d = data[tmp.data.getN1()];
                        String[] val = d.split(" ");

                        //parses in a state array of values
                        if (data[tmp.data.getN1()].charAt(2) == ' ') {
                            nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
                        } else {
                            nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
                        }
                        if (data[tmp.data.getN2()].charAt(2) == ' ') {
                            nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
                        } else {
                            nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
                        }
                        search.push(nxt1);
                        if (!((nxt1.getN1() == nxt2.getN1()) && (nxt1.getN2() == nxt1.getN2()))) {
                            search.push(nxt2);
                        }
                    while ((search.size > 1)) {
                        //while the SCAN node isn't at the head
                        while ((search.peek().data.getN1() != -1) && (search.peek().data.getN2() != -1)) {
                             if(tmp.data.getCh() == '.')
                             {
                                 handleWildcard(tmp);
                             }
                            tmp = search.pop();
                            if (tmp.data.getN1() == 0 && tmp.data.getN2() == 0) {
                                if(!matchFound) {
                                    System.out.println("End state reached, match found!");
                                }
                                matchFound = true;
                                //return;
                            }
                            //returnObject just to tidy up some messy code
                            returnObject p = parseValues(pos, line, tmp, nxt1, nxt2);
                            line = p.str;
                            pos = p.s;
                            tmp = p.node;
                            nxt1 = p.s1;
                            nxt2 = p.s2;
                        }
                       /* if(matchFound = true)
                        {
                            break;
                        }*/
                        if (search.size == 1) {
                            //System.out.println("List is empty, no match found");
                            incrementer++;
                        } else {
                            //this will always be the scan item
                            tmp = search.pop();
                            //put scan item at the end of the list, giving us more possible current states to work with
                            search.put(scan);
                        }

                    }
                    /*if(matchFound == true) {
                        matchFound = false;
                        break;*/
                    }
                }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }
    //fills an array with all items
    private static void initialise(BufferedReader b, int i)
    {
        try
        {
            String in;
            if(i == 0)
            {
                if(data[0] != null)
                {
                    search.push(s);
                }
                else {
                    while ((in = b.readLine()) != null) {
                        if (in.compareTo("end") == 0) {
                            break;
                        }
                        //String d = in;
                        String[] val = d.split(" ");
                        int index = Integer.parseInt(val[0]);
                        char c = in.charAt(2);
                        if(c == ' ')
                        {
                            s = new State(index, c, Integer.parseInt(val[3]), Integer.parseInt(val[4]));
                        }
                        else {
                            s = new State(index, c, Integer.parseInt(val[2]), Integer.parseInt(val[3]));
                        }
                        if (index == 0) {
                            search.push(s);
                        }
                        if (index == arraySize) {
                            data = expandArray(data);
                        }
                        data[index] = in;

                    }
                }
            }
            else
            {
                search.push(s);
            }

        }
        catch(Exception e)
        {
            System.err.println("ERROR in initialisation");
        }

    }

    private static void handleWildcard(DEqueue.Node tmp)
    {
        String d = data[tmp.data.getN1()];
       String[] val = d.split(" ");
       State nxt1,nxt2;

      if (data[tmp.data.getN1()].charAt(2) == ' ') {
          nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
      } else {
          nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
      }
      if (data[tmp.data.getN2()].charAt(2) == ' ') {
          nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
      } else {
          nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
      }
      search.push(nxt1);
      search.push(nxt2);
      pos++;
    }
    private static String[] expandArray(String[] d)
    {
        String[] tmp = new String[d.length+arraySize];
        for(int i = 0; i<d.length; i++)
        {
            tmp[i]=d[i];
        }
        return tmp;
    }
    //method to parse the input from array, also deals with double digits
    private static returnObject parseValues(int pos, String line, DEqueue.Node tmp, State nxt1, State nxt2)
    {
        if (pos < line.length()) {
            if (tmp.data.getCh() == ' ') {
                String d = data[tmp.data.getN1()];
                String[] val = d.split(" ");
                if(data[tmp.data.getN1()].charAt(2) == ' ') {
                    nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
                }
                else
                {
                    nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
                }
                d = data[tmp.data.getN2()];
                val = d.split(" ");
                if(data[tmp.data.getN2()].charAt(2) == ' ') {
                    nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
                }
                else
                {
                    nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
                }
                search.push(nxt1);
                search.push(nxt2);
            }
            if (tmp.data.getCh() == line.charAt(pos)) {
                String d = data[tmp.data.getN1()];
                String[] val = d.split(" ");
                if(data[tmp.data.getN1()].charAt(2) == ' ') {
                    nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
                }
                else
                {
                    nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
                }
                d = data[tmp.data.getN2()];
                val = d.split(" ");
                if(data[tmp.data.getN2()].charAt(2) == ' ') {
                    nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[3]), Integer.parseInt(val[4]));
                }
                else
                {
                    nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt(val[2]), Integer.parseInt(val[3]));
                }
                search.put(nxt1);
                search.put(nxt2);
                pos++;
            }

        }
        returnObject r = new returnObject(pos, line, tmp, nxt1, nxt2);
        return r;
    }
    //used to return multiple values from the parse method
    private static class returnObject
    {
        private int s;
        private String str;
        private DEqueue.Node node;
        private State s1;
        private State s2;

        public int getS() {
            return s;
        }

        public String getStr() {
            return str;
        }

        public DEqueue.Node getNode() {
            return node;
        }

        public State getS1() {
            return s1;
        }

        public State getS2() {
            return s2;
        }

        public returnObject(int s, String str, DEqueue.Node node, State s1, State s2) {
            this.s = s;
            this.str = str;
            this.node = node;
            this.s1 = s1;
            this.s2 = s2;
        }

    }
    /*
    TODO: Create Deque class to hold all possible current states
    TODO: and possible next states
     */
}
