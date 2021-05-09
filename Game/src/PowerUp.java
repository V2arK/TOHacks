import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class PowerUp {

	//fields.
	private double x;
	private double y;
	private int r = 20;

	private int iconDrawX;
	private int iconDrawY;
	
	private int type;
	
	private int speed = 1;
	
	// private Color color1;

	private Image icon = null;
	
	//1: + life
	//2: + bullet number
	//3: + bullet size
	//4: - firing delay
	
	//constructor.
	public PowerUp(int type, double x , double y) {
		
		this.type = type;
		this.x = x;
		this.y = y;
		
		//System.out.println(type);
				
		
		switch(type) {
		
			case 1:
				// color1 = GamePanel.type1;
				try{
					icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("mask.png"));

				}
				catch (IOException e){
					e.printStackTrace();
				}
				break;
			
			case 2:
				//color1 = GamePanel.type2;
				try{
					icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("stay at home.png"));

				}
				catch (IOException e){
					e.printStackTrace();
				}
				break;
				
			case 3:
				// color1 = GamePanel.type3;
				try{
					icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("sleep.png"));

				}
				catch (IOException e){
					e.printStackTrace();
				}
				break;
			
			case 4:
				// color1 = GamePanel.type4;
				try{
					icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("washing hand.png"));

				}
				catch (IOException e){
					e.printStackTrace();
				}
				break;
			
		}
		
	}
	
	//methods;
	public double getx() { return x;}
	public double gety() { return y;}
	public double getr() { return r;}
	
	public int getType() {return type;}
	
	public boolean update() {
		
		y += speed;
		
		if(y > GamePanel.HEIGHT + r) {
			return true;
		}
		
		
		return false;
		
	}
	
	public void draw(Graphics2D g) {
		
		//g.setColor(color1);
		//g.fillOval((int)(x - r),(int) (y - r), r * 2, r * 2);

		iconDrawX = (int) Math.round(x - r );
		iconDrawY = (int) Math.round(y - r - 30);
		g.drawImage(icon ,iconDrawX, iconDrawY, 2 * r, 2 * r, null);
	}
	
}
