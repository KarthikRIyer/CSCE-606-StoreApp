package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class OrderView extends JFrame {

    private JButton btnAdd = new JButton("Add a new item");
    private JButton btnPay = new JButton("Finish and pay");

    private JTextArea addressArea;
    private JTextArea billlingAddressArea;
    private JTextField creditCardNumber;
    private JTextField cvvField;
    private JTextField validThruMonth;
    private JTextField validThruYear;

    private DefaultTableModel items = new DefaultTableModel(); // store information for the table!

    private JTable tblItems = new JTable(items);
    private JLabel labTotal = new JLabel("Total: ");
    private Order order = null;

    public OrderView() {

        this.setTitle("Order View");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setSize(400, 600);


        items.addColumn("Product ID");
        items.addColumn("Name");
        items.addColumn("Price");
        items.addColumn("Quantity");
        items.addColumn("Cost");

        JPanel panelOrder = new JPanel();
        panelOrder.setPreferredSize(new Dimension(400, 450));
        panelOrder.setLayout(new BoxLayout(panelOrder, BoxLayout.PAGE_AXIS));
        tblItems.setBounds(0, 0, 400, 350);
        panelOrder.add(tblItems.getTableHeader());
        panelOrder.add(tblItems);
        panelOrder.add(labTotal);
        tblItems.setFillsViewportHeight(true);

        addressArea = new JTextArea();
        JLabel address = new JLabel("Shipping Address: ");
        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
        addressPanel.add(address);
        addressPanel.add(addressArea);
        panelOrder.add(addressPanel);

        JLabel ccNum = new JLabel("Credit Card number: ");
        creditCardNumber = new JTextField();
        JPanel ccPanel = new JPanel();
        ccPanel.setLayout(new BoxLayout(ccPanel, BoxLayout.X_AXIS));
        ccPanel.add(ccNum);
        ccPanel.add(creditCardNumber);
        panelOrder.add(ccPanel);

        JLabel cvv = new JLabel("CVV: ");
        cvvField = new JTextField();
        JPanel cvvPanel = new JPanel();
        cvvPanel.setLayout(new BoxLayout(cvvPanel, BoxLayout.X_AXIS));
        cvvPanel.add(cvv);
        cvvPanel.add(cvvField);
        panelOrder.add(cvvPanel);

        JLabel validThru = new JLabel("Valid Thru (MM YYYY):");
        validThruMonth = new JTextField();
        validThruYear = new JTextField();
        JPanel validThruPanel = new JPanel();
        validThruPanel.setLayout(new BoxLayout(validThruPanel, BoxLayout.X_AXIS));
        validThruPanel.add(validThru);
        validThruPanel.add(validThruMonth);
        validThruPanel.add(validThruYear);
        panelOrder.add(validThruPanel);

        billlingAddressArea = new JTextArea();
        JLabel billingAddress = new JLabel("Billing Address: ");
        JPanel billingAddressPanel = new JPanel();
        billingAddressPanel.setLayout(new BoxLayout(billingAddressPanel, BoxLayout.X_AXIS));
        billingAddressPanel.add(billingAddress);
        billingAddressPanel.add(billlingAddressArea);
        panelOrder.add(billingAddressPanel);


        this.getContentPane().add(panelOrder);

        JPanel panelButton = new JPanel();
        panelButton.setPreferredSize(new Dimension(400, 100));
        panelButton.add(btnAdd);
        panelButton.add(btnPay);
        this.getContentPane().add(panelButton);

        this.order = new Order();
        this.order.setOrderID(-1);

        this.btnAdd.addActionListener(e -> addProduct());
        this.btnPay.addActionListener(e -> {
            try {
                makeOrder();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnPay() {
        return btnPay;
    }

    public JLabel getLabTotal() {
        return labTotal;
    }

    public void addRow(Object[] row) {
        items.addRow(row);
    }


    private void makeOrder() throws SQLException {

        String shippingAddress = addressArea.getText().trim();
        String ccNumber = creditCardNumber.getText().trim().replaceAll("\\s+", "");
        String cvv = cvvField.getText().trim();
        String validThruMM = validThruMonth.getText().trim();
        String validThruYYYY = validThruYear.getText().trim();
        String billingAddress = billlingAddressArea.getText().trim();

        if (shippingAddress == null || shippingAddress.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter shipping address!");
            return;
        }

        if (billingAddress == null || billingAddress.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter billing address!");
            return;
        }

        if (ccNumber == null || ccNumber.isEmpty() || ccNumber.length() != 16 || !Util.isNumeric(ccNumber)) {
            JOptionPane.showMessageDialog(null, "Enter valid credit card number!");
            return;
        }

        if (cvv == null || cvv.isEmpty() || cvv.length() < 3 || cvv.length() > 4 || !Util.isNumeric(cvv)) {
            JOptionPane.showMessageDialog(null, "Enter valid CVV number!");
            return;
        }

        if (validThruMM == null || validThruMM.isEmpty() || validThruMM.length() != 2 || !Util.isNumeric(cvv)) {
            JOptionPane.showMessageDialog(null, "Enter valid expiry month!");
            return;
        }

        if (validThruYYYY == null || validThruYYYY.isEmpty() || validThruYYYY.length() != 4 || !Util.isNumeric(validThruYYYY)) {
            JOptionPane.showMessageDialog(null, "Enter valid expiry year!");
            return;
        }
        int validThruMonth = Integer.parseInt(validThruMM);
        int validThruYear = Integer.parseInt(validThruYYYY);
        if (validThruYear < Calendar.getInstance().get(Calendar.YEAR) ||
                (validThruYear == Calendar.getInstance().get(Calendar.YEAR) &&
                        validThruMonth <= Calendar.getInstance().get(Calendar.MONTH)+1)) {
            JOptionPane.showMessageDialog(null, "Enter valid expiry date!");
            return;
        }

        if (this.order.getLines().size() == 0) {
            JOptionPane.showMessageDialog(null, "Add products to the cart!");
            return;
        }

        this.order.setOrderID(Application.getInstance().getDataAdapter().getNextOrderID());
        this.order.getLines().forEach(x -> x.setOrderID(order.getOrderID()));
        order.setDate(Date.from(Instant.now()).toString());
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setCCNumber(Util.maskCCNumber(ccNumber));
        order.setCVV(Integer.parseInt(cvv));
        order.setValidThruMM(Integer.parseInt(validThruMM));
        order.setValidThruYYYY(Integer.parseInt(validThruYYYY));
        Application.getInstance().getDataAdapter().saveOrder(order);
        createAndSaveReceipt(order);

        order = new Order();
        items.setRowCount(0);
        this.getLabTotal().setText("Total: $0");
        this.addressArea.setText("");
        this.billlingAddressArea.setText("");
        this.creditCardNumber.setText("");
        this.cvvField.setText("");
        this.validThruMonth.setText("");
        this.validThruYear.setText("");
        this.invalidate();

        JOptionPane.showMessageDialog(null, "Order placed!");
    }

    private void createAndSaveReceipt(Order o) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==========================================");
        stringBuilder.append("\n");
        stringBuilder.append("                  RECEIPT                 ");
        stringBuilder.append("\n");
        stringBuilder.append("==========================================");
        stringBuilder.append("\n");
        stringBuilder.append("Product    |   Price    | Qty    |  Total ");
        stringBuilder.append("\n");
        stringBuilder.append("==========================================");
        stringBuilder.append("\n");

        for (OrderLine ol : o.getLines()) {
            stringBuilder.append(ol.getProduct().getName());
            stringBuilder.append("\t\t");
            stringBuilder.append(ol.getProduct().getPrice());
            stringBuilder.append("\t\t");
            stringBuilder.append(ol.getQuantity());
            stringBuilder.append("\t\t");
            stringBuilder.append(ol.getCost());
            stringBuilder.append("\n");
        }

        stringBuilder.append("==========================================");
        stringBuilder.append("\n");
        stringBuilder.append("Total Tax = $").append(o.getTotalTax());
        stringBuilder.append("\n");
        stringBuilder.append("Total = $").append(o.getTotalCost());
        stringBuilder.append("\n");
        stringBuilder.append("==========================================");
        stringBuilder.append("\n");

        Application.getInstance().getDataAdapter().saveReceipt(stringBuilder.toString(), order.getOrderID());
    }

    private void addProduct() {
        String id = JOptionPane.showInputDialog("Enter ProductID: ");
        int productId;
        try {
            productId = Integer.parseInt(id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Product ID must be an integer!");
            return;
        }

        Product product = Application.getInstance().getDataAdapter().loadProduct(productId);
        if (product == null) {
            JOptionPane.showMessageDialog(null, "This product does not exist!");
            return;
        }

        double quantity = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter quantity: "));

        if (quantity < 0 || quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null, "This quantity is not valid!");
            return;
        }

        boolean itemPresent = order.getLines().stream().anyMatch(x -> x.getProductID() == productId);
        if (itemPresent) {
            JOptionPane.showMessageDialog(null, "Item is already present in cart!");
            return;
        }

        OrderLine line = new OrderLine();
        line.setOrderID(this.order.getOrderID());
        line.setProductID(product.getProductID());
        line.setQuantity(quantity);
        line.setCost(quantity * product.getPrice());
        line.setProduct(product);
        order.getLines().add(line);
        order.setTotalCost(order.getTotalCost() + line.getCost());

        Object[] row = new Object[5];
        row[0] = line.getProductID();
        row[1] = product.getName();
        row[2] = product.getPrice();
        row[3] = line.getQuantity();
        row[4] = line.getCost();

        this.addRow(row);
        this.getLabTotal().setText("Total: $" + order.getTotalCost());
        this.invalidate();
    }
}
