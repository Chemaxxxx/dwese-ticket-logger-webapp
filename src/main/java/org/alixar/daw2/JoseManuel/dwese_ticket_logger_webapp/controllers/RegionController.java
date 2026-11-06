
package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.controllers;

import jakarta.validation.Valid;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao.RegionDAO;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Controlador que maneja las operaciones CRUD para la entidad Region.
 * Utiliza RegionDAO para interactuar con la base de datos.
 */
@Controller
@RequestMapping("/regions")
public class RegionController {

    private static final Logger logger = LoggerFactory.getLogger(RegionController.class);

    // DAO para gestionar las operaciones de las regiones
    @Autowired
    private RegionDAO regionDAO;

    @Autowired
    private MessageSource messageSource;

    /**
     * Lista todas las regiones y las pasa como atributo al modelo.
     *
     * @param model Modelo para pasar datos a la vista.
     * @param locale Idioma actual.
     * @return Plantilla Thymeleaf para mostrar la lista de regiones.
     */
    @GetMapping
    public String listRegions(Model model, Locale locale) {
        logger.info("Solicitando la lista de todas las regiones...");

        List<Region> listRegions = null;

        try {
            listRegions = regionDAO.listAllRegions();
            logger.info("Se han cargado {} regiones.", listRegions.size());

        } catch (DataAccessException e) {
            logger.error("Error al listar las regiones: {}", e.getMessage());

            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.region.list.error", null, locale));
        }

        model.addAttribute("listRegions", listRegions);

        return "regions/region";
    }

    /**
     * Muestra el formulario para crear una nueva región.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return Plantilla Thymeleaf del formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva región.");

        model.addAttribute("region", new Region());

        return "regions/region-form";
    }

    /**
     * Muestra el formulario para editar una región existente.
     *
     * @param id ID de la región a editar.
     * @param model Modelo para pasar datos a la vista.
     * @param locale Idioma actual.
     * @return Plantilla Thymeleaf del formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, Locale locale) {
        logger.info("Mostrando formulario de edición para la región con ID {}", id);

        Region region = null;

        try {
            region = regionDAO.getRegionById(id);

            if (region == null) {
                logger.warn("No se encontró la región con ID {}", id);
            }

        } catch (DataAccessException e) {
            logger.error("Error al obtener la región con ID {}: {}", id, e.getMessage());

            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.region.get.error", null, locale));
        }

        model.addAttribute("region", region);

        return "regions/region-form";
    }

    /**
     * Inserta una nueva región en la base de datos.
     *
     * @param region Objeto que contiene los datos del formulario.
     * @param result Resultado de las validaciones.
     * @param redirectAttributes Atributos para mensajes flash.
     * @param locale Idioma actual.
     * @return Redirección a la lista de regiones o al formulario.
     */
    @PostMapping("/insert")
    public String insertRegion(@Valid @ModelAttribute("region") Region region,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Insertando nueva región con código {}", region.getCode());

        try {
            if (result.hasErrors()) {
                return "regions/region-form";
            }

            if (regionDAO.existsRegionByCode(region.getCode())) {
                logger.warn("El código de la región {} ya existe.", region.getCode());

                String errorMessage = messageSource.getMessage(
                        "msg.region-controller.insert.codeExist", null, locale);

                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

                return "redirect:/regions/new";
            }

            regionDAO.insertRegion(region);

            logger.info("Región {} insertada con éxito.", region.getCode());

        } catch (DataAccessException e) {
            logger.error("Error al insertar la región {}: {}",
                    region.getCode(), e.getMessage());

            String errorMessage = messageSource.getMessage(
                    "msg.region-controller.insert.error", null, locale);

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }

        return "redirect:/regions";
    }

    /**
     * Actualiza una región existente en la base de datos.
     *
     * @param region Objeto que contiene los datos del formulario.
     * @param result Resultado de las validaciones.
     * @param redirectAttributes Atributos para mensajes flash.
     * @param locale Idioma actual.
     * @return Redirección a la lista de regiones o al formulario.
     */
    @PostMapping("/update")
    public String updateRegion(@Valid @ModelAttribute("region") Region region,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Actualizando región con ID {}", region.getId());

        try {
            if (result.hasErrors()) {
                return "regions/region-form";
            }

            if (regionDAO.existsRegionByCodeAndNotId(
                    region.getCode(), region.getId())) {

                logger.warn("El código de la región {} ya existe para otra región.",
                        region.getCode());

                String errorMessage = messageSource.getMessage(
                        "msg.region-controller.update.codeExist", null, locale);

                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

                return "redirect:/regions/edit?id=" + region.getId();
            }

            regionDAO.updateRegion(region);

            logger.info("Región con ID {} actualizada con éxito.", region.getId());

        } catch (DataAccessException e) {
            logger.error("Error al actualizar la región con ID {}: {}",
                    region.getId(), e.getMessage());

            String errorMessage = messageSource.getMessage(
                    "msg.region-controller.update.error", null, locale);

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }

        return "redirect:/regions";
    }

    /**
     * Elimina una región de la base de datos.
     *
     * @param id ID de la región a eliminar.
     * @param redirectAttributes Atributos para mensajes flash.
     * @param locale Idioma actual.
     * @return Redirección a la lista de regiones.
     */
    @PostMapping("/delete")
    public String deleteRegion(@RequestParam("id") Long id,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Eliminando región con ID {}", id);

        try {
            regionDAO.deleteRegion(id);

            logger.info("Región con ID {} eliminada con éxito.", id);

        } catch (DataAccessException e) {
            logger.error("Error al eliminar la región con ID {}: {}",
                    id, e.getMessage());

            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.region.delete.error", null, locale));
        }

        return "redirect:/regions";
    }
}
