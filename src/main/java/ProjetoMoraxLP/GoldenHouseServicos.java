package ProjetoMoraxLP;

public interface GoldenHouseServicos {
    double tirarExtrato();

    double pedirCartaoCredito(GoldenHouseUsuario cliente);

    double transferirMora(GoldenHouseUsuario nome, double valor);

    double sacarMora(GoldenHouseUsuario cliente, double valor) throws ClienteSemSaldo;
    boolean verificaCartao(GoldenHouseUsuario cliente);
}