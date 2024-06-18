package med.volll.api.domain.paciente;

import jakarta.persistence.*;
import med.volll.api.domain.consulta.Consulta;
import med.volll.api.domain.endereco.Endereco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Consulta> consultas = new ArrayList<>();
    private boolean ativo;

    public Paciente() {
    }

    public Paciente(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.dadosEndereco());
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if (dados.dadosEndereco() != null){
            this.endereco.atualizarInformacoes(dados.dadosEndereco());
        }
    }

    public void setConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public void deletar() {
        this.ativo = false;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Long getId() {
        return id;
    }
}
