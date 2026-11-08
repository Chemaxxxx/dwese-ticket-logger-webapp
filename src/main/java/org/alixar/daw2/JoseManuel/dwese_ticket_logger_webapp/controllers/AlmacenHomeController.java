package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlmacenHomeController {

    @GetMapping("/almacen")
    public String homeAlmacen() {
        return "index-almacen";
    }
}