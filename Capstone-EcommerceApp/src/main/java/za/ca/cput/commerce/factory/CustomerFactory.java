/* 
  Customer____.java
  Author: 222709006 Qhama dyushu
  Date: 22/03/2026
    */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Customer;

public class CustomerFactory {

    public static Customer createCustomer(String name, String email, String phone) {

        if (name == null || name.isBlank()) {
            return null;
        }

        if (email == null || email.isBlank()) {
            return null;
        }

        if (phone == null || phone.isBlank()) {
            return null;
        }

        return new Customer.Builder()
                .setName(name)
                .setEmail(email)
                .setPhone(phone)
                .build();
    }

    /** Same as above, but for an account that can sign in. Pass the BCrypt hash, never the plain password. */
    public static Customer createCustomer(String name, String email, String phone, String passwordHash) {

        if (passwordHash == null || passwordHash.isBlank()) {
            return null;
        }

        Customer base = createCustomer(name, email, phone);
        if (base == null) {
            return null;
        }

        return new Customer.Builder()
                .copy(base)
                .setPasswordHash(passwordHash)
                .build();
    }
}