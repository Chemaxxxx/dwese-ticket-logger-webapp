package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.repositories;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de provincias. Los métodos CRUD vienen heredados de
 * JpaRepository.
 */
public interface ProvinceRepository extends JpaRepository<Province, Long> {


    // GET ID: Buscar una región por su ID
    // Ya existe como findById(id).

    // INSERT: Insertar una región
    // Se utiliza save(province).

    // UPDATE: Actualizar una región
    // También se utiliza save(province).

    // DELETE: Eliminar una región por su ID
    // Se utiliza deleteById(id).

    @Query("SELECT COUNT(p) > 0 FROM Province p WHERE p.code = :code")
    boolean existsProvinceByCode(@Param("code") String code);

    @Query("SELECT COUNT(p) > 0 FROM Province p WHERE p.code = :code AND p.id != :id")
    boolean existsProvinceByCodeAndNotId(@Param("code") String code,
                                         @Param("id") Long id);
}