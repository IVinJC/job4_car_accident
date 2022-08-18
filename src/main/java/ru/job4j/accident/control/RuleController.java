package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.RuleService;

@Controller
public class RuleController {
    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping("/rules")
    public String rules(Model model) {
        model.addAttribute("rules", ruleService.findAll());
        return "rules";
    }

    @GetMapping("/formAddRule")
    public String addRule(Model model) {
        return "addRule";
    }

    @PostMapping("/createRule")
    public String createRule(@ModelAttribute Rule rule) {
        ruleService.add(rule);
        return "redirect:/rules";
    }

    @PostMapping("/updateRules")
    public String updateRule(@ModelAttribute Rule rule) {
        ruleService.update(rule, rule.getId());
        return "redirect:/rules";
    }

    @GetMapping("/formUpdateRule/{ruleId}")
    public String formUpdateCity(Model model, @PathVariable("ruleId") int id) {
        model.addAttribute("rule", ruleService.findById(id));
        return "updateRules";
    }
}
