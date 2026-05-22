package ru.popacopa.deliverySystem.config;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.popacopa.deliverySystem.entity.Client;
import ru.popacopa.deliverySystem.entity.Courier;
import ru.popacopa.deliverySystem.service.ClientService;
import ru.popacopa.deliverySystem.service.CourierService;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain ClientFilterChain(@NonNull HttpSecurity http) throws Exception {
        http
                .securityMatcher("/client/**", "/", "/access", "/client**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/client_login", "/client_registration", "/courier_login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/client_registration").permitAll()
                        .requestMatchers("/client/home**").hasRole("CLIENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/client_login") //Страница логина для клиентов
                        .successHandler(customAuthenticationSuccessHandler)
                        .loginProcessingUrl("/client_auth")    // URL для отправки формы
                        .failureUrl("/client_login?error=true")    // Куда при ошибке
                        .permitAll()
                )
                .userDetailsService(ClientDetailsService())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
    @Bean
    @Order(2)
    public SecurityFilterChain CourierFilterChain(@NonNull HttpSecurity http) throws Exception {
        http
                .securityMatcher("/courier**", "/access")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/courier_login", "/courier_registration", "/client_login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/courier_registration").permitAll()
                        .requestMatchers("/client/home**").hasRole("CLIENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/courier_login") //Страница логина для клиентов
                        .successHandler(customAuthenticationSuccessHandler)
                        .loginProcessingUrl("/courier_auth")    // URL для отправки формы
                        .failureUrl("/courier_login?error=true")    // Куда при ошибке
                        .permitAll()
                )
                .userDetailsService(CourierDetailsService())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService ClientDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
                Client client = clientService.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
                Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
                return new org.springframework.security.core.userdetails.User(client.getLastname(), client.getPasswd(), authorities);
            }
        };
    }
    @Bean
    public UserDetailsService CourierDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
                Courier courier = courierService.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
                Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_COURIER"));
                return new org.springframework.security.core.userdetails.User(courier.getLastname(), courier.getPasswd(), authorities);
            }
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}