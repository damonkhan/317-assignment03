package com.company;


public class DEqueue {
    int size = 0;
    Node head = null;
    Node tail = null;

    class Node
    {
        Node prev, next;
        State data;

        public Node(State d)
        {
            data = d;
            prev = null;
            next = null;
        }
    }
    //add an item to the head of the queue
    public void push(State d)
    {
        Node n = new Node(d);
        //if list is empty, add a head
        if(size == 0)
        {
            this.head = n;
            size++;
            return;
        }
        //else change head to equal new node
        Node tmp = head;
        tmp.prev = n;
        head = n;
        head.next = tmp;
        size++;
    }

    public void put(State d)
    {
        Node n = new Node(d);
        //if list is empty then add a new tail and make head point to tail also
        if(size == 0)
        {
            tail = n;
            head = tail;
            size++;
            return;
        }
        //otherwise, add a tail to the end of the list
        Node tmp = tail;
        tail = n;
        tmp.next = tail;
        tail.prev = tmp;
        size++;
    }

    //removes the first item in the list
    public Node pop()
    {
        Node val = head;
        if(head == null)
        {
            System.err.println("cannot remove head, it is null");
            return null;
        }
        head = head.next;
        head.prev = null;
        return val;
    }

    //prints out the head of the queue
    public void printHead()
    {
        Node cu = head;
        while(cu.next!=null)
        {
            System.out.println((""+cu.data.getState()) + (""+cu.data.getCh()) + (""+cu.data.getN1()) + (""+cu.data.getN2()));
            cu = cu.next;
        }
        //System.out.println(head.data);
    }

    //prints out the tail of the queue
    public void printTail() {
        System.out.println(tail.data);
    }
}

