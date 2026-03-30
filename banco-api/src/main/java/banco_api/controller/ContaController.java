package banco_api.controller;

import banco_api.model.Conta;
import banco_api.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService service;

    @PostMapping
    public Conta criarConta(@RequestBody banco_api.dto.ContaDTO dto) {
        Conta conta;
        if(dto.getTipo().equalsIgnoreCase("CORRENTE")){
            conta = new banco_api.model.ContaCorrente(
                    dto.getNumero(),
                    dto.getTitular(),
                    dto.getSaldo()
            );
        }else{
            conta = new banco_api.model.ContaPoupanca(
                    dto.getNumero(),
                    dto.getTitular(),
                    dto.getSaldo()
            );
        }
        return service.criarConta(conta);
    }

    @GetMapping
    public List<Conta> listarContas() {
        return service.listarContas();
    }

    @GetMapping("/{numero}")
    public Conta buscarConta(@PathVariable int numero) {
        return service.buscarPorNumero(numero);
    }

    @PostMapping("/depositar")
    public void depositar(@RequestParam int numero, @RequestParam double valor) {
        service.depositar(numero, valor);
    }

    @PostMapping("/sacar")
    public void sacar(@RequestParam int numero, @RequestParam double valor) {
        service.sacar(numero, valor);
    }

    @PostMapping("/transferir")
    public void transferir(@RequestBody banco_api.dto.TransferenciaDTO dto){
        service.transferir(dto.getOrigem(), dto.getDestino(), dto.getValor());
    }

    @GetMapping("/tributos")
    public double calcularTributos() {
        return service.calcularTributos();
    }
}

