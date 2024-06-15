import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {
    private static int number = 0; // Variable to store the current number
    private static JLabel numberLabel; // Label to display the current number
    private static final String FILE_NAME = "lastnumber.txt"; // File to save the number

    public static void main(String[] args) {
        // Load the number from the file at startup
        loadNumber();

        // Create a new window (frame) with the title "Job Application"
        JFrame frame = new JFrame("Job Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        frame.setSize(400, 300); // Set the size of the window
        frame.setLayout(new BorderLayout()); // Set the layout of the window to BorderLayout

        // Create a label to display the current number, centered in the window
        numberLabel = new JLabel(String.valueOf(number), SwingConstants.CENTER);
        numberLabel.setFont(new Font("Serif", Font.PLAIN, 48)); // Set the font size of the number
        frame.add(numberLabel, BorderLayout.CENTER); // Add the label to the center of the window

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();

        // Create a button labeled "+1"
        JButton incrementButton = new JButton("+1");
        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Increment the number and check if it reaches 10
                number++;
                if (number >= 11) {
                    number = 0; // Reset to 0 if number reaches 10
                }
                numberLabel.setText(String.valueOf(number));
            }
        });
        panel.add(incrementButton); // Add the increment button to the panel

        // Create a button labeled "Exit"
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the number to the file before exiting
                saveNumber();
                System.exit(0);
            }
        });
        panel.add(exitButton); // Add the exit button to the panel

        // Add the panel with buttons to the bottom of the window
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // Method to save the number to a file
    private static void saveNumber() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(String.valueOf(number));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the number from a file
    private static void loadNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            if (line != null) {
                number = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
