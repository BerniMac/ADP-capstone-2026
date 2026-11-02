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
}
