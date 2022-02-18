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
import java.util.Objects;

public class RegisterCustomer {
    private JPanel panel1;
    private JTextField nameField;
    private JTextField emailField;
    private JButton submitButton;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JComboBox roomComboBox;
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;
    private JButton cancelButton;
    private JFrame frame;
    private ButtonGroup buttonGroup;

    public RegisterCustomer(Manager user) {
        frame = new JFrame();
        $$$setupUI$$$();
        frame.setTitle(Constants.PAGE_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(maleRadioButton);
        buttonGroup.add(femaleRadioButton);
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
                JOptionPane.showMessageDialog(null, "Customer has been registered successfully!", "Registration Complete", JOptionPane.PLAIN_MESSAGE);
                Customer.addNewCustomer(compileNewData());
                logRegistration(user.getName(), nameField.getText());
                resetData();
            }
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new ManagerMenu(user);
        });
    }

    //Gather data from UI Componenets into a Customer Object
    private Customer compileNewData() {
        int unqiueID = Customer.fetchCustomerCount() + 1001;
        String DOB = yearComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() + "/" + dayComboBox.getSelectedItem();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";
        return new Customer(Integer.toString(unqiueID),
                nameField.getText(),
                "Customer",
                emailField.getText(),
                DOB,
                gender,
                Integer.parseInt((String) Objects.requireNonNull(roomComboBox.getSelectedItem())));
    }

    private int validateInput() {
        if (nameField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please input a name!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (emailField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please input an email address!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (!maleRadioButton.isSelected() && !femaleRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select customer gender!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (!emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(null, "Please input a valid email address!", "Registration Failure", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        return 0;
    }

    //AuditLogs.txt entry
    private void logRegistration(String managerName, String name) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime date = LocalDateTime.now();
        String logDate = "[" + formatter.format(date) + "] ";
        try {
            FileWriter fileWriter = new FileWriter(Constants.LOG_FILE, true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            file.write(logDate + name + " Successfully Registered By Manager " + managerName + "\n");
            file.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Empty fields to prepare for new entry
    private void resetData() {
        nameField.setText("");
        emailField.setText("");
        roomComboBox.setSelectedIndex(0);
        maleRadioButton.setSelected(false);
        femaleRadioButton.setSelected(false);
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
        label1.setText("Customer Registration");
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
        gbc.gridy = 0;
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
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        panel3.add(label3, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16777216));
        label4.setText("Gender:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        panel3.add(label4, gbc);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(150, 25));
        nameField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 0);
        panel3.add(nameField, gbc);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(49, 25));
        emailField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel3.add(emailField, gbc);
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-16777216));
        label5.setText("Room:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        panel3.add(label5, gbc);
        maleRadioButton = new JRadioButton();
        maleRadioButton.setForeground(new Color(-16777216));
        maleRadioButton.setHideActionText(false);
        maleRadioButton.setOpaque(false);
        maleRadioButton.setText("Male");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel3.add(maleRadioButton, gbc);
        femaleRadioButton = new JRadioButton();
        femaleRadioButton.setForeground(new Color(-16777216));
        femaleRadioButton.setHideActionText(false);
        femaleRadioButton.setOpaque(false);
        femaleRadioButton.setText("Female");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel3.add(femaleRadioButton, gbc);
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-16777216));
        label6.setText("Birthdate:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 5, 10);
        panel3.add(label6, gbc);
        roomComboBox = new JComboBox();
        roomComboBox.setMinimumSize(new Dimension(81, 38));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("1");
        defaultComboBoxModel1.addElement("2");
        defaultComboBoxModel1.addElement("3");
        defaultComboBoxModel1.addElement("4");
        defaultComboBoxModel1.addElement("5");
        defaultComboBoxModel1.addElement("6");
        defaultComboBoxModel1.addElement("7");
        defaultComboBoxModel1.addElement("8");
        defaultComboBoxModel1.addElement("9");
        defaultComboBoxModel1.addElement("10");
        defaultComboBoxModel1.addElement("11");
        defaultComboBoxModel1.addElement("12");
        defaultComboBoxModel1.addElement("13");
        defaultComboBoxModel1.addElement("14");
        defaultComboBoxModel1.addElement("15");
        defaultComboBoxModel1.addElement("16");
        defaultComboBoxModel1.addElement("17");
        defaultComboBoxModel1.addElement("18");
        defaultComboBoxModel1.addElement("19");
        defaultComboBoxModel1.addElement("20");
        roomComboBox.setModel(defaultComboBoxModel1);
        roomComboBox.setPreferredSize(new Dimension(76, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 0);
        panel3.add(roomComboBox, gbc);
        monthComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel3.add(monthComboBox, gbc);
        dayComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 0);
        panel3.add(dayComboBox, gbc);
        yearComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel3.add(yearComboBox, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel4.setBackground(new Color(-1));
        panel4.setPreferredSize(new Dimension(0, 90));
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

