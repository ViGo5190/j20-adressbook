package com.gumeniuk.view;

import com.gumeniuk.adressbook.ContactManager;
import com.gumeniuk.adressbook.type.Contact;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

class ContactPanelEdit extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField NameTextField;
    private JTextField EmailTextField;
    private JTextField PhoneTextField;
    private JLabel NameLabel;
    private JLabel PhoneLabel;
    private JLabel EmailLabel;
    private JLabel MessageLabel;

    private int id;
    private ContactManager cm;
    private Contact person;
    private ContactTableUtil contactTableUtil;

    ContactPanelEdit(int id, ContactTableUtil contactTableUtil) {
        this.contactTableUtil = contactTableUtil;
        cm = ContactManager.getInstance();
        this.id = id;
        System.out.println(id);

        person = cm.getContactById(this.id);

        NameTextField.setText(person.getName());
        EmailTextField.setText(person.getEmail());
        PhoneTextField.setText(person.getPhone());


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        person.setName(NameTextField.getText());
        person.setEmail(EmailTextField.getText());
        person.setPhone(PhoneTextField.getText());
        // add your code here
        try{
            contactTableUtil.onChangeData();
            dispose();
        } catch (Exception ex) {
            MessageLabel.setText("Some error on save. Try again, please!");
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
