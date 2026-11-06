package za.ca.cput.commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

/**
 * Every endpoint needs a signed-in customer (email + password, HTTP Basic) except registration.
 * Passwords are checked against BCrypt hashes in the customer table by CustomerUserDetailsService.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/error").permitAll()
                        // Only staff and the owner may add, change or delete products
                        .requestMatchers(HttpMethod.POST, "/api/products", "/api/products/**").hasAnyRole("STAFF", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/products", "/api/products/**").hasAnyRole("STAFF", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/products", "/api/products/**").hasAnyRole("STAFF", "OWNER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
