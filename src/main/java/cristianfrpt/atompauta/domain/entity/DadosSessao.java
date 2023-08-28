package cristianfrpt.atompauta.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Builder
@Data
@ToString
public class DadosSessao {
    private Integer idSessao;
    private Integer idPauta;
    private Timestamp dtHrEncerramento;
}
