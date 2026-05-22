package ru.popacopa.deliverySystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.popacopa.deliverySystem.entity.Courier;
import ru.popacopa.deliverySystem.service.ClientService;
import ru.popacopa.deliverySystem.service.CourierService;

@Controller
@RequestMapping("/courier/v1")
public class CourierAppController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CourierService courierService;

    @GetMapping("/login")
    public String courier_login() {
        return "courier_login";
    }

    @GetMapping("/registration")
    public String courier_registration() {
        return "courier_registration";
    }

    @PostMapping("/registration")
    public String addCourier(@RequestParam String login,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam String phone) {

        final String passwd = passwordEncoder.encode(password);
        final Courier client = new Courier(login, passwd, name, phone);
        courierService.addCourier(client);
        return "redirect:/courier_login";
    }

    @GetMapping("/home/{id}")
    public String clientHome(@PathVariable Long id, Model model) {
        Courier courier = courierService.findByCourierid(id).orElse(null);
        model.addAttribute("courier", courier);
        return "courier_home";
    }
}
