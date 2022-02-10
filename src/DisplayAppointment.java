import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class DisplayAppointment {

    final String[] columns = {"Customer", "Status", "Start Date", "End Date", "Appliance"};
    private DefaultTableModel tableModel;
    private JPanel panel1;
    private JPanel mainPanel;
    private JComboBox statusComboBox;
    private JTable table;
    private JPanel tabelPanel;
    private JScrollPane tableScrollPane;
    private JButton updateTableButton;
    private JPanel tableRightGap;
    private JPanel tableLeftGap;
    private JPanel bottomPanel;
    private JButton printCurrentRowButton;
    private JButton collectPaymentButton;
    private JButton returnToMenuButton;
    private JButton addFeedbackButton;
    private Technician currentUser;
    private List<Appointment> currentAppointments;

    public DisplayAppointment(Technician user) {
        currentUser = user;
        JFrame frame = new JFrame();
        $$$setupUI$$$();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(700, 400);
        frame.setMinimumSize(new Dimension(700, 400));
        updateTableButton.addActionListener(e -> {
            int statusIndex = statusComboBox.getSelectedIndex();
            updateTable(statusIndex);
        });
        collectPaymentButton.addActionListener(e -> {
            if (table.getSelectedRowCount() > 1) {
                JOptionPane.showMessageDialog(null, "Please select only one appointment from the list!", "Failure!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Please select an appointment from the list!", "Failure!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rowNumber = table.getSelectedRow();
            Appointment rowAppointment = currentAppointments.get(rowNumber);
            if (rowAppointment.getStatus().equals("PENDING")) {
                new Payment(user, rowAppointment);
            } else {
                JOptionPane.showMessageDialog(null, "Selected appointment is not pending payment!",
                        "Failure!", JOptionPane.ERROR_MESSAGE);
            }
        });
        addFeedbackButton.addActionListener(e -> {
            if (table.getSelectedRowCount() > 1) {
                JOptionPane.showMessageDialog(null, "Please select only one appointment from the list!", "Failure!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (table.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Please select an appointment from the list!", "Failure!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rowNumber = table.getSelectedRow();
            Appointment rowAppointment = currentAppointments.get(rowNumber);
            if (rowAppointment.getStatus().equals("COMPLETE")) {
                new FeedbackForm(user, rowAppointment);
            } else {
                JOptionPane.showMessageDialog(null, "Selected appointment must be complete before collecting feedback!",
                        "Failure!", JOptionPane.ERROR_MESSAGE);
            }
        });
        returnToMenuButton.addActionListener(e -> {
            frame.dispose();
            new TechnicianMenu(user);
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //Only the third column
                return false;
            }
        };
        table = new JTable(tableModel);
        tableModel.setDataVector(fetchData(), columns);
        table.setPreferredScrollableViewportSize(new Dimension(600, 200));
        table.setFillsViewportHeight(true);
        centerTableCells();

        tableScrollPane = new JScrollPane(table);


    }

    private Object[][] fetchData() {
        List<Appointment> appointmentList = Appointment.fetchIndividualAppointments(currentUser);
        currentAppointments = appointmentList;
        Object[][] data = new Object[appointmentList.size()][6];
        for (int i = 0; i < appointmentList.size(); i++) {
            data[i][0] = appointmentList.get(i).getCustomer().getName();
            data[i][1] = appointmentList.get(i).getStatus();
            data[i][2] = appointmentList.get(i).getStartDate();
            data[i][3] = appointmentList.get(i).getEndDate();
            data[i][4] = appointmentList.get(i).getAppliance().getName();
        }
        return data;
    }

    private Object[][] fetchData(String status) {
        List<Appointment> appointmentList = Appointment.fetchIndividualAppointments(currentUser, status);
        currentAppointments = appointmentList;
        Object[][] data = new Object[appointmentList.size()][6];
        for (int i = 0; i < appointmentList.size(); i++) {
            data[i][0] = appointmentList.get(i).getCustomer().getName();
            data[i][1] = appointmentList.get(i).getStatus();
            data[i][2] = appointmentList.get(i).getStartDate();
            data[i][3] = appointmentList.get(i).getEndDate();
            data[i][4] = appointmentList.get(i).getAppliance().getName();
        }
        return data;
    }

    private void centerTableCells() {
        DefaultTableCellRenderer centerCells = new DefaultTableCellRenderer();
        centerCells.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerCells);
        table.getColumnModel().getColumn(2).setCellRenderer(centerCells);
        table.getColumnModel().getColumn(3).setCellRenderer(centerCells);
        table.getColumnModel().getColumn(4).setCellRenderer(centerCells);
    }

    private void updateTable(int comboChoice) {
        Object[][] updatedData = new Object[10][6];
        switch (comboChoice) {
            case 0:
                updatedData = fetchData();
                break;
            case 1:
                updatedData = fetchData("PENDING");
                break;
            case 2:
                updatedData = fetchData("COMPLETE");
                break;
            case 3:
                updatedData = fetchData("CANCELLED");
                break;
        }
        tableModel.setDataVector(updatedData, columns);
        centerTableCells();
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
        panel1.setPreferredSize(new Dimension(700, 220));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-4605511));
        panel2.setPreferredSize(new Dimension(0, 50));
        panel1.add(panel2, BorderLayout.NORTH);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-4605511));
        Font label1Font = this.$$$getFont$$$("Serif", Font.BOLD, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setHorizontalAlignment(0);
        label1.setText("Appliance Appointments");
        panel2.add(label1, BorderLayout.CENTER);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        panel1.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-15000805)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel3.setBackground(new Color(-1));
        panel3.setForeground(new Color(-1));
        panel3.setPreferredSize(new Dimension(0, 40));
        mainPanel.add(panel3, BorderLayout.NORTH);
        statusComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("All Appointments");
        defaultComboBoxModel1.addElement("Pending Appointments");
        defaultComboBoxModel1.addElement("Completed Appointments");
        defaultComboBoxModel1.addElement("Cancelled Appointments");
        statusComboBox.setModel(defaultComboBoxModel1);
        statusComboBox.setPreferredSize(new Dimension(200, 25));
        panel3.add(statusComboBox);
        updateTableButton = new JButton();
        updateTableButton.setPreferredSize(new Dimension(120, 25));
        updateTableButton.setText("Update Table");
        panel3.add(updateTableButton);
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        bottomPanel.setBackground(new Color(-1));
        bottomPanel.setPreferredSize(new Dimension(0, 130));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        collectPaymentButton = new JButton();
        collectPaymentButton.setPreferredSize(new Dimension(500, 30));
        collectPaymentButton.setText("Collect Payment");
        bottomPanel.add(collectPaymentButton);
        addFeedbackButton = new JButton();
        addFeedbackButton.setPreferredSize(new Dimension(500, 30));
        addFeedbackButton.setText("Add Feedback");
        bottomPanel.add(addFeedbackButton);
        returnToMenuButton = new JButton();
        returnToMenuButton.setPreferredSize(new Dimension(300, 30));
        returnToMenuButton.setText("Return To Menu");
        bottomPanel.add(returnToMenuButton);
        tabelPanel = new JPanel();
        tabelPanel.setLayout(new BorderLayout(0, 0));
        tabelPanel.setBackground(new Color(-1));
        tabelPanel.setPreferredSize(new Dimension(20, 0));
        mainPanel.add(tabelPanel, BorderLayout.CENTER);
        tabelPanel.add(tableScrollPane, BorderLayout.CENTER);
        tableRightGap = new JPanel();
        tableRightGap.setLayout(new BorderLayout(0, 0));
        tableRightGap.setMaximumSize(new Dimension(10, 2147483647));
        tableRightGap.setPreferredSize(new Dimension(10, 0));
        tabelPanel.add(tableRightGap, BorderLayout.WEST);
        tableLeftGap = new JPanel();
        tableLeftGap.setLayout(new BorderLayout(0, 0));
        tableLeftGap.setMaximumSize(new Dimension(10, 2147483647));
        tableLeftGap.setPreferredSize(new Dimension(10, 0));
        tabelPanel.add(tableLeftGap, BorderLayout.EAST);
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
