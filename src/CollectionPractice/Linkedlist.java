package CollectionPractice;

import org.w3c.dom.NodeList;

public class Linkedlist {
    Node head;
    public void insert(int data){
        Node node = new Node();
        node.data = data;
        node.next = null;
        if(head == null){
            head = node;
        }
        else{
            Node n = head;
            while(n.next!= null){ //loop continues until it finds the last node
                n= n.next; // Move to the next node
            }
            n.next = node; //Once the last node is found, set its 'next' pointer to the new node
        }
    }
    public void show(){
        Node node = head;
        while (node.next != null){
            System.out.println(node.data);
            node = node.next;
        }
        System.out.println(node.data);
    }
}
