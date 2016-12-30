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

    private JFrame dashboard;

    public ContactTableUtil(JFrame dashboard) {
        this.dashboard = dashboard;
        this.cm = ContactManager.getInstance();
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

    private ContactTableUtil initTableEvents(){
        initTable();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    int id = (int) tableModel.getValueAt(row,0);
                    onClickCell(id);
                }
            }
        });
        return this;
    }

    public void onClickCell(int id){
        ContactPanelEdit ctnpe = new ContactPanelEdit(id, this);
        ctnpe.pack();
        ctnpe.setVisible(true);
    }

    public JTable getTable() {
        initTable();
        initTableEvents();
        return table;
    }

    public void onChangeData() throws IOException{
        fillTableWithContacts();
        tableModel.fireTableDataChanged();
        cm.dump();
    }

    public void reloadData() throws IOException, ClassNotFoundException {
        cm.invoke();
        onChangeData();

    }
}
