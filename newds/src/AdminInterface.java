import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminInterface extends JFrame {
    private ArrayList<Event> eventsList;
    private DefaultTableModel eventTableModel;
    private JTable eventTable;
    private JTextField eventNameField;
    private JTextField eventDateField;
    private JTextField eventIdField;
    private JTextField eventVenueField;
    private JTextField eventDescField;
    private JTextField eventFeesField;
    private JTextField eventCapacityField;

    public AdminInterface(ArrayList<Event> eventsList) {
        this.eventsList = eventsList;
        setTitle("Admin Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout to center buttons
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE); // White background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Create action buttons
        JButton addButton = createButton("Add Event", new Color(102, 205, 170), e -> openAddEventForm());
        JButton deleteButton = createButton("Delete Event", new Color(255, 99, 71), e -> openDeleteEventForm());
        JButton viewButton = createButton("View Events", new Color(100, 149, 237), e -> listEvents());
        JButton searchButton = createButton("Search Event", new Color(238, 130, 238), e -> openSearchEventForm());
        JButton logoutButton = createButton("Logout", new Color(255, 165, 0), e -> confirmLogout());

        // Add buttons to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(addButton, gbc);
        gbc.gridx++;
        mainPanel.add(deleteButton, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(viewButton, gbc);
        gbc.gridx++;
        mainPanel.add(searchButton, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span across both columns
        mainPanel.add(logoutButton, gbc); // Logout button

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Method to create buttons with specific styles and actions
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

    // Confirmation dialog for logout
    private void confirmLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            logout();
        }
    }

    // Method to handle logout
    private void logout() {
        dispose(); // Close the admin interface
        new LoginScreen(); // Return to login screen
    }

    // Method to open the form for adding an event
    private void openAddEventForm() {
        JDialog addEventDialog = new JDialog(this, "Add Event", true);
        addEventDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create text fields
        eventNameField = createTextField("Event Name:", addEventDialog, gbc, 0, 0);
        eventDateField = createTextField("Event Date (YYYY-MM-DD):", addEventDialog, gbc, 0, 1);
        eventIdField = createTextField("Event ID:", addEventDialog, gbc, 0, 2);
        eventVenueField = createTextField("Venue:", addEventDialog, gbc, 0, 3);
        eventDescField = createTextField("Description:", addEventDialog, gbc, 0, 4);
        eventFeesField = createTextField("Fees:", addEventDialog, gbc, 0, 5);
        eventCapacityField = createTextField("Capacity:", addEventDialog, gbc, 0, 6);

        // Add button to submit the form
        JButton addEventBtn = new JButton("Add Event");
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        addEventDialog.add(addEventBtn, gbc);

        // Action for adding the event
        addEventBtn.addActionListener(e -> {
            try {
                String name = eventNameField.getText();
                String date = eventDateField.getText();
                String id = eventIdField.getText();
                String venue = eventVenueField.getText();
                String description = eventDescField.getText();
                double fees = Double.parseDouble(eventFeesField.getText());
                int capacity = Integer.parseInt(eventCapacityField.getText());

                // Basic validation
                if (name.isEmpty() || date.isEmpty() || id.isEmpty() || venue.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Date format validation
                if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(this, "Please enter the date in the format YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Event newEvent = new Event(name, date, id, venue, description, fees, capacity);
                eventsList.add(newEvent);
                JOptionPane.showMessageDialog(this, "Event added successfully!");
                listEvents(); // Update the events list
                addEventDialog.dispose(); // Close the form
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Fees and Capacity must be valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addEventDialog.setSize(400, 400);
        addEventDialog.setLocationRelativeTo(this);
        addEventDialog.setVisible(true);
    }

    // Utility method to create a labeled text field
    private JTextField createTextField(String labelText, JDialog dialog, GridBagConstraints gbc, int x, int y) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25)); // Width of 200 pixels
        textField.setMinimumSize(new Dimension(200, 25)); // Minimum size for proper layout
        textField.setMaximumSize(new Dimension(200, 25)); // Maximum size to avoid stretching
        gbc.gridx = x;
        gbc.gridy = y;
        dialog.add(label, gbc);
        gbc.gridx = x + 1;
        dialog.add(textField, gbc);
        return textField;
    }


    // Method to open the delete event dialog
    private void openDeleteEventForm() {
        String eventId = JOptionPane.showInputDialog(this, "Enter Event ID to delete:");
        if (eventId != null && !eventId.isEmpty()) {
            boolean removed = eventsList.removeIf(event -> event.getId().equals(eventId));
            if (removed) {
                JOptionPane.showMessageDialog(this, "Event deleted successfully!");
                listEvents();
            } else {
                JOptionPane.showMessageDialog(this, "Event not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to list all events in a table
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

    // Method to update the events table with current data
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

    // Method to open the search event dialog
    private void openSearchEventForm() {
        String eventId = JOptionPane.showInputDialog(this, "Enter Event ID to search:");
        if (eventId != null && !eventId.isEmpty()) {
            for (Event event : eventsList) {
                if (event.getId().equals(eventId)) {
                    JTable resultTable = new JTable();
                    resultTable.setModel(new DefaultTableModel(new Object[]{"Name", "Date", "Venue", "Description", "Fees", "Capacity"}, 0));
                    DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
                    model.addRow(new Object[]{
                            event.getName(),
                            event.getDate(),
                            event.getVenue(),
                            event.getDescription(),
                            event.getFees(),
                            event.getCapacity()
                    });

                    JOptionPane.showMessageDialog(this, new JScrollPane(resultTable), "Event Found", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Event not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        ArrayList<Event> events = new ArrayList<>();
        new AdminInterface(events);
    }
}

// Event class to represent event objects

