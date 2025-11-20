package com.ust.frs.main;

import com.ust.frs.bean.*;
import com.ust.frs.dao.AdministratorDAO;
import com.ust.frs.dao.CustomerDAO;
import com.ust.frs.util.DBUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Main {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private String currentUserId;
    private String currentUserType;
    
    // DAOs
    private AdministratorDAO adminDAO;
    private CustomerDAO customerDAO;
    
    // Components
    private JTable flightTable, routeTable, scheduleTable, reservationTable;
    private DefaultTableModel flightTableModel, routeTableModel, scheduleTableModel, reservationTableModel;
    
    public Main() {
        adminDAO = new AdministratorDAO();
        customerDAO = new CustomerDAO();
        initialize();
    }
    
    private void initialize() {
        mainFrame = new JFrame("Flight Reservation System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);
        mainFrame.setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create different panels
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createAdminDashboard(), "ADMIN_DASHBOARD");
        mainPanel.add(createCustomerDashboard(), "CUSTOMER_DASHBOARD");
        mainPanel.add(createFlightManagementPanel(), "FLIGHT_MANAGEMENT");
        mainPanel.add(createRouteManagementPanel(), "ROUTE_MANAGEMENT");
        mainPanel.add(createScheduleManagementPanel(), "SCHEDULE_MANAGEMENT");
        mainPanel.add(createBookTicketPanel(), "BOOK_TICKET");
        mainPanel.add(createViewTicketsPanel(), "VIEW_TICKETS");
        
        mainFrame.add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
        mainFrame.setVisible(true);
    }
    
    // LOGIN PANEL
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Flight Reservation System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        // User ID
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("User ID:"), gbc);
        
        JTextField userIdField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(userIdField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(passwordField, gbc);
        
        // User Type
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("User Type:"), gbc);
        
        JComboBox<String> userTypeCombo = new JComboBox<>(new String[]{"Administrator", "Customer"});
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(userTypeCombo, gbc);
        
        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
        
        // Register Button
        JButton registerButton = new JButton("New User? Register Here");
        registerButton.setForeground(new Color(0, 102, 204));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        gbc.gridy = 5;
        panel.add(registerButton, gbc);
        
        // Login Action
        loginButton.addActionListener(e -> {
            String userId = userIdField.getText().trim();
            String password = new String(passwordField.getPassword());
            String userType = userTypeCombo.getSelectedItem().toString();
            
            if (authenticateUser(userId, password, userType)) {
                currentUserId = userId;
                currentUserType = userType;
                JOptionPane.showMessageDialog(panel, "Login Successful!");
                
                if ("Administrator".equals(userType)) {
                    cardLayout.show(mainPanel, "ADMIN_DASHBOARD");
                    refreshAdminData();
                } else {
                    cardLayout.show(mainPanel, "CUSTOMER_DASHBOARD");
                    refreshCustomerData();
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Register Action
        registerButton.addActionListener(e -> showRegistrationDialog());
        
        return panel;
    }
    
    // ADMIN DASHBOARD
    private JPanel createAdminDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Administrator Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(0, 102, 204));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Navigation Buttons
        JPanel navPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        navPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] buttons = {
            "Manage Flights", "Manage Routes", "Manage Schedules",
            "View All Flights", "View All Routes", "View All Schedules"
        };
        
        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.addActionListener(new AdminButtonListener());
            navPanel.add(button);
        }
        
        panel.add(navPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // CUSTOMER DASHBOARD
    private JPanel createCustomerDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Customer Dashboard - Welcome " + currentUserId, JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(0, 102, 204));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Navigation Buttons
        JPanel navPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        navPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        String[] buttons = {
            "Book Ticket", "View My Tickets", "Cancel Ticket", "Search Flights"
        };
        
        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.addActionListener(new CustomerButtonListener());
            navPanel.add(button);
        }
        
        panel.add(navPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // FLIGHT MANAGEMENT PANEL
    private JPanel createFlightManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ADMIN_DASHBOARD"));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(new JLabel("Flight Management", JLabel.CENTER), BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(new TitledBorder("Add/Modify Flight"));
        
        JTextField flightIdField = new JTextField();
        JTextField flightNameField = new JTextField();
        JTextField seatingCapacityField = new JTextField();
        JTextField reservationCapacityField = new JTextField();
        
        formPanel.add(new JLabel("Flight ID:"));
        formPanel.add(flightIdField);
        formPanel.add(new JLabel("Flight Name:"));
        formPanel.add(flightNameField);
        formPanel.add(new JLabel("Seating Capacity:"));
        formPanel.add(seatingCapacityField);
        formPanel.add(new JLabel("Reservation Capacity:"));
        formPanel.add(reservationCapacityField);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Flight");
        JButton modifyButton = new JButton("Modify Flight");
        JButton deleteButton = new JButton("Delete Flight");
        JButton clearButton = new JButton("Clear");
        
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Flight ID", "Flight Name", "Seating Capacity", "Reservation Capacity"};
        flightTableModel = new DefaultTableModel(columns, 0);
        flightTable = new JTable(flightTableModel);
        JScrollPane tableScroll = new JScrollPane(flightTable);
        
        panel.add(tableScroll, BorderLayout.CENTER);
        
        // Button Actions
        addButton.addActionListener(e -> {
            try {
                FlightBean flight = new FlightBean();
                flight.setFlightName(flightNameField.getText());
                flight.setSeatingCapacity(Integer.parseInt(seatingCapacityField.getText()));
                flight.setReservationCapacity(Integer.parseInt(reservationCapacityField.getText()));
                
                String result = adminDAO.addFlight(flight);
                JOptionPane.showMessageDialog(panel, result);
                refreshFlightTable();
                clearForm(flightIdField, flightNameField, seatingCapacityField, reservationCapacityField);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        flightTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && flightTable.getSelectedRow() != -1) {
                int row = flightTable.getSelectedRow();
                flightIdField.setText(flightTableModel.getValueAt(row, 0).toString());
                flightNameField.setText(flightTableModel.getValueAt(row, 1).toString());
                seatingCapacityField.setText(flightTableModel.getValueAt(row, 2).toString());
                reservationCapacityField.setText(flightTableModel.getValueAt(row, 3).toString());
            }
        });
        
        modifyButton.addActionListener(e -> {
            try {
                FlightBean flight = new FlightBean();
                flight.setFlightID(flightIdField.getText());
                flight.setFlightName(flightNameField.getText());
                flight.setSeatingCapacity(Integer.parseInt(seatingCapacityField.getText()));
                flight.setReservationCapacity(Integer.parseInt(reservationCapacityField.getText()));
                
                boolean success = adminDAO.modifyFlight(flight);
                if (success) {
                    JOptionPane.showMessageDialog(panel, "Flight updated successfully!");
                    refreshFlightTable();
                    clearForm(flightIdField, flightNameField, seatingCapacityField, reservationCapacityField);
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to update flight", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        clearButton.addActionListener(e -> clearForm(flightIdField, flightNameField, seatingCapacityField, reservationCapacityField));
        
        return panel;
    }
    
    // ROUTE MANAGEMENT PANEL (Similar structure to flight management)
    private JPanel createRouteManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Similar implementation as flight management
        // ... (code for route management UI)
        
        return panel;
    }
    
    // SCHEDULE MANAGEMENT PANEL (Similar structure)
    private JPanel createScheduleManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Similar implementation as flight management
        // ... (code for schedule management UI)
        
        return panel;
    }
    
    // BOOK TICKET PANEL
    private JPanel createBookTicketPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER_DASHBOARD"));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(new JLabel("Book Ticket", JLabel.CENTER), BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        searchPanel.setBorder(new TitledBorder("Search Flights"));
        
        JTextField sourceField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JButton searchButton = new JButton("Search");
        
        searchPanel.add(new JLabel("Source:"));
        searchPanel.add(sourceField);
        searchPanel.add(new JLabel("Destination:"));
        searchPanel.add(destinationField);
        searchPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        searchPanel.add(dateField);
        searchPanel.add(new JLabel(""));
        searchPanel.add(searchButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Results Table
        String[] columns = {"Schedule ID", "Flight", "Departure", "Duration", "Available Seats", "Fare"};
        scheduleTableModel = new DefaultTableModel(columns, 0);
        scheduleTable = new JTable(scheduleTableModel);
        JScrollPane tableScroll = new JScrollPane(scheduleTable);
        
        panel.add(tableScroll, BorderLayout.CENTER);
        
        // Booking Form
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.setBorder(new TitledBorder("Passenger Details"));
        
        JPanel passengerForm = new JPanel(new GridLayout(1, 4, 10, 10));
        JTextField nameField = new JTextField();
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField ageField = new JTextField();
        JTextField seatField = new JTextField();
        
        passengerForm.add(new JLabel("Name:"));
        passengerForm.add(nameField);
        passengerForm.add(new JLabel("Gender:"));
        passengerForm.add(genderCombo);
        passengerForm.add(new JLabel("Age:"));
        passengerForm.add(ageField);
        passengerForm.add(new JLabel("Seat:"));
        passengerForm.add(seatField);
        
        JButton addPassengerButton = new JButton("Add Passenger");
        JButton bookButton = new JButton("Confirm Booking");
        
        JPanel passengerButtons = new JPanel();
        passengerButtons.add(addPassengerButton);
        passengerButtons.add(bookButton);
        
        bookingPanel.add(passengerForm, BorderLayout.CENTER);
        bookingPanel.add(passengerButtons, BorderLayout.SOUTH);
        
        panel.add(bookingPanel, BorderLayout.SOUTH);
        
        // Search Action
        searchButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String date = dateField.getText();
            
            if (source.isEmpty() || destination.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill all search fields");
                return;
            }
            
            ArrayList<ScheduleBean> schedules = customerDAO.viewScheduleByRoute(source, destination, date);
            refreshScheduleTable(schedules);
        });
        
        return panel;
    }
    
    // VIEW TICKETS PANEL
    private JPanel createViewTicketsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "CUSTOMER_DASHBOARD"));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(new JLabel("My Tickets", JLabel.CENTER), BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Reservations Table
        String[] columns = {"Reservation ID", "Flight", "Route", "Journey Date", "Seats", "Total Fare", "Status"};
        reservationTableModel = new DefaultTableModel(columns, 0);
        reservationTable = new JTable(reservationTableModel);
        JScrollPane tableScroll = new JScrollPane(reservationTable);
        
        panel.add(tableScroll, BorderLayout.CENTER);
        
        // Action Buttons
        JPanel actionPanel = new JPanel();
        JButton viewDetailsButton = new JButton("View Details");
        JButton cancelTicketButton = new JButton("Cancel Ticket");
        JButton refreshButton = new JButton("Refresh");
        
        actionPanel.add(viewDetailsButton);
        actionPanel.add(cancelTicketButton);
        actionPanel.add(refreshButton);
        
        panel.add(actionPanel, BorderLayout.SOUTH);
        
        // Button Actions
        refreshButton.addActionListener(e -> refreshReservationTable());
        
        viewDetailsButton.addActionListener(e -> {
            int row = reservationTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Please select a reservation");
                return;
            }
            
            String reservationId = reservationTableModel.getValueAt(row, 0).toString();
            showTicketDetails(reservationId);
        });
        
        cancelTicketButton.addActionListener(e -> {
            int row = reservationTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(panel, "Please select a reservation");
                return;
            }
            
            String reservationId = reservationTableModel.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(panel, 
                "Are you sure you want to cancel this ticket?", "Confirm Cancellation", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = customerDAO.cancelTicket(reservationId);
                if (success) {
                    JOptionPane.showMessageDialog(panel, "Ticket cancelled successfully!");
                    refreshReservationTable();
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to cancel ticket", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return panel;
    }
    
    // HELPER METHODS
    private boolean authenticateUser(String userId, String password, String userType) {
        // Simplified authentication - in real application, verify against database
        if ("admin".equals(userId) && "admin123".equals(password) && "Administrator".equals(userType)) {
            return true;
        }
        if (userId.startsWith("CU") && "customer123".equals(password) && "Customer".equals(userType)) {
            return true;
        }
        return false;
    }
    
    private void showRegistrationDialog() {
        JDialog registerDialog = new JDialog(mainFrame, "User Registration", true);
        registerDialog.setSize(400, 500);
        registerDialog.setLocationRelativeTo(mainFrame);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Registration form fields
        String[] fields = {"First Name", "Last Name", "Date of Birth", "Gender", 
                          "Street", "Location", "City", "State", "Pincode", "Mobile", "Email"};
        
        JTextField[] textFields = new JTextField[fields.length];
        
        for (int i = 0; i < fields.length; i++) {
            panel.add(new JLabel(fields[i] + ":"));
            textFields[i] = new JTextField();
            panel.add(textFields[i]);
        }
        
        JPasswordField passwordField = new JPasswordField();
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        registerDialog.add(panel, BorderLayout.CENTER);
        registerDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        registerButton.addActionListener(e -> {
            // Registration logic here
            JOptionPane.showMessageDialog(registerDialog, "Registration functionality to be implemented");
            registerDialog.dispose();
        });
        
        cancelButton.addActionListener(e -> registerDialog.dispose());
        
        registerDialog.setVisible(true);
    }
    
    private void refreshAdminData() {
        refreshFlightTable();
        refreshRouteTable();
        refreshScheduleTable();
    }
    
    private void refreshCustomerData() {
        refreshReservationTable();
    }
    
    private void refreshFlightTable() {
        flightTableModel.setRowCount(0);
        ArrayList<FlightBean> flights = adminDAO.viewByAllFlights();
        for (FlightBean flight : flights) {
            flightTableModel.addRow(new Object[]{
                flight.getFlightID(),
                flight.getFlightName(),
                flight.getSeatingCapacity(),
                flight.getReservationCapacity()
            });
        }
    }
    
    private void refreshRouteTable() {
        // Implementation similar to refreshFlightTable
    }
    
    private void refreshScheduleTable() {
        // Implementation for admin schedule view
    }
    
    private void refreshScheduleTable(ArrayList<ScheduleBean> schedules) {
        scheduleTableModel.setRowCount(0);
        for (ScheduleBean schedule : schedules) {
            scheduleTableModel.addRow(new Object[]{
                schedule.getScheduleID(),
                schedule.getFlightName(),
                schedule.getDepartureTime(),
                schedule.getTravelDuration(),
                schedule.getAvailableSeats(),
                schedule.getFare()
            });
        }
    }
    
    private void refreshReservationTable() {
        reservationTableModel.setRowCount(0);
        ArrayList<ReservationBean> reservations = customerDAO.getUserReservations(currentUserId);
        for (ReservationBean reservation : reservations) {
            String status = reservation.getBookingStatus() == 1 ? "Confirmed" : "Cancelled";
            reservationTableModel.addRow(new Object[]{
                reservation.getReservationID(),
                reservation.getFlightName(),
                reservation.getSource() + " to " + reservation.getDestination(),
                reservation.getJourneyDate(),
                reservation.getNoOfSeats(),
                reservation.getTotalFare(),
                status
            });
        }
    }
    
    private void showTicketDetails(String reservationId) {
        Map<ReservationBean, ArrayList<PassengerBean>> ticket = customerDAO.viewTicket(reservationId);
        
        if (!ticket.isEmpty()) {
            ReservationBean reservation = ticket.keySet().iterator().next();
            ArrayList<PassengerBean> passengers = ticket.get(reservation);
            
            StringBuilder details = new StringBuilder();
            details.append("Reservation ID: ").append(reservation.getReservationID()).append("\n");
            details.append("Flight: ").append(reservation.getFlightName()).append("\n");
            details.append("Route: ").append(reservation.getSource()).append(" to ").append(reservation.getDestination()).append("\n");
            details.append("Journey Date: ").append(reservation.getJourneyDate()).append("\n");
            details.append("Total Fare: $").append(reservation.getTotalFare()).append("\n\n");
            details.append("Passengers:\n");
            
            for (PassengerBean passenger : passengers) {
                details.append("- ").append(passenger.getName()).append(" (")
                      .append(passenger.getGender()).append(", ")
                      .append(passenger.getAge()).append(" years) Seat: ")
                      .append(passenger.getSeatNo()).append("\n");
            }
            
            JOptionPane.showMessageDialog(mainFrame, details.toString(), "Ticket Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearForm(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
    
    private void logout() {
        currentUserId = null;
        currentUserType = null;
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    // ACTION LISTENERS
    private class AdminButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "Manage Flights":
                    cardLayout.show(mainPanel, "FLIGHT_MANAGEMENT");
                    break;
                case "Manage Routes":
                    cardLayout.show(mainPanel, "ROUTE_MANAGEMENT");
                    break;
                case "Manage Schedules":
                    cardLayout.show(mainPanel, "SCHEDULE_MANAGEMENT");
                    break;
                case "View All Flights":
                    refreshFlightTable();
                    JOptionPane.showMessageDialog(mainFrame, "Flights data refreshed");
                    break;
                case "View All Routes":
                    refreshRouteTable();
                    JOptionPane.showMessageDialog(mainFrame, "Routes data refreshed");
                    break;
                case "View All Schedules":
                    refreshScheduleTable();
                    JOptionPane.showMessageDialog(mainFrame, "Schedules data refreshed");
                    break;
            }
        }
    }
    
    private class CustomerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "Book Ticket":
                    cardLayout.show(mainPanel, "BOOK_TICKET");
                    break;
                case "View My Tickets":
                    cardLayout.show(mainPanel, "VIEW_TICKETS");
                    refreshReservationTable();
                    break;
                case "Cancel Ticket":
                    cardLayout.show(mainPanel, "VIEW_TICKETS");
                    refreshReservationTable();
                    break;
                case "Search Flights":
                    cardLayout.show(mainPanel, "BOOK_TICKET");
                    break;
            }
        }
    }
    
    // MAIN METHOD
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Main();
        });
    }
}
