package com.gumeniuk.builders;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuItemBuilder {

    private final JMenuItem proto;

    public MenuItemBuilder() {
        this.proto = new JMenuItem();
    }

    public MenuItemBuilder(ActionListener listener) {
        this.proto = new JMenuItem();
        proto.addActionListener(listener);
    }

    public MenuItemBuilder setText(String text) {
        proto.setText(text);
        return this;
    }

    public MenuItemBuilder addListener(ActionListener listener) {
        proto.addActionListener(listener);
        return this;
    }

    public MenuItemBuilder setAccelerator(String accelerator) {
        proto.setAccelerator(KeyStroke.getKeyStroke(accelerator));
        return this;
    }

    public JMenuItem build() {
//        return proto;
        JMenuItem item = new JMenuItem(proto.getText());
        for (ActionListener listener : proto.getActionListeners()) {
            item.addActionListener(listener);
        }

        KeyStroke acc = proto.getAccelerator();
        if (acc != null) {
            item.setAccelerator(acc);
            proto.setAccelerator(null);
        }
        return item;
    }
}
