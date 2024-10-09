package CollectionPractice;

public class Stack {
    int stack[];
    int top= 0;
    int size;

    public Stack(int size) {
        this.size = size;
        stack = new int[size];
    }

    public void push (int data){
        if(this.size<=5){
            System.out.println("Stack is full");
        }else {
            stack[top] = data;
            top++;
        }
    }
    public int pop(){
        int data=0;
        if(top == 0){
            System.out.println("No element");
        }else {
            top--;
            data = stack[top];
            stack[top] = 0;
        }
        return data;
    }
    public int peek(){
        int data;
        data = stack[top-1];
        return data;
    }
    public int size(){
        return top;
    }
    public boolean isEmpty(){
         return top<=0;
    }
    public void show(){
        for(int n : stack){
            System.out.println(n);
        }
    }
}
