package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de gestionar la página principal del almacén.
 */
@Controller
public class AlmacenHomeController {

    /**
     * Muestra la página principal del almacén.
     *
     * @return nombre de la vista de inicio del almacén.
     */
    @GetMapping("/almacen")
    public String homeAlmacen() {
        return "index-almacen";
    }
}