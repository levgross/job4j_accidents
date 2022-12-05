package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.service.AccidentService;
import ru.job4j.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("rules", ruleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        if (ids == null || !accidentService.create(accident, ids)) {
            return "redirect:/createAccident?fail=true";
        }
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String update(Model model, @RequestParam("id") int id) {
        Optional<Accident> optAccident = accidentService.findById(id);
        if (optAccident.isEmpty()) {
            return "404";
        }
        model.addAttribute("accident", optAccident.get());
        return "formUpdateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/index";
    }
}
