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

/**
 * Controlador encargado de gestionar las operaciones relacionadas
 * con las estanterías, como listar, insertar, actualizar y eliminar.
 */
@Controller
@RequestMapping("/estanterias")
public class EstanteriaController {

    /** DAO utilizado para acceder a los datos de las estanterías. */
    @Autowired
    private EstanteriaDAO estanteriaDAO;

    /** Objeto utilizado para obtener los mensajes según el idioma seleccionado. */
    @Autowired
    private MessageSource messageSource;

    /**
     * Muestra la lista de todas las estanterías.
     *
     * @param model objeto utilizado para enviar datos a la vista.
     * @param locale idioma seleccionado para mostrar los mensajes.
     * @return nombre de la vista que muestra la lista de estanterías.
     */
    @GetMapping
    public String listEstanterias(Model model, Locale locale){

        List<Estanteria> estanteriaList = null;
        try{
            estanteriaList = estanteriaDAO.listEstanterias();
        }catch (DataAccessException e){
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.estanteria.list.error", null, locale));
        }

        model.addAttribute("estanteriaList", estanteriaList);
        return "estanterias/estanteria";
    }

    /**
     * Muestra el formulario para registrar una nueva estantería.
     *
     * @param model objeto utilizado para enviar datos a la vista.
     * @return nombre de la vista del formulario de estanterías.
     */
    @GetMapping("/new")
    public String showNewForm(Model model){

        model.addAttribute("estanteria", new Estanteria());
        return "estanterias/estanteria-form";
    }

    /**
     * Busca una estantería por su identificador y muestra sus datos
     * en el formulario para poder modificarlos.
     *
     * @param id identificador de la estantería que se desea editar.
     * @param model objeto utilizado para enviar datos a la vista.
     * @param locale idioma seleccionado para mostrar los mensajes.
     * @return nombre de la vista del formulario de estanterías.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, Locale locale){
        Estanteria estanteria = null;
        try{
            estanteria = estanteriaDAO.getById(id);
        }catch(DataAccessException e){
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.estanteria.get.error", null, locale));
        }

        model.addAttribute("estanteria", estanteria);
        return "estanterias/estanteria-form";
    }

    /**
     * Registra una nueva estantería después de comprobar que los datos
     * son válidos y que su código no existe previamente.
     *
     * @param estanteria estantería que se desea insertar.
     * @param result resultado de las validaciones del formulario.
     * @param redirectAttributes objeto utilizado para enviar mensajes entre redirecciones.
     * @param locale idioma seleccionado para mostrar los mensajes.
     * @return redirección a la lista de estanterías o al formulario si ocurre algún error.
     */
    @PostMapping("/insert")
    public String insertEstanteria(@Valid @ModelAttribute("estanteria") Estanteria estanteria,
                                   BindingResult result, RedirectAttributes redirectAttributes, Locale locale){

        try{
            if(result.hasErrors())
                return "estanterias/estanteria-form";

            if(estanteriaDAO.existsByCode(estanteria.getCodigo())){
                String errorMessage = messageSource.getMessage("msg.estanteria-controller.insert.codigoExists",
                        null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

                return "redirect:/estanterias/new";
            }

            estanteriaDAO.insertEstanteria(estanteria);
        }catch(DataAccessException e){
            String errorMessage = messageSource.getMessage("msg.estanteria-controller.insert.error",
                    null, locale);

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/estanterias/new";
        }

        return "redirect:/estanterias";
    }

    /**
     * Actualiza los datos de una estantería después de comprobar que los datos
     * son válidos y que su código no pertenece a otra estantería.
     *
     * @param estanteria estantería con los datos que se desean actualizar.
     * @param result resultado de las validaciones del formulario.
     * @param redirectAttributes objeto utilizado para enviar mensajes entre redirecciones.
     * @param locale idioma seleccionado para mostrar los mensajes.
     * @return redirección a la lista de estanterías o al formulario si ocurre algún error.
     */
    @PostMapping("/update")
    public String updateEstanteria(@Valid @ModelAttribute("estanteria") Estanteria estanteria,
                                   BindingResult result, RedirectAttributes redirectAttributes, Locale locale){

        try{
            if(result.hasErrors())
                return "estanterias/estanteria-form";

            if(estanteriaDAO.existsByCodeAndNotId(estanteria.getCodigo(), estanteria.getId())){

                String errorMessage = messageSource.getMessage("msg.estanteria-controller.update.codigoExists",
                        null, locale);

                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/estanterias/edit?id=" + estanteria.getId();

            }

            estanteriaDAO.updateEstanteria(estanteria);
        }catch(DataAccessException e){
            String errorMessage = messageSource.getMessage("msg.estanteria-controller.update.error",
                    null, locale);

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/estanterias/edit?id=" + estanteria.getId();
        }

        return "redirect:/estanterias";
    }

    /**
     * Elimina una estantería de la base de datos mediante su identificador.
     *
     * @param id identificador de la estantería que se desea eliminar.
     * @param redirectAttributes objeto utilizado para enviar mensajes entre redirecciones.
     * @param locale idioma seleccionado para mostrar los mensajes.
     * @return redirección a la lista de estanterías.
     */
    @PostMapping("/delete")
    public String deleteEstanteria(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Locale locale){

        try{
            estanteriaDAO.deleteEstanteria(id);
        }catch(DataAccessException e){
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.estanteria.delete.error", null, locale));

        }

        return "redirect:/estanterias";
    }
}