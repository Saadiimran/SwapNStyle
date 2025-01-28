import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwapNStyle {

    private List<User> userList;
    private List<Item> itemList;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private User loggedInUser;

    public SwapNStyle() {
        
        userList = new ArrayList<>(); 
        itemList = new ArrayList<>();
        
        frame = new JFrame("SwapNStyle");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
    
        setupMainMenu();
        setupRegisterScreen();
        setupLoginScreen();
        setupUserDashboard();
    
        frame.add(mainPanel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    

    @SuppressWarnings("unused")
    private void setupMainMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(exitButton);

        mainPanel.add(panel, "MainMenu");
    }

    @SuppressWarnings("unused")
    private void setupRegisterScreen() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (registerUser(username, email, password)) {
                JOptionPane.showMessageDialog(frame, "User registered successfully!");
                cardLayout.show(mainPanel, "MainMenu");
            } else {
                JOptionPane.showMessageDialog(frame, "Email already registered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(backButton);

        mainPanel.add(panel, "Register");
    }

    @SuppressWarnings("unused")
    private void setupLoginScreen() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            loggedInUser = loginUser(email, password);
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                cardLayout.show(mainPanel, "Dashboard");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);

        mainPanel.add(panel, "Login");
    }

    @SuppressWarnings("unused")
    private void setupUserDashboard() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Enter item name:");
            String size = JOptionPane.showInputDialog(frame, "Enter item size:");
            String condition = JOptionPane.showInputDialog(frame, "Enter item condition:");
            String category = JOptionPane.showInputDialog(frame, "Enter item category:");

            addItem(loggedInUser, name, size, condition, category);
        });

        JButton browseItemsButton = new JButton("Browse Items");
        browseItemsButton.addActionListener(e -> {
            String filterCategory = JOptionPane.showInputDialog(frame, "Filter by category (leave blank for all):");
            String filterSize = JOptionPane.showInputDialog(frame, "Filter by size (leave blank for all):");
            String filterCondition = JOptionPane.showInputDialog(frame, "Filter by condition (leave blank for all):");

            browseItems(filterCategory.isEmpty() ? null : filterCategory,
                            filterSize.isEmpty() ? null : filterSize,
                            filterCondition.isEmpty() ? null : filterCondition);
        });

        JButton initiateSwapButton = new JButton("Initiate Swap");
        initiateSwapButton.addActionListener(e -> {
            String itemIdStr = JOptionPane.showInputDialog(frame, "Enter item ID to initiate swap:");
            try {
                int itemId = Integer.parseInt(itemIdStr);
                initiateSwap(loggedInUser, itemId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid item ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            JOptionPane.showMessageDialog(frame, "Logged out successfully.");
            cardLayout.show(mainPanel, "MainMenu");
        });

        panel.add(addItemButton);
        panel.add(browseItemsButton);
        panel.add(initiateSwapButton);
        panel.add(logoutButton);

        mainPanel.add(panel, "Dashboard");
    }

    public boolean registerUser(String username, String email, String password) {
        for (User user : userList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already registered.");
                return false;
            }
        }
        userList.add(new User(username, email, password));
        System.out.println("User registered successfully!");
        return true;
    }
    
    public User loginUser(String email, String password) {
        for (User user : userList) {
            if (user.getEmail().equalsIgnoreCase(email) && user.checkPassword(password)) {
                System.out.println("Login successful!");
                return user;
            }
        }
        System.out.println("Invalid email or password.");
        return null;
    }

    public void addItem(User user, String name, String size, String condition, String category) {
        Item item = new Item(name, size, condition, category);
        user.addItem(item);
        itemList.add(item);
        System.out.println("Item added successfully with ID: " + item.getId());
    }
    
    @SuppressWarnings("unused")
    public void browseItems(String filterCategory, String filterSize, String filterCondition) {
        
        JPanel browsePanel = new JPanel(new BorderLayout());
        
        
        String[] columnNames = {"ID", "Name", "Size", "Condition", "Category", "Status"};
        
        
        Object[][] data = itemList.stream()
            .filter(item -> item.getStatus().equals("Available") &&
                   (filterCategory == null || item.getCategory().equalsIgnoreCase(filterCategory)) &&
                   (filterSize == null || item.getSize().equalsIgnoreCase(filterSize)) &&
                   (filterCondition == null || item.getCondition().equalsIgnoreCase(filterCondition)))
            .map(item -> new Object[]{item.getId(), item.getName(), item.getSize(), item.getCondition(), item.getCategory(), item.getStatus()})
            .toArray(Object[][]::new);
    
        // Create the table
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false); // Make the table read-only
        
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add the scroll pane to the panel
        browsePanel.add(scrollPane, BorderLayout.CENTER);
    
        // Add a back button to return to the dashboard
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));
        browsePanel.add(backButton, BorderLayout.SOUTH);
    
        // Add the browse panel to the main panel and switch to it
        mainPanel.add(browsePanel, "BrowseItems");
        cardLayout.show(mainPanel, "BrowseItems");
    }
    
    
    
    
    public void initiateSwap(User requester, int itemId) {
        for (Item item : itemList) {
            if (item.getId() == itemId && item.getStatus().equals("Available")) {
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Do you want to initiate a swap for this item?\n" + item.toString(),
                        "Confirm Swap",
                        JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    item.setStatus("Swapped");
                    requester.addItem(item); // Add item to the requester's inventory
                    System.out.println("Swap successful for item ID: " + itemId);
                    JOptionPane.showMessageDialog(frame, "Swap successful for item ID: " + itemId);
                } else {
                    JOptionPane.showMessageDialog(frame, "Swap canceled.");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Item not found or already swapped.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(SwapNStyle::new);
    }
}