package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Inimigo{
	
	public double x,y;
	public int width, height;
	public double spd = 0.2;

	///Definindo dimenções do inimigo
	public Inimigo(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 5;
		this.height = 40;
	}
	
	///Movimentação do inimigo
	public void tick() {
		y+= (Game.ball.y - y - 6) *spd;//Função para seguir a bolinha limitando a velocidade para o jogador poder ganhar
		
		if(y+height+5 > Game.HEIGHT*Game.SCALE) {///Definindo que ele não pode se  mover para fora da janela
		
			y = Game.HEIGHT*Game.SCALE - height-5;

		}else if(y < 5) {
			y =  5;
		}
	}
	
	///Rederizando o inimigo
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, width, height);
	}
}