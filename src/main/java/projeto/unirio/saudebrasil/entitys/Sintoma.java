package projeto.unirio.saudebrasil.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sintoma")
public class Sintoma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSintoma;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Integer intensidade;

    @Column(length = 500)
    private String medicamentos;

    @Column(length = 500)
    private String gatilhos;

    @Column(length = 500)
    private String obs;

    @Column(nullable = false)
    private LocalDate dataRegistro;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;
}