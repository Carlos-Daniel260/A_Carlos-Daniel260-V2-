package org.springframework.samples.petclinic.recetas;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecetaController {
    @Autowired
    RecetaRepository recetas;
    private ModelAndView modelAndView;

    public RecetaController(RecetaRepository recetas) {
        this.recetas = recetas;
    }

    @RequestMapping("citas/receta/save")
    public String saveReceta(@Valid Receta r, BindingResult result) {
        recetas.save(r);
        System.out.println("Holaaaa");
        return "redirect:/";
    }

    @GetMapping("/recetas/ver")
    public ModelAndView getCita() {
        System.out.println("HOLAAA");
        // Citas citaAux = citas.findById(id);
        Collection<Receta> recetas1 = recetas.All();
        modelAndView = new ModelAndView("ver_recetas");
        modelAndView.addObject("recetas", recetas1);
        // modelAndView.addObject("receta", new Receta());
        // modelAndView.addObject("exitsError", false);
        return modelAndView;
    }

}
