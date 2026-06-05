package projeto.unirio.saudebrasil.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import projeto.unirio.saudebrasil.dto.LoginRequest;
import projeto.unirio.saudebrasil.entitys.Usuario;
import projeto.unirio.saudebrasil.repository.SintomaRepository;
import projeto.unirio.saudebrasil.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

 
    private final SintomaRepository sintomaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SintomaRepository sintomaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sintomaRepository = sintomaRepository;
    }


    private void validarEmail(String email) {
        if (email != null && !email.endsWith("@gmail.com")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O email deve ser um Gmail"
            );
        }
    }

    public Usuario cadastrar(Usuario usuario) {
        validarEmail(usuario.getEmail());

        if (!usuario.getSenha().matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "A senha deve ter no mínimo 8 caracteres, contendo letras e números"
            );
        }

        try {
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Email ou CPF já cadastrado"
            );
        }
    }

    public Usuario login(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email ou senha incorretos"
            ));

        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Email ou senha incorretos"
            );
        }
        return usuario;
    }

    public Usuario buscarPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + idUsuario));
    }

    public Usuario atualizarPerfil(Long id, Usuario usuarioNovosDados) {
        Usuario usuarioAntigo = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"
            ));

        if (usuarioNovosDados.getNome() != null) {
            usuarioAntigo.setNome(usuarioNovosDados.getNome());
        }

        if (usuarioNovosDados.getEmail() != null) {
            // CORRIGIDO: validação de @gmail.com agora aplicada também na atualização
            validarEmail(usuarioNovosDados.getEmail());
            usuarioAntigo.setEmail(usuarioNovosDados.getEmail());
        }

        if (usuarioNovosDados.getSenha() != null) {
            usuarioAntigo.setSenha(usuarioNovosDados.getSenha());
        }

        if (usuarioNovosDados.getTelefone() != null) {
            usuarioAntigo.setTelefone(usuarioNovosDados.getTelefone());
        }

        if (usuarioNovosDados.getGenero() != null) {
            usuarioAntigo.setGenero(usuarioNovosDados.getGenero());
        }

        return usuarioRepository.save(usuarioAntigo);
    }

    public void excluirPerfil(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }

    
        sintomaRepository.deleteAll(
            sintomaRepository.findByUsuarioIdUsuario(id)
        );
        usuarioRepository.deleteById(id);
    }
}