package com.gumeniuk;

import com.gumeniuk.adressbook.ContactManager;
import com.gumeniuk.view.Dashboard;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ContactManager cm = ContactManager.getInstance();
        cm.invoke();

        System.out.println(cm.getContactsCount());

        Dashboard dash = new Dashboard(cm);
    }
}
