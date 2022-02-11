import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;

public class Payment {
    private JPanel panel1;
    private JLabel title;
    private JPanel mainPanel;
    private JPanel footerPanel;
    private JPanel inputPanel;
    private JComboBox appointmentComboBox;
    private DefaultComboBoxModel comboBoxModel;
    private JTextField applianceField;
    private JTextField durationField;
    private JTextField feeField;
    private JTextField totalField;
    private JButton cancelButton;
    private JButton payButton;
    private Appointment chosenAppointment;
    private List<Appointment> appointmentList;

    public Payment(Technician user) {
        appointmentList = Appointment.fetchIndividualAppointments(user, "PENDING");
        JFrame frame = new JFrame();
        $$$setupUI$$$();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
        populateAppointmentComboBox();
        appointmentComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (appointmentComboBox.getSelectedIndex() == 0) {
                    resetPaymentData();
                } else {
                    chosenAppointment = appointmentList.get(appointmentComboBox.getSelectedIndex() - 1);
                    displayPaymentData();
                }
            }
        });
        payButton.addActionListener(e -> {
            if (appointmentComboBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Please select an appointment!", "Payment Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                chosenAppointment.setStatus("COMPLETE");
                Appointment.updateAppointment(chosenAppointment);
                if (JOptionPane.showConfirmDialog(null, "Payment Successful, Would you like to add feedback?",
                        "Payment Successful!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    new FeedbackForm(user, chosenAppointment);
                }
                Transaction.addNewTransaction(compileTransactionData());
                comboBoxModel.removeElementAt(appointmentComboBox.getSelectedIndex());
                appointmentList.remove(appointmentComboBox.getSelectedIndex());
                resetPaymentData();
            }
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new TechnicianMenu(user);
        });
    }

    public Payment(Technician user, Appointment appointment) {
        chosenAppointment = appointment;
        JFrame frame = new JFrame();
        $$$setupUI$$$();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
        String appointmentDisplay = chosenAppointment.getCustomer().getName() + " - " + chosenAppointment.getId();
        System.out.println(appointmentDisplay);
        comboBoxModel.setSelectedItem(appointmentDisplay);
        appointmentComboBox.setEnabled(false);
        displayPaymentData();
        payButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "Would you like to proceed with payment?",
                    "Payment Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                chosenAppointment.setStatus("COMPLETE");
                Appointment.updateAppointment(chosenAppointment);
                if (JOptionPane.showConfirmDialog(null, "Payment Successful, Would you like to add feedback?",
                        "Payment Successful!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    new FeedbackForm(user, chosenAppointment);
                }
                Transaction.addNewTransaction(compileTransactionData());
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Payment operation has been cancelled",
                        "Payment Failure", JOptionPane.ERROR_MESSAGE);
                frame.dispose();
            }
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        comboBoxModel = new DefaultComboBoxModel();
        appointmentComboBox = new JComboBox(comboBoxModel);
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);
    }

    private void populateAppointmentComboBox() {
        comboBoxModel.addElement("--Select Appointment--");
        for (Appointment appointment : appointmentList) {
            comboBoxModel.addElement(appointment.getCustomer().getName() + " - " + appointment.getId());
        }
    }

    private void resetPaymentData() {
        if (appointmentComboBox.getSelectedIndex() != 0) {
            appointmentComboBox.setSelectedIndex(0);
        }
        applianceField.setText("");
        durationField.setText("");
        feeField.setText("");
        totalField.setText("");
    }

    private void displayPaymentData() {
        float fee, total;
        long durationInDays;
        HomeAppliance appliance = chosenAppointment.getAppliance();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate startDate = LocalDate.parse(chosenAppointment.getStartDate(), timeFormat);
        LocalDate endDate = LocalDate.parse(chosenAppointment.getEndDate(), timeFormat);
        //System.out.println(endDate + "\n" + startDate);
        durationInDays = DAYS.between(startDate, endDate);
        fee = appliance.getPricePerDay();
        total = durationInDays * fee;
        applianceField.setText(appliance.getName());
        durationField.setText(durationInDays + " Days");
        feeField.setText(fee + " RM");
        totalField.setText(total + "");
    }

    private Transaction compileTransactionData() {
        int uniqueId = Transaction.fetchTransactionCount() + 10001;
        return new Transaction(Integer.toString(uniqueId),
                chosenAppointment,
                Float.parseFloat(totalField.getText()));
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
        title = new JLabel();
        Font titleFont = this.$$$getFont$$$("Serif", Font.BOLD, 28, title.getFont());
        if (titleFont != null) title.setFont(titleFont);
        title.setForeground(new Color(-16777216));
        title.setHorizontalAlignment(0);
        title.setText("Payment Collection");
        panel2.add(title, BorderLayout.CENTER);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        panel1.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-15000805)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        footerPanel.setBackground(new Color(-1));
        footerPanel.setPreferredSize(new Dimension(0, 100));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        payButton = new JButton();
        payButton.setBackground(new Color(-1));
        payButton.setBorderPainted(true);
        payButton.setForeground(new Color(-16720892));
        payButton.setPreferredSize(new Dimension(350, 30));
        payButton.setText("Pay");
        footerPanel.add(payButton);
        cancelButton = new JButton();
        cancelButton.setBackground(new Color(-1));
        cancelButton.setForeground(new Color(-3864057));
        cancelButton.setPreferredSize(new Dimension(200, 30));
        cancelButton.setText("Cancel");
        footerPanel.add(cancelButton);
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(-1));
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setText("Appointment:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 10, 10);
        inputPanel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16777216));
        label2.setText("Appliance:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 10);
        inputPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16777216));
        label3.setText("Duration:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 10);
        inputPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16777216));
        label4.setText("Fee Per Day:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 10);
        inputPanel.add(label4, gbc);
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-16777216));
        label5.setText("Total:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 10);
        inputPanel.add(label5, gbc);
        appointmentComboBox.setForeground(new Color(-16777216));
        appointmentComboBox.setPreferredSize(new Dimension(200, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 10, 5);
        inputPanel.add(appointmentComboBox, gbc);
        applianceField = new JTextField();
        applianceField.setDisabledTextColor(new Color(-16777216));
        applianceField.setEditable(false);
        applianceField.setEnabled(true);
        applianceField.setHorizontalAlignment(0);
        applianceField.setPreferredSize(new Dimension(50, 26));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(applianceField, gbc);
        durationField = new JTextField();
        durationField.setDisabledTextColor(new Color(-16777216));
        durationField.setEditable(false);
        durationField.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(durationField, gbc);
        feeField = new JTextField();
        feeField.setDisabledTextColor(new Color(-16777216));
        feeField.setEditable(false);
        feeField.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(feeField, gbc);
        totalField = new JTextField();
        totalField.setDisabledTextColor(new Color(-16777216));
        totalField.setEditable(false);
        totalField.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(totalField, gbc);
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

}
