package ru.job4j.controller;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.User;
import ru.job4j.service.AuthorityService;
import ru.job4j.service.UserService;

@Controller
@AllArgsConstructor
public class RegController {
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final AuthorityService authorityService;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
            if (!userService.create(user)) {
                return "redirect:/reg?error=true";
            }
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage(@RequestParam(value = "error", required = false) String error,
                          Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username already exists !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "reg";
    }
}
