package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estanteria {

    private Long id;
    private String codigo;
    private String ubicacion;
    private String material;
    private String estado;

    public Estanteria(String codigo, String ubicacion, String material, String estado) {
        this.codigo = codigo;
        this.ubicacion = ubicacion;
        this.material = material;
        this.estado = estado;
    }
}
