import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginScreen extends Frame implements ActionListener {
    private TextField usernameField;
    private TextField passwordField;

    public LoginScreen() {
        // Set up the login frame
        setTitle("Campus Event Management System - Login");
        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create a panel for the login form
        Panel loginPanel = new Panel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setSize(400, 300);
        loginPanel.setForeground(Color.BLACK);

        // Add padding and border effect
        Panel borderPanel = new Panel();
        borderPanel.setLayout(new FlowLayout());
        borderPanel.setBackground(new Color(153, 204, 255));
        borderPanel.add(loginPanel);

        // Create a label for the title
        Label titleLabel = new Label("Campus Event Management System", Label.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));

        usernameField = new TextField(20);
        passwordField = new TextField(20);
        passwordField.setEchoChar('*');

        Button loginButton = new Button("Login");
        Button cancelButton = new Button("Cancel");

        // Set colors for buttons
        loginButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(204, 0, 0));
        cancelButton.setForeground(Color.WHITE);

        // Add components to the login panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(new Label("Username:"), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(new Label("Password:"), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        gbc.gridx++;
        loginPanel.add(cancelButton, gbc);

        // Add the outer box (borderPanel) to the frame
        add(borderPanel);

        // Action listeners
        loginButton.addActionListener(this);
        cancelButton.addActionListener(e -> dispose());

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Add window listener for closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle login action
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check credentials for admin
        if (username.equals("admin") && password.equals("admin")) {
            ArrayList<Event> eventsList = new ArrayList<>(); // Initialize your event list here
            AdminInterface adminInterface = new AdminInterface(eventsList); // Open Admin Interface
            adminInterface.setVisible(true); // Ensure AdminInterface is visible
            dispose(); // Close Login Screen
        }
        // Check credentials for user
        else if (username.equals("user") && password.equals("user")) {
            User user = new User("User Name", "user@example.com", "user"); // Create a user object with necessary data
            UserInterface userInterface = new UserInterface(new ArrayList<Event>(), user); // Open User Interface
            userInterface.setVisible(true); // Ensure UserInterface is visible
            dispose(); // Close Login Screen
        }
        // Handle invalid login
        else {
            showDialog("Login Failed", "Invalid credentials!");
        }
    }

    private void showDialog(String title, String message) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new Label(message, Label.CENTER), BorderLayout.CENTER);
        Button okButton = new Button("OK");
        okButton.addActionListener(evt -> dialog.dispose());
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setSize(250, 100);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginScreen(); // Create an instance of LoginScreen
    }
}
