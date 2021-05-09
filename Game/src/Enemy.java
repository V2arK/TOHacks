import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Enemy {
	
	//define
	private double x;
	private double y;
	private int r;
	
	private long currentTime;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private int health;
	private int maxHealth;
	private int type;
	private int rank;
	
	private Color colorNormal;
	private Color colorGotHit;
	private Color presentColor;

	private Image icon = null;

	private int iconWidth;
	private int iconHeight;
	private int iconDrawX;
	private int iconDrawY;

	private double currentSpeed;
	
	private long timeGotHit = 0;
	private long timeCaculate = 0;
	private long damageColorDelay = 100;
	
	private boolean gotHit = false;
	
	private boolean ready;
	private boolean dead;
	
	private int R;
	private int G;
	private int B;
	
	private int damageCashe;
	public String damageDisplay; 
	private int  damageDisplayDelay = 500;
	private boolean damageDisplaying = false;
	private String address = "enemy.png";
	 
	private int bulletSpeed;
	private int bulletDelay;
	
	private int randomSpeedRange = 10;
	
	//constructor
	public Enemy(int type, int rank) {

		try{
			icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream(address));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		this.damageCashe = 0;
		this.damageDisplay = "";
		
		this.type = type;
		this.rank = rank;
		
		int devide = (int)(type / 10);
		int rest = type % 10;
		
		R = 0 + rest * 26;
		G = 0 + rest * 26;
		B = 255 - rest * 26;
		
		if(R > 255) {R = 255;}
		if(G > 255) {G = 255;}
		if(B < 0) {B = 0;}
		
		colorNormal = new Color(R,G,B);
		
		//set the color of the enemy.(Type)
		
		int rankRest = rank / 5;
		
		maxHealth = 1 + rank ; 
		speed = 1 + rankRest / 2;
		r = 10;
		health = maxHealth;
		
		//set the config of the enemy.(rank)
		
		x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
		y = - r;
		
		colorGotHit = Color.RED.darker();
		//the color when enemies got hit.
		
		presentColor = colorNormal;
		
		double angle = Math.random() * 140 + 20;
		//facing down
		rad = Math.toRadians(angle);
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		ready = false;
		dead = false;
	}
	
	public Enemy(double x, double y, int type, int rank) {

		try{
			icon = ImageIO.read(getClass().getResource(address));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		this.damageCashe = 0;
		this.damageDisplay = "";
		
		this.type = type;
		this.rank = rank;
		
		this.x = x;
		this.y = y;
		
		int devide = (int)(type / 10);
		int rest = type % 10;
		
		R = 0 + rest * 26;
		G = 0 + rest * 26;
		B = 255 - rest * 26;
		
		if(R > 255) {R = 255;}
		if(G > 255) {G = 255;}
		if(B < 0) {B = 0;}
		
		colorNormal = new Color(R,G,B);
		
		//set the color of the enemy.(Type)
		
		int rankRest = rank / 5;
		
		//maxHealth = 10 ; 
		maxHealth = 1 + type ; 
		speed = 1 + rankRest;
		r = 10 + rankRest + devide;
		health = maxHealth;
		
		//set the config of the enemy.(rank)
		
		//x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
		//y = - r;
		
		colorGotHit = Color.RED.darker();
		//the color when enemies got hit.
		
		presentColor = colorNormal;
		
		double angle = Math.random() * 140 + 20;
		//facing down
		rad = Math.toRadians(angle);
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		
		ready = false;
		dead = false;
	}
	
	//method
	public double getx() {return x;}
	public double gety() {return y;}
	public double getr() {return r;}
	public int getCurrentLife() {return (int)health;}
	public int getMaxLife() {return (int)maxHealth;}
	public int getType() {return type;}
	public int getRank() {return rank;}
	public String getDamageDisplay() {return this.damageDisplay;}
	
	private void checkDamageDisplay() {
		
		if(this.currentTime - this.timeGotHit < this.damageDisplayDelay) {
			
			this.damageDisplay = "- " + this.damageCashe;
			
		}
		else if(this.currentTime - this.timeGotHit >= this.damageDisplayDelay) {
			
			this.damageDisplay = "";
			this.damageCashe = 0;
			
		}
		
		
	}
	public void hit(int damage) {
		
		gotHit = true;
		timeGotHit = GamePanel.getCurrentTime();
		//NanoSec to MillSec by divided by 1000,000
		
		this.damageCashe += damage;
		
		for(int i = 0; i < damage; i ++) {
		health --;
		if(health <= 0) {
			dead = true;
		}
		}
	}
	
	public boolean update() {
		
		currentTime = System.nanoTime() / 1000000;
		
		x += dx;
		y += dy;
		
		if(!ready) {
			if(x > r && x < GamePanel.WIDTH - r && y > r && y < GamePanel.HEIGHT - r) {
				ready = true;
				
			}
			
		}
		
		
		timeCaculate = currentTime;
		
		if(gotHit == true && timeCaculate - timeGotHit < damageColorDelay) {
			presentColor = colorGotHit;
			
		}
		else if(gotHit == true && timeCaculate - timeGotHit > damageColorDelay) {
			presentColor = colorNormal;
			gotHit = false;
		}
		//Changing color when got hit.
		
		checkDamageDisplay();
		
		if (x < r && dx < 0) {dx = - dx ;}
		if(y < r && dy < 0) {dy = -dy ;}
		if(x > GamePanel.WIDTH - r && dx > 0) {dx = -dx;}
		if(y > GamePanel.HEIGHT - r && dy > 0) {y = 0;}
		//if(y > GamePanel.HEIGHT - r && dy > 0) {dy = -dy;}
		
		/**
		currentSpeed = Math.sqrt(dx * dx + dy * dy);
		
		if(currentSpeed > 5) {
			
			dx -= 1;
			dy -= 1;
			
		}
		
		**/
		//slow down
		
		
		if(x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
			return true;
		}
		
		return false;
		
	}
	
	public void draw(Graphics2D g) {
		/*
		g.setColor(presentColor);
		g.fillRect((int)(x - r), (int)(y - r), 2 * r, 2 * r);
		
		g.setStroke(new BasicStroke(3));
		g.setColor(presentColor.darker());
		g.drawRect((int)(x - r), (int)(y - r), 2 * r, 2 * r);
		*/
		iconDrawX = (int) Math.round(x - r );
		iconDrawY = (int) Math.round(y - r - 30);
		g.drawImage(icon ,iconDrawX, iconDrawY, 2 * r, 2 * r, null);

		g.setStroke(new BasicStroke(1));
		g.setColor(Color.RED);
		g.setFont(new Font(GamePanel.textFont, Font.BOLD, 30));
		g.drawString(this.getDamageDisplay(), (int)(getx() + 1.2 * getr()), (int)(gety() - 1.2 * getr()));
	}

	public boolean isDead() {
		// TODO Auto-generated method stub
		return dead;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return colorNormal;
	}
	
}
