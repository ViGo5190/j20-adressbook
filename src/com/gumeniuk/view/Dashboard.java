package com.gumeniuk.view;

import com.gumeniuk.adressbook.ContactManager;
import com.gumeniuk.builders.MenuBarBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Dashboard extends AbstractFrame {
    private ContactManager cm;

    private JTable contactTable;
    private Component contactTablePane;
    private ContactTableUtil contactTableBuilder;

    public Dashboard(ContactManager cm) throws HeadlessException {
        this.cm = cm;
        this.contactTableBuilder = new ContactTableUtil(this);
        contactTablePane = createTablePane();

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

    private Component createTablePane() {

        String[] col = {"ID", "Name", "Email", "Telephone"};

        contactTable = contactTableBuilder
                .setModelColumns(col)
                .fillTableWithContacts()
                .getTable();

        return new JScrollPane(contactTable);
    }

    private void reloadData() {
        try {
            contactTableBuilder.reloadData();
            System.out.println(cm.getContactsCount());
        } catch (IOException | ClassNotFoundException e) {
            showError("Error", e.getMessage());
        }
    }

    private Component createCreateButton(){
        JButton btn = new JButton("Create");
        btn.addActionListener(getActionListenerImpl());

        return btn;
    }

    @Override
    protected void onInit() {
        add(createMenu(), BorderLayout.NORTH);
        add(contactTablePane, BorderLayout.CENTER);
        add(createCreateButton(), BorderLayout.SOUTH);
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
    protected void onButtonClick(JButton btn) {
        System.out.println("foo");
        if (btn.getText() == "Create") {
            ContactPanelCreate ctnpe = new ContactPanelCreate(contactTableBuilder);
            ctnpe.pack();
            ctnpe.setVisible(true);
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
