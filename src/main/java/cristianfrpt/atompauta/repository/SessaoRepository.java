package cristianfrpt.atompauta.repository;

import cristianfrpt.atompauta.domain.entity.DadosSessao;
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

@Repository
@Slf4j
public class SessaoRepository{

    private static String INSERT = "INSERT INTO tbSessoes (idPauta, dtHrEncerramento) values (?,?)";
    private static String FINDBYID = "SELECT TOP 1 * from tbSessoes p where p.idSessao = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DadosSessao save(DadosSessao dadosSessao){

        log.info("Abrindo votacao para a sessao {}!", dadosSessao);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dadosSessao.getIdPauta());
            ps.setTimestamp(2, dadosSessao.getDtHrEncerramento());
            return ps;
        }, keyHolder);

        dadosSessao.setIdSessao(keyHolder.getKey().intValue());

        log.info("Sessao cadastrada no banco de dados {}!", dadosSessao);
        return dadosSessao;
    }


    public DadosSessao findById(Integer idSessao) throws SQLException {
        DadosSessao sessao = DadosSessao.builder().build();
        List<DadosSessao> optional = jdbcTemplate.query(FINDBYID, new Object[]{idSessao}, new int[]{Types.BIGINT}, mapperSessao);

        if(optional.size() > 0)
            sessao = optional.get(0);

        return sessao;
    }

    private RowMapper<DadosSessao> mapperSessao = (resultSet, rowNum) -> {
        DadosSessao sessao = DadosSessao.builder()
                .idSessao(resultSet.getInt("idSessao"))
                .idPauta(resultSet.getInt("idPauta"))
                .dtHrEncerramento(resultSet.getTimestamp("dtHrEncerramento"))
                .build();

        return sessao;
    };
}
