package projeto.unirio.saudebrasil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.unirio.saudebrasil.entitys.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);
}
