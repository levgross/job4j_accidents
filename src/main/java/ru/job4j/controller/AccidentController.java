package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentService;
import ru.job4j.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("rules", ruleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        for (String id : ids) {
            Optional<Rule> optRule = ruleService.findById(Integer.parseInt(id));
            if (optRule.isEmpty()) {
                throw new IllegalArgumentException("No such rule registered.");
            }
            rules.add(optRule.get());
        }
        accident.setRules(rules);
        accidentService.create(accident);
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
