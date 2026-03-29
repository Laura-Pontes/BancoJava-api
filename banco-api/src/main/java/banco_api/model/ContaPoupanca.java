package banco_api.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("POUPANCA")
public class ContaPoupanca extends Conta {

    public ContaPoupanca() {
    }

    public ContaPoupanca(int numero, String titular, double saldo) {
        super(numero, titular, saldo);
    }
}