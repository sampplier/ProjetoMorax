package ProjetoMoraxLP;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ProgramaGoldenHouse{
    public static void main(String[] args) throws IOException, ClienteSemSaldo{
        //exibe mensagem de boas vindas
        int mensagem = MensagemBoasVindas.exibirMensagem();
        //objeto que pega data
        DolarCotacaoDia calendario = new DolarCotacaoDia();
        //Arquivo onde as contas ficam salvas
        String contasClientesGoldenHouse = "contasClientesGoldenHouse.txt";
        //opcoes válidas do codigo
        ArrayList<String> opcoes = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            opcoes.add(Integer.toString(i));
        }
        //objeto da classe que gerencia os arquivos e tem a arraylsit
        GerenciaArquivosGoldenHouse sistema = new GerenciaArquivosGoldenHouse();
        //file reader do arquivo
        FileReader arquivo = new FileReader(contasClientesGoldenHouse);
        //metodo que le o arquivo, transforma em objetos da goldenhouseusuario e adciona numa arraylist
        sistema.recuperaUsuario(arquivo);
        //variavel para o while principal
        boolean sair = false;
        while (!sair) {
            //variavel para p switch case
            int selecaoModo = 0;
            String quest;
            quest = JOptionPane.showInputDialog("""
                    Qual opção você deseja?
                    1- Abrir conta
                    2- Ver Saldo
                    3- Depósito
                    4- Realizar transferência
                    5- Saque
                    6- Corrigir dados
                    7- Verificar se a conta existe no banco
                    8- Falar com o gerente
                    9- Câmbio para dólar\s
                    10- Jogo do bicho
                    11- Pedir cartão de crédito
                    12- Apagar conta
                    13- Pagar Fatura
                    14- Sair""");
            //tratar erros da entrada
            if(quest!=null && !quest.equals("") && opcoes.contains(quest)){
                selecaoModo = Integer.parseInt(quest);
            }
            switch (selecaoModo) {
                case 1:
                    //abrir conta
                    boolean contaJaExiste = false;
                    //while pra nao deixar ter duas contas iguais
                    while(!contaJaExiste) {
                        JOptionPane.showMessageDialog(null, "Vamos precisar de alguns dados seus, prepare seus documentos");
                        String nome = JOptionPane.showInputDialog("Qual o seu nome completo?");
                        String idade = JOptionPane.showInputDialog("Qual sua data de nascimento?");
                        String cpf = JOptionPane.showInputDialog("Qual o seu cpf?");
                        if(nome!=null&&idade!=null&&cpf!=null&&!cpf.isEmpty()&&!idade.isEmpty()&&!nome.isEmpty()){
                            for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                                if (((cliente.getNome().toLowerCase()).equals(nome.toLowerCase())) && ((cliente.getCpf().toLowerCase()).equals(cpf)) && ((cliente.getIdade().toLowerCase()).equals(idade.toLowerCase()))) {
                                    JOptionPane.showMessageDialog(null, "Esta conta já está cadastrada, tente novamente");
                                    contaJaExiste = true;
                                    break;
                                }
                                break;
                            }
                            if (!contaJaExiste) {
                                String endereco = JOptionPane.showInputDialog("Qual o seu endereco?");
                                String telefone = JOptionPane.showInputDialog("Qual o seu telefone?");
                                String senha = JOptionPane.showInputDialog("Qual será sua senha?");
                                String chavePix = JOptionPane.showInputDialog(null, "Qual sua chave pix?");
                                double limiteCredito = -1.0;
                                double saldo = 0.0;
                                String vencimentoFatura = "---";
                                String faturaPaga = "nao";
                                String numConta = GoldenHouseRandom.gerarNumConta();
                                JOptionPane.showMessageDialog(null, "Seu número de conta é:" + numConta);
                                for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                                    while (cliente.getNumConta().equals(numConta)) {
                                        numConta = GoldenHouseRandom.gerarNumConta();
                                    }
                                }
                                GoldenHouseUsuario conta = new GoldenHouseUsuario(nome, idade, cpf, endereco, telefone, numConta, chavePix, limiteCredito, limiteCredito,vencimentoFatura,faturaPaga,senha, saldo);
                                sistema.clientesGoldenHouse.add(conta);
                                //escreve no arquivo
                                sistema.write(contasClientesGoldenHouse, conta.toWriteString());
                                JOptionPane.showMessageDialog(null, "Sucesso ao criar sua conta, seja bem vindo ao mercado de Teyvat!!!");
                                contaJaExiste=true;
                            }
                        } else{
                            JOptionPane.showMessageDialog(null,"Houve um erro ao cadrastar sua conta, tente novamente");
                        }
                    }
                    break;
                case 2:
                    //tirar extrato
                    JOptionPane.showMessageDialog(null, "Insira seu nome e a sua senha");
                    String nome = JOptionPane.showInputDialog("Qual é o seu nome?");
                    String senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    boolean extratoRealizado = false;
                    double extratoValor = 0.0;
                    for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                        if(nome!=null&&senha!=null&&!senha.isEmpty()&&!nome.isEmpty()){
                            if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())) {
                                extratoValor = cliente.tirarExtrato();
                                extratoRealizado = true;
                            }
                        }
                    }
                    if (extratoRealizado) {
                        JOptionPane.showMessageDialog(null, "Saldo bem sucedido!!\n" + "Seu saldo é de R$: " + extratoValor);
                    } else {
                        JOptionPane.showMessageDialog(null, "Saldo mal sucedido, tente novamente");
                    }
                    break;
                case 3:
                    //deposito
                    JOptionPane.showMessageDialog(null, "Insira seu nome e a sua chave Pix");
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    String chavePix = JOptionPane.showInputDialog("Qual a sua chave pix?");
                    String valorStr = (JOptionPane.showInputDialog("Qual o valor?"));
                    double valor = 0;
                    if(valorStr!=null && !valorStr.equals("")){
                        valor = Double.parseDouble(valorStr);
                    }
                    boolean depositoRealizado = false;
                    for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                        if(nome!=null&&chavePix!=null&&!chavePix.isEmpty()&&!nome.isEmpty()){
                            if (chavePix.equals(cliente.getChavePix()) && nome.equals(cliente.getNome())) {
                                double saldoNovo = cliente.transferirMora(cliente, valor);
                                JOptionPane.showMessageDialog(null, "Depósito bem sucedido!!\n" + "Seu extrato é de R$: " + saldoNovo);
                                depositoRealizado = true;
                            }
                        }
                    }
                    if (!depositoRealizado) {
                        JOptionPane.showMessageDialog(null, "Depósito mal sucedido, tente novamente");
                    }
                    break;
                case 4:
                    //transferencia
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    valorStr = (JOptionPane.showInputDialog("Qual o valor?\n"+"Se não possuir o valor na conta, a transferência irá ser paga com seu limite de crédito"));
                    String nome2 = JOptionPane.showInputDialog("Qual o nome do destinatário?");
                    String transfMetodo = JOptionPane.showInputDialog("Insira o número da conta ou a chave pix do destinatário");
                    valor=0;
                    if(valorStr!=null && !valorStr.equals("")){
                        valor = Double.parseDouble(valorStr);
                    }
                    int transferenciaRealizada= 0;
                    if(nome!=null&&senha!=null&&!senha.isEmpty()&&!nome.isEmpty()){
                        transferenciaRealizada= sistema.transferenciaMora(nome, senha, nome2,transfMetodo,valor);
                    }
                    if(transferenciaRealizada==1){
                        JOptionPane.showMessageDialog(null, "Sua transferência foi realizada");
                        break;
                    } else if (transferenciaRealizada==2) {
                        String data = calendario.getData3();
                        for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                            if(nome!=null&&senha!=null&&!senha.isEmpty()&&!nome.isEmpty()){
                                cliente.setVencimentoFatura(data);
                                cliente.setFaturaPaga("nao");
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Sua transferência foi realizada no crédito, pague o valor R$:"+valor+"até o dia"+data+"ou sua conta será negativada");
                        break;
                    }else{
                        JOptionPane.showMessageDialog(null,"Sua transferência não foi realizada, tente novamente");
                    }
                    break;
                case 5:
                    //saque
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    String quantSaqueStr = (JOptionPane.showInputDialog("Insira o valor"));
                    double quantSaque = 0;
                    boolean saqueRealizado = false;
                    if(quantSaqueStr!=null && !quantSaqueStr.equals("")){
                        quantSaque = Double.parseDouble(quantSaqueStr);
                    }
                    for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                        if (nome!=null&&senha!=null&&!senha.isEmpty()&&!nome.isEmpty()&&senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())) {
                            try{
                                cliente.sacarMora(cliente, quantSaque);
                                saqueRealizado=true;
                                break;
                        }catch (ClienteSemSaldo e){
                                JOptionPane.showMessageDialog(null, "O cliente não possui saldo suficiente");
                                e.printStackTrace();
                            }
                        }
                    }
                    if(saqueRealizado){
                        JOptionPane.showMessageDialog(null, "Saque realizado");
                    }else{
                        JOptionPane.showMessageDialog(null, "Saque não realizado, tente novamente");
                    }
                    break;
                case 6:
                    //correcao de dados
                    //falta atributos
                    String dado = JOptionPane.showInputDialog("""
                            Qual dado você deseja corrigir?
                            1- Nome
                            2- Data de nascimento
                            3- CPF
                            4- Telefone
                            5- Endereço
                            6- Chave Pix""");
                    //tratar erros da entrada
                    int dadoS = 0;
                    if(dado!=null && !dado.equals("") && opcoes.contains(dado)){
                        dadoS = Integer.parseInt(dado);
                    }
                    boolean changeRealizado = false;
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    String dadoChange = JOptionPane.showInputDialog("Insira o novo dado");
                    if(nome!=null&&senha!=null&&dadoChange!=null&&!senha.isEmpty()&&!nome.isEmpty()&&!dadoChange.isEmpty()){
                        switch (dadoS) {
                            case 1:
                                for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                                    System.out.println(cliente);
                                    if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())){
                                        cliente.setNome(dadoChange);
                                        changeRealizado= true;
                                    }
                                }
                                break;
                            case 2:
                                for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                                    if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())){
                                        cliente.setIdade(dadoChange);
                                        changeRealizado= true;
                                    }
                                }
                                break;
                            case 3:
                                for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                                    if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())){
                                        cliente.setCpf(dadoChange);
                                        changeRealizado= true;
                                    }
                                }
                                break;
                            case 4:
                                for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                                    if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())){
                                        cliente.setTelefone(dadoChange);
                                        changeRealizado= true;;
                                    }
                                }
                                break;
                            case 5:
                                for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                                    if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())){
                                        cliente.setEndereco(dadoChange);
                                        changeRealizado= true;
                                    }
                                }
                                break;
                            case 6:
                                for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                                    if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())){
                                        cliente.setChavePix(dadoChange);
                                        changeRealizado = true;
                                    }
                                }
                                break;

                        }
                    }
                    if (changeRealizado) {
                        JOptionPane.showMessageDialog(null, "Seus dados foram atualizados");
                    } else {
                        JOptionPane.showMessageDialog(null, "Houve um erro ao atualizar, tente novamente");
                    }
                    break;
                case 7:
                    //ver se existe
                    nome = JOptionPane.showInputDialog("Qual o nome da conta?");
                    String numConta = JOptionPane.showInputDialog("Qual o número da conta?");
                    boolean pertenceBanco = false;
                    for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                        if (nome!=null&&numConta!=null&&!numConta.isEmpty()&&!nome.isEmpty()&&numConta.equals(cliente.getNumConta()) && nome.equals(cliente.getNome())) {
                            pertenceBanco = true;
                            break;
                        }
                    }
                    if (pertenceBanco) {
                        JOptionPane.showMessageDialog(null, "Sim, ela pertence ao banco");
                    } else {
                        JOptionPane.showMessageDialog(null, "Não, ela não pertence ao banco");
                    }
                    break;
                //dar uma olhada na lista com for each
                case 8:
                    //gerente brabo
                    ImageIcon iconC = new ImageIcon("cachorroChupetao.jpg");
                    JOptionPane.showMessageDialog(null, "TUC TUC TUC TUC","Golden House",JOptionPane.PLAIN_MESSAGE,iconC);
                    break;
                case 9:
                    //converter dolar
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    String valorConverterStr = (JOptionPane.showInputDialog("Qual o valor que deseja converter"));
                    double valorConverter = 0.0;
                    if(valorConverterStr!=null&&!valorConverterStr.isEmpty()){
                        valorConverter = Double.parseDouble(valorConverterStr);
                    }
                    String opTypeStr = (JOptionPane.showInputDialog("1- Trasferência(Real-->Dólar)\n"+"2- Depósito(Dólar-->Real)"));
                    int opType = 0;
                    if(valorConverterStr!=null&&!valorConverterStr.isEmpty()){
                        opType = Integer.parseInt(opTypeStr);
                    }
                    double cotacaoAtual = DolarCotacaoDia.getCotacao();
                    int cambioRealizado = 0;
                    if(nome!=null&&senha!=null&&!senha.isEmpty()&&!nome.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Aguarde, buscando cotação");
                        //busca na api o dolar atual
                        JOptionPane.showMessageDialog(null, "Dólar hoje: " + cotacaoAtual);
                        for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                            if (senha.equals(cliente.getSenha()) && nome.equals(cliente.getNome())) {
                                if(opType==1){
                                    if(cliente.getSaldo()<valorConverter*cotacaoAtual){
                                        nome2 = JOptionPane.showInputDialog("Qual o nome do destinatário?");
                                        transfMetodo = JOptionPane.showInputDialog("Insira o número da conta ou a chave pix do destinatário");
                                        cambioRealizado = sistema.transferenciaMora(nome, senha, nome2,transfMetodo, valorConverter/DolarCotacaoDia.getCotacao());
                                        if(cambioRealizado==1){
                                            JOptionPane.showMessageDialog(null, "Sua transferência foi realizada com sucesso");
                                        }else if(cambioRealizado==2){
                                            String data = calendario.getData3();
                                            for (GoldenHouseUsuario cliente2 : sistema.getClientesGoldenHouse()) {
                                                if(nome!=null&&senha!=null&&!senha.isEmpty()&&!nome.isEmpty()){
                                                    cliente2.setVencimentoFatura(data);
                                                }
                                            }
                                            JOptionPane.showMessageDialog(null, "Sua transferência foi realizada no crédito, pague o valor R$:"+valorConverterStr+"até o dia"+data+"ou sua conta será negativada");
                                            break;
                                        }
                                    }

                                }else if(opType==2){
                                    cliente.setSaldo(cliente.getSaldo()+(valorConverter * cotacaoAtual));
                                    JOptionPane.showMessageDialog(null, "Câmbio realizado, saldo atual:" + cliente.getSaldo());
                                    cambioRealizado = 1;
                                }
                            }
                        }
                    }
                    if (cambioRealizado!=1&&cambioRealizado!=2) {
                        JOptionPane.showMessageDialog(null, "Não foi possível realizar o câmbio");
                    }
                    break;
                case 10:
                    //jogo do bicho KKKKKKKKKKK
                    ImageIcon iconU = new ImageIcon("urubuDoPix.jpeg");
                    JOptionPane.showMessageDialog(null,"Pode vir chegando que o jogo do bicho tá rolando","Golden House",JOptionPane.PLAIN_MESSAGE,iconU);
                    String nomeAnimal = JOptionPane.showInputDialog("""
                            Deseja apostar em qual animal?
                            Avestruz
                            Águia
                            Burro
                            Borboleta
                            Cachorro
                            Cabra
                            Carneiro
                            Camelo
                            Cobra
                            Coelho
                            Cavalo
                            Elefante
                            Galo
                            Gato
                            Jacaré
                            Leão
                            Macaco
                            Porco
                            Pavão
                            Peru
                            Touro
                            Tigre
                            Urso
                            Veado
                            Vaca""");
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    String valorApostaStr = (JOptionPane.showInputDialog("Quanto deseja apostar?"));
                    double valorAposta = 0.0;
                    if(valorApostaStr!=null&&!valorApostaStr.isEmpty()){
                        valorAposta = Double.parseDouble(valorApostaStr);
                    }
                    List<String> animais = new ArrayList<>();
                    animais.add("Avestruz");
                    animais.add("Águia");
                    animais.add("Burro");
                    animais.add("Borboleta");
                    animais.add("Cachorro");
                    animais.add("Cabra");
                    animais.add("Carneiro");
                    animais.add("Camelo");
                    animais.add("Cobra");
                    animais.add("Coelho");
                    animais.add("Cavalo");
                    animais.add("Elefante");
                    animais.add("Galo");
                    animais.add("Gato");
                    animais.add("Jacaré");
                    animais.add("Leão");
                    animais.add("Macaco");
                    animais.add("Porco");
                    animais.add("Pavão");
                    animais.add("Peru");
                    animais.add("Touro");
                    animais.add("Tigre");
                    animais.add("Urso");
                    animais.add("Veado");
                    animais.add("Vaca");
                    //procura um aleatorio na lista
                    String animalAleatorio = GoldenHouseRandom.randomChoice(animais);
                    boolean apostaRealizada=false;
                    for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                        if (nome!=null&&senha!=null&&!nome.isEmpty()&&!senha.isEmpty()&&nomeAnimal!=null&&!nomeAnimal.isEmpty()&&(senha.equals(cliente.getSenha())) && (nome.equals(cliente.getNome())) && (valorAposta <= cliente.getSaldo())) {
                            apostaRealizada=true;
                            if ((nomeAnimal.toLowerCase()).equals(animalAleatorio.toLowerCase())){
                                double valorGanho = valorAposta * 2;
                                cliente.setSaldo(cliente.getSaldo() + valorGanho);
                                JOptionPane.showMessageDialog(null, "Parabéns!!! Você ganhou R$" + valorGanho);
                            } else {
                                cliente.setSaldo(cliente.getSaldo() - valorAposta);
                                JOptionPane.showMessageDialog(null, "infelizmente você perdeu R$:" + valorAposta +"\n O animal era: "+animalAleatorio);
                            }
                        }
                    }
                    if(!apostaRealizada){
                        JOptionPane.showMessageDialog(null,"Sua aposta não foi realziada, tente novamente");
                    }
                    break;
                case 11:
                    //pedir cartao
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    boolean limite = false;
                    for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                        if (nome!=null&&senha!=null&&!nome.isEmpty()&&!senha.isEmpty()&&senha.equals(cliente.getSenha()) && (cliente.getLimiteCreditoFinal()>0) &&(cliente.getLimiteCreditoFinal()<1)&& (nome.equals(cliente.getNome()))){
                            double limiteAtual = cliente.getLimiteCredito();
                            limite=true;
                            cliente.setLimiteCredito(limiteAtual+cliente.pedirCartaoCredito(cliente));
                            cliente.setLimiteCreditoFinal(limiteAtual+cliente.pedirCartaoCredito(cliente));
                            JOptionPane.showMessageDialog(null, "Parabéns!!! Seu limite de: R$ "+cliente.getLimiteCredito()+"foi aprovado");
                        }
                    }
                    if(!limite){
                        JOptionPane.showMessageDialog(null, "Ainda não foi possível liberar crédito para você, movimente mais sua conta e tente novamente");
                    }
                    break;
                case 12:
                    //apagar conta
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    senha = JOptionPane.showInputDialog("Qual a sua senha?");
                    numConta = JOptionPane.showInputDialog("Qual o número da conta?");
                    boolean contaApagada = false;
                    for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()){
                        if (nome!=null&&senha!=null&&numConta!=null&&!numConta.isEmpty()&&!nome.isEmpty()&&!senha.isEmpty()&&(senha.equals(cliente.getSenha())) && (cliente.getNumConta().equals(numConta)) && (nome.equals(cliente.getNome()))){
                            sistema.clientesGoldenHouse.remove(cliente);
                            contaApagada=true;
                            JOptionPane.showMessageDialog(null, "Conta apagada com sucesso");
                        }
                    }
                    if(!contaApagada){
                        JOptionPane.showMessageDialog(null, "Não foi possível apagar, tente novamente");
                    }
                    break;
                case 13:
                    JOptionPane.showMessageDialog(null, "Insira seu nome e a sua chave Pix");
                    nome = JOptionPane.showInputDialog("Qual o seu nome?");
                    chavePix = JOptionPane.showInputDialog("Qual a sua chave pix?");
                    String valorFaturaStr = (JOptionPane.showInputDialog("Qual o valor?"));
                    double valorFatura = 0;
                    if(valorFaturaStr!=null && !valorFaturaStr.equals("")){
                        valorFatura = Double.parseDouble(valorFaturaStr);
                    }
                    boolean faturaPaga = false;
                    if(nome!=null&&chavePix!=null&&!nome.isEmpty()&&!chavePix.isEmpty()){
                        for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                            if((valorFatura==(cliente.getLimiteCreditoFinal()-cliente.getLimiteCredito()))&&nome!=null&&chavePix!=null&&!chavePix.isEmpty()&&!nome.isEmpty()){
                                if (chavePix.equals(cliente.getChavePix()) && nome.equals(cliente.getNome())) {
                                    cliente.setLimiteCredito((cliente.getLimiteCreditoFinal()-cliente.getLimiteCredito()));
                                    cliente.setFaturaPaga("sim");
                                    JOptionPane.showMessageDialog(null, "Fatura paga com sucesso!!");
                                    faturaPaga=true;
                                }
                            }
                        }
                    }
                    if (!faturaPaga) {
                        JOptionPane.showMessageDialog(null, "Depósito mal sucedido, tente novamente");
                    }
                    break;
                case 14:
                    //é tchau
                    ImageIcon icon = new ImageIcon("paimonBye.png");
                    JOptionPane.showMessageDialog(null,"Até mais, viajante","Golden House",JOptionPane.PLAIN_MESSAGE,icon);
                    sair = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro, tente novamente");
            }
            //salva as alteracoes no arquivo so quando sai do while
            //se o usuario safado for sacanear ele perde os dados hehe
            sistema.limpaArquivo("contasClientesGoldenHouse.txt");
            for (GoldenHouseUsuario cliente : sistema.getClientesGoldenHouse()) {
                sistema.write("contasClientesGoldenHouse.txt", cliente.toWriteString());
            }
        }
    }
}