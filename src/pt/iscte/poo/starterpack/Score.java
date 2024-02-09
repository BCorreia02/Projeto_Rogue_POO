package pt.iscte.poo.starterpack;
import javax.swing.JOptionPane;

public class Score {

	private int pontos;
	private String jogador;

	public Score() {
		this.pontos=0;
		this.jogador = JOptionPane.showInputDialog("Insira o seu nome: ");
	}
	
	public Score(int pontos, String jogador){
		this.pontos=pontos;
		this.jogador=jogador;
	}
	

	public String getJogador() {
		return jogador;
	}

	public int getPontos() {
		return pontos;
	}

	public void resetPoints() {
		this.pontos=0;
	}

	@Override
	public String toString() {
		return pontos + ":" + jogador;
	}
	
	public void setPontos(int pontos) {
		this.pontos+=pontos;
	}
}
