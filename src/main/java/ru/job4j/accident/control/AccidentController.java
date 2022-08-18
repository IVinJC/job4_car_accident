package ru.job4j.accident.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.service.AccidentService;

@Controller
public class AccidentController {
    private final AccidentService accidentService;

    @Autowired
    public AccidentController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    public String accidents(Model model) {
        /*model.addAttribute("accidents", accidentService.findAll());*/
        return "ind";
    }
/*
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident) {
        Rule rule = ruleService.findById(accident.getRule().getId());
        accident.setRule(rule);
        Type type = typeService.findById(accident.getType().getId());
        accident.setType(type);
        accidentService.add(accident);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Accident accident, HttpServletRequest req) {
        Rule rule = ruleService.findById(accident.getRule().getId());
        accident.setRule(rule);
        Type type = typeService.findById(accident.getType().getId());
        accident.setType(type);
        accidentService.update(accident, accident.getId());
        String[] ids = req.getParameterValues("rIds");
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.findById(id));
        return "accident/update";
    }*/
}
