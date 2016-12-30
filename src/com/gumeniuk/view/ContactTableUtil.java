package com.gumeniuk.view;

import com.gumeniuk.adressbook.ContactManager;
import com.gumeniuk.adressbook.type.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

public class ContactTableUtil {

    private ContactManager cm;
    private JTable table;

    public ContactTableUtil(ContactManager cm) {
        this.cm = cm;
    }

    private DefaultTableModel tableModel = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
        }
    };

    public ContactTableUtil setModelColumns(String[] columns) {
        if (columns.length == 0) {
            throw new IllegalArgumentException("Empty columns");
        }
        for (String col : columns) {
            tableModel.addColumn(col);
        }

        return this;
    }

    public ContactTableUtil fillTableWithContacts() {
        return setRowsByContacts(cm.getContacts());
    }

    public ContactTableUtil clearData() {
        int rowCount = tableModel.getRowCount();
        if (rowCount > 0) {
            for (int i = rowCount - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }
        }
        return this;
    }

    private ContactTableUtil setRowsByContacts(ArrayList<Contact> objects) {

        clearData();

        for (Contact contact : objects) {
            Object[] obj = {
                    contact.getId(),
                    contact.getName(),
                    contact.getEmail(),
                    contact.getPhone()
            };
            tableModel.addRow(obj);
        }

        return this;
    }

    private ContactTableUtil initTable() {
        if (table == null) {
            table = new JTable(tableModel) {
                public void fireMe() {
                    fillTableWithContacts();
                    tableModel.fireTableDataChanged();
                    System.out.println("foo");
                }
            };
        }

        return this;
    }

    public JTable getTable() {
        initTable();
        return table;
    }

    public void reloadData() throws IOException, ClassNotFoundException {
        cm.invoke();
        fillTableWithContacts();
        tableModel.fireTableDataChanged();
    }
}
