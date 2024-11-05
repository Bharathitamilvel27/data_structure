import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserInterface extends JFrame {
    private ArrayList<Event> eventsList;
    private DefaultTableModel eventTableModel;
    private JTable eventTable;
    private User user; // Store user information

    public UserInterface(ArrayList<Event> eventsList, User user) {
        this.eventsList = eventsList; // Initialize the events list
        this.user = user; // Initialize the user
        setTitle("User Interface - " + user.getUsername()); // Set title with username
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with a GridBagLayout to center the buttons
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(255, 255, 255)); // White background for simplicity
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between buttons

        // Create buttons for viewing events and logging out
        JButton viewEventsButton = createButton("View Events", new Color(100, 149, 237), e -> listEvents());
        JButton logoutButton = createButton("Logout", new Color(255, 165, 0), e -> logout());

        // Place buttons in a grid layout at the center
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(viewEventsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(logoutButton, gbc); // Add logout button

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Method to create buttons with specific colors and action listeners
    private JButton createButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 50)); // Medium size buttons
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    // Method to logout and return to the login screen
    private void logout() {
        dispose(); // Close User Interface
        new LoginScreen(); // Open the Login Screen again
    }

    // Method to list events in a table
    private void listEvents() {
        JFrame listFrame = new JFrame("List of Events");
        listFrame.setSize(600, 400);
        listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {"Name", "Date", "ID", "Venue", "Description", "Fees", "Capacity"};
        eventTableModel = new DefaultTableModel(columnNames, 0);
        eventTable = new JTable(eventTableModel);
        JScrollPane scrollPane = new JScrollPane(eventTable);
        listFrame.add(scrollPane);
        updateEventTable();
        listFrame.setVisible(true);
    }

    // Method to update the event table with current event data
    private void updateEventTable() {
        eventTableModel.setRowCount(0); // Clear existing rows
        for (Event event : eventsList) {
            eventTableModel.addRow(new Object[]{
                    event.getName(),
                    event.getDate(),
                    event.getId(),
                    event.getVenue(),
                    event.getDescription(),
                    event.getFees(),
                    event.getCapacity()
            });
        }
    }

    public static void main(String[] args) {
        ArrayList<Event> events = new ArrayList<>(); // Initialize an empty event list
        User user = new User("JohnDoe", "password123", "john@example.com"); // Sample user
        new UserInterface(events, user).setVisible(true); // Open the User Interface
    }
}
