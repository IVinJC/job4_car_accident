package ru.job4j.accident.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.User;
import ru.job4j.accident.service.AuthorityService;
import ru.job4j.accident.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class RegController {
    @Autowired
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final AuthorityService authorities;

    @Autowired
    public RegController(PasswordEncoder encoder, UserService userService, AuthorityService authorities) {
        this.encoder = encoder;
        this.userService = userService;
        this.authorities = authorities;
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "reg";
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByPassword(
                user.getUsername(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        userService.add(user);
        return "redirect:/index";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}