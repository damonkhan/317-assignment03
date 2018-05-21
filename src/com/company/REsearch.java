package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class REsearch {
    static int arraySize = 20;
    public static void main(String[] args) {
        try
        {
            if(args.length != 1)
            {
                System.out.println("Not enough arguments passed, need a filename to read");
            }

            DEqueue search = new DEqueue();
            String[] data = new String[arraySize];
            InputStreamReader isRead = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isRead);
            State scan = new State(-1,  '/', -1,-1);
            String filename = "/home/lwc7/comp317/Assignment3/"+args[0];
            FileReader fr = new FileReader(filename);
            BufferedReader b = new BufferedReader(fr);
            String line;

            search.put(scan);
            String in;
            while((in = br.readLine()) != null)
            {
                if(in.compareTo("end") == 0)
                {
                    break;
                }
                int index = Integer.parseInt(""+in.charAt(0));
                char c = in.charAt(2);
                State s = new State(index, c, Integer.parseInt(""+in.charAt(4)), Integer.parseInt(""+in.charAt(6)));
                if(index == 0) {
                    search.push(s);
                }
                if(index == arraySize)
                {
                    data = expandArray(data);
                }
                data[index] = in;
            }
            int pos = 0;
            char chr;
            while((line = b.readLine())!=null)
            {
                DEqueue.Node tmp;
                tmp = search.pop();
                State nxt1 = new State(tmp.data.getN1(), data[tmp.data.getN1()].charAt(0), Integer.parseInt(""+data[tmp.data.getN1()].charAt(2)), Integer.parseInt(""+data[tmp.data.getN2()].charAt(3)));
                State nxt2 = new State(tmp.data.getN2(), data[tmp.data.getN2()].charAt(0), Integer.parseInt(""+data[tmp.data.getN1()].charAt(2)), Integer.parseInt(""+data[tmp.data.getN2()].charAt(3)));
                if(pos < line.length())
                {
                    chr = line.charAt(pos);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
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
