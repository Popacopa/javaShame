package ru.popacopa.deliverySystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.popacopa.deliverySystem.entity.Client;
import ru.popacopa.deliverySystem.entity.Courier;
import ru.popacopa.deliverySystem.service.ClientService;
import ru.popacopa.deliverySystem.service.CourierService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CourierService courierService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String user = request.getParameter("username");
        System.out.println("username: " + user);
        // Проверяем роль пользователя
        boolean isClient = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

        boolean isCourier = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_COURIER"));

        if (isClient) {
            Client client = clientService.findByLogin(user).orElse(null);
            if (client != null) {
                response.sendRedirect("/client/home/" + client.getClientid().toString());
            }
        } else if (isCourier) {
            Courier courier = courierService.findByLogin(user).orElse(null);
            if (courier != null) {
               response.sendRedirect("/courier/home/" + courier.getCourier_id().toString());
            }
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}
