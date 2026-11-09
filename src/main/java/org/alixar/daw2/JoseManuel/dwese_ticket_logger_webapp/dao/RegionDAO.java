package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Region;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a la base de datos
 * relacionadas con las regiones.
 */
public interface RegionDAO {

    /**
     * Obtiene todas las regiones almacenadas en la base de datos.
     *
     * @return lista con todas las regiones.
     */
    List<Region> listAllRegions();

    /**
     * Inserta una nueva región en la base de datos.
     *
     * @param region objeto Region con los datos que se van a insertar.
     */
    void insertRegion(Region region);

    /**
     * Actualiza los datos de una región existente.
     *
     * @param region objeto Region con los datos actualizados.
     */
    void updateRegion(Region region);

    /**
     * Elimina una región de la base de datos mediante su identificador.
     *
     * @param id identificador de la región que se quiere eliminar.
     */
    void deleteRegion(Long id);

    /**
     * Busca una región en la base de datos mediante su identificador.
     *
     * @param id identificador de la región que se quiere buscar.
     * @return la región encontrada o null si no existe.
     */
    Region getRegionById(Long id);

    /**
     * Comprueba si existe una región con el código indicado.
     *
     * @param code código de la región que se quiere comprobar.
     * @return true si existe una región con ese código o false si no existe.
     */
    boolean existsRegionByCode(String code);

    /**
     * Comprueba si existe otra región con el mismo código, excluyendo
     * la región que tiene el identificador indicado.
     *
     * @param code código de la región que se quiere comprobar.
     * @param id identificador de la región que se debe excluir.
     * @return true si existe otra región con ese código o false si no existe.
     */
    boolean existsRegionByCodeAndNotId(String code, Long id);

}