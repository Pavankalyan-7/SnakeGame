package snakegame;
import javax.swing.JFrame;

public class Snake extends JFrame {

	
	private static final long serialVersionUID = 1L;

	
	Snake() {
		
		setTitle("Snake Game");
		add(new Board());
		pack();
		
//		setSize(300,300);
		
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Snake();
	}

}
