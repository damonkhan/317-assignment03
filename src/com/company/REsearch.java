package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class REsearch {
    static int arraySize = 20;
    static DEqueue search = new DEqueue();
    static String[] data = new String[arraySize];
    static State s;
    public static void main(String[] args) {
        try
        {
            if(args.length != 1)
            {
                System.out.println("Not enough arguments passed, need a filename to read");
            }


            InputStreamReader isRead = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isRead);
            State scan = new State(-1,  '/', -1,-1);
            String filename = "D:/Intellij/317assignment03/src/com/company/"+args[0];
            FileReader fr = new FileReader(filename);
            BufferedReader b = new BufferedReader(fr);
            String line;
            search.put(scan);
            int lineNum = 0;
            boolean matchFound = false;

            int pos = 0;
            int incrementer = 0;
            while((line = b.readLine())!=null) {
                matchFound = false;
                System.out.println("Line: " + lineNum);
                lineNum++;
                incrementer = 0;
                while (incrementer < line.length()) {
                    initialise(br, incrementer);
                    pos = incrementer;
                    DEqueue.Node tmp;
                    tmp = search.pop();
                    State nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt("" + data[tmp.data.getN1()].charAt(4)), Integer.parseInt("" + data[tmp.data.getN1()].charAt(6)));
                    State nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt("" + data[tmp.data.getN2()].charAt(4)), Integer.parseInt("" + data[tmp.data.getN2()].charAt(6)));
                    search.push(nxt1);
                    if (!((nxt1.getN1() == nxt2.getN1()) && (nxt1.getN2() == nxt1.getN2()))) {
                        search.push(nxt2);
                    }
                    while ((search.size > 1)) {
                        //while the SCAN node isn't at the head
                        while ((search.peek().data.getN1() != -1) && (search.peek().data.getN2() != -1)) {
                            tmp = search.pop();
                            if (tmp.data.getN1() == 0 && tmp.data.getN2() == 0) {
                                if(matchFound == false) {
                                    System.out.println("End state reached, match found!");
                                }
                                matchFound = true;
                                //return;
                            }
                            if (pos < line.length()) {
                                if (tmp.data.getCh() == ' ') {
                                    nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt("" + data[tmp.data.getN1()].charAt(4)), Integer.parseInt("" + data[tmp.data.getN1()].charAt(6)));
                                    nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt("" + data[tmp.data.getN2()].charAt(4)), Integer.parseInt("" + data[tmp.data.getN2()].charAt(6)));
                                    search.push(nxt1);
                                    search.push(nxt2);
                                }
                                if (tmp.data.getCh() == line.charAt(pos)) {
                                    nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(2), Integer.parseInt("" + data[tmp.data.getN1()].charAt(4)), Integer.parseInt("" + data[tmp.data.getN1()].charAt(6)));
                                    nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(2), Integer.parseInt("" + data[tmp.data.getN2()].charAt(4)), Integer.parseInt("" + data[tmp.data.getN2()].charAt(6)));
                                    search.put(nxt1);
                                    search.put(nxt2);
                                    pos++;
                                }

                            }
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
                        int index = Integer.parseInt("" + in.charAt(0));
                        char c = in.charAt(2);
                        s = new State(index, c, Integer.parseInt("" + in.charAt(4)), Integer.parseInt("" + in.charAt(6)));
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
    private static String[] expandArray(String[] d)
    {
        String[] tmp = new String[d.length+arraySize];
        for(int i = 0; i<d.length; i++)
        {
            tmp[i]=d[i];
        }
        return tmp;
    }

    /*
    TODO: Create Deque class to hold all possible current states
    TODO: and possible next states
     */
}
