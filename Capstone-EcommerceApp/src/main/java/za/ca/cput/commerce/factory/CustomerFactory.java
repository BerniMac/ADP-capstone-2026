/* 
  Customer____.java
  Author: 222709006 Qhama dyushu
  Date: 22/03/2026
    */
package za.ca.cput.commerce.factory;

import za.ca.cput.commerce.domain.Customer;
import za.ca.cput.commerce.domain.Role;

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

    /** Same as above, but with a chosen role (used for staff and owner accounts). */
    public static Customer createCustomer(String name, String email, String phone, String passwordHash, Role role){
        Customer base = createCustomer(name, email, phone, passwordHash);
        if (base == null) {
            return null;
        }

        return new Customer.Builder()
                .copy(base)
                .setRole(role)
                .build();
    }
}