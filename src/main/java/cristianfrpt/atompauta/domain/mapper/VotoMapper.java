package cristianfrpt.atompauta.domain.mapper;

import cristianfrpt.atompauta.domain.entity.Voto;
import cristianfrpt.atompauta.domain.request.VotoRequest;
import cristianfrpt.atompauta.domain.response.VotoResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VotoMapper {
    public static Voto toEntity(VotoRequest request){
        return Voto.builder()
                .idSessao(request.getIdSessao())
                .cpfAssociado(request.getCpfAssociado())
                .voto(request.getVoto())
                .build();
    }
    public static VotoResponse toResponse(Voto voto){
        return VotoResponse.builder()
                .idVoto(voto.getIdVoto())
                .build();
    }


}