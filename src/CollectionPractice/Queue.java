package CollectionPractice;

public class Queue {
    int queue[];
    int size;
    int front;
    int rear;

    public Queue(int size) {
        queue = new int[size];
    }

    public void enQueue(int data){
        if(isFull()){
            System.out.println("queue is full");
    }
        else{
            queue[rear]= data;    //making circular array in queue by mod(%)
            rear = (rear+1)%queue.length;
            size++;
        }
     }
     public int deQueue(){
         int data = queue[front];
        if(!isEmpty()){
         front= (front+1)%queue.length;
         size--;
        }
        else {
            System.out.println("empty queue");
        }
        return data;
     }
     public void show(){
         for(int i = 0; i<size; i++){
             System.out.print(queue[(front+i)%queue.length]+" ");
         }
//         System.out.println();
//         for(int n : queue){
//             System.out.print(n+ " ");
//         }
     }
     public int getSize(){
         return size;
     }
     public boolean isEmpty(){
         return size == 0;
     }

    public boolean isFull() {
        return  size == queue.length;
    }
}
