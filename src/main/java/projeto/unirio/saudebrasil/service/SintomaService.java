package projeto.unirio.saudebrasil.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import projeto.unirio.saudebrasil.entitys.Sintoma;
import projeto.unirio.saudebrasil.entitys.Usuario;
import projeto.unirio.saudebrasil.repository.SintomaRepository;
import projeto.unirio.saudebrasil.repository.UsuarioRepository;

import java.util.List;

@Service
public class SintomaService {

    private final SintomaRepository sintomaRepository;
    private final UsuarioRepository usuarioRepository;

    public SintomaService(
            SintomaRepository sintomaRepository,
            UsuarioRepository usuarioRepository) {

        this.sintomaRepository = sintomaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Sintoma cadastrar(Sintoma sintoma) {

        Long idUsuario = sintoma.getUsuario().getIdUsuario();

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        sintoma.setUsuario(usuario);
        return sintomaRepository.save(sintoma);
    }

    public Sintoma editar(Long idSintoma, Sintoma dadosAtualizados) {
        Sintoma sintoma = buscarPorId(idSintoma);

        sintoma.setTipo(dadosAtualizados.getTipo());
        sintoma.setIntensidade(dadosAtualizados.getIntensidade());
        sintoma.setMedicamentos(dadosAtualizados.getMedicamentos());
        sintoma.setGatilhos(dadosAtualizados.getGatilhos());
        sintoma.setObs(dadosAtualizados.getObs());
        sintoma.setDataRegistro(dadosAtualizados.getDataRegistro());

        return sintomaRepository.save(sintoma);
    }

    public void excluir(Long idSintoma) {
        Sintoma sintoma = buscarPorId(idSintoma);
        sintomaRepository.delete(sintoma);
    }

    public Sintoma buscarPorId(Long idSintoma) {

        return sintomaRepository.findById(idSintoma)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sintoma não encontrado"
                ));
    }

    public List<Sintoma> buscarPorUsuario(Long idUsuario) {
        return sintomaRepository.findByUsuarioIdUsuario(idUsuario);
    }

}