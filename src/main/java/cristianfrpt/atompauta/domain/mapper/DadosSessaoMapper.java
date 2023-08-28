package cristianfrpt.atompauta.domain.mapper;

import cristianfrpt.atompauta.domain.entity.DadosSessao;
import cristianfrpt.atompauta.domain.request.AbrirVotacaoRequest;
import cristianfrpt.atompauta.domain.response.AbrirVotacaoResponse;

import java.text.SimpleDateFormat;

public class DadosSessaoMapper {
    public static DadosSessao toEntity(AbrirVotacaoRequest request){
        return DadosSessao.builder()
                .idPauta(request.getIdPauta())
                .dtHrEncerramento(request.getDtHrEncerramento())
                .build();
    }

    public static AbrirVotacaoResponse toResponse(DadosSessao dadosSessao) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDtHrEncerramento = sdf.format(dadosSessao.getDtHrEncerramento());

        return AbrirVotacaoResponse.builder()
                .idSessao(dadosSessao.getIdSessao())
                .idPauta(dadosSessao.getIdPauta())
                .dtHrEncerramento(formattedDtHrEncerramento)
                .build();
    }
}
