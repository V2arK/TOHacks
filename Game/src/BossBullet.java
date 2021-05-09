import java.awt.Color;
import java.awt.Graphics2D;


public class BossBullet {
	
		//Define
	private double x;
	private double y;
	private int r;
	//private int maxR = 15;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private Color color1;
	
	//Constructor;
	public BossBullet(double angle, int x, int y, int r) {
		
		this.x = x;
		this.y = y;
		this.r = r;
		//r = 3;
		
		rad = Math.toRadians(angle);
		speed = 5;
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;

		
		color1 = Color.RED;
	}
	
	//method
	
	public double getx() {return x;}
	public double gety() {return y;}
	public double getr() {return r;}
	

	public boolean update () {
		x += dx;
		y += dy;
		
		if(x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
			return true;
		}
		
		return false;
		
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(color1);
		g.drawRect((int)(x - r), (int)(y - r),2 * r, 2 * r);
		g.fillRect((int)(x - r), (int)(y - r),2 * r, 2 * r);
	}
	
}
