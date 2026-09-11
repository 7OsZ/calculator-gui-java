import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {
    private final JTextField display = new JTextField();
    private double first = 0;
    private String op = "";
    private boolean fresh = true;

    public Calculator(){
        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] keys = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String k : keys){
            JButton b = new JButton(k);
            b.setFont(new Font("Arial", Font.BOLD, 24));
            b.addActionListener(e -> press(k));
            grid.add(b);
        }
        add(grid, BorderLayout.CENTER);
        setVisible(true);
    }

    private void press(String k){
        if (k.matches("[0-9]")){
            if (fresh){
                display.setText("");
                fresh = false;
            }
            display.setText(display.getText() + k);
        } else if (k.equals("C")){
            display.setText("");
            first = 0;
            op = "";
            fresh = true;
        } else if (k.equals("=")) {
            calc();
        } else {
            first = parse();
            op = k;
            fresh = true;
        }
    }

    private void calc(){
        double second = parse();

        double r = switch (op) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> second == 0 ? Double.NaN : first / second;
            default -> second;
        };

        display.setText(Double.isNaN(r) ? "Error" : trim(r));
        fresh = true;
    }

    private double parse(){
        try {
            return Double.parseDouble(display.getText());
        } catch (Exception e){
            return 0;
        }
    }

    private String trim(double d){
        return d == (long) d ? String.valueOf((long) d) : String.valueOf(d);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }

}
