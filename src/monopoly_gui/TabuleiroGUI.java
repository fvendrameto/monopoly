package monopoly_gui;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Responsavel por exibir o tabuleiro e os peões na GUI
 */
public class TabuleiroGUI extends JPanel {
    private JLayeredPane layers;
    private int height, width, hpeao, wpeao;
    private JPanel tabuleiro;
    private ArrayList<JPanel> panels;
    private int numJogadores;
    private int[] nPeoesCasa;
    private int[] casaPeao;

    /**
     * @param file Arquivo que contem a imagem do tabuleiro
     * @param width Largura do componente
     * @param height Altura do componente
     * @param numJogadores Numero de jogadores na partida
     */
    public TabuleiroGUI(String file, int width, int height, int numJogadores) {
        layers = new JLayeredPane();
        wpeao = width/44;
        hpeao = (5/4)*(height/44);
        this.width = width;
        this.height = height;
        this.numJogadores = numJogadores;
        panels = new ArrayList(numJogadores);


        for (int i = 0; i < numJogadores; i++) {
            panels.add(null);
        }

        nPeoesCasa = new int[40];
        casaPeao = new int[numJogadores];
        nPeoesCasa[0] = numJogadores;
//        nPeoesCasa[0] =  this.numJogadores;

        try{
            tabuleiro = new Background(file, width, height, 0, 0);
            tabuleiro.setSize(width, height);
            tabuleiro.setVisible(true);
            layers.setSize(width, height);
            layers.setVisible(true);
            layers.add(tabuleiro,0,0);
        } catch(IOException e){
            System.out.println("Erro no tabuleiro: "+e);
        }
//        panels.add(tabuleiro);
        this.setLayout(null);
        this.add(layers);
        this.setSize(width, height);
        this.setVisible(true);

    }
    /**
     * Posiciona um peão em uma determinada posição do tabuleiro
     * @param index Indice do peão
     * @param pos Posição que o peão deve ser posicionado
     */
    public void putPeao(int index, int pos){
        String file;

        switch (index%5) {
            case 0:
                file = "images/peao-vermelho.png";
                break;
            case 1:
                file = "images/peao-azul.png";
                break;
            case 2:
                file = "images/peao-amarelo.png";
                break;
            case 3:
                file = "images/peao-azul.png";
                break;
            case 4:
                file = "images/peao-rosa.png";
                break;
            default:
                file = "images/peao-vermelho.png";
                break;
        }



        putPeao(index, pos, file);
    }


    /** Calcula a posicao em pixels de um espaco no tabuleiro
     * @param pos Número da posição
     * @return Dimensao calculada
     */
    private Dimension getPos(int pos){

        Dimension d = new Dimension();
        if(pos >= 0 && pos < 10){
            d.setSize((this.width/13) * (11-(pos)) + width/(26*(pos+1)),
                    (height/13)*12);
        }else if(pos >= 10 && pos < 20){
            d.setSize((width/26),
                    (this.height/13) * (11-(pos%10)) + (height/(13*(1 + pos%10))));
        } if(pos >= 20 && pos < 30){
            d.setSize((this.width/13) * (pos%19) + ((pos%20) * width)/208,
                    (height/26));
        }else if(pos >= 30 && pos < 40){
            d.setSize(12*(width/13),
                    (this.height/13) * (pos%29) + ((pos%30) * height)/208);
        }

        switch (pos) {
            case 0:
                d.setSize(d.getWidth() + width/26, d.getHeight());
                break;
            case 10:
                d.setSize(d.getWidth(), d.getHeight()+ height/52);
                break;
            case 20:
                d.setSize(d.getWidth() - width/13, d.getHeight());
                break;
            case 30:
                d.setSize(d.getWidth(), d.getHeight()- height/26);
                break;
            default:
                break;
        }

        return d;
    }


    private void putPeao(int index, int pos, String file){
        int desloc;

        int posAntiga = casaPeao[index];


        desloc = nPeoesCasa[pos];

        nPeoesCasa[posAntiga]--;

        nPeoesCasa[pos]++;

        casaPeao[index] = pos;

        putPeao(index, pos, 4*desloc, 4*desloc, file);



    }

    private void putPeao(int index, int pos, int x, int y, String file){
        Dimension d = getPos(pos);
        JPanel peao = null;

        for (int i = 0; i < numJogadores; i++){
            peao = panels.get(i);
        }


        try{
            if(layers.getComponentCountInLayer(index+1) > 0){
                peao = panels.get(index);
                layers.remove(layers.getIndexOf(peao));
            }

            peao = new Background(file, wpeao, hpeao, (int) d.getWidth() + x, (int) d.getHeight() + y);
            peao.setSize(width, height);
            peao.setLayout(null);

            panels.set(index, peao);
            layers.add(peao, index+1, 0);

        } catch (Exception e){
            System.out.println("Nao foi possivel colocar o peao, erro: "+e);
        }
    }
}