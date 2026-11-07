
package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Caja;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CajaDAOImpl implements CajaDAO {

    private final JdbcTemplate jdbcTemplate;

    public CajaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Caja> listEstanterias() {
        String consulta = "SELECT * FROM caja";
        List<Caja> cajaList = jdbcTemplate.query(consulta, new BeanPropertyRowMapper<>(Caja.class));

        return cajaList;
    }

    @Override
    public void insertEstanteria(Caja caja) {

        String consulta = "INSERT INTO caja (codigo, fecha_empaque, es_fragil, peso_kg, contenido, id_estanteria) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(consulta, caja.getCodigo(), caja.getFecha_empaque(), caja.isEs_fragil(),
                caja.getPeso_kg(), caja.getContenido(), caja.getId_estanteria());
    }

    @Override
    public void updateEstanteria(Caja caja) {

        String consulta = "UPDATE caja SET codigo = ?, fecha_empaque = ?, es_fragil = ?, " +
                "peso_kg = ?, contenido = ?, id_estanteria = ? WHERE id = ?";

        jdbcTemplate.update(consulta, caja.getCodigo(), caja.getFecha_empaque(), caja.isEs_fragil(),
                caja.getPeso_kg(), caja.getContenido(), caja.getId_estanteria(), caja.getId());
    }

    @Override
    public void deleteCaja(Long id) {

        String consulta = "DELETE FROM caja WHERE id = ?";

        jdbcTemplate.update(consulta, id);
    }

    @Override
    public Caja getById(Long id) {

        String consulta = "SELECT * FROM caja WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(consulta, new BeanPropertyRowMapper<>(Caja.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean existsByCode(String codigo) {

        String consulta = "SELECT COUNT(*) FROM caja WHERE codigo = ?";

        Integer resultado = jdbcTemplate.queryForObject(consulta, Integer.class, codigo);

        return resultado != null && resultado > 0;
    }

    @Override
    public boolean existsByCodeAndNotId(String codigo, Long id) {

        String consulta = "SELECT COUNT(*) FROM caja WHERE codigo = ? AND id != ?";

        Integer resultado = jdbcTemplate.queryForObject(consulta, Integer.class, codigo, id);

        return resultado != null && resultado > 0;
    }
}
