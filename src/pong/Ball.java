package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	
	public double x,y;
	public int width, height;
	public double dx,dy;
	public double speed = 4;
	int pontoplayer = 0,pontoinimigo = 0;
	
	///Propriedades da bola
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 5;
		this.height = 5;
		///Gerando angulo e direções em y e x para deixar o movimento mais natural
		int angle = new Random().nextInt(120-45) + 45 + 1;
		dx = Math.cos(angle);
		dy = Math.sin(angle);
	}
	
	public void tick() {
		
		if(y+(dx*speed) + height >= Game.HEIGHT*Game.SCALE) {///Caso acerte o topo da janela redireciona
			dy*= -1;
		}else if(y +(dx*speed) <= 0) {
			dy*=-1;
		}
		if(x > Game.WIDTH*Game.SCALE) {///Caso acerte a lateral da janela
			pontoplayer++;
			x = 15;
			y = Game.player.y;
		}else if(x < 0) {
			pontoinimigo++;
			x = Game.WIDTH*Game.SCALE- 15;
			y = Game.player.y;
		}
		///Usando Rectangle Bounds para gerar a colisão bola jogador e bola inimigo
		Rectangle bounds = new Rectangle((int)(x+(dx*speed)),(int)(y+(dy*speed)),width,height);
		Rectangle boundsPlayer = new Rectangle(Game.player.x,Game.player.y,Game.player.width,Game.player.height);
		Rectangle boundsInimigo = new Rectangle((int)Game.inimigo.x,(int)Game.inimigo.y,Game.inimigo.width,Game.inimigo.height);
		
		///Redirecionamento caso acerte
		if(bounds.intersects(boundsPlayer)) {
			dx*= -1;
		}else if(bounds.intersects(boundsInimigo)) {
			int angle = new Random().nextInt(120-45) + 45 + 1;
			dx = Math.cos(angle);
			dy = Math.sin(angle);
			if(dx < 0) {
				dx = -1;
			}
		}
		///Movimentação normal da bola
		x+=dx*speed;
		y+=dy*speed;
	}
	
	///Renderização da bola
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, width, height);
	
	}
}