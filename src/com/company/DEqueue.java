package com.company;


public class DEqueue {
    int size = 0;
    Node head = null;
    Node tail = null;

    class Node
    {
        Node prev, next;
        String data;

        public Node(String d)
        {
            data = d;
            prev = null;
            next = null;
        }
    }
    //add an item to the head of the queue
    public void addToHead(String d)
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

    public void addToTail(String d)
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
    public void removeHead()
    {
        if(head == null)
        {
            System.err.println("cannot remove head, it is null");
            return;
        }
        head = head.next;
        head.prev = null;
    }

    //removes the last item in the list
    public void removeTail()
    {
        if(tail == null)
        {
            System.err.println("Cannot remove tail, it is null");
        }
        tail = tail.prev;
        tail.next = null;
    }

    //prints out the head of the queue
    public void printHead()
    {
        System.out.println(head.data);
    }

    //prints out the tail of the queue
    public void printTail()
    {
        System.out.println(tail.data);
    }
}