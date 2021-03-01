package org.springframework.samples.petclinic.recetas;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecetaController {
    @Autowired
    RecetaRepository recetas;

    public RecetaController(RecetaRepository recetas) {
        this.recetas = recetas;
    }

    @RequestMapping("citas/receta/save")
    public String saveReceta(@Valid Receta r, BindingResult result) {
        recetas.save(r);
        System.out.println("Holaaaa");
        return "redirect:/";
    }

}
