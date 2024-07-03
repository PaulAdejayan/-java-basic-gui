# -java-basic-gui
new changes 

The main functionality of the application is to increment a number each time a button is pressed and reset the number when it reaches 10. The number is saved to a file on exit and loaded from the file when the application starts.
JLabel: Displays the current number in a large font.
JPanel: Holds the buttons for user interaction.
JButton: Includes buttons for incrementing the number and exiting the application.
The application reads the saved number from a file (lastnumber.txt) and displays it.
Clicking the "+1" button increases the number by 1. When the number reaches 10, it resets to 0.
Exit Button : Clicking the "Exit" button saves the current number to lastnumber.txt and closes the application.
The saveNumber method writes the current number to lastnumber.txt, demonstrating how to persist data between sessions.
Swing Components: Utilizes Java Swing to create a simple and effective GUI, including buttons and labels, to interact with the user.
Try-Catch Blocks: Handles potential I/O exceptions when reading from or writing to the file, ensuring robustness.