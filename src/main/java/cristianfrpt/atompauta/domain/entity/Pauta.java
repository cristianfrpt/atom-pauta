package cristianfrpt.atompauta.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Builder
@Getter
@Setter
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPauta;
    private String nome;
}
