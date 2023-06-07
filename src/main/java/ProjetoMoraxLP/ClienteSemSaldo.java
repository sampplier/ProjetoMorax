package ProjetoMoraxLP;

public class ClienteSemSaldo extends Exception{
    //exception do cliente nao ter saldo, podia ser um if mas nao tinha visto nenhum lugar que precisava de excecao, vai nesse mesmo
    public ClienteSemSaldo(String mensagem) {
        super(mensagem);
    }

}
