package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Clase que representa una caja dentro de la aplicación.
 * Contiene sus datos principales y las validaciones de sus campos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Caja {

    /** Identificador único de la caja. */
    private Long id;

    /** Código de la caja. */
    @NotBlank(message = "{msg.caja.codigo.notEmpty}")
    @Size(max = 10, message = "{msg.caja.codigo.size}")
    private String codigo;

    /** Fecha en la que se empaquetó la caja. */
    @NotNull(message = "{msg.caja.fechaEmpaque.notNull}")
    private LocalDate fecha_empaque;

    /** Indica si la caja contiene objetos frágiles. */
    private boolean es_fragil;

    /** Peso de la caja expresado en kilogramos. */
    private double peso_kg;

    /** Descripción del contenido de la caja. */
    @NotBlank(message = "{msg.caja.contenido.notEmpty}")
    @Size(max = 255, message = "{msg.caja.contenido.size}")
    private String contenido;

    /** Identificador de la estantería a la que pertenece la caja. */
    @NotNull(message = "{msg.caja.idEstanteria.notNull}")
    private Long id_estanteria;

    /**
     * Constructor para crear una caja sin especificar su identificador.
     *
     * @param codigo código de la caja.
     * @param fecha_empaque fecha en la que se empaquetó la caja.
     * @param es_fragil indica si la caja contiene objetos frágiles.
     * @param peso_kg peso de la caja en kilogramos.
     * @param contenido descripción del contenido de la caja.
     * @param id_estanteria identificador de la estantería a la que pertenece.
     */
    public Caja(String codigo, LocalDate fecha_empaque, boolean es_fragil, double peso_kg, String contenido, Long id_estanteria) {
        this.codigo = codigo;
        this.fecha_empaque = fecha_empaque;
        this.es_fragil = es_fragil;
        this.peso_kg = peso_kg;
        this.contenido = contenido;
        this.id_estanteria = id_estanteria;
    }
}