import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private final JTextField display;
    private double currentResult;
    private String currentOperator;
    private boolean startOfNewNumber;

    public Calculator() {
        currentResult = 0;
        currentOperator = "=";
        startOfNewNumber = true;

        setTitle("Calculator");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display field
        display = new JTextField("0");
        display.setFont(new Font("Consolas", Font.BOLD, 30));
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        // Button labels
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "<"
        };

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(Color.BLACK);

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setActionCommand(text);
            button.addActionListener(this);

            // Color styling
            if ("0123456789.".contains(text)) {
                button.setBackground(new Color(20, 20, 20));
            } else if ("+-*/".contains(text)) {
                button.setBackground(new Color(40, 40, 40));
            } else if (text.equals("=")) {
                button.setBackground(new Color(255, 165, 0));
                button.setForeground(Color.BLACK);
            } else {
                button.setBackground(new Color(60, 60, 60));
            }

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        if ("0123456789.".contains(cmd)) {
            if (startOfNewNumber) {
                display.setText(cmd.equals(".") ? "0." : cmd);
                startOfNewNumber = false;
            } else {
                // Prevent multiple decimal points
                if (cmd.equals(".") && display.getText().contains(".")) {
                    return;
                }
                display.setText(display.getText() + cmd);
            }
        } else if ("+-*/".contains(cmd)) {
            calculate(Double.parseDouble(display.getText()));
            currentOperator = cmd;
            startOfNewNumber = true;
        } else if (cmd.equals("=")) {
            calculate(Double.parseDouble(display.getText()));
            currentOperator = "=";
            startOfNewNumber = true;
        } else if (cmd.equals("C")) {
            display.setText("0");
            currentResult = 0;
            currentOperator = "=";
            startOfNewNumber = true;
        } else if (cmd.equals("<")) {
            if (!startOfNewNumber) {
                String text = display.getText();
                if (text.length() > 1) {
                    display.setText(text.substring(0, text.length() - 1));
                } else {
                    display.setText("0");
                    startOfNewNumber = true;
                }
            }
        }
    }

    private void calculate(double num) {
        switch (currentOperator) {
            case "+":
                currentResult += num;
                break;
            case "-":
                currentResult -= num;
                break;
            case "*":
                currentResult *= num;
                break;
            case "/":
                if (num == 0) {
                    display.setText("Error");
                    currentResult = 0;
                    currentOperator = "=";
                    startOfNewNumber = true;
                    return;
                }
                currentResult /= num;
                break;
            case "=":
                currentResult = num;
                break;
        }

        if (currentResult % 1 == 0) {
            display.setText(String.valueOf((int) currentResult));
        } else {
            display.setText(String.valueOf(currentResult));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}