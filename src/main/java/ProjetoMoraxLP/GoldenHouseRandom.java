package ProjetoMoraxLP;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;

public class GoldenHouseRandom {
    public static String gerarNumConta(){
        Random geraNumConta = new Random();
        DecimalFormat df = new DecimalFormat("#.#####");
        String numConta = df.format(geraNumConta.nextInt(1000000));
        return numConta;
        }
    public static String randomChoice(List<String> lista) {
        Random random = new Random();
        int indiceAleatorio = random.nextInt(lista.size());
        return lista.get(indiceAleatorio);
    }
}
