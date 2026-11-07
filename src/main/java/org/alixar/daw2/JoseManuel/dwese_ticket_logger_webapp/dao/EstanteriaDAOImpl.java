package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.dao;

import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Estanteria;
import org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.entities.Region;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstanteriaDAOImpl implements EstanteriaDAO {

    private final JdbcTemplate jdbcTemplate;

    public EstanteriaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Estanteria> listEstanterias() {
        String consulta = "SELECT * FROM estanteria";
        List<Estanteria> estanterias = jdbcTemplate.query(consulta, new
                BeanPropertyRowMapper<>(Estanteria.class));

        return estanterias;
    }

    @Override
    public void insertEstanteria(Estanteria estanteria) {

        String consulta = "INSERT INTO estanteria (codigo,ubicacion,material,estado) VALUES(?,?,?,?)";

        int filasAfectadas = jdbcTemplate.update(consulta, estanteria.getCodigo(),
                estanteria.getUbicacion(), estanteria.getMaterial(),estanteria.getEstado());
    }

    @Override
    public void updateEstanteria(Estanteria estanteria) {

        String consulta = "UPDATE estanteria SET codigo = ?, ubicacion = ?, material = ?, estado = ? WHERE id = ?";

        jdbcTemplate.update(consulta, estanteria.getCodigo(),
                estanteria.getUbicacion(), estanteria.getMaterial(), estanteria.getEstado(), estanteria.getId());
    }

    @Override
    public void deleteEstanteria(Long id) {

        String consulta = "DELETE FROM estanteria WHERE id = ?";
        jdbcTemplate.update(consulta, id);
    }

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

    @Override
    public boolean existsByCode(String codigo) {

        String consulta = "SELECT COUNT(*) FROM estanteria WHERE UPPER(codigo)= ?";

        Integer count = jdbcTemplate.queryForObject(consulta, Integer.class, codigo.toUpperCase());
        boolean exists = count != null && count > 0;

        return exists;
    }

    @Override
    public boolean existsByCodeAndNotId(String codigo, Long id) {

        String consulta = "SELECT COUNT(*) FROM estanteria WHERE UPPER(codigo) = ? AND id != ?";

        Integer count= jdbcTemplate.queryForObject(consulta, Integer.class, codigo, id);
        return count != null && count > 0;
    }
}
