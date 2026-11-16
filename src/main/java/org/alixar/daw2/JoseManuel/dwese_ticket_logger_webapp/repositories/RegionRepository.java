package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.repositories;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio de regiones. Los métodos CRUD (findAll, findById, save,
 * deleteById...) ya vienen heredados de JpaRepository.
 */
public interface RegionRepository extends JpaRepository<Region, Long> {

    // GET ID: Buscar una región por su ID
    // Ya existe como findById(id).

    // INSERT: Insertar una región
    // Se utiliza save(region).

    // UPDATE: Actualizar una región
    // También se utiliza save(region).

    // DELETE: Eliminar una región por su ID
    // Se utiliza deleteById(id).



    @Query("SELECT COUNT(r) > 0 FROM Region r WHERE r.code = :code")
    boolean existsRegionByCode(@Param("code") String code);

    @Query("SELECT COUNT(r) > 0 FROM Region r WHERE r.code = :code AND r.id != :id")
    boolean existsRegionByCodeAndNotId(@Param("code") String code,
                                       @Param("id") Long id);
}