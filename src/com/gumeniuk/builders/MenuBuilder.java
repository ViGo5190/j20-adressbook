package com.gumeniuk.builders;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class MenuBuilder {
    private final Queue<MenuItemBuilder> items;

    private final ActionListener listener;

    private final String text;

    public MenuBuilder(String text, ActionListener listener) {
        this.listener = listener;
        this.text = text;

        items = new LinkedList<>();
    }

    private MenuItemBuilder item(String text) {
        MenuItemBuilder item = new MenuItemBuilder(listener);
        item.setText(text);

        items.add(item);
        return item;
    }

    public MenuBuilder add(String text, String acc) {
        item(text).setAccelerator(acc);
        return this;
    }

    public MenuBuilder add(String text) {
        item(text);
        return this;
    }

    public JMenu build() {
        JMenu jMenu = new JMenu(text);
        while (!items.isEmpty()) {
            MenuItemBuilder builder = items.poll();
            JMenuItem item = builder.build();
            jMenu.add(item);
        }

        return jMenu;
    }
}
