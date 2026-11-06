package Capstone.app;

import Capstone.model.Customer;
import Capstone.net.Api;

/** Holds the customer who is currently signed in. */
public final class Session {

    private static Customer customer;

    private Session() {}

    public static void signIn(Customer c) {
        customer = c;
        Cart.get().clear();
    }

    public static void signOut() {
        customer = null;
        Api.clearCredentials();
        Cart.get().clear();
    }

    public static Customer customer() {
        return customer;
    }

    public static String customerId() {
        return customer == null ? null : customer.getCustomerId();
    }

    /** CUSTOMER, STAFF or OWNER. An account with no role counts as a customer. */
    public static String role() {
        if ( customer  == null || customer.getRole() == null) {
            return "CUSTOMER";
        }
        return customer.getRole();
    }

    public static boolean isStaff() {
        return role().equals("STAFF") || role().equals("OWNER");
    }

    public static boolean isOwner(){
        return role().equals("OWNER");
    }
}
