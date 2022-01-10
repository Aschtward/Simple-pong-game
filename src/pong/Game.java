package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1227254042505466843L;
	
	///Definindo parametros para janela
	public static JFrame frame;
	public static int WIDTH = 160;
	public static int HEIGHT = 120;
	public static int SCALE = 3;
	///Fim parametros para janela
	
	private BufferedImage image;
	
	
	private Thread thread;
	private boolean isRunning = true;
	private int dif = 3;

	
	public static Player player;
	public static Inimigo inimigo;
	public static Ball ball;
	
	public Game() {
		
		///Criação da Janela
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		inicia_frame();
		///
		player = new Player();
		inimigo = new Inimigo(WIDTH*SCALE-15,10);
		ball = new Ball(100,HEIGHT/2- 1);
		this.addKeyListener(this);
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
	}
	
	public void inicia_frame() {///Inicializa janela
		frame = new JFrame("Pong");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	
	public void tick() {
		player.tick();
		inimigo.tick();
		ball.tick();
		if(ball.pontoinimigo == ball.pontoplayer - dif && dif < 5) {///Adaptação de dificuldade aumentando velocidade do inimigo
			dif++;
			inimigo.spd += 0.2;
			System.out.println(inimigo.spd);
		}
	}
	
	public void  render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		///Implementação fundo  preto
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(20, 40, 80, 80);
		g = bs.getDrawGraphics();
		g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		///
		
		///Criando titulo do  jogo
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.white);
		g.drawString("Ping Pong",190,20);
		///Fim titulo
		
		///Criando Placar
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.white);
		g.drawString(""+ ball.pontoplayer,160,20);
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.white);
		g.drawString(""+ ball.pontoinimigo,300,20);
		///Fim do placar
			
		///Render player
		player.render(g);
		inimigo.render(g);
		ball.render(g);
		///Fim render player
		
		bs.show();
	}
	
	public void run() {
		
		//Implementação game looping
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
		int frames = 0;
		
		while(isRunning) {
			long now = System.nanoTime();
			delta+=(now - lastTime)/ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				frames = 0;
				timer += 1000;
			}
		}//Fim implementação game looping
		
		stop();
}




	public void keyPressed(KeyEvent e) {///Gerando criação de evento para tecla pressionada
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
		
	}


	public void keyReleased(KeyEvent e) {///Finalizando evento para tecla solta
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down =false;
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
