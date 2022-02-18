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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RegisterAppointment {
    private JPanel panel1;
    private JPanel mainPanel;
    private JPanel footerPanel;
    private JPanel inputPanel;
    private JComboBox customerComboBox;
    private JComboBox applianceComboBox;
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;
    private JTextField durationField;
    private JComboBox technicianComboBox;
    private JButton submitButton;
    private JButton cancelButton;
    private LocalDate currentDate;
    private DefaultComboBoxModel customerBoxModel;
    private DefaultComboBoxModel applianceBoxModel;
    private DefaultComboBoxModel technicianBoxModel;
    private List<Customer> customerList;
    private List<HomeAppliance> applianceList;
    private List<Technician> technicianList;

    public RegisterAppointment(Manager user) {
        $$$setupUI$$$();
        customerList = Customer.fetchAllCustomers();
        applianceList = HomeAppliance.fetchAllAppliance();
        technicianList = Technician.fetchAllTechnicians();
        JFrame frame = new JFrame();
        frame.setTitle(Constants.PAGE_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
        populateCustomerComboBox();
        populateApplianceComboBox();
        populateTechnicianComboBox();
        submitButton.addActionListener(e -> {
            if (validateInput() == 0) {
                Appointment newAppointment = compileAppointmentData();
                Appointment.addNewAppointment(newAppointment);
                JOptionPane.showMessageDialog(null, "Appointment has been registered successfully!", "Success!", JOptionPane.PLAIN_MESSAGE);
                logAppointment(user.getName(), newAppointment);
                resetData();
            }
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new ManagerMenu(user);
        });
        monthComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object month = e.getItem();
                    String monthValue = (String) month;
                    Object year = yearComboBox.getSelectedItem();
                    int yearValue = (int) year;
                    if (monthValue.equals("02")) { // Special case for February (different number of days)
                        if (yearValue % 4 == 0) { // Leap Year
                            dayComboBox.setModel(new DefaultComboBoxModel(Constants.days29));
                        } else { // Normal Year
                            dayComboBox.setModel(new DefaultComboBoxModel(Constants.days28));
                        }
                    } else if (Arrays.stream(Constants.months31).anyMatch(monthValue::equals)) { // If month has 31 days
                        dayComboBox.setModel(new DefaultComboBoxModel(Constants.days31));
                    } else {
                        dayComboBox.setModel(new DefaultComboBoxModel(Constants.days30)); // Normal 30 days months
                    }
                }
            }
        });
        yearComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int yearValue = (int) e.getItem();
                    Object month = monthComboBox.getSelectedItem();
                    String monthValue = (String) month;
                    if (yearValue % 4 == 0) {
                        if (monthValue.equals("02")) { // Leap Year Validation
                            dayComboBox.setModel(new DefaultComboBoxModel(Constants.days29));
                        }
                    }
                }
            }
        });
    }

    private void populateCustomerComboBox() {
        customerBoxModel.addElement("--Select Customer--");
        for (Customer customer : customerList) {
            customerBoxModel.addElement(customer.getName() + " - " + customer.getId());
        }
    }

    private void populateApplianceComboBox() {
        applianceBoxModel.addElement("--Select Appliance--");
        for (HomeAppliance appliance : applianceList) {
            applianceBoxModel.addElement(appliance.getName());
        }
    }

    private void populateTechnicianComboBox() {
        technicianBoxModel.addElement("--Select Technician--");
        for (Technician technician : technicianList) {
            technicianBoxModel.addElement(technician.getName());
        }
    }

    //Empty fields to prepare for new entry
    private void resetData() {
        customerComboBox.setSelectedIndex(0);
        applianceComboBox.setSelectedIndex(0);
        technicianComboBox.setSelectedIndex(0);
        durationField.setText("");
    }

    private int validateInput() {
        int duration;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate chosenDate = LocalDate.parse(yearComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() +
                "/" + dayComboBox.getSelectedItem(), formatter);
        if (customerComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select a customer from the list!", "Failure!", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (applianceComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select an appliance from the list!", "Failure!", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (technicianComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select a technician from the list!", "Failure!", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        if (chosenDate.isBefore(currentDate)) {
            JOptionPane.showMessageDialog(null, "Please do not select a preceding start date", "Failure!", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        try {
            duration = Integer.parseInt(durationField.getText());
            if (duration <= 0) {
                JOptionPane.showMessageDialog(null, "Please make sure to input a positive number of days", "Failure!", JOptionPane.ERROR_MESSAGE);
                return 1;
            }
            if (duration > 365) {
                JOptionPane.showMessageDialog(null, "Duration cannot exceed a year (365 days)!", "Failure!", JOptionPane.ERROR_MESSAGE);
                return 1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please input a valid numerical duration (days)", "Failure!", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        return 0;
    }

    //Gather Appointment info from UI Components and compile into a Appointment Object
    private Appointment compileAppointmentData() {
        int uniqueId = Appointment.fetchAppointmentCount() + 1501;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String startDate = yearComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() + "/" + dayComboBox.getSelectedItem();
        LocalDate endDate = currentDate.plusDays(Integer.parseInt(durationField.getText()));
        return new Appointment(Integer.toString(uniqueId),
                customerList.get(customerComboBox.getSelectedIndex() - 1),
                technicianList.get(technicianComboBox.getSelectedIndex() - 1),
                "PENDING",
                startDate,
                formatter.format(endDate),
                applianceList.get(applianceComboBox.getSelectedIndex() - 1));
    }

    //AuditLogs.txt entry
    private void logAppointment(String managerName, Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime date = LocalDateTime.now();
        String logDate = "[" + formatter.format(date) + "] ";
        try {
            FileWriter fileWriter = new FileWriter(Constants.LOG_FILE, true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            file.write(logDate + "New Appointment (" + appointment.getId() + ") for Customer (" +
                    appointment.getCustomer().getName() + ") registered by Manager (" + managerName + ")\n");
            file.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Serif", Font.BOLD, 28, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Appointment Registration");
        panel2.add(label1, BorderLayout.CENTER);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        panel1.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-15000805)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        footerPanel.setBackground(new Color(-1));
        footerPanel.setPreferredSize(new Dimension(0, 80));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        submitButton = new JButton();
        submitButton.setPreferredSize(new Dimension(150, 30));
        submitButton.setText("Submit");
        footerPanel.add(submitButton);
        cancelButton = new JButton();
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setText("Cancel");
        footerPanel.add(cancelButton);
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(-1));
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16777216));
        label2.setText("Customer:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 10, 10);
        inputPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16777216));
        label3.setText("Appliance:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        inputPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16777216));
        label4.setText("Start Date:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        inputPanel.add(label4, gbc);
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-16777216));
        label5.setText("Duration:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 10, 10);
        inputPanel.add(label5, gbc);
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial", Font.PLAIN, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-16777216));
        label6.setText("Technician:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 5, 10);
        inputPanel.add(label6, gbc);
        customerComboBox.setPreferredSize(new Dimension(200, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 5);
        inputPanel.add(customerComboBox, gbc);
        applianceComboBox.setPreferredSize(new Dimension(76, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 5);
        inputPanel.add(applianceComboBox, gbc);
        yearComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 5);
        inputPanel.add(yearComboBox, gbc);
        monthComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(monthComboBox, gbc);
        durationField = new JTextField();
        durationField.setPreferredSize(new Dimension(49, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 0);
        inputPanel.add(durationField, gbc);
        final JLabel label7 = new JLabel();
        label7.setForeground(new Color(-16777216));
        label7.setText("Days");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(label7, gbc);
        technicianComboBox.setPreferredSize(new Dimension(150, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 5, 5);
        inputPanel.add(technicianComboBox, gbc);
        dayComboBox.setPreferredSize(new Dimension(70, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(dayComboBox, gbc);
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
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);
        customerBoxModel = new DefaultComboBoxModel();
        applianceBoxModel = new DefaultComboBoxModel();
        technicianBoxModel = new DefaultComboBoxModel();
        customerComboBox = new JComboBox(customerBoxModel);
        applianceComboBox = new JComboBox(applianceBoxModel);
        technicianComboBox = new JComboBox(technicianBoxModel);

        currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        yearComboBox = new JComboBox();
        yearComboBox.addItem(currentYear);
        yearComboBox.addItem(currentYear + 1);
        monthComboBox = new JComboBox(Constants.months);
        dayComboBox = new JComboBox(Constants.days31);
    }
}
