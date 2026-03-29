package banco_api.repository;

import banco_api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Conta findByNumero(int numero);
}
