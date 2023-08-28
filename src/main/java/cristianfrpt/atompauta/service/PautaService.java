package cristianfrpt.atompauta.service;


import cristianfrpt.atompauta.domain.entity.DadosSessao;
import cristianfrpt.atompauta.domain.entity.Pauta;
import cristianfrpt.atompauta.domain.entity.Voto;
import cristianfrpt.atompauta.domain.exception.PautaException;
import cristianfrpt.atompauta.domain.exception.SessaoException;
import cristianfrpt.atompauta.domain.exception.VotoException;
import cristianfrpt.atompauta.domain.response.ResultadoPautaResponse;
import cristianfrpt.atompauta.repository.PautaRepository;
import cristianfrpt.atompauta.repository.SessaoRepository;
import cristianfrpt.atompauta.repository.VotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PautaService {
   @Autowired
   PautaRepository pautaRepository;
    @Autowired
    SessaoRepository sessaoRepository;
    @Autowired
    VotoRepository votoRepository;

    public Pauta cadastrarPauta(Pauta pauta)  {
        return pautaRepository.save(pauta);
    }

    public DadosSessao abrirVotacao(DadosSessao dadosSessao) throws SQLException {
        log.info("Validando a pauta!");
        validaPauta(dadosSessao.getIdPauta());
        log.info("Pauta validada!");
        return sessaoRepository.save(dadosSessao);
    }

    private void validaPauta(Integer idPauta) throws SQLException {
       Pauta pauta = pautaRepository.findById(idPauta);

       if(pauta == null || pauta.getIdPauta() == null)
           throw new PautaException("Pauta nao encontrada");
    }

    public Voto cadastrarVoto(Voto voto) throws SQLException {
        log.info("Validando as informacoes do voto!");
        validaSessao(voto.getIdSessao());
        log.info("Sessao validada!");
        validaVotoUnico(voto.getIdSessao(), voto.getCpfAssociado());
        log.info("VotoUnico validado!");

         return votoRepository.save(voto);
    }

    private void validaSessao(Integer idSessao) throws SQLException {
        DadosSessao sessao = sessaoRepository.findById(idSessao);
        if(sessao == null || sessao.getIdSessao() == null)
            throw new SessaoException("Sessao nao encontrada");
    }

    private void validaVotoUnico(Integer idSessao, String cpfAssociado) throws SQLException {
        Voto voto = votoRepository.findByCpfAndSessaoId(idSessao,cpfAssociado);

         if(voto != null && voto.getIdVoto() != null)
             throw new VotoException("O Associado informado já votou nesta sessao");
    }

    public ResultadoPautaResponse buscarResultadoSessao(Integer idSessao) throws SQLException {
        log.info("Validando termino da sessao de id: {}", idSessao);
        validaTerminoSessao(idSessao);
        log.info("Sessao de id {} finalizada, contabilizando resultados...", idSessao);

        return calculaResultadoSessao(idSessao);
    }


    private void validaTerminoSessao(Integer idSessao) throws SQLException {
        DadosSessao sessao = sessaoRepository.findById(idSessao);
        if(sessao == null || sessao.getIdSessao() == null)
            throw new SessaoException("Sessao nao encontrada");

        Timestamp dataAgora = Timestamp.valueOf(LocalDateTime.now());

        if(dataAgora.before(sessao.getDtHrEncerramento()))
             throw new SessaoException("Sessao ainda em andamento");
    }

    private ResultadoPautaResponse calculaResultadoSessao(Integer idSessao) {
         List<Voto> votos = votoRepository.findAllBySessaoId(idSessao);

         if(votos.size() == 0)
           throw new SessaoException("Nao existem votos para a sessao");


         long contVotoSim = votos.stream().filter(voto -> voto.getVoto() == 1).count();
         long contVotoNao = votos.stream().filter(voto -> voto.getVoto() == 2).count();


         return ResultadoPautaResponse.builder()
                 .idSessao(idSessao)
                .resultado(contVotoSim == contVotoNao ? 3 : contVotoSim > contVotoNao ? 1 : 2)
                .build();
    }

}
