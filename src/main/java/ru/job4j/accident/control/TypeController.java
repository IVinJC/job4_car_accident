package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.Type;
import ru.job4j.accident.service.TypeService;

@Controller
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/types")
    public String types(Model model) {
        model.addAttribute("types", typeService.findAll());
        return "types";
    }

    @GetMapping("/formAddType")
    public String addType(Model model) {
        return "addType";
    }

    @PostMapping("/createType")
    public String createType(@ModelAttribute Type type) {
        typeService.add(type);
        return "redirect:/types";
    }

    @PostMapping("/updateTypes")
    public String updateType(@ModelAttribute Type type) {
        typeService.update(type, type.getId());
        return "redirect:/types";
    }

    @GetMapping("/formUpdateType/{typeId}")
    public String formUpdateCity(Model model, @PathVariable("typeId") int id) {
        model.addAttribute("type", typeService.findById(id));
        return "updateType";
    }
}
