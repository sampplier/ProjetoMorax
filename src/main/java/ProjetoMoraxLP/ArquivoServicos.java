package ProjetoMoraxLP;

import java.io.FileReader;
import java.util.ArrayList;

public interface ArquivoServicos {
    int transferenciaMora(String nome, String senha, String nome2,String transfMetodo,double valor);
    ArrayList<GoldenHouseUsuario> getClientesGoldenHouse();
    void recuperaUsuario(FileReader arquivo);
    boolean write(String caminho, String texto);
    void limpaArquivo(String nomeArquivo);
}
