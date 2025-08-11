package Practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

class Product implements Comparable<Product>{
    String name;
    double price;
    Category category;

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name='" + name +
                ", price=" + price +
                ", category=" + category;
    }

    @Override
    public int compareTo(Product that) {
       return Double.compare(this.price, that.price); //ascending order
        //that.price, this.price //descending order
    }
}
enum Category{
    ELECTRONICS, CLOTHING, GROCERY;
}
interface ProductFilter{
    boolean filter(Product product);
}
class ProductManager{
    ArrayList<Product> product = new ArrayList<>();
    public void addProduct(String name, double price , Category category){
        product.add(new Product(name , price, category));
    }
    public void removeproduct(String name){
        product.removeIf(product1 -> product1.getName().equals(name));
    }
    public void sort(){
        Collections.sort(product);
    }
     public ArrayList<Product> filter(ProductFilter filter){
        return (ArrayList<Product>) product.stream()
                .filter(product -> filter.filter(product))
                .collect(Collectors.toList());
     }

    public void displayProducts() {
        product.forEach(prod -> System.out.println(prod));//for loop
    }
}
public class Main1 {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();

        // Adding products
        manager.addProduct("Laptop", 1200.0, Category.ELECTRONICS);
        manager.addProduct("Jeans", 40.0, Category.CLOTHING);
        manager.addProduct("Smartphone", 800.0, Category.ELECTRONICS);
        manager.addProduct("Bread", 2.0, Category.GROCERY);

        // Display all products
//        System.out.println("All Products:");
//        manager.toString();
//
//        // Filter products by category (ELECTRONICS) using an anonymous class
//        ProductFilter electronicsFilter = product -> product.category == Category.ELECTRONICS
//                && product.price >=500;
//
//        System.out.println("Filtered Prodects(Category: ELECTRONICS)");
//        ArrayList<Product> electronicproducts = manager.filter(electronicsFilter);
//        for (Product product : electronicproducts) {
//          System.out.println(product);
//        }
        manager.sort();
        manager.displayProducts();
        System.out.println(" ");
        manager.removeproduct("Jeans");
        manager.displayProducts();
    }
}

