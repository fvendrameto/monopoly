import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static Tabuleiro tabuleiro; 
	public static int numero_jogadores;
	
	public static void main(String[] args) throws IOException{
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		ArrayList<Espaco> espacos = new ArrayList<Espaco>();
		Jogador jogador;
		Espaco espaco_atual;
		int resultado_dados;
		int[] dados;
		
		System.out.println("Digite o numero de jogadores: ");
		numero_jogadores = EntradaTeclado.leInt();
		System.out.println(numero_jogadores);
		
		for(int i=0;i<numero_jogadores;i++){
			System.out.println("Digite o nome do jogador: ");
			String nome = EntradaTeclado.leString();
			jogadores.add(new Jogador(nome));
		}
		
		tabuleiro = new Tabuleiro(jogadores,espacos);
		
		
		while(tabuleiro.jogoContinua()){
			jogador = tabuleiro.getJogadorAtual();
			System.out.println("Aperte enter para jogar os dados");
			EntradaTeclado.leString();
			resultado_dados = Dados.rolar(2,dados);
			
			tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro()).removeJogador(jogador);
			boolean ret = jogador.andarPosicaoTabuleiro(resultado_dados);
			if(ret) jogador.depositarDinheiro(200); //se jogador passou pelo inicio recebe dinheiro
			espaco_atual = tabuleiro.getEspacoPosicao(jogador.getPosicaoTabuleiro());
			espaco_atual.addJogador(jogador);
			
			System.out.println("Voce parou no" + espaco_atual.getNome());
			//OPCÃO QUANDO PARA NO ESPAÇO
			if(espaco_atual.compravel()){
				Compravel e_atual = (Compravel) espaco_atual;
				if(e_atual.temDono()){
					System.out.println("Esse lugar tem dono, você vai pagar aluguel");
					int aluguel = e_atual.getAluguel();
					jogador.sacarDinheiro(aluguel);
					e_atual.getDono().depositarDinheiro(aluguel);
				}else{ //o espaço n tem dono, entao pode ser comprado
					if(jogador.getSaldo() >= e_atual.getPreco()){
						System.out.println("Deseja comprar a localidade? 1 - Sim | 2 - Nao");
						int op = EntradaTeclado.leInt();
						if(op == 1){
							Banco.comprarPropriedade(e_atual, jogador);
						}							
					}
				}
			}else{ //Espaço de ação
				Acao.realizarAcao(tabuleiro,jogador,espaço_atual);
			}
			
			//OPCOẼ DE JOGO DO JOGADOR
			int cmd = 1;
			while(cmd != 0){
				System.out.println("Opçoes de jogo: 1 - Hipoteca | 2 - Construir casa | 3 - Vender Casa | 4 - Oferta de compra | 0- Sair");
				if(cmd == 1){
					int i = 0;
					for(Compravel p : jogador.getCompraveis()){
						System.out.println(i++ +  " - " + p.getNome() + " " + p.getPreco());
					}
					System.out.println("\nDigite a desejada: ");
					int idx_p = EntradaTeclado.leInt();
					Compravel p_hipoteca = jogador.getPropridadeIndice(idx_p);
					Banco.hipotecaPropriedade(p_hipoteca, jogador);
				}else if(cmd == 2){
					int i = 0;
					for(Compravel p : jogador.getPropriedades()){
						if(p instanceof propriedade)
							System.out.println(i +  " - " + p.getNome() + " " + p.getPreco());
						i++;
					}
					System.out.println("\nDigite a desejada: ");
					int idx_p = EntradaTeclado.leInt();
					Propriedade p_escolhida = jogador.getPropridadeIndice(idx_p);
					if(jogador.getSaldo() >= p_escolhida.getPrecoCasa() && jogador.temTodas(p_escolhida)){
						Banco.costruirCasa(p_escolhida, jogador);
					}
				}else if(cmd == 3){
					int i = 0;
					for(Compravel p : jogador.getPropriedades()){
						if(p instanceof propriedade)
							System.out.println(i +  " - " + p.getNome() + " " + p.getPreco());
						i++;
					}
					System.out.println("\nDigite a desejada: ");
					int idx_p = EntradaTeclado.leInt();
					Propriedade p_escolhida = jogador.getPropridadeIndice(idx_p);
					if(p_escollhida.getNumeroCasas() > 0){
						Banco.vendeCasa(p_escolhida, jogador);
					}
				}else if(cmd == 4){
					//depois
				}
				
			}
			
		}
	
	}
}	
