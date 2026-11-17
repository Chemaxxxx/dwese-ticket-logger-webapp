package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * La clase `Region` representa una entidad que modela una región dentro de la
 * base de datos.
 *
 * Contiene los campos `id`, `code`, `name` e `image`, donde `id` es el
 * identificador único de la región, `code` es un código asociado a la región,
 * `name` es el nombre de la región e `image` es el nombre del archivo de
 * imagen asociado (almacenado en la carpeta definida por UPLOAD_PATH).
 *
 * Las anotaciones de Lombok ayudan a reducir el código repetitivo al generar
 * automáticamente métodos comunes como getters, setters, constructores y
 * otros métodos estándar de los objetos.
 */
@Entity
@Table(name = "regions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    // Campo que almacena el identificador único de la región.
    // Es una clave primaria autogenerada por la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo que almacena el código de la región.
    // Ejemplo: "01" para Andalucía.
    @NotEmpty(message = "{msg.region.code.notEmpty}")
    @Size(max = 2, message = "{msg.region.code.size}")
    @Column(name = "code", nullable = false, length = 2)
    private String code;

    // Campo que almacena el nombre completo de la región.
    // Ejemplo: "Andalucía" o "Cataluña".
    @NotEmpty(message = "{msg.region.name.notEmpty}")
    @Size(max = 100, message = "{msg.region.name.size}")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Nombre del archivo de imagen de la región (ej: "3f2a...c1.jpg").
    // Es opcional, por eso admite null.
    @Column(name = "image", length = 255)
    private String image;

    // Relación uno a muchos con la entidad Province.
    // Una región puede tener muchas provincias.
    // Se excluye de toString y equals/hashCode para evitar bucles infinitos
    // con la relación bidireccional.
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Province> province;

    /**
     * Constructor personalizado que no incluye el campo `id`.
     * Se utiliza para crear instancias de `Region` cuando no es necesario o no
     * se conoce el `id` de la región (por ejemplo, antes de insertar la región
     * en la base de datos, donde el `id` es autogenerado).
     *
     * @param code Código de la región.
     * @param name Nombre de la región.
     */
    public Region(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Constructor personalizado que no incluye `id` ni la lista de provincias,
     * pero sí la imagen asociada.
     *
     * @param code  Código de la región.
     * @param name  Nombre de la región.
     * @param image Nombre del archivo de imagen de la región.
     */
    public Region(String code, String name, String image) {
        this.code = code;
        this.name = name;
        this.image = image;
    }
}