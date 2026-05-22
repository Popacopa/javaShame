package ru.popacopa.deliverySystem.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.popacopa.deliverySystem.entity.*;
import ru.popacopa.deliverySystem.repository.FoodRepository;
import ru.popacopa.deliverySystem.repository.RestorantRepository;
import ru.popacopa.deliverySystem.service.*;

import java.util.List;
import java.util.Optional;


@Controller
public class AppController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RestorantService restorantService;
    @Autowired
    private FoodService foodService;

    @PostMapping("/client/home/{clientid}/menu/{restid}/send")
    public String addOrder(@PathVariable("clientid") String clientid,
                           @PathVariable("restid") String restid) {
        return "redirect:/client/home" + clientid;
    }

    @GetMapping("/client/home/{id}")
    public String access(@PathVariable Long id, Model model) {
        Client client = clientService.findByClientid(id);
        List<Booking> bookinglist = bookingService.findByClientid(id);
        List<Restorant> restorants = restorantService.getRestorants();
        model.addAttribute("client", client);
        model.addAttribute("restorants",  restorants);
        model.addAttribute("bookinglist", bookinglist);
        model.addAttribute("id", id);
        return "client_home";
    }


    @GetMapping("/menu/{id}")
    public String menu(@PathVariable("id") String id, Model model) {
        Long idd =  Long.parseLong(id);
        List<Food> food = foodService.findById(idd);
        model.addAttribute("food", food);
        return "menu";
    }

    @PostMapping("/client/restaurant/select")
    public String selectRestaurant(@RequestParam("restaurantId") String id) {
        return "redirect:/menu/" + id;
    }

    @GetMapping("/orderform")
    public String orderForm(Model model) {
        List<Restorant> restorants = restorantService.getRestorants();
        List<Food> food = foodService.findAll();
        model.addAttribute("food", food);
        model.addAttribute("rests", restorants);
        return "orderform";
    }

    @GetMapping("/client_login")
    public String client_login() {
        return "client_login";
    }

    @GetMapping("/courier_login")
    public String courier_login() {
        return "courier_login";
    }


    @GetMapping("/client_registration")
    public String client_registration() {
        return "client_registration";
    }

    @GetMapping("/courier_registration")
    public String courier_registration() {
        return "courier_registration";
    }

    @PostMapping("/client_registration")
    public String addClient(@RequestParam String login,
                            @RequestParam String password,
                            @RequestParam String name,
                            @RequestParam String phone,
                            @RequestParam String address) {

        final String passwd = passwordEncoder.encode(password);
        final Client client = new Client(login, passwd, name, phone, address);
        clientService.addClient(client);
        return "redirect:/client_login";
    }

    @PostMapping("/courier_registration")
    public String addCourier(@RequestParam String login,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam String phone) {

        final String passwd = passwordEncoder.encode(password);
        final Courier client = new Courier(login, passwd, name, phone);
        courierService.addCourier(client);
        return "redirect:/courier_login";
    }
}