import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class LifeDrawer {
	private int x;
	private int y;
	private int width=40;
	private int height=10;
	private int life;
	private int maxLife;
	
	private int alpha = 200;
	
	
	//constructor
	public LifeDrawer (int size, int x, int y,int life, int maxLife) {
		super();
		this.x = x;
		this.y = y;
		this.life = life;
		this.maxLife = maxLife;
		this.width = size;
	}
	private int caculateX(int x, int r) {
		
		return (int)(x - 0.5 * r);
	}
	private int caculateY(int y, int r) {
		
		return (int)(y - r);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void draw(Graphics g){
		
		Graphics2D g2=(Graphics2D) g;
		//draw the border
		
		Rectangle2D r2=new Rectangle2D.Double(caculateX(x,width), caculateY(y,width), width, height);
		g2.setColor(new Color(255,215,0,alpha));
		g2.draw(r2);
		//draw the health depends on the ratio.
		
		Rectangle2D r=new Rectangle2D.Double(caculateX(x,width), caculateY(y,width), width*((double)life/maxLife)-1, height-1);
		g2.setColor(new Color(255,97,3,alpha));
		g2.fill(r);
	}
}