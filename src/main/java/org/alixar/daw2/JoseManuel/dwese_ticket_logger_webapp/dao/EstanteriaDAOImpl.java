package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Estanteria;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Region;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementación de la interfaz EstanteriaDAO para gestionar
 * las operaciones de acceso a datos de las estanterías.
 * Utiliza JdbcTemplate para realizar las consultas a la base de datos.
 */
@Repository
public class EstanteriaDAOImpl implements EstanteriaDAO {

    /** Objeto utilizado para ejecutar consultas SQL. */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor de la clase que recibe el JdbcTemplate.
     *
     * @param jdbcTemplate objeto utilizado para acceder a la base de datos.
     */
    public EstanteriaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene la lista de todas las estanterías de la base de datos.
     *
     * @return lista de estanterías.
     */
    @Override
    public List<Estanteria> listEstanterias() {
        String consulta = "SELECT * FROM estanteria";
        List<Estanteria> estanterias = jdbcTemplate.query(consulta, new
                BeanPropertyRowMapper<>(Estanteria.class));

        return estanterias;
    }

    /**
     * Inserta una nueva estantería en la base de datos.
     *
     * @param estanteria estantería que se desea insertar.
     */
    @Override
    public void insertEstanteria(Estanteria estanteria) {

        String consulta = "INSERT INTO estanteria (codigo,ubicacion,material,estado) VALUES(?,?,?,?)";

        int filasAfectadas = jdbcTemplate.update(consulta, estanteria.getCodigo(),
                estanteria.getUbicacion(), estanteria.getMaterial(),estanteria.getEstado());
    }

    /**
     * Actualiza los datos de una estantería existente.
     *
     * @param estanteria estantería con los datos actualizados.
     */
    @Override
    public void updateEstanteria(Estanteria estanteria) {

        String consulta = "UPDATE estanteria SET codigo = ?, ubicacion = ?, material = ?, estado = ? WHERE id = ?";

        jdbcTemplate.update(consulta, estanteria.getCodigo(),
                estanteria.getUbicacion(), estanteria.getMaterial(), estanteria.getEstado(), estanteria.getId());
    }

    /**
     * Elimina una estantería de la base de datos mediante su identificador.
     *
     * @param id identificador de la estantería que se desea eliminar.
     */
    @Override
    public void deleteEstanteria(Long id) {

        String consulta = "DELETE FROM estanteria WHERE id = ?";
        jdbcTemplate.update(consulta, id);
    }

    /**
     * Busca una estantería en la base de datos mediante su identificador.
     *
     * @param id identificador de la estantería.
     * @return estantería encontrada o null si no existe o se produce un error.
     */
    @Override
    public Estanteria getById(Long id) {

        String consulta = "SELECT * FROM estanteria WHERE id = ?";
        try{
            Estanteria estanteria = jdbcTemplate.queryForObject(consulta, new BeanPropertyRowMapper<>(Estanteria.class),
                    id);
            return estanteria;
        }catch(Exception e){
            return  null;
        }
    }

    /**
     * Comprueba si existe una estantería con el código indicado.
     * La comparación no distingue entre mayúsculas y minúsculas.
     *
     * @param codigo código de la estantería que se desea comprobar.
     * @return true si existe una estantería con ese código, false en caso contrario.
     */
    @Override
    public boolean existsByCode(String codigo) {

        String consulta = "SELECT COUNT(*) FROM estanteria WHERE UPPER(codigo)= ?";

        Integer count = jdbcTemplate.queryForObject(consulta, Integer.class, codigo.toUpperCase());
        boolean exists = count != null && count > 0;

        return exists;
    }

    /**
     * Comprueba si existe otra estantería con el mismo código,
     * excluyendo la estantería cuyo identificador se indica.
     * La comparación no distingue entre mayúsculas y minúsculas.
     *
     * @param codigo código de la estantería que se desea comprobar.
     * @param id identificador de la estantería que se desea excluir.
     * @return true si existe otra estantería con ese código, false en caso contrario.
     */
    @Override
    public boolean existsByCodeAndNotId(String codigo, Long id) {

        String consulta = "SELECT COUNT(*) FROM estanteria WHERE UPPER(codigo) = ? AND id != ?";

        Integer count= jdbcTemplate.queryForObject(consulta, Integer.class, codigo.toUpperCase(), id);
        return count != null && count > 0;
    }
}