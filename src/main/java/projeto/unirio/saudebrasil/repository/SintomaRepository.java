package projeto.unirio.saudebrasil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.unirio.saudebrasil.entitys.Sintoma;

import java.time.LocalDate;
import java.util.List;

public interface SintomaRepository extends JpaRepository<Sintoma, Long> {

    List<Sintoma> findByUsuarioIdUsuario(Long idUsuario);

    List<Sintoma> findByUsuarioIdUsuarioAndDataRegistroBetween(
            Long idUsuario,
            LocalDate inicio,
            LocalDate fim
    );
}