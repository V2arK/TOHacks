import java.awt.*;


public class Bullet {
	
		//Define
	private double x;
	private double y;
	private int r;
	private int xBefore;
	private int yBefore;
	private int xBefore1;
	private int yBefore1;
	private int xBefore2;
	private int yBefore2;
	//private int maxR = 15;

	private int life;

	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private Color color1;
	
	//Constructor;
	public Bullet(double angle, int x, int y, int r) {
		
		this.x = x;
		this.y = y;
		this.r = r;
		this.r = r;
		life = r;
		//r = 3;

		xBefore = x;
		xBefore1 = x;
		xBefore2 = x;
		
		yBefore = y;
		yBefore1 = y;
		yBefore2 = y;
		
		rad = Math.toRadians(angle);
		speed = 50;
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;

		
		color1 = Color.BLUE;
	}
	
	//method
	
	public double getx() {return x;}
	public double gety() {return y;}
	public double getr() {return r;}
	public double getlife() {return life;}
	public double takelife() {life --; return life;}

	public boolean update () {
		
		xBefore2 = xBefore1;
		yBefore2 = yBefore1;
		
		xBefore1 = xBefore;
		yBefore1 = yBefore;
		
		xBefore = (int) x;
		yBefore = (int) y;
		
		x += dx;
		y += dy;
		
		if(x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
			return true;
		}
		
		return false;
		
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(color1);
		g.drawOval((int)(x - r), (int)(y - r),2 * r, 2 * r);
		g.fillOval((int)(x - r), (int)(y - r),2 * r, 2 * r);
		
		g.setColor(color1.brighter().brighter());
		g.drawOval((int)(x - r), (int)(y + 2 - r),2 * r, 2 * r);
		g.fillOval((int)(x - r), (int)(y + 2 - r),2 * r, 2 * r);
		
		g.setColor(color1.brighter().brighter().brighter().brighter());
		g.drawOval((int)(x - r), (int)(y + 4 - r),2 * r, 2 * r);
		g.fillOval((int)(x - r), (int)(y + 4 - r),2 * r, 2 * r);
		
		g.setColor(Color.WHITE);
		g.drawOval((int)(x - r), (int)(y + 6 - r),2 * r, 2 * r);
		g.fillOval((int)(x - r), (int)(y + 6 - 
				r),2 * r, 2 * r);
	} 
	
}
