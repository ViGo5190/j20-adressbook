package com.gumeniuk.adressbook;

import com.gumeniuk.adressbook.type.Contact;

import java.io.Serializable;
import java.util.HashMap;

class ContactsWrapper implements Serializable {
    HashMap<Integer, Contact> contacts;
    String version = "1.0";
}
