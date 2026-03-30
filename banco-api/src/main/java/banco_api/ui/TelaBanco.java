package banco_api.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TelaBanco extends JFrame {
    private JTextField campoConta;
    private JTextField campoValor;
    private JTextArea areaResultado;

    public TelaBanco(){
        setTitle("Banco Java");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nº da conta: "));
        campoConta = new JTextField(10);
        add(campoConta);

        add(new JLabel("Valor: "));
        campoValor = new JTextField(10);
        add(campoValor);

        JButton btnDepositar = new JButton("Depositar");
        JButton btnSacar = new JButton("Sacar");
        JButton btnSaldo = new JButton("Ver Saldo");

        areaResultado = new JTextArea(5, 30);
        add(areaResultado);

        btnDepositar.addActionListener(this::depositar);
        btnSacar.addActionListener(this::sacar);
        btnSaldo.addActionListener(this::verSaldo);
        setVisible(true);
    }
    private void depositar(ActionEvent e){
        try{
            int numero = Integer.parseInt(campoConta.getText());
            double valor = Double.parseDouble(campoValor.getText());

            URL url = new URL("http://localhost:8080/contas/depositar?numero=" + numero + "&valor=" + valor);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            areaResultado.setText("Depósito realizado!");
        } catch (Exception ex) {
            areaResultado.setText("Ocorreu um erro durante o depósito");
        }
    }
    private void sacar(ActionEvent e){
        try{
            int numero = Integer.parseInt(campoConta.getText());
            double valor = Double.parseDouble(campoValor.getText());

            URL url = new URL("http://localhost:8080/contas/sacar?numero=" + numero + "&valor=" + valor);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            areaResultado.setText("Saque realizado!");
        } catch (Exception ex) {
            areaResultado.setText("Erro no saque");
        }
    }

    private void verSaldo(ActionEvent e) {
        try {
            int numero = Integer.parseInt(campoConta.getText());

            URL url = new URL("http://localhost:8080/contas/" + numero);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            String resposta = scanner.useDelimiter("\\A").next();

            areaResultado.setText(resposta);

        } catch (Exception ex) {
            areaResultado.setText("Erro ao consultar saldo");
        }
    }

    public static void main(String[] args) {
        new TelaBanco();
    }
}
