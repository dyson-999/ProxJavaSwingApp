package ManageShipmentsForm;

import javax.swing.*;

public class ManageShipmentsFormui {
    public JPanel mainPanel;
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField3;
    public JTextField textField4;
    public JComboBox<String> comboBox1;
    public JButton addButton;
    public JButton updateButton;
    public JButton deleteButton;
    public JButton clearButton;
    private JTextField SHIPMENTFORMTextField;

    public ManageShipmentsFormui() {
        comboBox1.addItem("Pending");
        comboBox1.addItem("In Transit");
        comboBox1.addItem("Delivered");
        comboBox1.addItem("Cancelled");
    }
}
