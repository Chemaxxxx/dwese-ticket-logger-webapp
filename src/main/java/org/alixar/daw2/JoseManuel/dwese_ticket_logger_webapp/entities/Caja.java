
package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Caja {

    private Long id;

    @NotBlank(message = "{msg.caja.codigo.notEmpty}")
    @Size(max = 10, message = "{msg.caja.codigo.size}")
    private String codigo;

    @NotNull(message = "{msg.caja.fechaEmpaque.notNull}")
    private LocalDate fecha_empaque;


    private boolean es_fragil;


    private double peso_kg;


    @NotBlank(message = "{msg.caja.contenido.notEmpty}")
    @Size(max = 255, message = "{msg.caja.contenido.size}")
    private String contenido;


    @NotNull(message = "{msg.caja.idEstanteria.notNull}")
    private Long id_estanteria;


    public Caja(String codigo, LocalDate fecha_empaque, boolean es_fragil, double peso_kg, String contenido, Long id_estanteria) {
        this.codigo = codigo;
        this.fecha_empaque = fecha_empaque;
        this.es_fragil = es_fragil;
        this.peso_kg = peso_kg;
        this.contenido = contenido;
        this.id_estanteria = id_estanteria;
    }
}
