package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Caja;



import java.util.List;

public interface CajaDAO {

    List<Caja> listEstanterias();
    void insertEstanteria(Caja caja);
    void updateEstanteria(Caja caja);
    void deleteCaja(Long id);
    Caja getById(Long id);
    boolean existsByCode(String codigo);
    boolean existsByCodeAndNotId(String codigo, Long id);
}
