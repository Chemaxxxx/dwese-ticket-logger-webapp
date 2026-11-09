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

/**
 * Controlador encargado de gestionar las operaciones relacionadas con las cajas.
 * Permite listar, crear, editar y eliminar cajas.
 */
@Controller
@RequestMapping("/cajas")
public class CajaController {

    /** DAO utilizado para realizar operaciones sobre las cajas. */
    @Autowired
    private CajaDAO cajaDAO;

    /** DAO utilizado para obtener los datos de las estanterías. */
    @Autowired
    private EstanteriaDAO estanteriaDAO;

    /** Objeto utilizado para obtener los mensajes traducidos de la aplicación. */
    @Autowired
    private MessageSource messageSource;

    /**
     * Muestra el listado de todas las cajas y las estanterías disponibles.
     *
     * @param model objeto utilizado para enviar datos a la vista.
     * @param locale idioma utilizado para mostrar los mensajes.
     * @return nombre de la vista que muestra el listado de cajas.
     */
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

    /**
     * Muestra el formulario para registrar una nueva caja.
     *
     * @param model objeto utilizado para enviar datos a la vista.
     * @param locale idioma utilizado para mostrar los mensajes.
     * @return nombre de la vista del formulario de cajas.
     */
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

    /**
     * Busca una caja por su identificador y muestra sus datos en el formulario de edición.
     *
     * @param id identificador de la caja que se quiere editar.
     * @param model objeto utilizado para enviar datos a la vista.
     * @param locale idioma utilizado para mostrar los mensajes.
     * @return nombre de la vista del formulario de cajas.
     */
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

    /**
     * Valida e inserta una nueva caja en la base de datos.
     * Comprueba también que no exista otra caja con el mismo código.
     *
     * @param caja objeto con los datos de la caja que se quiere insertar.
     * @param result resultado de las validaciones del formulario.
     * @param redirectAttributes objeto utilizado para enviar mensajes después de una redirección.
     * @param locale idioma utilizado para mostrar los mensajes.
     * @return redirección al listado de cajas o al formulario si se produce un error.
     */
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

    /**
     * Valida y actualiza los datos de una caja existente.
     * Comprueba que el código no esté utilizado por otra caja.
     *
     * @param caja objeto con los datos actualizados de la caja.
     * @param result resultado de las validaciones del formulario.
     * @param redirectAttributes objeto utilizado para enviar mensajes después de una redirección.
     * @param locale idioma utilizado para mostrar los mensajes.
     * @return redirección al listado de cajas o al formulario si se produce un error.
     */
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

    /**
     * Elimina una caja de la base de datos utilizando su identificador.
     *
     * @param id identificador de la caja que se quiere eliminar.
     * @param redirectAttributes objeto utilizado para enviar mensajes después de una redirección.
     * @param locale idioma utilizado para mostrar los mensajes.
     * @return redirección al listado de cajas.
     */
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