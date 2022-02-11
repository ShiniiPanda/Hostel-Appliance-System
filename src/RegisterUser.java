import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegisterUser {
    private JPanel panel1;
    private JTextField nameField;
    private JTextField emailField;
    private JButton submitButton;
    private JComboBox accessComboBox;
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;
    private JButton cancelButton;
    private JTextField usernameField;
    private JTextField passwordField;
    private JFrame frame;

    public RegisterUser(Manager user) {
        frame = new JFrame();
        $$$setupUI$$$();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
        monthComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object month = e.getItem();
                    String monthValue = (String) month;
                    Object year = yearComboBox.getSelectedItem();
                    String yearValue = (String) year;
                    if (monthValue.equals("02")) {
                        if (Integer.parseInt(yearValue) % 4 == 0) {
                            dayComboBox.setModel(new DefaultComboBoxModel(Constants.days29));
                        } else {
                            dayComboBox.setModel(new DefaultComboBoxModel(Constants.days28));
                        }
                    } else if (Arrays.stream(Constants.months31).anyMatch(monthValue::equals)) {
                        dayComboBox.setModel(new DefaultComboBoxModel(Constants.days31));
                    } else {
                        dayComboBox.setModel(new DefaultComboBoxModel(Constants.days30));
                    }
                }
            }
        });
        yearComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object year = e.getItem();
                    String yearValue = (String) year;
                    Object month = monthComboBox.getSelectedItem();
                    String monthValue = (String) month;
                    if (Integer.parseInt(yearValue) % 4 == 0) {
                        if (monthValue.equals("02")) {
                            dayComboBox.setModel(new DefaultComboBoxModel(Constants.days29));
                        }
                    }
                }
            }
        });
        submitButton.addActionListener(e -> {
            if (validateInput() == 0) {
                JOptionPane.showMessageDialog(null, "System User has been registered successfully!", "Registration Complete", JOptionPane.PLAIN_MESSAGE);
                User.addNewUser(compileNewData());
                logRegistration(user.getName(), nameField.getText());
                resetData();
            }
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
        });
    }


    private User compileNewData() {
        String DOB = yearComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() + "/" + dayComboBox.getSelectedItem();
        return new User(usernameField.getText(),
                passwordField.getText(),
                nameField.getText(),
                (String) accessComboBox.getSelectedItem(),
                emailField.getText(),
                DOB);
    }

    private int validateInput() {
        if (usernameField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please input a username!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (passwordField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please input a password!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (!Pattern.matches(Constants.passwordRegEx, passwordField.getText())) {
            JOptionPane.showMessageDialog(null, "Please make sure the password contains at least:\nOne Lowercase Letter\nOne Uppercase Letter\nOne number",
                    "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (nameField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please input a name!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (emailField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please input an email address!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (!emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(null, "Please input a valid email address!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        return 0;
    }

    private void logRegistration(String managerName, String name) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime date = LocalDateTime.now();
        String logDate = "[" + formatter.format(date) + "] ";
        try {
            FileWriter fileWriter = new FileWriter("./TextFiles/AuditLogs.txt", true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            file.write(logDate + "New " + (String) accessComboBox.getSelectedItem() + name + " Successfully Registered By Manager: " + managerName + "\n");
            file.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void resetData() {
        usernameField.setText("");
        passwordField.setText("");
        nameField.setText("");
        emailField.setText("");
        accessComboBox.setSelectedIndex(0);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-4605511));
        panel2.setPreferredSize(new Dimension(0, 50));
        panel1.add(panel2, BorderLayout.NORTH);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Serif", Font.BOLD, 28, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("System User Registration");
        panel2.add(label1, BorderLayout.CENTER);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setBackground(new Color(-1));
        panel1.add(panel3, BorderLayout.CENTER);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16777216));
        label2.setText("Name:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 10, 10);
        panel3.add(label2, gbc);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16777216));
        label3.setText("Email:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        panel3.add(label3, gbc);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(150, 25));
        nameField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 0);
        panel3.add(nameField, gbc);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(49, 25));
        emailField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel3.add(emailField, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16777216));
        label4.setText("Access Level:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        panel3.add(label4, gbc);
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-16777216));
        label5.setText("Birthdate:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 5, 10);
        panel3.add(label5, gbc);
        accessComboBox = new JComboBox();
        accessComboBox.setMinimumSize(new Dimension(81, 38));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Manager");
        defaultComboBoxModel1.addElement("Technician");
        defaultComboBoxModel1.addElement("Denied");
        accessComboBox.setModel(defaultComboBoxModel1);
        accessComboBox.setPreferredSize(new Dimension(100, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 5);
        panel3.add(accessComboBox, gbc);
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-16777216));
        label6.setText("Password:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 10, 10);
        panel3.add(label6, gbc);
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setForeground(new Color(-16777216));
        label7.setText("Username:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 10, 10);
        panel3.add(label7, gbc);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 25));
        usernameField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 0);
        panel3.add(usernameField, gbc);
        passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(150, 25));
        passwordField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 0);
        panel3.add(passwordField, gbc);
        monthComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel3.add(monthComboBox, gbc);
        dayComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 0);
        panel3.add(dayComboBox, gbc);
        yearComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel3.add(yearComboBox, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel4.setBackground(new Color(-1));
        panel4.setPreferredSize(new Dimension(0, 80));
        panel1.add(panel4, BorderLayout.SOUTH);
        submitButton = new JButton();
        submitButton.setForeground(new Color(-16777216));
        submitButton.setPreferredSize(new Dimension(200, 30));
        submitButton.setText("Submit");
        panel4.add(submitButton);
        cancelButton = new JButton();
        cancelButton.setPreferredSize(new Dimension(150, 30));
        cancelButton.setText("Cancel");
        panel4.add(cancelButton);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        yearComboBox = new JComboBox(Constants.years);
        monthComboBox = new JComboBox(Constants.months);
        dayComboBox = new JComboBox(Constants.days31);
    }

}

