package cristianfrpt.atompauta.repository;

import cristianfrpt.atompauta.domain.entity.Pauta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

@Repository
@Slf4j
public class PautaRepository{

    private static String INSERT = "INSERT INTO tbPautas (nome) values (?)";
    private static String FINDBYID = "SELECT TOP 1 * from tbPautas p where p.idPauta = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Pauta save(Pauta pauta){

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pauta.getNome());
            return ps;
        }, keyHolder);

        pauta.setIdPauta(keyHolder.getKey().intValue());

        return pauta;
    }

    public Pauta findById(Integer idPauta) {
        return jdbcTemplate.query(FINDBYID, new Object[]{idPauta}, new int[]{Types.BIGINT}, mapperPauta).get(0);
    }

    private RowMapper<Pauta> mapperPauta = (resultSet, rowNum) -> {
        Pauta pauta = Pauta.builder()
                .idPauta(resultSet.getInt("idPauta"))
                .nome(resultSet.getString("nome"))
                .build();

        return pauta;

    };
}
