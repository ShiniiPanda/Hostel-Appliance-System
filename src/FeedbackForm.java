import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedbackForm {
    private JPanel panel1;
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel footerPanel;
    private JPanel inputPanel;
    private JComboBox appointmentComboBox;
    private JTextField applianceField;
    private JRadioButton rating1;
    private JRadioButton rating2;
    private JRadioButton rating3;
    private JRadioButton rating4;
    private JRadioButton rating5;
    private JTextArea reviewTextArea;
    private JButton submitFeedbackButton;
    private JButton cancelButton;
    private JPanel in;
    private ButtonGroup ratingGroup;
    private DefaultComboBoxModel comboBoxModel;
    private Appointment chosenAppointment;
    private List<Appointment> appointmentList;

    //Accessed through technician menu
    public FeedbackForm(Technician user) {
        appointmentList = user.fetchIndividualAppointments("COMPLETE");
        JFrame frame = new JFrame();
        $$$setupUI$$$();
        initializeRadioButtons();
        frame.setTitle(Constants.PAGE_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setVisible(true);
        populateAppointmentList();
        appointmentComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (appointmentComboBox.getSelectedIndex() == 0) {
                    resetFeedbackData();
                } else {
                    chosenAppointment = appointmentList.get(appointmentComboBox.getSelectedIndex() - 1);
                    displayAppliance();
                }
            }
        });
        submitFeedbackButton.addActionListener(e -> {
            if (appointmentComboBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Please select an appointment!", "Feedback Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                Feedback.addNewFeedback(compileFeedbackData());
                JOptionPane.showMessageDialog(null, "Review has been submitted successfully!", "Success!", JOptionPane.PLAIN_MESSAGE);
                resetFeedbackData();
            }
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new TechnicianMenu(user);
        });
    }

    //Accessed through appointment list
    public FeedbackForm(Technician user, Appointment appointment) {
        chosenAppointment = appointment;
        JFrame frame = new JFrame();
        $$$setupUI$$$();
        initializeRadioButtons();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setVisible(true);
        String appointmentDisplay = chosenAppointment.getCustomer().getName() + " - " + chosenAppointment.getId();
        System.out.println(appointmentDisplay);
        comboBoxModel.setSelectedItem(appointmentDisplay);
        appointmentComboBox.setEnabled(false);
        displayAppliance();
        submitFeedbackButton.addActionListener(e -> {
            Feedback.addNewFeedback(compileFeedbackData());
            JOptionPane.showMessageDialog(null, "Review has been submitted successfully!", "Success!", JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            frame.dispose();
        });
    }

    private void initializeRadioButtons() {
        ratingGroup = new ButtonGroup();
        ratingGroup.add(rating1);
        ratingGroup.add(rating2);
        ratingGroup.add(rating3);
        ratingGroup.add(rating4);
        ratingGroup.add(rating5);
        rating5.setSelected(true);
    }

    private void displayAppliance() {
        applianceField.setText(chosenAppointment.getAppliance().getName());
    }

    private void populateAppointmentList() {
        comboBoxModel.addElement("--Select Appointment--");
        for (Appointment appointment : appointmentList) {
            comboBoxModel.addElement(appointment.getCustomer().getName() + " - " + appointment.getId());
        }
    }

    private void resetFeedbackData() {
        if (appointmentComboBox.getSelectedIndex() != 0) {
            appointmentComboBox.setSelectedIndex(0);
        }
        applianceField.setText("");
        reviewTextArea.setText("");
        rating5.setSelected(true);
    }

    //Gathers information from UI Components and returns a Feedback Object
    private Feedback compileFeedbackData() {
        int rating = rating1.isSelected() ? 1 :
                (rating2.isSelected() ? 2 :
                        (rating3.isSelected() ? 3 :
                                (rating4.isSelected() ? 4 : 5)));
        String reviewContent = reviewTextArea.getText().equals("") ? "No Message" :
                reviewTextArea.getText().replaceAll("[\\t\\n\\r]+", " "); // Regex to replace new lines and tabs with space
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime date = LocalDateTime.now();
        return new Feedback(chosenAppointment.getCustomer(),
                chosenAppointment.getAppliance(),
                chosenAppointment,
                timeFormat.format(date),
                rating,
                reviewContent);
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
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout(0, 0));
        titlePanel.setBackground(new Color(-4605511));
        titlePanel.setPreferredSize(new Dimension(0, 50));
        panel1.add(titlePanel, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Serif", Font.BOLD, 28, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setHorizontalAlignment(0);
        label1.setText("Feedback");
        titlePanel.add(label1, BorderLayout.CENTER);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        panel1.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-15000805)));
        footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        footerPanel.setBackground(new Color(-1));
        footerPanel.setPreferredSize(new Dimension(0, 90));
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        submitFeedbackButton = new JButton();
        submitFeedbackButton.setBackground(new Color(-1));
        Font submitFeedbackButtonFont = this.$$$getFont$$$(null, -1, 14, submitFeedbackButton.getFont());
        if (submitFeedbackButtonFont != null) submitFeedbackButton.setFont(submitFeedbackButtonFont);
        submitFeedbackButton.setForeground(new Color(-16720892));
        submitFeedbackButton.setPreferredSize(new Dimension(300, 30));
        submitFeedbackButton.setText("Submit Feedback");
        footerPanel.add(submitFeedbackButton);
        cancelButton = new JButton();
        cancelButton.setBackground(new Color(-1));
        Font cancelButtonFont = this.$$$getFont$$$(null, -1, 14, cancelButton.getFont());
        if (cancelButtonFont != null) cancelButton.setFont(cancelButtonFont);
        cancelButton.setForeground(new Color(-3864057));
        cancelButton.setPreferredSize(new Dimension(200, 30));
        cancelButton.setText("Cancel");
        footerPanel.add(cancelButton);
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(new Color(-1));
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.PLAIN, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16777216));
        label2.setPreferredSize(new Dimension(110, 22));
        label2.setText("Appointment:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 10, 10);
        inputPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial", Font.PLAIN, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16777216));
        label3.setText("Appliance:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 5, 10, 10);
        inputPanel.add(label3, gbc);
        appointmentComboBox.setPreferredSize(new Dimension(180, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 10, 5);
        inputPanel.add(appointmentComboBox, gbc);
        applianceField = new JTextField();
        applianceField.setDisabledTextColor(new Color(-16777216));
        applianceField.setEditable(false);
        applianceField.setHorizontalAlignment(0);
        applianceField.setPreferredSize(new Dimension(180, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(applianceField, gbc);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.PLAIN, 18, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-16777216));
        label4.setText("Rating:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 5, 5, 10);
        inputPanel.add(label4, gbc);
        rating1 = new JRadioButton();
        rating1.setFocusable(false);
        rating1.setForeground(new Color(-16777216));
        rating1.setHorizontalTextPosition(0);
        rating1.setOpaque(false);
        rating1.setText("1");
        rating1.setVerticalTextPosition(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(rating1, gbc);
        rating2 = new JRadioButton();
        rating2.setFocusable(false);
        rating2.setForeground(new Color(-16777216));
        rating2.setHorizontalTextPosition(0);
        rating2.setOpaque(false);
        rating2.setText("2");
        rating2.setVerticalTextPosition(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(rating2, gbc);
        rating3 = new JRadioButton();
        rating3.setFocusable(false);
        rating3.setForeground(new Color(-16777216));
        rating3.setHorizontalTextPosition(0);
        rating3.setOpaque(false);
        rating3.setText("3");
        rating3.setVerticalTextPosition(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(rating3, gbc);
        rating4 = new JRadioButton();
        rating4.setFocusable(false);
        rating4.setForeground(new Color(-16777216));
        rating4.setHorizontalTextPosition(0);
        rating4.setOpaque(false);
        rating4.setText("4");
        rating4.setVerticalTextPosition(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(rating4, gbc);
        rating5 = new JRadioButton();
        rating5.setFocusable(false);
        rating5.setForeground(new Color(-16777216));
        rating5.setHorizontalTextPosition(0);
        rating5.setOpaque(false);
        rating5.setText("5");
        rating5.setVerticalTextPosition(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(rating5, gbc);
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Arial", Font.PLAIN, 18, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-16777216));
        label5.setText("Review:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 5, 5, 10);
        inputPanel.add(label5, gbc);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setPreferredSize(new Dimension(250, 90));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 5, 5);
        inputPanel.add(reviewTextArea, gbc);
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
        comboBoxModel = new DefaultComboBoxModel();
        appointmentComboBox = new JComboBox(comboBoxModel);
        reviewTextArea = new JTextArea();
        Border reviewBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        reviewTextArea.setBorder(reviewBorder);
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);

    }
}
