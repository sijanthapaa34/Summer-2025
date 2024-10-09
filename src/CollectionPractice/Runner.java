package CollectionPractice;

public class Runner {
    public static void main(String[] args) {
//        Linkedlist list = new Linkedlist();
//        list.insert(6);
//        list.insert(45);
//        list.insert(55);
//        list.show();
//        Stack stack = new Stack(5);
//        stack.push(2);
//        stack.push(5);
//        stack.push(8);
//        stack.push(9);
//        stack.push(10);
//
//        stack.show();

        Queue q = new Queue(7);
        q.enQueue(5);
        q.enQueue(2);
        q.enQueue(7);
        q.enQueue(3);
        q.enQueue(9);
        q.enQueue(1);
//        q.enQueue(2);
//        q.enQueue(7);
//        q.enQueue(3);
//        q.enQueue(9);
//        q.enQueue(1);

      //q.deQueue();
       //System.out.println(q.isFull());
        q.show();
    }
}
