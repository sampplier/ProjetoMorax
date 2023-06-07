package ProjetoMoraxLP;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
public class MensagemBoasVindas extends JOptionPane {
    public static int exibirMensagem() {
        try {
            //caminho da fonte personalizada
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonteGenshin.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            // Usar a fonte personalizada
            Font font = customFont.deriveFont(Font.BOLD, 14);
            UIManager.put("OptionPane.messageFont", font);
            MensagemBoasVindas optionPane = new MensagemBoasVindas();
            // Crie um painel personalizado com o layout desejado
            JPanel panel = new JPanel(new BorderLayout());
            JLabel messageLabel = new JLabel("Olá, agora você está acessando o maior banco de Teyvat, que Rex Lapis lhe abençoe com abuntante mora!!!");
            //adiciona o texto no inicio do painel
            panel.add(messageLabel, BorderLayout.PAGE_START);
            // Adicione a imagem ao centro do painel
            //caminho do gif
            ImageIcon icon = new ImageIcon("paimon.gif");
            JLabel iconLabel = new JLabel(icon);
            panel.add(iconLabel, BorderLayout.CENTER);
            // Cria botão personalizado com o texto "Vamos lá"
            Object[] options = { "Vamos lá" };
            messageLabel.setFont(font);
            // Exibe JOptionPane personalizado
            int mensagem = JOptionPane.showOptionDialog(null, panel, "Golden House",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            return mensagem;
            //num sei pra que excecao obrigatoria
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

