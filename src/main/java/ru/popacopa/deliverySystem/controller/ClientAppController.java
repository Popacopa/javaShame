package ru.popacopa.deliverySystem.controller;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.popacopa.deliverySystem.dto.FoodsDTO;
import ru.popacopa.deliverySystem.entity.*;
import ru.popacopa.deliverySystem.repository.BookinglistReposiroty;
import ru.popacopa.deliverySystem.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/client/v1")
public class ClientAppController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RestorantService restorantService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    FoodService foodService;
    @Autowired
    private BookingListService bookingListService;

    @GetMapping("/login")
    public String client_login() {
        return "client_login";
    }

    @GetMapping("/registration")
    public String client_registration() {
        return "client_registration";
    }

    @PostMapping("/registration")
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

    @GetMapping("/home/{id}")
    public String access(@PathVariable Long id, Model model) {
        Client client = clientService.findByClientid(id);
        List<Booking> bookinglist = bookingService.findByClientid(id);
        List<Restorant> restorants = restorantService.findForts5();
        model.addAttribute("client", client);
        model.addAttribute("restorants",  restorants);
        model.addAttribute("bookinglist", bookinglist);
        model.addAttribute("id", id);
        return "client_home";
    }

    @GetMapping("/home/{id}/order")
    public String addOrder(@PathVariable("id") Long id,
                           @RequestParam(value="rest") Long restid,
                           Model model) {
        Client client = clientService.findByClientid(id);
        List<Food> food = foodService.findByRestid(restid);
        model.addAttribute("restid", restid);
        model.addAttribute("client", client);
        model.addAttribute("food", food);
        return "add_order";
    }

    @PostMapping("/home/{id}/order")
    public String addtoBase(@PathVariable("id") Long id,
                            @RequestParam(value = "rest", required = true) Long rest,
                            @RequestBody FoodsDTO foods) {
        //Food f = foodService.findById(food);
        List<Food> f = new ArrayList<>();
        for (Long foodid : foods.getFoodid()) {
            f.add(foodService.findById(foodid));
        }
        Restorant r = restorantService.findByRestid(rest);
        //List<Food> l = new ArrayList<Food>();
        //l.add(f);
        Booking booking = bookingService.addBooking(id, null, rest, r.getRestname(), null, "готовится", f);
        //bookingListService.add(booking.getBookid(), food);
        for (Long foodid : foods.getFoodid()) {
            bookingListService.add(booking.getBookid(), foodid);
        }
        return "redirect:/client/v1/home/" + id;
    }

    @GetMapping("/home/{id}/orderdetails")
    public String getDetails(@PathVariable("id") Long id,
                             @RequestParam("order") Long order,
                             Model model) {
        Booking booking = bookingService.findById(order);
        Client client = clientService.findByClientid(id);
        List<Booking_list> bookingList = bookingListService.findBybookingid(order);
        List<Food> foodlist = new ArrayList<>();
        System.out.println(foodlist.size());
        for (Booking_list booking_list : bookingList) {
            Food food = foodService.findById(booking_list.getFoodid());
            foodlist.add(food);
            System.out.println(food.toString());
        }
        model.addAttribute("foodlist", foodlist);
        model.addAttribute("booking", booking);
        model.addAttribute("client", client);
        return "orderdetails";
    }
}
