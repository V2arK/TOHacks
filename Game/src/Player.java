import java.awt.*;

import java.awt.Image;  
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;

//import java.awt.EventQueue;
//import java.awt.Graphics;

import javax.imageio.ImageIO;
//import javax.swing.JComponent;
//import javax.swing.JFrame;


public class Player {
	//fields
	public static int typeOfBullet;
	private int maxTypeOfBullet = 5;
	
	private int x;
	private int y;
	private int r;
	
	private int bulletAngleRange = 5;
	
	public int type1 = 0;
	public int type2 = 0;
	public int type3 = 0;
	public int type4 = 0;
	
	private int dx;
	private int dy;
	public static int speed;
	
	private int bulletR;
	private int maxBulletR = 20;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	private long minFireingDelay = 50;
	
	public static int maxLives;
	public static int lives;
	private int energy;
	
	public Color color1;
	private Color color2;
	private BufferedImage img = null;
	private Image icon = null;

	
	private int iconWidth;
	private int iconHeight;
	private int iconDrawX;
	private int iconDrawY;
	private int iconX;
	private int iconY;
	
	private long DamageDelay = 1000;
	private long DamageTime;
	
	private int maxAbilityTime = 2000;
	private int abilityTime;
	private long currentTime;
	private long startFiringTime;
	private long stopFiringTime;
	public static long abilityFiringDelay = 100;
	private long minAbilityFiringDelay = 5;
	public static int abilityCost = 5;
	private long displayAbilityTime;
	private long casheAbilityTime;
	private long casheStartAbilityTime;
	
	private int restoreAbilitySpeed = 1;
	//private long stopAbilityFiringTime; 
	
	public boolean abilityFiring;
	
	
	//constructor;
	public Player() {
		
		 /**try{
	            File f=new File("images\\top.jpg");
	            img = ImageIO.read(f);
	        }catch(Exception e){
	            System.out.println("not find file");
	        }
	     **/  
		
		
		//debug print
		// System.out.println(new File("player.png").exists());
		 try{
			 icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player.png"));
			 // icon = ImageIO.read(new File("./src/Resources/player.png"));

	        }
	        catch (IOException e){
	            e.printStackTrace();
	        }

		 
		//icon = Toolkit.getDefaultToolkit().createImage("Resources/player.png");
		 
		currentTime = GamePanel.getCurrentTime();
		abilityTime = maxAbilityTime;
		displayAbilityTime = maxAbilityTime;
		stopFiringTime = currentTime;
		startFiringTime = currentTime;
		casheAbilityTime = maxAbilityTime;
		DamageTime = currentTime;
		//stopAbilityFiringTime = currentTime;
		
		abilityFiring = false;
		
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		
		r = 10;
		
		dx = 0;
		dy = 0;
		speed = 5;
		
		bulletR = 1;
		
		maxLives = 5;
		lives = maxLives;
		
		typeOfBullet = 1;
		
		energy = 150;		
		
		color1 = Color.WHITE;
		color2 = Color.RED;
		
		firing = false;
		firingTimer = GamePanel.getCurrentTime();
		firingDelay = 300;
		
		//player icon caculate;
		iconWidth = 5 * r;
		iconHeight = 5 * r;
		
	}
	//method;
	public void powerupCount(int type) {
		
		switch(type){
		
			case 1: 
				type1 += 1;
				break;
					
			case 2:
				type2 += 1;
				break;
				
			case 3:
				type3 += 1;
				break;
				
			case 4:
				type4 += 1;
				break;
		
		}		
	}
	
	public int getx() { return x;}
	public int gety() { return y;}
	public int getr() { return r;}
	
	public int getAbilityTime() {return (int)abilityTime;}
	public int getMaxAbilityTime() {return (int)maxAbilityTime;}
	
	public int getLives() {return lives;}
	public int getMaxLives() {return maxLives;}
	
