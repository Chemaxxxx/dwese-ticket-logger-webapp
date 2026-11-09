package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Caja;

import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos de las cajas.
 */
public interface CajaDAO {

    /**
     * Obtiene la lista de todas las cajas.
     *
     * @return lista de cajas.
     */
    List<Caja> listEstanterias();

    /**
     * Inserta una nueva caja en la base de datos.
     *
     * @param caja caja que se desea insertar.
     */
    void insertEstanteria(Caja caja);

    /**
     * Actualiza los datos de una caja existente.
     *
     * @param caja caja con los datos actualizados.
     */
    void updateEstanteria(Caja caja);

    /**
     * Elimina una caja de la base de datos mediante su identificador.
     *
     * @param id identificador de la caja que se desea eliminar.
     */
    void deleteCaja(Long id);

    /**
     * Busca una caja mediante su identificador.
     *
     * @param id identificador de la caja.
     * @return caja encontrada o null si no existe.
     */
    Caja getById(Long id);

    /**
     * Comprueba si existe una caja con el código indicado.
     *
     * @param codigo código de la caja.
     * @return true si existe una caja con ese código, false en caso contrario.
     */
    boolean existsByCode(String codigo);

    /**
     * Comprueba si existe otra caja con el mismo código,
     * excluyendo la caja cuyo identificador se indica.
     *
     * @param codigo código de la caja.
     * @param id identificador de la caja que se desea excluir.
     * @return true si existe otra caja con ese código, false en caso contrario.
     */
    boolean existsByCodeAndNotId(String codigo, Long id);
}