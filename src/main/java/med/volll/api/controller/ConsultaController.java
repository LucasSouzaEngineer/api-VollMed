package med.volll.api.controller;

import jakarta.validation.Valid;
import med.volll.api.domain.consulta.ConsultaService;
import med.volll.api.domain.consulta.DadosCadastroConsulta;
import med.volll.api.domain.consulta.DadosCancelamentoConsulta;
import med.volll.api.domain.consulta.DadosDetalheConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private ConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroConsulta dados, UriComponentsBuilder uriComponentsBuilder){
        var detalhamentoConsulta = service.cadastrar(dados);
        var uri = uriComponentsBuilder.path("/consultas/{id}")
                        .buildAndExpand(detalhamentoConsulta.id())
                        .toUri();
        return ResponseEntity.created(uri).body(detalhamentoConsulta);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalheConsulta>> listar(Pageable pageable){
        var page = service.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
