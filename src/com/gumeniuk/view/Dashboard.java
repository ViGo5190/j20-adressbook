package com.gumeniuk.view;

import com.gumeniuk.adressbook.ContactManager;
import com.gumeniuk.builders.MenuBarBuilder;

import java.awt.*;
import java.io.IOException;

public class Dashboard extends AbstractFrame {
    private ContactManager cm;

    public Dashboard(ContactManager cm) throws HeadlessException {
        this.cm = cm;

        setTitle("Contacts");
        setResizable(false);
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

    @Override
    protected void onInit() {
        add(createMenu(), BorderLayout.NORTH);
//
//        add(createGameField(), BorderLayout.CENTER);
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
                try {
                    cm.invoke();
                } catch (IOException | ClassNotFoundException e) {
                    showError("Error", e.getMessage());
                }
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
