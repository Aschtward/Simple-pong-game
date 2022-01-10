package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Player{
	
	public boolean down;
	public boolean up;
	public int spd = 4;
	public int x,y;
	public int width,height;
	
	///Propriedades jogador
	public Player() {
		this.x = 5;
		this.y = 10;
		this.width = 5;
		this.height = 40;
	}
	///Movimentação jogador
	public void tick() {
		if(down) {
			y+= spd;
		}
		if(up) {
			y-= spd;
		}
		if(y+height+5 > Game.HEIGHT*Game.SCALE) {///Limite no eixo y, visto  que não se movimenta em x
			
			y = Game.HEIGHT*Game.SCALE - height-5;

		}else if(y < 5) {
			y =  5;
		}
	}
	
	///Renderizando jogador
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 5, 40);
	}
}
