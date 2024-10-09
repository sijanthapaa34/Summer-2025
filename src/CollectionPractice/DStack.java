package CollectionPractice;

public class DStack {
    int stack[];
    int top= 0;
    int capacity = 2;



    public void push (int data){
        if(size()==capacity){
            expand();

            stack[top] = data;
            top++;
        }
    }

    private void expand() {
        int length = size();
        int newStack[]= new int [capacity*2];
        System.arraycopy(stack, 0, newStack, 0, length);
        stack = newStack;
        capacity *= 2;
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
