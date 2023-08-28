package cristianfrpt.atompauta.domain.request;


import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ToString
@Validated
public class VotoRequest {
    @NotNull
    @Min(value = 1)
    private Integer idSessao;
    @NotNull
    private String cpfAssociado;
    @NotNull
    private Integer voto;
}
