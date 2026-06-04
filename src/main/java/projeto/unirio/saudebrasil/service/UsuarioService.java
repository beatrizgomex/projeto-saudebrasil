package projeto.unirio.saudebrasil.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import projeto.unirio.saudebrasil.dto.LoginRequest;
import projeto.unirio.saudebrasil.entitys.Usuario;
import projeto.unirio.saudebrasil.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;

@Service

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {

        if (!usuario.getEmail().endsWith("@gmail.com")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O email deve ser um Gmail"
            );
        }

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
                        HttpStatus.NOT_FOUND, "Usuária não encontrada com ID: " + idUsuario));
    }

    public Usuario atualizarPerfil(Long id, Usuario usuarioNovosDados) {

        Usuario usuarioAntigo = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        if(usuarioNovosDados.getNome() != null) {
            usuarioAntigo.setNome(usuarioNovosDados.getNome());
        }

        if(usuarioNovosDados.getEmail() != null) {
            usuarioAntigo.setEmail(usuarioNovosDados.getEmail());
        }

        if(usuarioNovosDados.getSenha() != null) {
            usuarioAntigo.setSenha(usuarioNovosDados.getSenha());
        }

        if(usuarioNovosDados.getTelefone() != null) {
            usuarioAntigo.setTelefone(usuarioNovosDados.getTelefone());
        }

        if(usuarioNovosDados.getGenero() != null) {
            usuarioAntigo.setGenero(usuarioNovosDados.getGenero());
        }

        return usuarioRepository.save(usuarioAntigo);
    }

    public void excluirPerfil(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }
        usuarioRepository.deleteById(id);
    }

}
