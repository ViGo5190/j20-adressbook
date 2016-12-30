package com.gumeniuk.view;

import com.gumeniuk.adressbook.ContactManager;
import com.gumeniuk.adressbook.type.Contact;
import com.gumeniuk.builders.MenuBarBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class Dashboard extends AbstractFrame {
    private ContactManager cm;

    public Dashboard(ContactManager cm) throws HeadlessException {
        this.cm = cm;

        setTitle("Contacts");
        setResizable(true);
        initLookAndFeel();
        setVisible(true);
    }

    private Component createMenu() {
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder(getActionListenerImpl());
        menuBarBuilder.menu("File")
                .add("Reload")
                .add("Save all")
                .add("Exit", "control Q");

        menuBarBuilder.menu("Help")
                .add("About");

        return menuBarBuilder.build();
    }

    private Component createTable() {

        JTable table = new JTable(generateTableModel());

        return new JScrollPane(table);
    }

    private DefaultTableModel generateTableModel() {
        String[] col = {"ID", "Name", "Email", "Telephone"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        for (Contact contact : cm.getContacts()) {
            Object[] obj = {
                    contact.getId(),
                    contact.getName(),
                    contact.getEmail(),
                    contact.getPhone()
            };
            tableModel.addRow(obj);
        }

        return tableModel;
    }

    private void reloadData() {
        try {
            cm.invoke();
        } catch (IOException | ClassNotFoundException e) {
            showError("Error", e.getMessage());
        }
    }

    @Override
    protected void onInit() {
        add(createMenu(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        pack();
    }

    @Override
    protected void onClose() {
        try {
            cm.dump();
        } catch (IOException ignore) {
        }
    }

    @Override
    protected void onMenuItemClick(String command) {
        switch (command) {
            case "Exit":
                dispose();
                break;
            case "Reload":
                reloadData();
                break;
            case "Save all":
                try {
                    cm.dump();
                } catch (IOException e) {
                    showError("Error", e.getMessage());
                }
                break;
            case "About":
                showMessage("About", "Stanislav Gumeniuk");
                break;
        }
    }
}
