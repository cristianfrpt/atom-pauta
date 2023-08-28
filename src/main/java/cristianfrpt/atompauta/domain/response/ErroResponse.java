package cristianfrpt.atompauta.domain.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class ErroResponse {
    private Date timestamp;
    private int status;
    private String path;
    private List<String> erros = new ArrayList<>();

    public void addError(String message) {
        this.erros.add(message);
    }

}
