package com.gumeniuk.adressbook;

import com.gumeniuk.adressbook.type.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ContactManager {
    private ContactsMap contacts = new ContactsMap();
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

    public ContactManager addContact(Contact contact) {
        if (contact.getId() == 0) {
            contact.setId(this.getContactsCount() + 1);

        }
        this.contacts.put(contact.getId(), contact);

        return this;
    }

    public ArrayList<Contact> getContacts() {
        Iterator it = contacts.entrySet().iterator();
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            contactArrayList.add((Contact) me.getValue());
        }
        return contactArrayList;
    }

    public Contact getContactById(int id){
        return contacts.get(id);
    }

    public ContactManager dump() throws IOException {
        reader.writeObj(contacts);

        return this;
    }

    public ContactManager invoke() throws IOException, ClassNotFoundException {
        contacts = reader.readObj();

        return this;
    }

    public int getContactsCount() {
        return contacts.size();
    }
}
