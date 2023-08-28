package cristianfrpt.atompauta.repository;

import cristianfrpt.atompauta.domain.entity.Voto;
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
import java.util.List;

@Slf4j
@Repository
public class VotoRepository {

    private static String INSERT = "INSERT INTO tbVotos (idSessao, cpfAssociado, voto) values (?,?,?)";
    private static String FINDBYIDANDSESSIONID = "SELECT TOP 1 * from tbVotos v where v.idSessao = ? AND v.cpfAssociado = ?";

    private static String FINDALLBYSESSIONID = "SELECT * from tbVotos v where v.idSessao = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Voto save(Voto voto){

        log.info("Salvando voto no banco de dados {}!", voto);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, voto.getIdSessao());
            ps.setString(2, voto.getCpfAssociado());
            ps.setInt(3, voto.getVoto());
            return ps;
        }, keyHolder);

        voto.setIdVoto(keyHolder.getKey().intValue());
        return voto;
    }


    public List<Voto> findAllBySessaoId(Integer idSessao) {
        return jdbcTemplate.query(FINDALLBYSESSIONID, new Object[]{idSessao}, new int[]{Types.BIGINT}, mapperVoto);
    }
    public Voto findByCpfAndSessaoId(Integer idSessao, String cpfAssociado) throws SQLException {
        Voto voto = Voto.builder().build();

        if(jdbcTemplate.query(FINDBYIDANDSESSIONID, new Object[]{idSessao,cpfAssociado}, new int[]{Types.BIGINT, Types.VARCHAR}, mapperVoto).size() > 0)
            voto = jdbcTemplate.query(FINDBYIDANDSESSIONID, new Object[]{idSessao,cpfAssociado}, new int[]{Types.BIGINT, Types.VARCHAR}, mapperVoto).get(0);

        return voto;
    }

    private RowMapper<Voto> mapperVoto = (resultSet, rowNum) -> {
        Voto voto = Voto.builder()
                .idVoto(resultSet.getInt("idVoto"))
                .idSessao(resultSet.getInt("idSessao"))
                .cpfAssociado(resultSet.getString("cpfAssociado"))
                .voto(resultSet.getInt("voto"))
                .build();

        return voto;

    };
}