	public boolean isDead() {
	
		if(lives <= 0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void setLeft(boolean b) {left = b ;}
	public void setRight(boolean b) {right = b ;}
	public void setUp(boolean b) {up = b ;}
	public void setDown(boolean b) {down = b ;}
	
	public void setFiring(boolean b) {firing = b;}
	
	public void increseAbilityTime() {

		
		if(abilityTime + 1 <= maxAbilityTime) {
			
			abilityTime += 1;
			
		}
			
		
	}
	public void checkVoidAbilityTime() {
		
		if(abilityTime < 0) {
			
			abilityTime = 0;
		}
		
		if(displayAbilityTime < 0) {
			
			displayAbilityTime = 0;
			
		}
		
	}
	
	public void setAbilityFiringTrue() {
		
		startFiringTime = currentTime;
		abilityFiring = true;
		
	}
	public void setAbilityFiringFalse() {
		
		stopFiringTime = currentTime;
		abilityFiring = false;
		
	}
	public void getDamage(int damage) {
		
		if(currentTime - DamageTime > DamageDelay) {
			
			lives -= damage;
			
			DamageTime = currentTime;
		}

		
	}
	
	public void minusDelay(int delay) {
		
		if(delay + firingDelay < (int)minFireingDelay) {
			
			firingDelay -= delay;
			
		}
		if(delay + abilityFiringDelay < (int)minAbilityFiringDelay) {
			
			abilityFiringDelay -= delay;
			
		}
		
		
	}
	
	public void addBulletSize(int size) {
		
		if(size + bulletR <= maxBulletR) {
			
			bulletR += size;
			
		}
		
	}
	
	
	public void addBullet(int amount) {
		
		
		if(amount + typeOfBullet <= maxTypeOfBullet) {
			
			typeOfBullet  += amount;			
			
		}
	}
	
	public void addHealth(int health) {
		
		
		if(health + lives <= maxLives) {
			
			lives += health;
		}
		
	}
	
	public void update() {
		
		currentTime = GamePanel.getCurrentTime();
		
		if(left) {
			dx = -speed;
		}
		if (right) {
			dx = speed;
		}
		if(up) {
			dy = -speed;
		}
		if(down) {
			dy = speed;
		}
		//add displacement information together;
		
		x += dx;
		y += dy;
		iconX = x - r;
		iconY = y - r;
		//caculate the location of the player using the displacement
		
		if(x < r) x = r;
		if(y < r) y = r;
		if(x > GamePanel.WIDTH - r) x = GamePanel.WIDTH - r;
		if(y > GamePanel.HEIGHT - r) y = GamePanel.HEIGHT - r;
		//check if the player is out the screen
		
		dx = 0;
		dy = 0;
		
		//boolean repeat = false;
		
		//System.out.println(abilityTime);
		
		checkVoidAbilityTime();
		//check void ability time ( < 0)
		
		
		if(abilityFiring && abilityTime > 0) {
			
			abilityTime -= abilityCost;
			
			//take the time away!
			//abilityTime = (int)displayAbilityTime;
			
			abilityFire(typeOfBullet);
			//ability shoot
			//repeat = true;
			
		}
		else if(!abilityFiring) {
			increseAbilityTime();
			//casheAbilityTime = abilityTime;
		}
		
		//increseAbilityTime();
		//restore the ability.
		
		if(firing) {
			fire(typeOfBullet);
			//fire the bullet 
		}
		//normal fire
		
		
		
	}
	
	public void addAbilityTime(int amount) {
		
		if(abilityTime + amount <= maxAbilityTime) {
			
			abilityTime += amount;
			
		}
		
	}
	
	public void fire(int bulletType) {
		// type 1 = ��ͨ
		// type 2 = 3������
		// type 3 = 5������
		// type 4 = �Զ�׷��.����
		
		long elapsed = (System.nanoTime() - firingTimer) / 1000000;
		//because the nanoTime() method return a nanoSecond,
		//so i convert it to milliSecond. 
		if(elapsed > firingDelay) {
			
			//System.out.println(bulletType);
			//if the time between the firing last time is greater than
			//the firingDelay, then fire the next bullet.
			switch(bulletType) {
				
				case 1:
					GamePanel.bullets.add(new Bullet(270,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
					
				case 2:
					GamePanel.bullets.add(new Bullet(268,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(272,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
					
				case 3:
					GamePanel.bullets.add(new Bullet(267,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(270,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(273,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
				
				case 4:
					GamePanel.bullets.add(new Bullet(264,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(267,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(270,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(273,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
					
				case 5:
					GamePanel.bullets.add(new Bullet(264,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(267,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(277,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(273,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(270,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;

					
				}
				
		}
		
	}
	
	public void abilityFire(int bulletType) {
		// type 1 = ��ͨ
		// type 2 = 3������
		// type 3 = 5������
		// type 4 = �Զ�׷��.����
		int count = (int)(2 * Math.random());
		
		for(int i = 0; i < count; i ++) {
			
			GamePanel.deathEffect.add(new DeathEffect(200,x,y,2));
			
		}
		
		long elapsed = (System.nanoTime() - firingTimer) / 1000000;
		//because the nanoTime() method return a nanoSecond,
		//so i convert it to milliSecond. 
		if(elapsed > abilityFiringDelay) {
			int random = GamePanel.getRandom(bulletAngleRange);
			//System.out.println(bulletType);
			//if the time between the firing last time is greater than
			//the firingDelay, then fire the next bullet.
			switch(bulletType) {
				
				case 1:
					GamePanel.bullets.add(new Bullet(270 + random,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
					
				case 2:
					GamePanel.bullets.add(new Bullet(268 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(272 + random,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
					
				case 3:
					GamePanel.bullets.add(new Bullet(267 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(270 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(273 + random,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
				
				case 4:
					GamePanel.bullets.add(new Bullet(264 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(267 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(270 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(273 + random,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
					
				case 5:
					GamePanel.bullets.add(new Bullet(264 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(267 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(277 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(273 + random,x,y,bulletR));
					GamePanel.bullets.add(new Bullet(270 + random,x,y,bulletR));
					firingTimer = System.nanoTime();
					break;
					
					
				}
				
		}
		
	}

	public void draw(Graphics2D g) {
		//g.setColor(color1);
		//g.fillOval(x - r,  y - r,  2 * r, 2 * r);
		
		//g.drawImage(img, 0, x,y);
		//g.setStroke(new BasicStroke(3));
		//g.setColor(color1.darker());
		//g.drawOval(x-r, y-r, 2*r, 2*r);
		//g.setStroke(new BasicStroke(1));
		iconDrawX = iconX - 13;
		iconDrawY = iconY - 25;
		g.drawImage(icon ,iconDrawX , iconDrawY, iconWidth, iconHeight, null);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
