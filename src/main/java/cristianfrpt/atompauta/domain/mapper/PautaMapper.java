package cristianfrpt.atompauta.domain.mapper;

import cristianfrpt.atompauta.domain.entity.Pauta;
import cristianfrpt.atompauta.domain.request.PautaRequest;
import cristianfrpt.atompauta.domain.response.PautaResponse;

public class PautaMapper {
    public static Pauta toEntity(PautaRequest request){
        return Pauta.builder()
                .nome(request.getNome())
                .build();
    }

    public static PautaResponse toResponse(Pauta pauta) {
        return PautaResponse.builder()
                .idPauta(pauta.getIdPauta())
                .nome(pauta.getNome())
                .build();
    }
}