package com.gumeniuk.adressbook;

import com.gumeniuk.adressbook.type.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ContactManager {
    private HashMap<Integer, Contact> contacts = new HashMap<>();
    private static Reader reader;
    private static ContactManager cm;

    private ContactManager() {
        reader = new Reader("data.dat");
    }

    public static synchronized ContactManager getInstance() {
        if (cm == null) {
            cm = new ContactManager();
        }
        return cm;
    }

    public void addContact(Contact contact) {
        this.contacts.put(contact.getId(), contact);
    }

    public ArrayList<Contact> getContacts() {
        Iterator it = contacts.entrySet().iterator();
        ArrayList<Contact> c = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            c.add((Contact) me.getValue());
        }
        return c;
    }

    public void dump() throws IOException {
        reader.writeObj(contacts);
    }

    public void invoke() throws IOException, ClassNotFoundException {
        contacts = reader.readObj();
    }

    public int getContactsCount() {
        return contacts.size();
    }
}
