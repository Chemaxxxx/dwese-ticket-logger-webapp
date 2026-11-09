package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Caja;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Clase encargada de realizar las operaciones de acceso a la base de datos
 * relacionadas con las cajas mediante JdbcTemplate.
 */
@Repository
public class CajaDAOImpl implements CajaDAO {

    /** Objeto utilizado para ejecutar consultas SQL sobre la base de datos. */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor que recibe el JdbcTemplate para realizar las consultas.
     *
     * @param jdbcTemplate objeto utilizado para acceder a la base de datos.
     */
    public CajaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene todas las cajas almacenadas en la base de datos.
     *
     * @return lista con todas las cajas.
     */
    @Override
    public List<Caja> listEstanterias() {
        String consulta = "SELECT * FROM caja";
        List<Caja> cajaList = jdbcTemplate.query(consulta, new BeanPropertyRowMapper<>(Caja.class));

        return cajaList;
    }

    /**
     * Inserta una nueva caja en la base de datos.
     *
     * @param caja objeto Caja con los datos que se van a insertar.
     */
    @Override
    public void insertEstanteria(Caja caja) {

        String consulta = "INSERT INTO caja (codigo, fecha_empaque, es_fragil, peso_kg, contenido, id_estanteria) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(consulta, caja.getCodigo(), caja.getFecha_empaque(), caja.isEs_fragil(),
                caja.getPeso_kg(), caja.getContenido(), caja.getId_estanteria());
    }

    /**
     * Actualiza los datos de una caja existente en la base de datos.
     *
     * @param caja objeto Caja con los datos actualizados.
     */
    @Override
    public void updateEstanteria(Caja caja) {

        String consulta = "UPDATE caja SET codigo = ?, fecha_empaque = ?, es_fragil = ?, " +
                "peso_kg = ?, contenido = ?, id_estanteria = ? WHERE id = ?";

        jdbcTemplate.update(consulta, caja.getCodigo(), caja.getFecha_empaque(), caja.isEs_fragil(),
                caja.getPeso_kg(), caja.getContenido(), caja.getId_estanteria(), caja.getId());
    }

    /**
     * Elimina una caja de la base de datos utilizando su identificador.
     *
     * @param id identificador de la caja que se quiere eliminar.
     */
    @Override
    public void deleteCaja(Long id) {

        String consulta = "DELETE FROM caja WHERE id = ?";

        jdbcTemplate.update(consulta, id);
    }

    /**
     * Busca una caja en la base de datos mediante su identificador.
     *
     * @param id identificador de la caja que se quiere buscar.
     * @return la caja encontrada o null si no existe.
     */
    @Override
    public Caja getById(Long id) {

        String consulta = "SELECT * FROM caja WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(consulta, new BeanPropertyRowMapper<>(Caja.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Comprueba si existe una caja con el código indicado.
     *
     * @param codigo código de la caja que se quiere comprobar.
     * @return true si existe una caja con ese código o false si no existe.
     */
    @Override
    public boolean existsByCode(String codigo) {

        String consulta = "SELECT COUNT(*) FROM caja WHERE codigo = ?";

        Integer resultado = jdbcTemplate.queryForObject(consulta, Integer.class, codigo);

        return resultado != null && resultado > 0;
    }

    /**
     * Comprueba si existe otra caja con el mismo código, excluyendo la caja
     * que tiene el identificador indicado.
     *
     * @param codigo código de la caja que se quiere comprobar.
     * @param id identificador de la caja que se debe excluir.
     * @return true si existe otra caja con ese código o false si no existe.
     */
    @Override
    public boolean existsByCodeAndNotId(String codigo, Long id) {

        String consulta = "SELECT COUNT(*) FROM caja WHERE codigo = ? AND id != ?";

        Integer resultado = jdbcTemplate.queryForObject(consulta, Integer.class, codigo, id);

        return resultado != null && resultado > 0;
    }
}