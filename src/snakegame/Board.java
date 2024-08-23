package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Board extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image apple, dot, head;
	private final int RANDOM_POS = 29;
	private final int DOT_SIZE = 10;
	private final int ALL_DOTS = 900;
	private int x[] = new int[ALL_DOTS];
	private int y[] = new int[ALL_DOTS];
	private int apple_x, apple_y;
	private int dots;
	private Timer timer;
	private int score = 0;
	
	private boolean rightDirection = true;
	private boolean leftDirection = false;
	private boolean upDirection = false;
	private boolean downDirection = false;
	
	private boolean inGame = true;
	
	Board() {
		
		addKeyListener(new TAdapter());
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300, 300));
		setFocusable(true);
		
		loadImages(); // to load images on frame
		initGame(); //initialzing game
	}
	
	public void loadImages() {
		
		ImageIcon appleIcon = new ImageIcon(ClassLoader.getSystemResource("./icons/apple.png"));
		apple = appleIcon.getImage();
		
		ImageIcon dotIcon = new ImageIcon(ClassLoader.getSystemResource("./icons/dot.png"));
		dot = dotIcon.getImage(); 
		
		ImageIcon headIcon = new ImageIcon(ClassLoader.getSystemResource("./icons/head.png"));
		head = headIcon.getImage();
		
	}
	
	public void initGame() {
		
		dots = 3;
		
		for(int i = 0; i < dots; i++) {
			y[i] = 50; //vertical axis is same for the images
			x[i] = 50 - i * DOT_SIZE; //horizontal axis differ for three images
			
		}
		
		locateApple();
		
		timer = new Timer(140, this);	
 		timer.start();
		
	}
	
	public void locateApple() {
		
		int r = (int) (Math.random() * RANDOM_POS);
		apple_x = r * DOT_SIZE;
		
		
		r = (int) (Math.random() * RANDOM_POS);
		apple_y = r * DOT_SIZE;
		
		
	}
	
	// To show images on frame we are using below function
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //calling parent method using super keyword
		
		draw(g);
		
	}
	
	
	public void draw(Graphics g) {
		
		if(inGame) {
			
			g.drawImage(apple, apple_x, apple_y, this); // showing apple image on frame
			
			for(int i = 0; i < dots; i++) {
				if(i == 0) {
					g.drawImage(head, x[i], y[i],  this); // showing head image on frame
				} else {
				g.drawImage(dot, x[i], y[i], this); // showing dot image on frame
				}
			}
			
			
			Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
	}

	private void gameOver(Graphics g) {
		String msg = "Game Over!" + " Your score is: "+ score;
		Font font = new Font("san-serif", Font.BOLD, 14);
		FontMetrics metrices = getFontMetrics(font);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg, (300 - metrices.stringWidth(msg))/2, 300/2);
		
	}

	public void move() {
		for(int i = dots; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		
		if(leftDirection) {
			x[0] = x[0] - DOT_SIZE;
		}
		
		if(rightDirection) {
			x[0] = x[0] + DOT_SIZE;
		}
		
		
		if(upDirection) {
			y[0] = y[0] - DOT_SIZE;
		}
		
		
		if(downDirection) {
			y[0] = y[0] + DOT_SIZE;
		}	
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(inGame) {
			
			checkApple();
			checkCollision();
			move();
		}

		repaint(); // works as similar to pack()
	}
	
	
	private void checkCollision() {
		
		for(int i = dots; i > 0; i--) {
			if((i > 4) && (x[0] == x[i] && y[0] == y[i])) {
				inGame = false;
			}
		}
		
		if(y[0] >= 300 || y[0] < 0) inGame = false;
		
		if(x[0] >= 300 || x[0] < 0) inGame = false;
		
		if(!inGame) timer.stop();
	}

	private void checkApple() {
		if((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++; 
			score++;
			locateApple();
		}
	}


	class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_LEFT && !rightDirection) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			} 
			
			if(key == KeyEvent.VK_RIGHT && !leftDirection) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			} 
			
			if(key == KeyEvent.VK_UP && !downDirection) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
			
			if(key == KeyEvent.VK_DOWN && !upDirection) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
		
	}
}
