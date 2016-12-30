package com.gumeniuk.builders;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class MenuBarBuilder {
    private final Queue<MenuBuilder> items;

    private final ActionListener listener;

    public MenuBarBuilder(ActionListener listener) {
        this.listener = listener;

        this.items = new LinkedList<>();
    }

    public MenuBuilder menu(String text) {
        MenuBuilder builder = new MenuBuilder(text, listener);
        items.add(builder);
        return builder;
    }

    public JMenuBar build() {
        JMenuBar jMenuBar = new JMenuBar();

        while (!items.isEmpty()) {
            jMenuBar.add(
                    items.poll().build()
            );
        }
        return  jMenuBar;
    }
}
