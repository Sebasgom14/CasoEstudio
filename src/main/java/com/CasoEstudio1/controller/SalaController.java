package com.CasoEstudio1.controller;

import com.CasoEstudio1.domain.Sala;
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
@RequestMapping("/sala")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var salas = salaService.getSalas();
        model.addAttribute("salas", salas);
        model.addAttribute("totalSalas", salas.size());
        return "sala/listado";
    }

    @GetMapping("/modificar/{idSala}")
    public String modificar(@PathVariable Long idSala,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        Optional<Sala> salaOpt = salaService.getSala(idSala);

        if (salaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La sala no fue encontrada.");
            return "redirect:/sala/listado";
        }

        model.addAttribute("sala", salaOpt.get());
        return "sala/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Sala sala,
                          RedirectAttributes redirectAttributes) {

        salaService.save(sala);
        redirectAttributes.addFlashAttribute("todoOk", "Sala guardada correctamente.");
        return "redirect:/sala/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long idSala,
                           RedirectAttributes redirectAttributes) {

        String titulo = "todoOk";
        String mensaje = "Sala eliminada correctamente.";

        try {
            salaService.delete(idSala);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            mensaje = "La sala no existe.";
        } catch (IllegalStateException e) {
            titulo = "error";
            mensaje = "No se puede eliminar la sala porque tiene reuniones asociadas.";
        } catch (Exception e) {
            titulo = "error";
            mensaje = "Ocurrió un error al eliminar la sala.";
        }

        redirectAttributes.addFlashAttribute(titulo, mensaje);
        return "redirect:/sala/listado";
    }
}