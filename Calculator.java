import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Calculator {
    private static double number = 0; // Variable to store the current number
    private static String operator = ""; // Variable to store the current operator
    private static JLabel numberLabel; // Label to display the current number
    private static final String FILE_NAME = "lastnumber.txt"; // File to save the number
    private static boolean isOperatorClicked = false; // Flag to check if an operator was clicked

    public static void main(String[] args) {
        // Load the number from the file at startup
        loadNumber();

        // Create a new window (frame) with the title "Calculator"
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        frame.setSize(400, 500); // Set the size of the window
        frame.setLayout(new BorderLayout()); // Set the layout of the window to BorderLayout

        // Create a label to display the current number, aligned to the right
        numberLabel = new JLabel(String.valueOf(number), SwingConstants.RIGHT);
        numberLabel.setFont(new Font("Serif", Font.PLAIN, 48)); // Set the font size of the number
        numberLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(numberLabel, BorderLayout.NORTH); // Add the label to the top of the window

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        // Add buttons for numbers and operations
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                " ", " ", " ", "clr"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Serif", Font.PLAIN, 36)); // Set the font size of the buttons
            if (!text.equals(" ")) { // Add action listener only to buttons that are not spaces
                button.addActionListener(new ButtonClickListener()); // Attach an action listener to handle button clicks
            }
            panel.add(button); // Add the button to the panel
        }

        // Add the panel with buttons to the center of the window
        frame.add(panel, BorderLayout.CENTER);

        // Create an exit button and add it to the bottom of the window
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.PLAIN, 24)); // Set the font size of the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the number to the file before exiting
                saveNumber();
                System.exit(0);
            }
        });
        frame.add(exitButton, BorderLayout.SOUTH);

        // Make the window visible
        frame.setVisible(true);
    }

    // Method to save the number to a file
    private static void saveNumber() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(String.valueOf(number)); // Write the current number to the file
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
    }

    // Method to load the number from a file
    private static void loadNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            if (line != null) {
                number = Double.parseDouble(line); // Parse and set the number if the file is not empty
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
    }

    // Action listener for button clicks
    static class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand(); // Get the text of the button clicked
            if (command.equals("clr")) {
                // Clear the display and reset variables if "clr" is clicked
                numberLabel.setText("");
                number = 0;
                operator = "";
                isOperatorClicked = false;
            } else if (command.chars().allMatch(Character::isDigit) || command.equals(".")) {
                // If a number or decimal point is clicked
                if (isOperatorClicked || numberLabel.getText().equals("0")) {
                    numberLabel.setText(""); // Clear the label if an operator was previously clicked or if the initial display is 0
                    isOperatorClicked = false;
                }
                numberLabel.setText(numberLabel.getText() + command); // Append the clicked number/decimal to the label
            } else if (command.equals("=")) {
                // If the equals sign is clicked
                calculate(); // Perform the calculation
                operator = ""; // Clear the operator
                isOperatorClicked = false;
            } else {
                // If an operator is clicked
                if (!operator.isEmpty() && !isOperatorClicked) {
                    calculate(); // Perform the previous calculation if an operator was already set and operator was not clicked consecutively
                }
                operator = command; // Set the current operator
                number = Double.parseDouble(numberLabel.getText()); // Store the current number
                isOperatorClicked = true; // Set the flag indicating an operator was clicked
            }
        }

        // Method to perform calculations
        private void calculate() {
            double currentNumber = Double.parseDouble(numberLabel.getText()); // Get the current number from the label
            switch (operator) {
                case "+":
                    number += currentNumber; // Add the current number to the stored number
                    break;
                case "-":
                    number -= currentNumber; // Subtract the current number from the stored number
                    break;
                case "*":
                    number *= currentNumber; // Multiply the stored number by the current number
                    break;
                case "/":
                    if (currentNumber != 0) {
                        number /= currentNumber; // Divide the stored number by the current number
                    } else {
                        JOptionPane.showMessageDialog(null, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                        number = 0; // Reset the number to 0 if division by zero is attempted
                    }
                    break;
            }
            numberLabel.setText(String.valueOf(number)); // Update the label with the result
        }
    }
}
