import javax.swing.*;
import java.awt.*;
public class GUI1st {
    public static void main(String[] args)
    {
        JFrame frame= new JFrame();
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel();
        label.setText("hello sijan");

        JTextField jTextField = new JTextField();
        jTextField.setSize(40,50);
        jTextField.setColumns(20);

        JButton button = new JButton("click");
        button.addActionListener(e -> {
            System.out.print("sijan dada"+jTextField.getText());
        });
        frame.add(label);
        frame.add(jTextField);
        frame.add(button);

        frame.setSize(500,500);
        frame.setVisible(true);
    }
}
