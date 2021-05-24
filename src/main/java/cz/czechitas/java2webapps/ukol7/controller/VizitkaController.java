package cz.czechitas.java2webapps.ukol7.controller;

import cz.czechitas.java2webapps.ukol7.entity.Vizitka;
import cz.czechitas.java2webapps.ukol7.repository.VizitkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class VizitkaController {
    private final VizitkaRepository repository;

    @Autowired
    public VizitkaController(VizitkaRepository repository) {
        this.repository = repository;
    }

    @InitBinder
    public void nullStringBinding(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/")
    public Object seznam() {
        return new ModelAndView("seznam")
                .addObject("vizitky", repository.findAll());
    }

    @GetMapping("/{id:[0-9]+}")
    public Object detail(@PathVariable Integer id) {
        Optional<Vizitka> vizitka = repository.findById(id);
        if (vizitka.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ModelAndView("vizitka")
                .addObject("vizitka", vizitka.get());
    }
}
