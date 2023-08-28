package cristianfrpt.atompauta.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class ResultadoPautaResponse {
    Integer idSessao;
    Integer resultado;
}
