package edu.ijse.datadish.util;

public class Regex {
    public static boolean validateName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    public static boolean validateContact(String contact) {
        return contact.matches("^\\d{10}$");
    }

    public static boolean validateAddress(String address) {
        return address.matches("^[a-zA-Z0-9\\s,.-]+$");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }
}
