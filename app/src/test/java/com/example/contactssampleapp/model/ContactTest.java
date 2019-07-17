package com.example.contactssampleapp.model;


import com.example.contactssampleapp.models.Contact;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactTest {

    /*
        Compare two Contacts that are Equal
     */
    @Test
    void isContactEqual_returnTrue() throws Exception {
        Contact contact1 = new Contact("Panda", "panda@mail.com", 345, "picture1");
        Contact contact2 = new Contact("Panda", "panda@mail.com", 345, "picture1");

        assertEquals(contact1, contact2);
        System.out.println("They the same contact!");
    }

    /*
        Compare two Contacts that are not Equal
     */
    @Test
    void isContactEqual_differentsNumbers_returnFalse() throws Exception {
        Contact contact1 = new Contact("Panda", "panda@mail.com", 245, "picture1");
        Contact contact2 = new Contact("Panda", "panda@mail.com", 345, "picture1");

        assertEquals(contact1, contact2);
        System.out.println("They the same contact!");
    }
}
