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

    void writeObj(HashMap<Integer, Contact> contacts) throws IOException {

        try (OutputStream stream = new FileOutputStream(file, false)) {

            ObjectOutputStream out = new ObjectOutputStream(stream);
            ContactsWrapper cw = new ContactsWrapper();
            cw.contacts = contacts;
            out.writeObject(cw);
            out.flush();
        }
    }

    HashMap<Integer, Contact>  readObj() throws IOException, ClassNotFoundException {
        HashMap<Integer, Contact>  contacts;
        try (InputStream stream = new FileInputStream(file)) {

            ObjectInputStream inputStream = new ObjectInputStream(stream);
            ContactsWrapper cw = (ContactsWrapper) inputStream.readObject();
            contacts =  cw.contacts;
            return contacts;
        }
    }


}

