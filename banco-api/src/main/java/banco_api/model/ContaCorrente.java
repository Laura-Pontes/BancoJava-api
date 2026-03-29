package banco_api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CORRENTE")
public class ContaCorrente extends Conta {

    public ContaCorrente() {
    }

    public ContaCorrente(int numero, String titular, double saldo) {
        super(numero, titular, saldo);
    }

    public double calcularTributo() {
        return getSaldo() * 0.01;
    }
}
