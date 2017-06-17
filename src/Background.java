import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Classe que extende JPanel adicionando uma imagem de fundo
 */
public class Background extends JPanel {

    private Image backgroundImage;
    private int width, heigth, x, y;
    
    /**
     * 
     * @param fileName Arquivo quem contem a imagem
     * @param width Largura da imagem
     * @param height Altura da imagem
     * @param x posiçao relativa a esquerda
     * @param y posicao relativa a direita
     * @throws IOException 
     */
    @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
    public Background(String fileName, int width, int height, int x, int y) throws IOException {
        this.width = width;
        this.heigth = height;
        this.x = x;
        this.y = y;
        
        
        File img = new File(fileName);
        
        
        try{
            backgroundImage = ImageIO.read(img);

        } catch(IOException e){
            System.out.println(e);
        }
        backgroundImage = backgroundImage.getScaledInstance(this.width, this.heigth, Image.SCALE_DEFAULT);
        
        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, x, y, this);
    }
    

}    

