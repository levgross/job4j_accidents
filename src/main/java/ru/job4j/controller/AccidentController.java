package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.model.Accident;
import ru.job4j.service.AccidentService;
import ru.job4j.service.RuleService;
import ru.job4j.service.TypeService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final RuleService ruleService;
    private final TypeService typeService;

    @GetMapping("/createAccident")
    public String create(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("types", typeService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        int typeId = accident.getType().getId();
        String[] ruleIds = req.getParameterValues("rIds");
        if (!accidentService.create(accident, typeId, ruleIds)) {
            return "redirect:/createAccident?fail=true";
        }
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String update(Model model,
                         @RequestParam("id") int id,
                         @RequestParam(name = "fail", required = false) Boolean fail) {
        Optional<Accident> optAccident = accidentService.findById(id);
        if (optAccident.isEmpty()) {
            return "404";
        }
        model.addAttribute("accident", optAccident.get());
        model.addAttribute("fail", fail != null);
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("types", typeService.findAll());
        return "formUpdateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        int typeId = accident.getType().getId();
        String[] ruleIds = req.getParameterValues("rIds");
        if (!accidentService.update(accident, typeId, ruleIds)) {
            return "redirect:/formUpdateAccident?fail=true";
        }
        return "redirect:/index";
    }
}
