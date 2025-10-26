import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0;
    private String operator = "";
    private boolean startNewNumber = true;
    private boolean degreeMode = true;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Display
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(6, 5, 3, 3));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] buttons = {
                "sin", "cos", "tan", "log", "ln",
                "√", "x²", "xʸ", "1/x", "π",
                "7", "8", "9", "/", "C",
                "4", "5", "6", "*", "(",
                "1", "2", "3", "-", ")",
                "0", ".", "=", "+", "DEG"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.addActionListener(this);

            if (text.matches("[0-9]") || text.equals(".")) {
                btn.setBackground(new Color(240, 240, 240));
            } else if (text.equals("=")) {
                btn.setBackground(new Color(100, 150, 255));
                btn.setForeground(Color.WHITE);
            } else if (text.matches("[+\\-*/]")) {
                btn.setBackground(new Color(255, 180, 100));
            } else {
                btn.setBackground(new Color(220, 220, 220));
            }

            mainPanel.add(btn);
        }

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            if (cmd.charAt(0) >= '0' && cmd.charAt(0) <= '9') {
                if (startNewNumber) {
                    display.setText(cmd);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + cmd);
                }
            } else if (cmd.equals(".")) {
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
            } else if (cmd.equals("C")) {
                display.setText("0");
                num1 = num2 = result = 0;
                operator = "";
                startNewNumber = true;
            } else if (cmd.equals("DEG")) {
                degreeMode = !degreeMode;
                ((JButton) e.getSource()).setText(degreeMode ? "DEG" : "RAD");
            } else if (cmd.equals("sin")) {
                double value = Double.parseDouble(display.getText());
                result = Math.sin(degreeMode ? Math.toRadians(value) : value);
                display.setText(String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", ""));
                startNewNumber = true;
            } else if (cmd.equals("cos")) {
                double value = Double.parseDouble(display.getText());
                result = Math.cos(degreeMode ? Math.toRadians(value) : value);
                display.setText(String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", ""));
                startNewNumber = true;
            } else if (cmd.equals("tan")) {
                double value = Double.parseDouble(display.getText());
                result = Math.tan(degreeMode ? Math.toRadians(value) : value);
                display.setText(String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", ""));
                startNewNumber = true;
            } else if (cmd.equals("log")) {
                double value = Double.parseDouble(display.getText());
                result = Math.log10(value);
                display.setText(String.valueOf(result));
                startNewNumber = true;
            } else if (cmd.equals("ln")) {
                double value = Double.parseDouble(display.getText());
                result = Math.log(value);
                display.setText(String.valueOf(result));
                startNewNumber = true;
            } else if (cmd.equals("√")) {
                double value = Double.parseDouble(display.getText());
                result = Math.sqrt(value);
                display.setText(String.valueOf(result));
                startNewNumber = true;
            } else if (cmd.equals("x²")) {
                double value = Double.parseDouble(display.getText());
                result = value * value;
                display.setText(String.valueOf(result));
                startNewNumber = true;
            } else if (cmd.equals("1/x")) {
                double value = Double.parseDouble(display.getText());
                if (value != 0) {
                    result = 1 / value;
                    display.setText(String.valueOf(result));
                } else {
                    display.setText("Error");
                }
                startNewNumber = true;
            } else if (cmd.equals("π")) {
                display.setText(String.valueOf(Math.PI));
                startNewNumber = true;
            } else if (cmd.equals("(") || cmd.equals(")")) {
                if (startNewNumber) {
                    display.setText(cmd);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText() + cmd);
                }
            } else if (cmd.equals("=")) {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                    case "xʸ":
                        result = Math.pow(num1, num2);
                        break;
                }
                display.setText(String.valueOf(result));
                operator = "";
                startNewNumber = true;
            } else {
                if (!operator.isEmpty()) {
                    num2 = Double.parseDouble(display.getText());
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                display.setText("Error");
                                return;
                            }
                            break;
                        case "xʸ":
                            result = Math.pow(num1, num2);
                            break;
                    }
                    display.setText(String.valueOf(result));
                    num1 = result;
                } else {
                    num1 = Double.parseDouble(display.getText());
                }
                operator = cmd;
                startNewNumber = true;
            }
        } catch (Exception ex) {
            display.setText("Error");
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScientificCalculator());
    }
}