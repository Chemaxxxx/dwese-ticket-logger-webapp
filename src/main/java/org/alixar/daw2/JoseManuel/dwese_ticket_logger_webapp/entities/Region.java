
package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase Region representa una región dentro de la base de datos.
 * Contiene tres campos: id, code y name.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    // Identificador único de la región.
    private Long id;

    // Código de la región.
    @NotBlank(message = "{msg.region.code.notEmpty}")
    @Size(max = 2, message = "{msg.region.code.size}")
    private String code;

    // Nombre completo de la región.
    @NotBlank(message = "{msg.region.name.notEmpty}")
    @Size(max = 100, message = "{msg.region.name.size}")
    private String name;

    /**
     * Constructor para crear una región sin especificar su ID.
     *
     * @param code Código de la región.
     * @param name Nombre de la región.
     */
    public Region(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
