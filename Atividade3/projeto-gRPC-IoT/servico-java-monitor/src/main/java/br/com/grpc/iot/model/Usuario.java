package br.com.grpc.iot.model;

import javax.persistence.*; 
import java.util.List;
import java.io.Serializable;

@Entity
@Table(name = "usuarios") // Nome da tabela no banco de dados
public class Usuario implements Serializable {

    @Id // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID (auto-incremento)
    private Long id;

    @Column(nullable = false, unique = true) // Coluna não nula e única
    private String email; // Usaremos o email como identificador único principal para o usuário

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sensor> sensores;

    // Construtor padrão (obrigatório para JPA)
    public Usuario() {}

    // Construtor com email e nome
    public Usuario(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() { // <-- ESTE MÉTODO É CRUCIAL PARA O ERRO getEmail()
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }

    @Override
    public String toString() {
        return "Usuario{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", nome='" + nome + '\'' +
               '}';
    }
}
