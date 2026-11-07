
package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estanteria {

    // Identificador único de la estanteria.
    private Long id;

    // Código de la estanteria.
    @NotBlank(message = "{msg.estanteria.codigo.notEmpty}")
    @Size(max = 10, message = "{msg.estanteria.codigo.size}")
    private String codigo;

    // Ubicación de la estanteria.
    @NotBlank(message = "{msg.estanteria.ubicacion.notEmpty}")
    @Size(max = 100, message = "{msg.estanteria.ubicacion.size}")
    private String ubicacion;

    // Material de la estanteria.
    @NotBlank(message = "{msg.estanteria.material.notEmpty}")
    @Size(max = 50, message = "{msg.estanteria.material.size}")
    private String material;

    // Estado de la estanteria.
    @NotBlank(message = "{msg.estanteria.estado.notEmpty}")
    @Size(max = 50, message = "{msg.estanteria.estado.size}")
    private String estado;


    public Estanteria(String codigo, String ubicacion, String material, String estado) {
        this.codigo = codigo;
        this.ubicacion = ubicacion;
        this.material = material;
        this.estado = estado;
    }
}
