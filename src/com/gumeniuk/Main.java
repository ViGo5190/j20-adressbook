package com.gumeniuk;

import com.gumeniuk.adressbook.ContactManager;
import com.gumeniuk.adressbook.type.Contact;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ContactManager cm = ContactManager.getInstance();
        cm.invoke();
        Contact c1 = new Contact(1, "name1", "email1", "phone1");
        Contact c2 = new Contact(2, "name2", "email2", "phone2");
        cm.addContact(c1);
        cm.addContact(c2);


        if (cm.getContactsCount()>0){
            for ( Contact contact : cm.getContacts()){
                System.out.println(contact.getName());
            }
        }

        cm.dump();

        System.out.println(cm.getContactsCount());

    }
}
