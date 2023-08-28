package cristianfrpt.atompauta.domain.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Voto {

    private Integer idVoto;
    private Integer idSessao;
    private String cpfAssociado;
    private Integer voto;
}
