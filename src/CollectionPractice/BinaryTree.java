package CollectionPractice;
class Node1{
    int data;
    Node1 left;
    Node1 right;

    public Node1(int data) {
        this.data = data;
    }
}
public class BinaryTree {
    Node1 root;
    public void insert(int data) {
            root = insertRec(root,data);

    }

    public Node1 insertRec(Node1 root, int data){
        if (root == null) {
            root = new Node1(data);
        }else if(data< root.data) {
            root.left = insertRec(root.left, data);

        }
        else if (data > root.data)
        {
            root.right = insertRec(root.right, data);
        }
        return root;
    }
public void inorder(){
        inorderRec(root);
}
    public void inorderRec(Node1 root){
        if(root != null){
            inorderRec(root.left);
            System.out.println(root.data+ "");
            inorderRec(root.right);
        }
    }
    public void preorder(){
        preorderRec(root);
    }
    public void preorderRec(Node1 root){
        if(root != null){
            System.out.println(root.data+ "");
            preorderRec(root.left);
            inorderRec(root.right);
        }
    }
        public void postorder(){
           postorderRec(root);
        }
        public void postorderRec(Node1 root){
            if(root != null){
                System.out.println(root.data+ "");
                preorderRec(root.left);
                inorderRec(root.right);
            }
    }
}



