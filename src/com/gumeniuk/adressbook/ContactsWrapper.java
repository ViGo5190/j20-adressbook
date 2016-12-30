package com.gumeniuk.adressbook;

import com.gumeniuk.adressbook.type.ContactsMap;

import java.io.Serializable;
import java.util.HashMap;

class ContactsWrapper implements Serializable {
    ContactsMap contacts;
    String version = "1.0";
}
