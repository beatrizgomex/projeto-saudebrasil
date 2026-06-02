package projeto.unirio.saudebrasil.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.unirio.saudebrasil.entitys.Sintoma;
import projeto.unirio.saudebrasil.service.SintomaService;

import java.util.List;

@RestController
@RequestMapping("/api/sintomas")
public class SintomaController {

    private final SintomaService sintomaService;

    public SintomaController(SintomaService sintomaService) {
        this.sintomaService = sintomaService;
    }

    @PostMapping
    public ResponseEntity<Sintoma> cadastrar(@RequestBody Sintoma sintoma) {
        Sintoma novoSintoma = sintomaService.cadastrar(sintoma);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoSintoma);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sintoma> buscarPorId(@PathVariable Long id) {
        Sintoma sintoma = sintomaService.buscarPorId(id);
        return ResponseEntity.ok(sintoma);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Sintoma>> buscarPorUsuario(@PathVariable Long idUsuario) {
        List<Sintoma> sintomas = sintomaService.buscarPorUsuario(idUsuario);
        return ResponseEntity.ok(sintomas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sintoma> editar(
            @PathVariable Long id,
            @RequestBody Sintoma sintoma) {

        Sintoma sintomaAtualizado = sintomaService.editar(id, sintoma);
        return ResponseEntity.ok(sintomaAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        sintomaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}