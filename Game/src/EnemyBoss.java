import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class EnemyBoss {
	
	//define
	private double x;
	private double y;
	private int r;
	
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
	
	private long timeGotHit = 0;
	private long timeCaculate = 0;
	private long damageColorDelay = 20;
	
	private boolean gotHit = false;
	
	private boolean ready;
	private boolean dead;
	
	private int R;
	private int G;
	private int B;
	
	private int bulletSpeed;
	private int bulletDelay;
	
	public long currentTime;
	public long spawnEnemiesDelay = 10000;
	public long firingBulletDelay = 5000;
	public long spawnTime;
	public long firingTime;
	
	private int damageCashe;
	public String damageDisplay = ""; 
	private int  damageDisplayDelay = 1000;
	private boolean damageDisplaying = false;

	// icon

	private int iconDrawX;
	private int iconDrawY;
	private Image icon = null;

	//constructor
	public EnemyBoss(int type, int rank) {

		try{
			icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("cough.png"));
		}
		catch (IOException e){
			e.printStackTrace();
		}

		currentTime = GamePanel.getCurrentTime();
		spawnTime = currentTime;
		firingTime = currentTime;
		
		this.type = type;
		this.rank = rank;
		
		int rest = type % 10;
		
		bulletDelay = 1000 - rank / 10;
		bulletSpeed = 20 + rank / 10;
		
		spawnEnemiesDelay -= rank * 10;
		firingBulletDelay -= rank * 20;
		
		R = 0 + rest * 26;
		G = 0 + rest * 26;
		B = 255 - rest * 26;
		
		if(R > 255) {R = 255;}
		if(G > 255) {G = 255;}
		if(B < 0) {B = 0;}
		
		colorNormal = new Color(R,G,B);
		
		//set the color of the EnemyBoss.(Type)
		
		int rankRest = rank / 5;
		
		maxHealth = 50 + type * 5 ; 
		speed = 1 + rankRest;
		r = 30 + rankRest;
		health = maxHealth;
		
		//set the config of the EnemyBoss.(rank)
		
		x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
		y = - r;
		
		colorGotHit = Color.RED.darker().darker();
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
		
		this.currentTime = GamePanel.getCurrentTime();
		
		x += dx;
		y += dy;
		
		if(!ready) {
			if(x > r && x < GamePanel.WIDTH - r && y > r && y < GamePanel.HEIGHT - r) {
				ready = true;
				
			}
			
		}
		
		
		this.timeCaculate = GamePanel.getCurrentTime();
		if(gotHit == true && timeCaculate - timeGotHit < damageColorDelay) {
			presentColor = colorGotHit;
			
		}
		else if(gotHit == true && timeCaculate - timeGotHit > damageColorDelay) {
			presentColor = colorNormal;
			gotHit = false;
		}
		//Changing color when got hit.
		
		if(spawnTime + spawnEnemiesDelay < currentTime ) {
			//System.out.println("spawning!!");
			
			for(int i = 0; i < rank * 2 + 2; i ++) {
				
				GamePanel.enemies.add(new Enemy(x, y, GamePanel.waveDevide, GamePanel.waveDevide ));
				//System.out.println("spawning!");
				
			}
			
			spawnTime = GamePanel.getCurrentTime();
			//save the spawn time
			
		}
		//spawn enemies;
		
		if(firingTime + firingBulletDelay < currentTime) {
			
			double roll = Math.random();
			
			if(roll <= 0.3) {
				
				GamePanel.bossBullet.add(new BossBullet(90,(int)x,(int)y,5));
				
			}
			else if(roll <= 0.7) {
				
				GamePanel.bossBullet.add(new BossBullet(95,(int)x,(int)y,5));
				GamePanel.bossBullet.add(new BossBullet(85,(int)x,(int)y,5));
				
			}
			else {
				
				GamePanel.bossBullet.add(new BossBullet(80,(int)x,(int)y,5));
				GamePanel.bossBullet.add(new BossBullet(90,(int)x,(int)y,5));
				GamePanel.bossBullet.add(new BossBullet(100,(int)x,(int)y,5));
				
			}
			
			
			firingTime = GamePanel.getCurrentTime();
			
			
		}
		
		this.checkDamageDisplay();
		
		
		if (x < r && dx < 0) {dx = - dx;}
		if(y < r && dy < 0) {dy = -dy;}
		if(x > GamePanel.WIDTH - r && dx > 0) {dx = -dx;}
		if(y > GamePanel.HEIGHT - r && dy > 0) {y = 0;}
		//if(y > GamePanel.HEIGHT - r && dy > 0) {dy = -dy;}
		
		
		
		if(x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
			return true;
		}
		
		return false;
		
	}
	
	public void draw(Graphics2D g) {
		
		//g.setColor(presentColor);
		//g.fillRect((int)(x - r), (int)(y - r), 2 * r, 2 * r);
		iconDrawX = (int) Math.round(x - r);
		iconDrawY = (int) Math.round(y - r);
		g.drawImage(icon ,iconDrawX, iconDrawY, 2 * r, 2 * r, null);
		g.setStroke(new BasicStroke(3));
		g.setColor(presentColor.darker());
		g.drawRect((int)(x - r), (int)(y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));
		
		g.setColor(Color.RED);
		g.setFont(new Font(GamePanel.textFont, Font.BOLD, 30));
		g.drawString(this.getDamageDisplay(), (int)(this.getx() + 1.2 * this.getr()), (int)(this.gety() - 1.2 * this.getr()));
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
