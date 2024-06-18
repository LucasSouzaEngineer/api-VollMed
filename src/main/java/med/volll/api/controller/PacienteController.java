package med.volll.api.controller;

import jakarta.validation.Valid;
import med.volll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder
                .path("/pacientes/{id}")
                .buildAndExpand(paciente.getId())
                .toUri();
        var dadosDetalhamentoPaciente = new DadosDetalhamentoPaciente(paciente);

        return ResponseEntity
                .created(uri)
                .body(dadosDetalhamentoPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(sort = {"nome"})Pageable paginacao){
        var page = repository.findByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        var dadosDetalhamento = new DadosDetalhamentoPaciente(paciente);

        return ResponseEntity.ok(dadosDetalhamento);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        DadosDetalhamentoPaciente dadosDetalhamentoPaciente = new DadosDetalhamentoPaciente(paciente);
        return ResponseEntity.ok(dadosDetalhamentoPaciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.deletar();
        return ResponseEntity.noContent().build();
    }
}
