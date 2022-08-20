package controllers;

import models.service.GeneradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // De momento esto se deja asi. No es escalable esta muy acomplado
    // En un futuro si hubiera otros generadores implementaría una interfaz generador y
    // aplicaría inyección de dependencias entre los distintos tipos de generadores.
    @Autowired
    private GeneradorService generadorService;

    @GetMapping({"/index", "/", "/home"})
    public String index(Model model) {
        String texto = generadorService.servicio();
        model.addAttribute("servicio", texto);
        return "index";
    }
}
