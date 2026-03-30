package banco_api.model;

public class Funcionario implements Autenticavel{

    protected String nome;
    protected String senha;

    public Funcionario(String nome, String senha){
        this.nome = nome;
        this.senha = senha;
    }
    @Override
    public boolean autenticar(String senha){
        return this.senha.equals(senha);
    }
    public String getNome(){
        return nome;
    }
}
