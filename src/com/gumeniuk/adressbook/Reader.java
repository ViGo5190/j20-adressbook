package com.gumeniuk.adressbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.gumeniuk.adressbook.type.*;

class Reader {

    private File file;

    Reader(String fileName) {
        file = new File(fileName);
    }

    void writeObj(ContactsMap contacts) throws IOException {

        try (OutputStream stream = new FileOutputStream(file, false)) {

            ObjectOutputStream out = new ObjectOutputStream(stream);
            ContactsWrapper cw = new ContactsWrapper();
            cw.contacts = contacts;
            out.writeObject(cw);
            out.flush();
        }
    }

    ContactsMap readObj() throws IOException, ClassNotFoundException {
        ContactsMap contacts;
        try (InputStream stream = new FileInputStream(file)) {

            ObjectInputStream inputStream = new ObjectInputStream(stream);
            ContactsWrapper cw = (ContactsWrapper) inputStream.readObject();
            contacts = cw.contacts;
        } catch (FileNotFoundException e) {
            contacts = new ContactsMap();
        }
        return contacts;
    }


}

