package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Estanteria;

import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos de las estanterías.
 */
public interface EstanteriaDAO {

    /**
     * Obtiene la lista de todas las estanterías.
     *
     * @return lista de estanterías.
     */
    List<Estanteria> listEstanterias();

    /**
     * Inserta una nueva estantería en la base de datos.
     *
     * @param estanteria estantería que se desea insertar.
     */
    void insertEstanteria(Estanteria estanteria);

    /**
     * Actualiza los datos de una estantería existente.
     *
     * @param estanteria estantería con los datos actualizados.
     */
    void updateEstanteria(Estanteria estanteria);

    /**
     * Elimina una estantería de la base de datos mediante su identificador.
     *
     * @param id identificador de la estantería que se desea eliminar.
     */
    void deleteEstanteria(Long id);

    /**
     * Busca una estantería mediante su identificador.
     *
     * @param id identificador de la estantería.
     * @return estantería encontrada o null si no existe.
     */
    Estanteria getById(Long id);

    /**
     * Comprueba si existe una estantería con el código indicado.
     *
     * @param codigo código de la estantería.
     * @return true si existe una estantería con ese código, false en caso contrario.
     */
    boolean existsByCode(String codigo);

    /**
     * Comprueba si existe otra estantería con el mismo código,
     * excluyendo la estantería cuyo identificador se indica.
     *
     * @param codigo código de la estantería.
     * @param id identificador de la estantería que se desea excluir.
     * @return true si existe otra estantería con ese código, false en caso contrario.
     */
    boolean existsByCodeAndNotId(String codigo, Long id);
}