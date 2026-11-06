package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.controllers;

import jakarta.validation.Valid;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao.EstanteriaDAO;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Estanteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/estanterias")
public class EstanteriaController {

    @Autowired
    private EstanteriaDAO estanteriaDAO;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String listEstanterias(Model model, Locale locale){

        List<Estanteria> estanteriaList = null;
        try{
            estanteriaList = estanteriaDAO.listEstanterias();
        }catch (DataAccessException e){
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.region.list.error", null, locale));
        }

        model.addAttribute("estanteriaList", estanteriaList);
        return "estanterias/estanteria";
    }

    @GetMapping("/new")
    public String showNewForm(Model model){

        model.addAttribute("estanteria", new Estanteria());
        return "estanterias/estanteria-form";
    }

    @RequestMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, Locale locale){
        Estanteria estanteria = null;
        try{
            estanteria = estanteriaDAO.getById(id);
        }catch(DataAccessException e){
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.estanteria.get.error", null, locale));
        }

        model.addAttribute("estanteria", estanteria);
        return "estanterias/estanteria";
    }

    @PostMapping("/insert")
    public String insertRegion(@Valid @ModelAttribute("estanteria") Estanteria estanteria,
                               BindingResult result, RedirectAttributes redirectAttributes, Locale locale){

        try{
            if(result.hasErrors())
                return "estanterias/estanteria-form";

            if(estanteriaDAO.existsByCode(estanteria.getCodigo())){
                String errorMenssage = messageSource.getMessage("msg.estanteria-controller.insert.codigoExists",
                        null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", errorMenssage);

                return "redirect:/estanterias/new";
            }

            estanteriaDAO.insertEstanteria(estanteria);
        }catch(DataAccessException e){
            String errorMessage = messageSource.getMessage("msg.estanteria-controller.insert.error",
                    null, locale);


        }

        return "redirect:/estanterias";
    }

    @PostMapping("/update")
    public String updateEstanteria(@Valid @ModelAttribute("estanteria") Estanteria estanteria,
                                   RedirectAttributes redirectAttributes, BindingResult result, Locale locale){

        try{
            if(result.hasErrors())
                return "estanterias/estanteria-form";

            if(estanteriaDAO.existsByCodeAndNotId(estanteria.getCodigo(), estanteria.getId())){

                String errorMessage = messageSource.getMessage("msg.estanteria-controller.update.codigoExists",
                        null, locale);

                redirectAttributes.addFlashAttribute("erroMessage", errorMessage);
                return "redirect:/estanterias/edit/id=" + estanteria.getId();

            }

            estanteriaDAO.updateEstanteria(estanteria);
        }catch(DataAccessException e){
            String errorMessage = messageSource.getMessage("msg.estanteria-controller.update.error",
                    null, locale);

            redirectAttributes.addFlashAttribute("erroMessage", errorMessage);
        }

        return "redirect:/estanterias";
    }

    @PostMapping("/delete")
    public String deleteEstanteria(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Locale locale){

        try{
            estanteriaDAO.deleteEstanteria(id);
        }catch(DataAccessException e){
            redirectAttributes.addFlashAttribute("errorMenssage",
                    messageSource.getMessage("msg.estanteria.delete.error", null, locale));

        }

        return "redirect:/estanterias";
    }
}
