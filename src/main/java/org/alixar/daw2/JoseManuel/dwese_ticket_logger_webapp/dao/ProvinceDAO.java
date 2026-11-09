package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Province;

import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a la base de datos
 * relacionadas con las provincias.
 */
public interface ProvinceDAO {

    /**
     * Obtiene todas las provincias almacenadas en la base de datos.
     *
     * @return lista con todas las provincias.
     */
    List<Province> listAllProvinces();

    /**
     * Inserta una nueva provincia en la base de datos.
     *
     * @param province objeto Province con los datos que se van a insertar.
     */
    void insertProvince(Province province);

    /**
     * Actualiza los datos de una provincia existente.
     *
     * @param province objeto Province con los datos actualizados.
     */
    void updateProvince(Province province);

    /**
     * Elimina una provincia de la base de datos mediante su identificador.
     *
     * @param id identificador de la provincia que se quiere eliminar.
     */
    void deleteProvince(Long id);

    /**
     * Busca una provincia en la base de datos mediante su identificador.
     *
     * @param id identificador de la provincia que se quiere buscar.
     * @return la provincia encontrada o null si no existe.
     */
    Province getProvinceById(Long id);

    /**
     * Comprueba si existe una provincia con el código indicado.
     *
     * @param code código de la provincia que se quiere comprobar.
     * @return true si existe una provincia con ese código o false si no existe.
     */
    boolean existsProvinceByCode(String code);

    /**
     * Comprueba si existe otra provincia con el mismo código, excluyendo
     * la provincia que tiene el identificador indicado.
     *
     * @param code código de la provincia que se quiere comprobar.
     * @param id identificador de la provincia que se debe excluir.
     * @return true si existe otra provincia con ese código o false si no existe.
     */
    boolean existsProvinceByCodeAndNotId(String code, Long id);
}