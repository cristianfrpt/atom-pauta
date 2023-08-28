package cristianfrpt.atompauta.domain.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ToString
@Valid
public class PautaRequest {
    @NotNull
    private String nome;
}
