package ProjetoMoraxLP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class DolarCotacaoDia {
    //pega a data de hoje à tres dias
    public String getData3(){
        Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);
        // Adicionar três dias
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        // Obter a nova data
        Date dataDaquiATresDias = calendar.getTime();
        String dataFormatada = formato.format(dataDaquiATresDias);
        return dataFormatada;
    }
    //pega a data de hoje
    public String getData(){
        Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formato.format(dataAtual);
        String data = dataFormatada;
        return data;
    }
    //metodo que pega a cotacao do dolar no banco central
    public static double getCotacao() {
        try {
            //data para  pegar apenas a cotacao do dia
            Date dataAtual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatada = formato.format(dataAtual);
            String data = dataFormatada;
            //acessa a api com get
            URL url = new URL("https://api.bcb.gov.br/dados/serie/bcdata.sgs.10813/dados?formato=json&dataInicial=" + data + "&dataFinal=" + data);//api dos cria
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//conecta
            connection.setRequestMethod("GET");
            //escrever tudin
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String linha;
            StringBuilder conteudo = new StringBuilder();
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha);
            }
            reader.close();
            connection.disconnect();
            //esceve tudo e fecha
            String dolarCotacaoBruta = conteudo.toString();
            String[] partes = dolarCotacaoBruta.split(":"); // Usando ":" como separador
            DecimalFormat format = new DecimalFormat("0.00");//formatar né pae
            double cotacaoDolar = Double.parseDouble((partes[2].replace("}]","")).replace("\"","")); //replace para deixar apenas os números
            String cotacaoDolarFormat = format.format(cotacaoDolar);
            System.out.println(cotacaoDolarFormat);
            //TEM QUE TIRAR A VIRGULA PRA BOTAR PONTO OU A DESGRACA DO PARSE NAO FUNCIONA FNARIOFNRIOFNRIFBRAINFINR
            //foi de retornas
            return (Double.parseDouble(cotacaoDolarFormat.replace(",",".")));
        } catch (IOException e) {
            System.err.println("Erro ao acessar a API: " + e.getMessage());
        }
        return 0;
    }
}
