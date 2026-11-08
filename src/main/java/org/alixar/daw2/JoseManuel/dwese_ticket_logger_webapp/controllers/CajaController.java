package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.controllers;

import jakarta.validation.Valid;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao.CajaDAO;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao.EstanteriaDAO;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Caja;
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
@RequestMapping("/cajas")
public class CajaController {

    @Autowired
    private CajaDAO cajaDAO;

    @Autowired
    private EstanteriaDAO estanteriaDAO;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String listCajas(Model model, Locale locale){

        List<Caja> cajaList = null;
        List<Estanteria>estanteriaList = null;
        try{
            cajaList = cajaDAO.listEstanterias();
            estanteriaList = estanteriaDAO.listEstanterias();
        }catch (DataAccessException e){
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.caja.list.error", null, locale));
        }

        model.addAttribute("cajaList", cajaList);
        model.addAttribute("estanteriaList",estanteriaList);
        return "cajas/caja";
    }

    @GetMapping("/new")
    public String showNewForm(Model model, Locale locale){
        List<Estanteria>estanteriaList = null;

        try{
            estanteriaList = estanteriaDAO.listEstanterias();
        }catch(DataAccessException e){
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.caja.get.error", null, locale));
        }
        model.addAttribute("caja", new Caja());
        model.addAttribute("estanteriaList",estanteriaList);
        return "cajas/caja-form";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, Locale locale){
        Caja caja = null;
        List<Estanteria>estanteriaList = null;
        try{
            caja = cajaDAO.getById(id);
            estanteriaList = estanteriaDAO.listEstanterias();
        }catch(DataAccessException e){
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.caja.get.error", null, locale));
        }

        model.addAttribute("caja", caja);
        model.addAttribute("estanteriaList",estanteriaList);
        return "cajas/caja-form";
    }

    @PostMapping("/insert")
    public String insertEstanteria(@Valid @ModelAttribute("caja")  Caja caja,
                                   BindingResult result, RedirectAttributes redirectAttributes, Locale locale){

        try{
            if(result.hasErrors())
                return "cajas/caja-form";

            if(cajaDAO.existsByCode(caja.getCodigo())){
                String errorMessage = messageSource.getMessage("msg.caja-controller.insert.codigoExists",
                        null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

                return "redirect:/cajas/new";
            }

            cajaDAO.insertEstanteria(caja);
        }catch(DataAccessException e){
            String errorMessage = messageSource.getMessage("msg.caja-controller.insert.error",
                    null, locale);

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/cajas/new";
        }

        return "redirect:/cajas";
    }

    @PostMapping("/update")
    public String updateEstanteria(@Valid @ModelAttribute("caja") Caja caja,
                                   BindingResult result, RedirectAttributes redirectAttributes, Locale locale){

        try{
            if(result.hasErrors())
                return "cajas/caja-form";

            if(cajaDAO.existsByCodeAndNotId(caja.getCodigo(), caja.getId())){

                String errorMessage = messageSource.getMessage("msg.caja-controller.update.codigoExists",
                        null, locale);

                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/cajas/edit?id=" + caja.getId();

            }

            cajaDAO.updateEstanteria(caja);
        }catch(DataAccessException e){
            String errorMessage = messageSource.getMessage("msg.caja-controller.update.error",
                    null, locale);

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/cajas/edit?id=" + caja.getId();
        }

        return "redirect:/cajas";
    }

    @PostMapping("/delete")
    public String deleteCaja(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Locale locale){

        try{
            cajaDAO.deleteCaja(id);
        }catch(DataAccessException e){
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.caja.delete.error", null, locale));

        }

        return "redirect:/cajas";
    }
}
