package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.controllers;

import jakarta.validation.Valid;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Region;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.repositories.RegionRepository;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controlador que maneja las operaciones CRUD para la entidad Region.
 * Utiliza RegionRepository (Spring Data JPA) para interactuar con la base
 * de datos.
 */
@Controller
@RequestMapping("/regions")
public class RegionController {

    private static final Logger logger =
            LoggerFactory.getLogger(RegionController.class);

    // Repositorio para gestionar las operaciones de las regiones
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MessageSource messageSource;

    // Servicio para gestionar las imágenes
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Lista todas las regiones y las pasa como atributo al modelo.
     */
    @GetMapping
    public String listRegions(Model model, Locale locale) {
        logger.info("Solicitando la lista de todas las regiones...");

        List<Region> listRegions = null;

        try {
            listRegions = regionRepository.findAll();
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
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva región.");

        model.addAttribute("region", new Region());

        return "regions/region-form";
    }

    /**
     * Muestra el formulario para editar una región existente.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {
        logger.info("Mostrando formulario de edición para la región con ID {}", id);

        try {
            Optional<Region> region = regionRepository.findById(id);

            if (region.isEmpty()) {
                // Si no existe, se vuelve al listado con un aviso
                logger.warn("No se encontró la región con ID {}", id);

                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage(
                                "msg.region-controller.edit.notFound", null, locale));

                return "redirect:/regions";
            }

            model.addAttribute("region", region.get());

        } catch (DataAccessException e) {
            logger.error("Error al obtener la región con ID {}: {}", id, e.getMessage());

            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage(
                            "msg.region-controller.edit.error", null, locale));

            return "redirect:/regions";
        }

        return "regions/region-form";
    }

    /**
     * Inserta una nueva región en la base de datos.
     */
    @PostMapping("/insert")
    public String insertRegion(@Valid @ModelAttribute("region") Region region,
                               BindingResult result,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Insertando nueva región con código {}", region.getCode());

        try {
            if (result.hasErrors()) {
                return "regions/region-form";
            }

            if (regionRepository.existsRegionByCode(region.getCode())) {
                logger.warn("El código de la región {} ya existe.", region.getCode());

                // El error se muestra junto al campo "code" y se conservan los datos
                result.rejectValue("code", "msg.region-controller.insert.codeExist");

                return "regions/region-form";
            }

            // Guardar la imagen subida
            if (!imageFile.isEmpty()) {
                String fileName = fileStorageService.saveFile(imageFile);

                if (fileName != null) {
                    region.setImage(fileName);
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            messageSource.getMessage(
                                    "msg.region-form.image.invalid", null, locale));

                    return "redirect:/regions/new";
                }
            }

            regionRepository.save(region);

            logger.info("Región {} insertada con éxito.", region.getCode());

        } catch (DataAccessException e) {
            logger.error("Error al insertar la región {}: {}",
                    region.getCode(), e.getMessage());

            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage(
                            "msg.region-controller.insert.error", null, locale));
        }

        return "redirect:/regions";
    }

    /**
     * Actualiza una región existente en la base de datos.
     */
    @PostMapping("/update")
    public String updateRegion(@Valid @ModelAttribute("region") Region region,
                               BindingResult result,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Actualizando región con ID {}", region.getId());

        try {
            if (result.hasErrors()) {
                return "regions/region-form";
            }

            if (regionRepository.existsRegionByCodeAndNotId(
                    region.getCode(), region.getId())) {

                logger.warn("El código de la región {} ya existe para otra región.",
                        region.getCode());

                result.rejectValue("code", "msg.region-controller.update.codeExist");

                return "regions/region-form";
            }

            // Obtener la región actual para conocer su imagen
            Optional<Region> existingRegion = regionRepository.findById(region.getId());

            if (existingRegion.isEmpty()) {
                logger.warn("No se encontró la región con ID {}", region.getId());

                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage(
                                "msg.region-controller.edit.notFound", null, locale));

                return "redirect:/regions";
            }

            // Guardar el nombre de la imagen anterior EN UNA VARIABLE: tras el
            // save, la entidad existente puede reflejar ya la imagen nueva.
            String oldImage = existingRegion.get().getImage();

            // Por defecto se conserva la imagen anterior
            region.setImage(oldImage);

            boolean imageReplaced = false;

            if (!imageFile.isEmpty()) {
                String fileName = fileStorageService.saveFile(imageFile);

                if (fileName != null) {
                    region.setImage(fileName);
                    imageReplaced = true;
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage",
                            messageSource.getMessage(
                                    "msg.region-form.image.invalid", null, locale));

                    return "redirect:/regions/edit?id=" + region.getId();
                }
            }

            regionRepository.save(region);

            // Eliminar la imagen anterior solo si se ha sustituido por otra
            if (imageReplaced && oldImage != null && !oldImage.isEmpty()) {
                fileStorageService.deleteFile(oldImage);
            }

            logger.info("Región con ID {} actualizada con éxito.", region.getId());

        } catch (DataAccessException e) {
            logger.error("Error al actualizar la región con ID {}: {}",
                    region.getId(), e.getMessage());

            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage(
                            "msg.region-controller.update.error", null, locale));
        }

        return "redirect:/regions";
    }

    /**
     * Elimina una región de la base de datos.
     */
    @PostMapping("/delete")
    public String deleteRegion(@RequestParam("id") Long id,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Eliminando región con ID {}", id);

        try {
            Optional<Region> regionOpt = regionRepository.findById(id);

            if (regionOpt.isPresent()) {
                String image = regionOpt.get().getImage();

                regionRepository.deleteById(id);

                // Eliminar la imagen asociada, solo después de borrar la región
                if (image != null && !image.isEmpty()) {
                    fileStorageService.deleteFile(image);
                }

                logger.info("Región con ID {} eliminada con éxito.", id);
            } else {
                logger.warn("No se encontró la región con ID {} para eliminar.", id);
            }

        } catch (DataIntegrityViolationException e) {
            // La clave ajena de provinces impide borrar una región con provincias
            logger.warn("No se puede eliminar la región con ID {}: tiene provincias asociadas.", id);

            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage(
                            "msg.region-controller.delete.hasProvinces", null, locale));

        } catch (DataAccessException e) {
            logger.error("Error al eliminar la región con ID {}: {}", id, e.getMessage());

            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage(
                            "msg.region-controller.delete.error", null, locale));
        }

        return "redirect:/regions";
    }
}