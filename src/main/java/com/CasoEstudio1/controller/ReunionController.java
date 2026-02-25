package com.CasoEstudio1.controller;

import com.CasoEstudio1.domain.Reunion;
import com.CasoEstudio1.domain.Sala;
import com.CasoEstudio1.service.ReunionService;
import com.CasoEstudio1.service.SalaService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reunion")
public class ReunionController {

    @Autowired
    private ReunionService reunionService;

    @Autowired
    private SalaService salaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var reuniones = reunionService.getReuniones();
        var salas = salaService.getSalas();

        model.addAttribute("reuniones", reuniones);
        model.addAttribute("salas", salas);
        model.addAttribute("totalReuniones", reuniones.size());

        return "reunion/listado";
    }

    @GetMapping("/modificar/{idReunion}")
    public String modificar(@PathVariable Long idReunion,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        Optional<Reunion> reunionOpt = reunionService.getReunion(idReunion);

        if (reunionOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La reunión no fue encontrada.");
            return "redirect:/reunion/listado";
        }

        model.addAttribute("reunion", reunionOpt.get());
        model.addAttribute("salas", salaService.getSalas());
        return "reunion/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Reunion reunion,
                          @RequestParam Long idSala,
                          RedirectAttributes redirectAttributes) {

        Optional<Sala> salaOpt = salaService.getSala(idSala);
        if (salaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La sala seleccionada no existe.");
            return "redirect:/reunion/listado";
        }

        reunion.setSala(salaOpt.get());
        reunionService.save(reunion);

        redirectAttributes.addFlashAttribute("todoOk", "Reunión guardada correctamente.");
        return "redirect:/reunion/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idReunion,
                           RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String mensaje = "Reunión eliminada correctamente.";

        try {
            reunionService.delete(idReunion);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "La reunión no existe.";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "Ocurrió un error al eliminar la reunión.";
        }

        redirectAttributes.addFlashAttribute(titulo, mensaje);
        return "redirect:/reunion/listado";
    }
}