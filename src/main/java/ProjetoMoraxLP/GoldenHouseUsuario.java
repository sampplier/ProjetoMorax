package ProjetoMoraxLP;
import java.util.Objects;

public class GoldenHouseUsuario implements GoldenHouseServicos{
    //change everything to english
    private String nome;
    private String endereco;
    private String cpf;
    private String idade;
    private String telefone;
    private String numConta;
    private String chavePix;
    private double limiteCreditoFinal;
    private double limiteCredito;
    private String faturaPaga;
    private String vencimentoFatura;
    private String senha;
    private double saldo;
    public GoldenHouseUsuario(String nome, String endereco, String cpf, String idade,  String telefone, String numConta, String chavePix, double limiteCreditoFinal, double limiteCredito,String vencimentoFatura, String faturaPaga,String senha, double saldo){
        this.nome=nome;
        this.endereco=endereco;
        this.cpf=cpf;
        this.idade=idade;
        this.telefone=telefone;
        this.numConta=numConta;
        this.chavePix = chavePix;
        this.limiteCreditoFinal=limiteCreditoFinal;
        this.limiteCredito=limiteCredito;
        this.vencimentoFatura=vencimentoFatura;
        this.faturaPaga=faturaPaga;
        this.senha=senha;
        this.saldo=saldo;

    }
    public GoldenHouseUsuario(){
        this("","","","","","","",0.0,0.0,"","nao","",0.0);
    }
    public double getLimiteCreditoFinal(){
        return limiteCreditoFinal;
    }

    public String isFaturaPaga() {
        return faturaPaga;
    }

    public void setFaturaPaga(String faturaPaga) {
        this.faturaPaga = faturaPaga;
    }

    public double getLimiteCredito(){
        return limiteCredito;
    }

    public String getVencimentoFatura() {
        return vencimentoFatura;
    }

    public void setVencimentoFatura(String vencimentoFatura) {
        this.vencimentoFatura = vencimentoFatura;
    }

    public void setLimiteCreditoFinal(double limiteCreditoFinal){
        this.limiteCreditoFinal=limiteCreditoFinal;
    }
    public void setLimiteCredito(double limiteCredito){
        this.limiteCredito=limiteCredito;
    }
    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public String getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNumConta() {
        return numConta;
    }

    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public double getSaldo(){
        return this.saldo;
    }
    public void setSaldo(double saldo){
        this.saldo=saldo;
    }
    public String toWriteString(){
        //to string nao tem a senha
        return (this.nome +"|"+ this.endereco +"|"+ this.cpf +"|"+ this.idade +"|"+ this.telefone +"|"+ this.numConta +"|"+this.chavePix+"|"+ this.limiteCreditoFinal +"|"+this.limiteCredito+"|"+this.vencimentoFatura+"|"+faturaPaga+"|"+this.saldo +"|"+ this.senha);
    }
    @Override
    public String toString(){
        //to string nao tem a senha
        return (this.nome + this.endereco + this.cpf + this.idade + this.telefone + this.numConta +this.chavePix+ this.limiteCreditoFinal+this.limiteCredito+this.vencimentoFatura+this.faturaPaga+this.saldo + this.senha);
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
    @Override
    public double tirarExtrato() {
        return this.saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoldenHouseUsuario that = (GoldenHouseUsuario) o;
        return Double.compare(that.saldo, saldo) == 0 && nome.equals(that.nome) && endereco.equals(that.endereco) && cpf.equals(that.cpf) && idade.equals(that.idade) && telefone.equals(that.telefone) && numConta.equals(that.numConta) && chavePix.equals(that.chavePix) &&vencimentoFatura.equals(that.vencimentoFatura) &&senha.equals(that.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, endereco, cpf, idade, telefone, numConta, chavePix, limiteCreditoFinal,limiteCredito,vencimentoFatura,senha, saldo);
    }

    @Override
    public double pedirCartaoCredito(GoldenHouseUsuario cliente) {
        if(cliente.getSaldo()>=200){
            return cliente.getSaldo()*5;
        }
        return 0;
    }
    @Override
    public boolean verificaCartao(GoldenHouseUsuario cliente){
        if(cliente.getLimiteCredito()<0.0){
            return true;
        }
        return false;
    }
    @Override
    public double transferirMora(GoldenHouseUsuario nome, double valor) {
        double saldoOriginal = nome.getSaldo();
        nome.setSaldo(saldoOriginal+valor);
        return nome.getSaldo();
    }
    //falta fazer
    @Override
    public double sacarMora(GoldenHouseUsuario cliente, double saldo) throws ClienteSemSaldo{
        if(cliente.getSaldo()>=saldo){
            return cliente.getSaldo();
        }else{
            throw new ClienteSemSaldo("O cliente não tem saldo para essa opereção");
        }
    }
}
