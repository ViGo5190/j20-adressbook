package com.gumeniuk.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import static javax.swing.UIManager.setLookAndFeel;

class AbstractFrame extends JFrame {

    private final ActionListenerImpl actionListenerImpl;


    AbstractFrame() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        actionListenerImpl = new ActionListenerImpl();

        WindowEventListener windowEventListener = new WindowEventListener();
        addWindowListener(windowEventListener);
        addWindowStateListener(windowEventListener);
        addWindowFocusListener(windowEventListener);
    }

    public ActionListenerImpl getActionListenerImpl() {
        return actionListenerImpl;
    }

    protected static void initLookAndFeel() {
        try {

            System.setProperty("apple.laf.useScreenMenuBar", "true");
            setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ReflectiveOperationException ignore) {
        }
    }


    protected void onInit() {

    }

    protected void onClose() {

    }

    protected void onAction(Object src, String cmd) {

    }

    protected void onButtonClick(JButton btn) {

    }

    protected void onMenuItemClick(String command) {
    }

    protected void showMessage(String title, String message) {
        showMessageDialog(this, message, title, PLAIN_MESSAGE);
    }

    protected void showError(String title, String message) {
        showMessageDialog(this, message, title, ERROR_MESSAGE);
    }

    private class WindowEventListener extends WindowAdapter {

        @Override
        public void windowOpened(WindowEvent e) {
            onInit();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            onClose();
        }

    }

    private class ActionListenerImpl implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if (source instanceof JButton) {
                onButtonClick((JButton) source);
            } else if (source instanceof JMenuItem) {
                onMenuItemClick(e.getActionCommand());
            } else {
                onAction(source, e.getActionCommand());
            }
        }
    }


}
