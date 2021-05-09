import java.awt.*;
import java.awt.image.BufferedImage;

public class DeathEffect {

	
	private Color color;
	private int R;
	private int G;
	private int B;
	private int colorAlpha = 255;
	private int A;
	
	private int x;
	private int y;
	private int xBefore;
	private int yBefore;
	private int xBefore1;
	private int yBefore1;
	private int xBefore2;
	private int yBefore2;
	private int r = 1;
	
	private double dx = 0;
	private double dy = 0;
	private double speed;
	
	private int fadeTimeRange ;
	private int speedRange ;

	private double rad;
	//private double angle;
	
	private BufferedImage img;
	
	private long startTime;
	private long currentTime;
	private double fadeTime;
	
	public DeathEffect(int fadeTime, int x, int y, int r, int speed, Color color) {
		
		startTime = GamePanel.getCurrentTime();
		currentTime = startTime;
		
		fadeTimeRange = fadeTime / 2;
		speedRange = speed / 2;
		
		A = colorAlpha;
		
		this.x = x;
		this.y = y;
		this.r = (int)(r / 5);
		this.color = color.brighter().brighter().brighter();
		this.speed = speed * 2 * Math.random();
		this.fadeTime = fadeTime * 2 * Math.random();
		//System.out.println(this.speed);
		
		R = color.getRed();
		G = color.getGreen();
		B = color.getBlue();
		
		xBefore = x;
		xBefore1 = x;
		xBefore2 = x;
		
		yBefore = y;
		yBefore1 = y;
		yBefore2 = y;
		
		this.speed = speed;
		
		double angle = (int)(360 * Math.random()); 
		
		rad = Math.toRadians(angle);
		//rad = angle;
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		//System.out.println("dx:" + dx  + ";dy:" + dy);
		
	}
	
public DeathEffect(int fadeTime, int x, int y, int speed) {
		color = Color.RED;
	
		startTime = GamePanel.getCurrentTime();
		currentTime = startTime;
		
		fadeTimeRange = fadeTime / 2;
		speedRange = speed / 2;
		
		A = colorAlpha;
		
		this.x = x;
		this.y = y;
		this.r = (int)(r / 5);
		this.color = color.brighter().brighter().brighter();
		this.speed = speed * 2 * Math.random();
		this.fadeTime = fadeTime * 2 * Math.random();
		//System.out.println(this.speed);
		
		R = color.getRed();
		G = color.getGreen();
		B = color.getBlue();
		
		xBefore = x;
		xBefore1 = x;
		xBefore2 = x;
		
		yBefore = y;
		yBefore1 = y;
		yBefore2 = y;
		
		this.speed = speed;
		
		double angle = (int)(180 + 180 * Math.random()); 
		
		rad = Math.toRadians(angle);
		//rad = angle;
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		//System.out.println("dx:" + dx  + ";dy:" + dy);
		
	}
	
	public boolean isFinish() {
		
		if(A <= 0) {
			
			return true;
			
		}
		else {
			
			return false;
			
		}
		
	}
	
	public int checkVoidAlpha() {
		
		if(A > 255) {return 255;}
		if(A < 0) {return 0;}
		else {return A;}
		
		
	}
	
	public void update() {
		xBefore2 = xBefore1;
		yBefore2 = yBefore1;
		
		xBefore1 = xBefore;
		yBefore1 = yBefore;
		
		xBefore = x;
		yBefore = y;
		
		currentTime = GamePanel.getCurrentTime();
		
		x += dx;
		y += dy;
		
		A = (int)(255 * Math.sin(3.14 * (currentTime - startTime) / fadeTime));
		
		
		color = new Color(R,G,B,checkVoidAlpha());
		
		if (x < r && dx < 0) {dx = - dx;}
		if(y < r && dy < 0) {dy = - dy;}
		if(y > GamePanel.HEIGHT - r && dy > 0) {dy = -dy;}
		
	}
	
	public void draw(Graphics2D g) {
		
		//g.setColor(color1);
		g.fillRect(x - r,  y - r,  2 * r, 2 * r);
		g.fillRect(xBefore - r,  yBefore - r,  2 * r, 2 * r);
		g.fillRect(xBefore1 - r,  yBefore1 - r,  2 * r, 2 * r);
		g.fillRect(xBefore2 - r,  yBefore2 - r,  2 * r, 2 * r);
		
		g.setStroke(new BasicStroke(1));
		g.setColor(color);
		g.drawRect(x-r, y-r, 2*r, 2*r);
		g.setColor(color.brighter().brighter());
		g.drawRect(xBefore-r, yBefore-r, 2*r, 2*r);
		g.setColor(color.brighter().brighter().brighter().brighter());
		g.drawRect(xBefore1-r, yBefore1-r, 2*r, 2*r);
		g.setColor(Color.WHITE);
		g.drawRect(xBefore2-r, yBefore2-r, 2*r, 2*r);
		
	}
}
