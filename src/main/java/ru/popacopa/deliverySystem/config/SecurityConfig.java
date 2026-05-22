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
                .securityMatcher("/client/**", "/", "/client**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/client/v1/login", "/client/v1/registration", "/courier/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/client/v1/registration").permitAll()
                        .requestMatchers("/client/v1/home**").hasRole("CLIENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/client/v1/login") //Страница логина для клиентов
                        .successHandler(customAuthenticationSuccessHandler)
                        .loginProcessingUrl("/client/v1/login")    // URL для отправки формы
                        .failureUrl("/client/v1/login?error=true")    // Куда при ошибке
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
                .securityMatcher("/courier**", "/courier/v1/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/courier/v1/login", "/courier/v1/registration", "/client/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/courier/v1/registration").permitAll()
                        .requestMatchers("/client/v1/home**").hasRole("CLIENT")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/courier/v1/login") //Страница логина для клиентов
                        .successHandler(customAuthenticationSuccessHandler)
                        .loginProcessingUrl("/courier/v1/login")    // URL для отправки формы
                        .failureUrl("/courier/v1/login?error=true")    // Куда при ошибке
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