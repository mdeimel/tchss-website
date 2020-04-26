package org.tchss.controller;

import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tchss.exception.AuthenticationException;
import org.tchss.model.Price;
import org.tchss.model.Event;
import org.tchss.model.EventType;
import org.tchss.model.Registration;
import org.tchss.model.User;
import org.tchss.service.EventService;
import org.tchss.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(HttpSession session,
                        Principal principal,
                        Model model) {
        if (principal != null) {
            String email = principal.getName();
            model.addAttribute("email", email);
        }
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("create-account")
    public String createAccountPage() {
        return "create-account";
    }

    @PostMapping("create-account")
    public String createAccount(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                Model model) {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        user = userService.registerUser(user);

        model.addAttribute("user", user);
        model.addAttribute("message", "Registration successful. Please login.");

        return "index";
    }

    @GetMapping("forgot-password")
    public String forgotPassword() {
        return "forgot_password";
    }

    @PostMapping("forgot-password")
    public String forgotPassword(@RequestParam("email") String email,
                                 Model model) {

        userService.generatePasswordResetToken(email);

        model.addAttribute("message", "If the email address links to an existing account, then a password email was sent");

        return "forgot_password";
    }

    @GetMapping("password-reset/{token}")
    public String passwordReset(@PathVariable String token, Model model) {
        model.addAttribute("token", token);
        return "password_reset";
    }

    @PostMapping("password-reset")
    public String passwordReset(@RequestParam("password") String password,
                                @RequestParam("token") String token,
                                Model model) {
        userService.passwordReset(token, password);
        model.addAttribute("message", "Password successfully reset, please login.");
        return "index";
    }

    @GetMapping("register")
    public String registerPage(Principal principal,
                               HttpServletRequest request,
                               HttpSession session,
                               Model model) {
        model.addAttribute("email", principal.getName());
        List<Integer> birthYears = new ArrayList<>();
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 3; i > currentYear - 18; i--) {
            birthYears.add(i);
        }
        model.addAttribute("birthYears", birthYears);

        // Retrieve existing registrations
        User user = getUser(session, principal);
        List<Registration> registrations = userService.getRegistrations(user);
        model.addAttribute("registrations", registrations);

        Integer totalCost = 0;
        for (Registration registration : registrations) {
            totalCost += registration.getCost();
        }
        model.addAttribute("totalCost", totalCost);

        // Retrieve this year's schedule
        List<Event> baseballEvents = eventService.findAllByEventType(EventType.BASEBALL);
        List<Event> soccerEvents = eventService.findAllByEventType(EventType.SOCCER);
        model.addAttribute("baseballEvents", baseballEvents);
        model.addAttribute("soccerEvents", soccerEvents);

        return "register";
    }

    @PostMapping("register")
    public String register(@RequestParam("first-name") String firstName,
                           @RequestParam("last-name") String lastName,
                           @RequestParam("birth-month") Integer birthMonth,
                           @RequestParam("birth-year") Integer birthYear,
                           @RequestParam("baseball") boolean baseball,
                           @RequestParam("soccer") boolean soccer,
                           HttpSession session,
                           Principal principal,
                           Model model) {
//        model.addAttribute("email", principal.getName());
        User user = getUser(session, principal);

        Price price = eventService.getPrice();
        Integer cost = 0;
        if (baseball && soccer) {
            cost = price.getTwoEvents();
        }
        else if (baseball || soccer) {
            cost = price.getOneEvent();
        }

        Registration registration = new Registration(null, user, firstName, lastName, birthMonth, birthYear, baseball, soccer, cost, null);
        userService.addRegistration(registration);
        return "redirect:/register";
    }

    @PostMapping("register/{id}")
    public String deleteRegistration(@PathVariable Integer id,
                                     HttpSession session,
                                     Principal principal) {
        User user = getUser(session, principal);
        userService.deleteRegistration(user, id);
        return "redirect:/register";
    }

    @GetMapping("roster")
    public String roster(Principal principal,
                         Model model) {
        model.addAttribute("email", principal.getName());
        return "roster";
    }

    @GetMapping("payment")
    public String payment(Principal principal,
                          Model model) {
        model.addAttribute("email", principal.getName());
        return "payment";
    }

    @PostMapping("payment")
    public String makePayment(Principal principal,
                              Model model) {
        PaymentIntent paymentIntent = new PaymentIntent();

        return "payment";
    }

    /**
     * Retrieve the current user, either from the HttpSession, or from the
     * Principal. Then make sure it is stored in the session for the next request.
     * @param session
     * @param principal
     * @return
     */
    private User getUser(HttpSession session, Principal principal) {
        User user = (User)session.getAttribute("user");
        if (user == null) {
            String email = principal.getName();
            String errorMessage = "Unexpectedly cannot find user by email: "+email;
            user = userService.findByEmail(email).orElseThrow(() ->
                    new AuthenticationException(errorMessage));
            session.setAttribute("user", user);
        }
        return user;
    }
}
