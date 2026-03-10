package campusconnect.view;

import campusconnect.controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {

    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);

    private AuthController authController;
    private final JFrame parent;

    public RegisterFrame(JFrame parent) {

        this.parent = parent;

        try {
            authController = new AuthController();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to initialize authentication system.",
                    "System Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        setTitle("Campus Connect - Register");
        setSize(420, 280);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                parent.setEnabled(true);
                parent.toFront();
            }

            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                parent.setEnabled(true);
                parent.toFront();
            }
        });

        initComponents();
    }

    private void initComponents() {

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel("Create Student Account", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));

        root.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 6, 6, 6);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        form.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        form.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        form.add(passwordField, gbc);

        root.add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel();

        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        registerButton.addActionListener(e -> doRegister());

        cancelButton.addActionListener(e -> {
            dispose();
            parent.setEnabled(true);
            parent.toFront();
        });

        actions.add(registerButton);
        actions.add(cancelButton);

        root.add(actions, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private void doRegister() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.length() < 3 || password.length() < 4) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username must be at least 3 characters and password at least 4 characters.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {

            authController.register(username, password);

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful. Please login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            parent.setEnabled(true);
            parent.toFront();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unexpected error occurred.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }
}
