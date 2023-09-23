package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

    private JButton btnBuy = new JButton("Order View");
    private JButton btnSell = new JButton("Product View");
    private JLabel nameVal;
    private JLabel usernameVal;

    public MainScreen() {
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        btnSell.setPreferredSize(new Dimension(120, 50));
        btnBuy.setPreferredSize(new Dimension(120, 50));


        JLabel title = new JLabel("Store Management System");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        this.getContentPane().add(panelTitle);

        JLabel name = new JLabel("Name: ");
        name.setFont(new Font("Sans Serif", Font.BOLD, 18));
        nameVal = new JLabel("");
        nameVal.setFont(new Font("Sans Serif", Font.BOLD, 18));
        JPanel namePanel = new JPanel();
        namePanel.add(name);
        namePanel.add(nameVal);

        JLabel username = new JLabel("Username: ");
        usernameVal = new JLabel("");
        username.setFont(new Font("Sans Serif", Font.BOLD, 18));
        usernameVal.setFont(new Font("Sans Serif", Font.BOLD, 18));
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(username);
        usernamePanel.add(usernameVal);

        JPanel vertPanel = new JPanel();
        vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.Y_AXIS));
        vertPanel.add(namePanel);
        vertPanel.add(usernamePanel);

        this.getContentPane().add(vertPanel);

        JPanel panelButton = new JPanel();
        panelButton.add(btnBuy);
        panelButton.add(btnSell);

        this.getContentPane().add(panelButton);

        btnBuy.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getOrderView().setVisible(true);            }
        });


        btnSell.addActionListener(new ActionListener() { // when controller is simple, we can declare it on the fly
            public void actionPerformed(ActionEvent e) {
                Application.getInstance().getProductView().setVisible(true);
            }
        });
    }

    public void loadUserData() {
        User user = Application.getInstance().getCurrentUser();
        nameVal.setText(user.getFullName());
        usernameVal.setText(user.getUsername());
        this.invalidate();
    }


}
