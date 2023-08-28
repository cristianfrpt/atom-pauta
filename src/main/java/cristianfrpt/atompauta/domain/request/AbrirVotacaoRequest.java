package cristianfrpt.atompauta.domain.request;

import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

@Data
@ToString
@Validated
public class AbrirVotacaoRequest {
    @Positive
    Integer idPauta;
    @NotNull
    Timestamp dtHrEncerramento;
}
