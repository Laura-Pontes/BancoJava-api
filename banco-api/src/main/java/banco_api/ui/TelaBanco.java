package banco_api.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TelaBanco extends JFrame {
    private JTextField campoNumero;
    private JTextField campoTitular;
    private JTextField campoSaldo;
    private JTextField campoDestino;
    private JTextArea areaResultado;

    public TelaBanco(){
        setTitle("BANCO JAVA");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BoxLayout());

        JLabel titulo = new JLabel("BANCO JAVA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel centro = new JPanel(new GridLayout(6, 2, 10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centro.add(new JLabel("Número da conta: "));
        campoNumero = new JTextField();
        centro.add(new JLabel("Titular: "));
        campoTitular = new JTextField();
        centro.add(campoTitular);
        centro.add(new JLabel("Conta Destino: "));
        campoDestino = new JTextField();
        centro.add(campoDestino);
        painel.add(centro, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(2, 3, 10, 10));
        JButton btnCriar = new JButton("Criar Conta");
        JButton btnDepositar = new JButton("Depositar");
        JButton btnSacar = new JButton("Sacar");
        JButton btnTransferir  = new JButton("Transferir");
        JButton btnSaldo = new JButton("Consultar Saldo");

        botoes.add(btnCriar);
        botoes.add(btnDepositar);
        botoes.add(btnSacar);
        botoes.add(btnTransferir);
        botoes.add(btnSaldo);
        painel.add(botoes, BorderLayout.SOUTH);

        areaResultado = new JTextArea(5, 40);
        areaResultado.setEditable(false);
        painel.add(new JScrollPane(areaResultado), BorderLayout.PAGE_END);
        add(painel);

        btnCriar.addActionListener(this::criarConta);
        btnDepositar.addActionListener(this::depositar);
        btnSacar.addActionListener(this::sacar);
        btnTransferir.addActionListener(this::transferir);
        btnSaldo.addActionListener(this::consultarSaldo);
        setVisible(true);
    }
    private void criarConta(ActionEvent e) {
        try {
            String json = "{ \"numero\": " + campoNumero.getText() +
                    ", \"titular\": \"" + campoTitular.getText() +
                    "\", \"saldo\": " + campoSaldo.getText() +
                    ", \"tipo\": \"CORRENTE\" }";

            URL url = new URL("http://localhost:8080/contas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.getOutputStream().write(json.getBytes());

            areaResultado.setText("Conta criada!");
        } catch (Exception ex) {
            areaResultado.setText("Erro ao criar conta");
        }
    }

    private void depositar(ActionEvent e) {
        try {
            URL url = new URL("http://localhost:8080/contas/depositar?numero="
                    + campoNumero.getText() + "&valor=" + campoSaldo.getText());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            areaResultado.setText("Depósito realizado!");
        } catch (Exception ex) {
            areaResultado.setText("Erro no depósito");
        }
    }

    private void sacar(ActionEvent e) {
        try {
            URL url = new URL("http://localhost:8080/contas/sacar?numero="
                    + campoNumero.getText() + "&valor=" + campoSaldo.getText());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            areaResultado.setText("Saque realizado!");
        } catch (Exception ex) {
            areaResultado.setText("Erro no saque");
        }
    }

    private void transferir(ActionEvent e) {
        try {
            String json = "{ \"origem\": " + campoNumero.getText() +
                    ", \"destino\": " + campoDestino.getText() +
                    ", \"valor\": " + campoSaldo.getText() + " }";

            URL url = new URL("http://localhost:8080/contas/transferir");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.getOutputStream().write(json.getBytes());

            areaResultado.setText("Transferência realizada!");
        } catch (Exception ex) {
            areaResultado.setText("Erro na transferência");
        }
    }

    private void consultarSaldo(ActionEvent e) {
        try {
            URL url = new URL("http://localhost:8080/contas/" + campoNumero.getText());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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

