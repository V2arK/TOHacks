import javax.swing.JFrame;


public class Game {
	
	public static void main(String[] Args) {
		
		JFrame window = new JFrame("Gone coViral");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setContentPane(new GamePanel());
		
		window.pack();
		window.setVisible(true);
	}
}
