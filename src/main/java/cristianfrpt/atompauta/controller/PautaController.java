package cristianfrpt.atompauta.controller;

import cristianfrpt.atompauta.domain.mapper.DadosSessaoMapper;
import cristianfrpt.atompauta.domain.mapper.PautaMapper;
import cristianfrpt.atompauta.domain.mapper.VotoMapper;
import cristianfrpt.atompauta.domain.request.AbrirVotacaoRequest;
import cristianfrpt.atompauta.domain.request.PautaRequest;
import cristianfrpt.atompauta.domain.request.VotoRequest;
import cristianfrpt.atompauta.domain.response.AbrirVotacaoResponse;
import cristianfrpt.atompauta.domain.response.PautaResponse;
import cristianfrpt.atompauta.domain.response.ResultadoPautaResponse;
import cristianfrpt.atompauta.domain.response.VotoResponse;
import cristianfrpt.atompauta.service.PautaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
public class PautaController {

    @Autowired
    PautaService service;

    @PostMapping(value = "/v1/cadastrarPauta")
    @ResponseBody
    public ResponseEntity<?> cadastrarPauta(@Valid @RequestBody @NotNull PautaRequest pautaRequest) {
        log.info("Iniciando cadastro da pauta: {}", pautaRequest);

        try {
            PautaResponse response = PautaMapper.toResponse(service.cadastrarPauta(PautaMapper.toEntity(pautaRequest)));

            log.info("Pauta {} cadastrada com sucesso!", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info("{}: {}",e.getMessage(),e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/v1/abrirVotacao")
    @ResponseBody
    public ResponseEntity<?> abrirVotacao(@Valid @RequestBody @NotNull AbrirVotacaoRequest request) {
        log.info("Iniciando votacao da pauta: {}", request);

        try {
            AbrirVotacaoResponse response = DadosSessaoMapper.toResponse(service.abrirVotacao(DadosSessaoMapper.toEntity(request)));
            log.info("Abertura da votacao realizada com sucesso: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info("{}: {}",e.getMessage(),e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/v1/cadastrarVoto")
    @ResponseBody
    public ResponseEntity<?> cadastrarVoto(@Valid @RequestBody @NotNull VotoRequest request) {
        log.info("Cadastrando voto: {}", request);

        try {
            VotoResponse response = VotoMapper.toResponse(service.cadastrarVoto(VotoMapper.toEntity(request)));

            log.info("Voto cadastrado com sucesso {}!", response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info("{}: {}",e.getMessage(),e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/v1/resultadoSessao/{idSessao}")
    @ResponseBody
    public ResponseEntity<?> buscarResultado(@Valid @PathVariable @Positive Integer idSessao) {
        log.info("Buscando resultado da sessao de id: {}", idSessao);

        try {
            ResultadoPautaResponse response = service.buscarResultadoSessao(idSessao);
            log.info("Votos contabilizados com sucesso! {}", idSessao);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info("{}: {}",e.getMessage(),e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
