package banco_api.service;
import banco_api.model.*;
import banco_api.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class ContaService {
    @Autowired
    private ContaRepository repository;

    public Conta criarConta(Conta conta) {
        return repository.save(conta);
    }

    public List<Conta> listarContas() {
        return repository.findAll();
    }

    public Conta buscarPorNumero(int numero) {
        return repository.findByNumero(numero);
    }

    public void depositar(int numero, double valor) {
        Conta conta = repository.findByNumero(numero);
        if (conta != null) {
            conta.depositar(valor);
            repository.save(conta);
        }
    }

    public void sacar(int numero, double valor) {
        Conta conta = repository.findByNumero(numero);
        if (conta != null) {
            conta.sacar(valor);
            repository.save(conta);
        }
    }

    public void transferir(int origem, int destino, double valor) {
        Conta contaOrigem = repository.findByNumero(origem);
        Conta contaDestino = repository.findByNumero(destino);

        if (contaOrigem != null && contaDestino != null) {
            contaOrigem.sacar(valor);
            contaDestino.depositar(valor);

            repository.save(contaOrigem);
            repository.save(contaDestino);
        }
    }

    public double calcularTributos() {
        double total = 0;

        List<Conta> contas = repository.findAll();

        for (Conta c : contas) {
            if (c instanceof ContaCorrente) {
                total += ((ContaCorrente) c).calcularTributo();
            }
        }

        return total;
    }
}

