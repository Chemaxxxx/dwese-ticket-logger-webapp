package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase `Province` representa una entidad que modela una provincia dentro de
 * la base de datos.
 *
 * Contiene cuatro campos: `id`, `code`, `name` y `region`, donde `id` es el
 * identificador único de la provincia, `code` es un código asociado a la
 * provincia, `name` es el nombre de la provincia y `region` es la relación
 * con la entidad `Region`, representando la comunidad autónoma a la que
 * pertenece la provincia.
 *
 * Las anotaciones de Lombok ayudan a reducir el código repetitivo al generar
 * automáticamente métodos comunes como getters, setters, constructores y
 * otros métodos estándar de los objetos.
 */
@Entity
@Table(name = "provinces")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Province {

    // Campo que almacena el identificador único de la provincia.
    // Es autogenerado y clave primaria.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo que almacena el código de la provincia.
    // Ejemplo: "23" para Jaén.
    @NotEmpty(message = "{msg.province.code.notEmpty}")
    @Size(max = 2, message = "{msg.province.code.size}")
    @Column(name = "code", nullable = false, length = 2)
    private String code;

    // Campo que almacena el nombre completo de la provincia.
    // Ejemplo: "Sevilla" o "Jaén".
    @NotEmpty(message = "{msg.province.name.notEmpty}")
    @Size(max = 100, message = "{msg.province.name.size}")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Relación con la entidad `Region`, representando la comunidad autónoma
    // a la que pertenece la provincia.
    @NotNull(message = "{msg.province.region.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_region", nullable = false)
    private Region region;

    /**
     * Constructor que excluye el campo `id`. Se utiliza para crear instancias
     * de `Province` cuando el `id` aún no se ha generado (por ejemplo, antes
     * de insertarla en la base de datos).
     *
     * @param code Código de la provincia.
     * @param name Nombre de la provincia.
     * @param region La región a la que pertenece la provincia.
     */
    public Province(String code, String name, Region region) {
        this.code = code;
        this.name = name;
        this.region = region;
    }
}