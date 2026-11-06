package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Estanteria;

import java.util.List;

public interface EstanteriaDAO {

    List<Estanteria> listEstanterias();
    void insertEstanteria(Estanteria estanteria);
    void updateEstanteria(Estanteria estanteria);
    void deleteEstanteria(Long id);
    Estanteria getById(Long id);
    boolean existsByCode(String codigo);
    boolean existsByCodeAndNotId(String codigo, Long id);
}
