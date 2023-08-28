package cristianfrpt.atompauta.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@Builder
@ToString
public class PautaResponse {
    Integer idPauta;
    String nome;
}
