package ProjetoMoraxLP;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GerenciaArquivosGoldenHouse extends GoldenHouseUsuario implements ArquivoServicos{
    //arraylist onde ficam as contas
    ArrayList<GoldenHouseUsuario> clientesGoldenHouse = new ArrayList<>();

    @Override
    public ArrayList<GoldenHouseUsuario> getClientesGoldenHouse() {
        return clientesGoldenHouse;
    }

    //mandar dinheirin
    @Override
    public int transferenciaMora(String nome, String senha, String nome2,String transfMetodo,double valor){
        double saldoDisponivel =0.0;
        double limiteCredito=0.0;
        for (GoldenHouseUsuario cliente : clientesGoldenHouse){
            //for de quem vai transferir
            if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())){
                nome=cliente.getNome();
                saldoDisponivel= cliente.getSaldo();
                //for de quem recebe
                if(saldoDisponivel>valor){
                    cliente.setSaldo(saldoDisponivel-valor);
                    for (GoldenHouseUsuario clienteRecebe : getClientesGoldenHouse()){
                        if (transfMetodo.equals(clienteRecebe.getChavePix()) || transfMetodo.equals(clienteRecebe.getNumConta()) && nome2.equals(clienteRecebe.getNome())){
                            clienteRecebe.setSaldo(clienteRecebe.getSaldo()+valor);
                            return 1;
                        }
                    }
                }
                saldoDisponivel= cliente.getLimiteCredito();
                if ((saldoDisponivel>valor&&limiteCredito>valor)){
                    nome=cliente.getNome();
                    limiteCredito = cliente.getLimiteCredito();
                    cliente.setLimiteCredito(limiteCredito-valor);
                    for (GoldenHouseUsuario clienteRecebe : getClientesGoldenHouse()){
                        if (transfMetodo.equals(clienteRecebe.getChavePix()) || transfMetodo.equals(clienteRecebe.getNumConta()) && nome2.equals(clienteRecebe.getNome())){
                            clienteRecebe.setSaldo(clienteRecebe.getSaldo()+valor);
                            return 2;
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public void recuperaUsuario(FileReader arquivo){
        //aproveita que ja foi usado data na classe dolar
        DolarCotacaoDia calendario = new DolarCotacaoDia();
        //le o arquivo e da split
        try (BufferedReader reader = new BufferedReader(arquivo)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\|"); // Usando "|" como separador
                if (partes.length == 13) { // Número de partes depende das informações
                    String nome = partes[0];
                    String endereco = partes[1];
                    String cpf = partes[2];
                    String idade = partes[3];
                    String telefone = partes[4];
                    //git config --global user.email "samuel.rodrigues@dcx.ufpb.br"
                    String numConta = partes[5];
                    String chavePix = partes[6];
                    double limiteCreditoFinal = Double.parseDouble(partes[7]);
                    double limiteCredito = Double.parseDouble(partes[8]);
                    String vencimentoFatura = partes[9];
                    String faturaPaga = (partes[10]);
                    double saldo = Double.parseDouble(partes[11]);
                    String senha = partes[12];
                    //rodar duas vezes deixa positivo!!
                    //verifica se o usuario pagou
                    if(!vencimentoFatura.equals("---")&&!faturaPaga.equals("sim")&&saldo>0&&limiteCreditoFinal>1){
                        if(calendario.getData().equals(vencimentoFatura)){
                            saldo= (limiteCredito+saldo)*-1;
                        }
                    }
                    GoldenHouseUsuario conta = new GoldenHouseUsuario(nome, endereco, cpf, idade, telefone, numConta, chavePix, limiteCreditoFinal,limiteCredito, vencimentoFatura,faturaPaga,senha, saldo);
                    this.clientesGoldenHouse.add(conta);

                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public boolean write(String caminho, String texto){
        try{

            FileWriter arq = new FileWriter(caminho,true);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(texto);
            gravarArq.close();
            return true;
        } catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }

    }
    @Override
    public void limpaArquivo(String nomeArquivo) {
        try {
            FileWriter writer = new FileWriter(nomeArquivo, false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

