import javax.swing.JPanel;

//import javafx.scene.media.*;
//import javafx.scene.media.Media;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import java.awt.event.*;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;



public class GamePanel extends JPanel implements Runnable , KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8070711578195767213L;
	//Define Fields
	public static int WIDTH = 400;
	//the width of the game window
	public static int HEIGHT = 800;
	//the height of the game window
	private Thread thread;
	//create the thread
	private boolean running;
	//is the game running or not.
	private BufferedImage image;
	//create a BufferedImage subject
	private Graphics2D g;
	//create a 2d Graphic subject
	private int FPS = 60;
	//max FPS
	private double averageFPS;
	
	private Color TextColor = Color.WHITE;
	//set the default color of the texts.
	
	static public Color type1 = Color.PINK;
	static public Color type2 = Color.YELLOW;
	static public Color type3 = Color.GREEN;
	static public Color type4 = Color.WHITE;
	//powerUps' color;
	
	
	private Image background;
	
	
	private int backgroundWidth = WIDTH;
	private int backgroundHeight = HEIGHT;
	
	private int bgY_0 = HEIGHT;
    private int bgY_1 = 0;
	
    private float scale = 2;
    
	//background relevant.
	
	static String textFont = "Agency FB";
	String cheat = "CHEAT ON!";
	
	private String backgroundAddress =  "/background.jpg";
	//private String backgroundAddressI = "/Resources/texture/background.jpg";
	//private String backMusicAddress =  "src/Resources/audio/background.wav";
	//size of the image and the size of the window.
	
	//i need 2 viable to storage the Y-axis of 2 background file.
	
	private int rollingSpeed = 1;	
	
	//background Image relevant.
	
	public static int waveDevide;
	public static int waveRest;
	//wave relevant;
	
	public static Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	public static LifeDrawer lifeDrawer;
	public static ArrayList<PowerUp> powerUps;
	public static PlayerLifeDrawer playerLifeDrawer;
	public static AbilityBarDrawer abilityBarDrawer;
	public static AudioPlayer backgroundPlayer;
	public static ArrayList<DeathEffect> deathEffect;
	
	public static ArrayList<EnemyBoss> enemyBoss;
	public static ArrayList<BossBullet> bossBullet;
	//import the class.
	
	public static long currentTime;
	
	private long waveStartTime;
	private long waveStartDelay;
	private int waveNumber;
	private  boolean waveStart;
	private int waveDelayCheat = 4000;
	private int waveDelay = 4000;
	FontMetrics FM;
	
	private boolean gameOver;
	
	private boolean replay;
	
	private boolean gameInstruction = false;
	
	private int Width;
	private int Height;
	
	private boolean C = false;
	private boolean H = false;
	private boolean E = false;
	private boolean A = false;
	private boolean T = false;
	private boolean O = false;
	private boolean N = false;
	
	private boolean Cheat = false;
	
	private int TitleWidth;
	private int TextWidth_0;
	private int TextWidth_1;
	private int TitleFontSize = 30;
	private int ChangingDelay = 100;
	private int pauseTime = 100;
	
	private int Ascent;
	private int Descent;
	
	public int Score = 0;
	private boolean Scaled;
	
	private int powerUpR = 20;
	
	
	private boolean pause = false;
	private boolean startGame = false;
	
	//for the popping of the title.
	private boolean Changing = true;
	
	private int titleFontSize = 35;
	private int subTitleFontSize = 30;
	private int textFontSize = 25;

	//powerup icon
	private Image wash_hand = null;
	private Image sleep = null;
	private Image wear_mask = null;
	private Image stay_home = null;

	private Image dead_icon = null;

	//Constructor
	
	public GamePanel() {
		
		super();
		//call the constructor in the parent class.
		//although there isn't a method called GamePanel in the 
		//class been imported, but it's better to.
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		//set the size of the window, and let it adjust it self.
		//otherwise i will just use setSize.
		setFocusable(true);
		//set focusable.
		requestFocus();
		//request focus.
		//System.out.println(new File(backMusicAddress).exists());

	}
	
	//Functions;
	
	public void addNotify() {
		//overwrite the method, and it's actually in the imported classes.
		super.addNotify();
		//as i overwrite it, so i need to call the parents class's method.
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		//if the game thread haven't started, then run it.
		addKeyListener(this);
		//add a Key Listener.
	}
	
	public void run() {
		
		replay = false;
		
		running = true;
		gameOver = false;
		
		Scaled = false;
		//set the value running true.
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		//create the Canvas that the game will been render on.
		
		g= (Graphics2D) image.getGraphics();
		//create the Graphic2D class of the image.
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//enable the Initializing!   

		//debug print
		// System.out.println(new File("player.png").exists());
		try{
			background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("background.jpg"));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		//powerup
		try{
			sleep = ImageIO.read(getClass().getClassLoader().getResourceAsStream("sleep.png"));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		try{
			wash_hand = ImageIO.read(getClass().getClassLoader().getResourceAsStream("washing hand.png"));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		try{
			stay_home = ImageIO.read(getClass().getClassLoader().getResourceAsStream("stay at home.png"));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		try{
			wear_mask = ImageIO.read(getClass().getClassLoader().getResourceAsStream("wearing mask.png"));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		try{
			dead_icon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("rip.png"));

		}
		catch (IOException e){
			e.printStackTrace();
		}

		player = new Player();
		//create a instance of the player.
		bullets = new ArrayList<Bullet>();
		
		powerUps = new ArrayList<PowerUp>();
		
		//backgroundPlayer = new AudioPlayer(backMusicAddress,true);
		
		enemies = new ArrayList<Enemy>();
		
		enemyBoss = new ArrayList<EnemyBoss>();
		
		bossBullet = new ArrayList<BossBullet>();
		
		deathEffect = new ArrayList<DeathEffect>();
		//for(int i = 0; i < 5; i ++) {
		//	enemies.add(new Enemy(1,1));	
		//}
		
		waveStartTime = 0;
		waveStartDelay = 0;
		waveStart = true;
		waveNumber = 0;
		
		//this is all the FPS relevant stuff.
		long startTime;
		long renderTime;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 120;
		
		long targetTime = 1000 / FPS;
		
		
		
		
		//Finally the game loop!!
		
		///new AudioPlayer(backMusicAddress,true);
		
		//PlayThread.run(backMusicAddress, true);
		
		
		while(startGame == false) {
			changingSize();
			StartMenu();
			gameDraw();
			
			
		}
		//start Screen
		
		while(running) {
			
			g.setColor(Color.BLACK);
			g.drawRect(0,0,WIDTH,HEIGHT);
			//draw background
			
			if(pause == true) {
				while(pause == true) {
					Delay(pauseTime);
					pauseScreen();
					
					
					gameDraw();
				}
							
			}
			//pause screen
			
			if(player.isDead()) {
				replay = false;
				gameOver = true;
				while(gameOver == true) {
					Delay(pauseTime);
					deadScreen();
					gameDraw();
					if(replay == true) {
						run();
						//restart the game
					}
				}
			}
			//death screen.
			
			//Start of the Main loop			
			startTime = System.nanoTime();
			//record the start time of this loop.
			
			
			gameUpdate();
			gameRender();
			gameDraw();
			//this is the main method that hold the game!
			
			renderTime = (System.nanoTime() - startTime) / 1000000;
			//after the Update, render, draw method, all the rest are not 
			//graphic relevant. so this is the time that the game
			//took to render the graphic.
			
			waitTime = targetTime - renderTime;
			//you could see it.
			
			
			try {
				if (waitTime > 0) {
					Delay((int)waitTime);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			
			totalTime += System.nanoTime() - startTime;
			frameCount ++;
			if(frameCount == maxFrameCount) {
				averageFPS =  1000.0 / (((double)totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
				
			}
			// to count the average FPS.
		}
		
	}
	
	private void CHEATCHECK(String string) {
		
		switch(string) {
		
		case "C":
			C = true;
			break;
			
		case "H":
			if(C) 
			{
				H = true;	
			}
			else 
			{
				C = false;	
			}
			break;
			
		case "E":
			if(C&&H) 
			{
				E = true;	
			}
			else 
			{
				C = false;
				H = false;
			}
			break;
			
		case "A":
			if(C&&H&&E) 
			{
				A = true;	
			}
			else 
			{
				C = false;
				H = false;
				E = false;
			}
			break;
		
		case "T":
			if(C&&H&&E&&A) 
			{
				T = true;	
			}
			else 
			{
				C = false;
				H = false;
				E = false;
				A = false;
			}
			break;
			
		case "O":
			if(C&&H&&E&&A&&T) 
			{
				O = true;	
			}
			else 
			{
				C = false;
				H = false;
				E = false;
				A = false;
				T = false;
			}
			break;
			
		case "N":
			if(C&&H&&E&&A&&T&&O) 
			{
				N = true;	
			}
			else 
			{
				C = false;
				H = false;
				E = false;
				A = false;
				T = false;
				O = false;
			}
			break;	
		}
		
	}
	
	
	private void CHEATCHECK() {
		
		if(C&&H&&E&&A&&T&&O&&N) {
			
			Cheat = true;
			
			Player.abilityCost = 0;
			Player.maxLives = 999999;
			Player.lives = 999999;
			Player.typeOfBullet = 5;
			Player.speed = 10;
			waveDelayCheat = 500;
			Player.abilityFiringDelay = 50;
		}
		
	}
	
	
	private void changingSize() {
		// TODO Auto-generated method stub
		
		Delay(ChangingDelay);
		
		
		if(TitleFontSize  <= 55 && Changing == true) {
			TitleFontSize += 1;
			if(Changing == true && TitleFontSize > 54) {
				Changing = false;
			}
		}
		else if(TitleFontSize >= 35 && Changing == false) {
			TitleFontSize -= 1;
			if(Changing == false && TitleFontSize < 36) {
				Changing = true;
			}
		}
		
	}
	
	
	private void DeathEffectGenerator(Enemy e) {
		
		int amount = (int) (e.getMaxLife() * 2 + 2);
		
		for(int i = 0; i < amount; i ++) {
			
			deathEffect.add(new DeathEffect(
					500,
					(int)(e.getx()),
					(int)(e.gety()),
					(int)(e.getr()),
					(int)(e.getr()),
					e.getColor()));
			
			
		}
		
		
		
	}
	//overwrite the method
	private void DeathEffectGenerator(EnemyBoss e) {
		
		//int amount = (int) (e.getMaxLife() * 2 + 2);
		
		int count = e.getMaxLife() * 2;
		
		int amount = (int) (count * 2 * Math.random());
		
		for(int i = 0; i < amount; i ++) {
			
			deathEffect.add(new DeathEffect(
					3000,
					(int)(e.getx()),
					(int)(e.gety()),
					(int)(e.getr()),
					(int)(e.getr() / 2),
					e.getColor()));
			
			
		}
		
		
		
	}
	
	//overwrite the method
		private void BulletDeathEffectGenerator(Bullet e) {
			
			int count = 20;
			
			//int amount = count * (int)(e.getr()) + getRandom(count);
			
			int amount = (int) (count * Math.random());
			
			//System.out.println(amount);
			
			for(int i = 0; i < amount; i ++) {
				
				deathEffect.add(new DeathEffect(
						300,
						(int)(e.getx()),
						(int)(e.gety()),
						4,
						2,
						Color.YELLOW));
				
				
			}
			
			
			
		}
		
	public static long getCurrentTime() {
		
		//System.out.println("Happy~" + currentTime);
		return currentTime;
		
		
	}
	
	private void removeDeathEffect() {
		
		
		for(int i = 0; i < deathEffect.size(); i ++) {
			
			if(deathEffect.get(i).isFinish()) {
				
				deathEffect.remove(i);
				
				i --;
				
			}
			
			
		}
		
	}
	
	
	private int CenterTextX(String string) {
		
		
		FM = g.getFontMetrics();
		Ascent = FM.getAscent();
		Descent = FM.getDescent();
		
		//Calculate the string's width
		Width = GamePanel.WIDTH;
				
		TitleWidth = FM.stringWidth(string);
		
		//Calculate the position.
		
		return((Width - TitleWidth) /2);
	}
	
	private int CenterTextY(String string) {
		
		
		FM = g.getFontMetrics();
		Ascent = FM.getAscent();
		Descent = FM.getDescent();
		
		//Calculate the string's width
		TitleWidth = FM.stringWidth(string);
		
		Height = GamePanel.HEIGHT;
		//Calculate the position.
		
		return((Height - (Ascent + Descent)) / 2 + Ascent);
	}

	//Calculate the position where the text should be to in the center of the screen.

	private void Delay(int delay) {
		try {
			
			Thread.sleep(delay);
			// try doing this while watching for anything bad happen.
		}
		catch(Exception e) {
			//System.out.print("Error!!");
			//e.printStackTrace(System.out);
			System.out.println(e);
			}
	}

	private void gameUpdate() {
		//all the game logic goes into here.
		waveDelay = waveDelayCheat;
		
		currentTime = System.nanoTime() / 1000000;
		
		
		if(waveStartTime == 0 && enemies.size() == 0 && enemyBoss.size() == 0) {
			//if the waveStartTime is 0 and there are no enemies in the screen;
			waveNumber ++;
			waveStart = false;
			waveStartTime = System.nanoTime();
		}
		else {
			
			waveStartDelay = (System.nanoTime() - waveStartTime) / 1000000;
			if(waveStartDelay > waveDelay) {
				
				waveStart = true;
			
				waveStartTime = 0;
				waveStartDelay = 0;
				
				
			}
		}
		// Wave Generator;

		// rolling background
		bgY_0 += rollingSpeed;
		bgY_1 += rollingSpeed;

		if (bgY_0 > HEIGHT) {
			bgY_0 = -HEIGHT;
		}
		if (bgY_1 > HEIGHT) {
			bgY_1 = -HEIGHT;
		}

		if(waveStart && enemies.size() == 0 && enemyBoss.size() == 0) {     
			
			createNewEnemies();
			
		}
		//enemies Generator;
		
		if(deathEffect.size() != 0) {
			
			for(int i = 0; i < deathEffect.size(); i ++) {
				
				deathEffect.get(i).update();
				
				
			}
		}
		removeDeathEffect();
		//death effect update;
		
		if(deathEffect.size() != 0) {
			
			for(int i = 0; i < deathEffect.size(); i ++) {
				
				deathEffect.get(i).update();
				
				
			}
		}
		
		
		player.update();
		//player update;
		
		for(int i = 0; i < enemyBoss.size(); i ++) {
			
			enemyBoss.get(i).update();
			
		}
		//enemy boss update;
		
		for(int i = 0; i < bossBullet.size(); i ++) {
			
			bossBullet.get(i).update();
			
		}
		//enemy boss update;
		
		int playerX = player.getx();
		int playerY = player.gety();
		int playerR = player.getr();

		for(int i = 0; i < enemies.size(); i ++) {
			//loop through all the enemies.
			Enemy e = enemies.get(i);
			double ex = e.getx();
			double ey = e.gety();
			double er = e.getr();

			double dx = playerX - ex;
			double dy = playerY - ey;
			double dist = Math.sqrt(dx * dx + dy * dy);

			if(dist < playerR + er) {
				e.hit(5);
				player.getDamage(1);

				break;
			}
		}
		//player-enemy collision
		
		for( int i = 0; i < bullets.size(); i ++) {
			boolean remove = bullets.get(i).update();
			if(remove) {
				bullets.remove(i);
				i --;
			}
		}
		//bullet update;
		
		for( int i = 0; i < powerUps.size(); i ++) {
			boolean remove = powerUps.get(i).update();
			if(remove) {
				powerUps.remove(i);
				i --;
			}
		}
		//powerups update
		
		for(int i = 0 ; i < enemies.size(); i ++) {
			enemies.get(i).update();
		}
		//enemy update
		
		for(int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			for(int j = 0; j < enemies.size(); j ++) {
				Enemy e = enemies.get(j);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();
				
				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < 1.5 * (br + er)) {
					e.hit((int) b.getlife());

					bullets.remove(i);
					BulletDeathEffectGenerator(b);
					i --;

					break;
				}
			}
		}
		//enemies' bullet collision
		
		for(int i = 0; i < bossBullet.size(); i ++) {
			BossBullet b = bossBullet.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			
			double px = player.getx();
			double py = player.gety();
			double pr = player.getr();
			
			double dx = bx - px;
			double dy = by - py;
			
			double dist = Math.sqrt(dx * dx + dy * dy);
			
			if(dist < 1.5 * (br + pr)) {
				
				bossBullet.remove(i);
				
				player.getDamage(1);
				
			}
			
		}
		//bosses' bullet player collision
		
		
		for(int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			
			
			for(int j = 0; j < enemyBoss.size(); j ++) {
				EnemyBoss e = enemyBoss.get(j);
				double ex = e.getx();
				double ey = e.gety();
				double er = e.getr();
				
				double dx = bx - ex;
				double dy = by - ey;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if(dist < 1.5 * (br + er)) {
					e.hit((int) b.getlife());

					bullets.remove(i);
					
					BulletDeathEffectGenerator(b);
					
					i --;
					break;
					//bullet collision
				}
			}
		}
		//enemies' boss & player & player bullet collision
		
		for(int i = 0; i < enemyBoss.size(); i ++) {
			EnemyBoss e = enemyBoss.get(i);
			double ex = e.getx();
			double ey = e.gety();
			double er = e.getr();
			
			
			double px = player.getx();
			double py = player.gety();
			double pr = player.getr();
			
			double ddx = ex - px;
			double ddy = ey - py;
			
			double d = Math.sqrt(ddx * ddx + ddy * ddy);
			
			if(d < 1.5 * (er + pr)) {
				
				player.getDamage(1);
			}
			//player & boss collision
			
			
		}
		
		for(int i = 0; i < powerUps.size(); i++) {
			PowerUp b = powerUps.get(i);
			double bx = b.getx();
			double by = b.gety();
			double br = b.getr();
			
						
			double px = player.getx();
			double py = player.gety();
			double pr = player.getr();
			
			double dx = bx - px;
			double dy = by - py;
			
			double dist = Math.sqrt(dx * dx + dy * dy);
			
			if(dist < 1.5 * (br + pr)) {
				
				int type = b.getType();
				
				switch (type) {
				
				case 1:
					player.addHealth(1);
					player.addAbilityTime(500);
					player.powerupCount(1);
					break;
				
				case 2:
					player.addBullet(1);
					player.powerupCount(2);
					break;
					
				case 3:
					player.addBulletSize(1);
					player.powerupCount(3);
					break;
					
				case 4:
					player.minusDelay(20);
					player.powerupCount(4);
					break;
					
				}
				
				
				powerUps.remove(i);
				
			}
			
			
		}
		//powerup player collision
		
		
		
		
		for(int i = 0; i < enemyBoss.size(); i ++) {
			
			if(enemyBoss.get(i).isDead()){
				
				EnemyBoss e = enemyBoss.get(i);
				
				DeathEffectGenerator(e);
				
				int addScore = e.getType() + e.getRank();
				
				if(addScore < 1) {
					
					Score += 1;
					
				}
				else {
					
					Score += addScore;
					
				}
				//check for dead Bosses.
				
				//check for power up drops;

				
				for(int j = 0; j < 5; j ++) {
					
					double roll = Math.random();
					
					if(roll < 0.01) {powerUps.add(new PowerUp(2,e.getx(), e.gety()));}
					//power up of the weapon.
					else if(roll < 0.3) {powerUps.add(new PowerUp(4,e.getx(), e.gety()));}
					//minus firing delay.
					else if (roll < 0.6){powerUps.add(new PowerUp(3,e.getx(), e.gety()));}
					//bigger bullets.
					else if(roll < 0.2) {powerUps.add(new PowerUp(1,e.getx(), e.gety()));}
					//restore 1 health.
				
				}

				
				//TODO: power ups' maker
				
				enemyBoss.remove(i);
				
			}
		}
		//check dead enemy boss;
		
		for(int i = 0; i < enemies.size(); i ++) {
			
			if(enemies.get(i).isDead()){
				
				Enemy e = enemies.get(i);
				
				int addScore = e.getType() + e.getRank();
				
				if(addScore < 1) {
					
					Score += 1;
					
				}
				else {
					
					Score += addScore;
					
				}
				
				//check for power up drops;
				double roll = Math.random();
				
				if(roll < 0.01) {powerUps.add(new PowerUp(2,e.getx(), e.gety()));}
				//power up of the weapon.
				else if(roll < 0.03) {powerUps.add(new PowerUp(4,e.getx(), e.gety()));}
				//minus firing delay.
				else if (roll < 0.05){powerUps.add(new PowerUp(3,e.getx(), e.gety()));}
				//bigger bullets.
				else if(roll < 0.08) {powerUps.add(new PowerUp(1,e.getx(), e.gety()));}
				//restore 1 health.

				
				//TODO: power ups' maker
				
				enemies.remove(i);
				DeathEffectGenerator(e);
				
			}
		}
		//check dead enemies;
		
		


		for( int i = 0; i < enemies.size(); i ++) {
			boolean remove = enemies.get(i).update();
			if(remove) {
				enemies.remove(i);
				i --;
			}
		}
		//remove the enemies outside the GamePanel.
		
		
		
	}
	
	private void StartMenu() {
		
		if(gameInstruction == true) {
				instructionScreen();
		}
		else {
			g.setFont(new Font(textFont, Font.BOLD, TitleFontSize));
			
			
			String Title = "Kill COVID19!";
			String Hint = "press ENTER to start the Game!";
			String Hint2 = "F1 for more detail about the control!";

			// backup draw background
			g.setColor(Color.black);
			g.fillRect(0,0,WIDTH,HEIGHT);

			g.drawImage(background , 0 ,0, backgroundWidth, backgroundHeight, null);
			//draw background
			
			g.setColor(TextColor);
			g.drawString(Title, CenterTextX(Title), CenterTextY(Title) / 4 );
			
			g.setFont(new Font(textFont, Font.BOLD, subTitleFontSize ));
			
			g.drawString(Hint, CenterTextX(Hint), CenterTextY(Hint) / 2 );
			
			g.drawString(Hint2, CenterTextX(Hint2), CenterTextY(Hint2) / 4 * 3 );
			 
			 
		}
	}
	//start menu.
	
	private void instructionScreen() {

		g.drawImage(background , 0 , bgY_0 , backgroundWidth, backgroundHeight, null);
		g.drawImage(background , 0 , bgY_1 , backgroundWidth, backgroundHeight, null);

		g.setColor(Color.black);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setFont(new Font(textFont, Font.BOLD, TitleFontSize));
		
		String Title = "< Instruction >";
		String hint = "Arrow Keys for moving the player";
		String hint2 = "Space to fire bullet, B for burst mode";
		
		String PowerUps = "Upgrade Instruction";
		String hint3 = "when virus died, you may do something!";
		
		String Type1PowerUp = "wearing masks";
		String Type2PowerUp = "Stay at home";
		String Type3PowerUp = "Sleep";
		String Type4PowerUp = "wash hands";
		
		String Type1PowerUpHint = "restore 1 health & Ability shoot time";
		String Type2PowerUpHint = "add 1 bullet each shoot";
		String Type3PowerUpHint = "make your bullet bigger";
		String Type4PowerUpHint = "shorter delay between each shoot";
				
		
		
		g.setColor(Color.cyan);
		
		///g.fillRect(0,0,WIDTH,HEIGHT);
		g.drawImage(background , 0 ,0, backgroundWidth, backgroundHeight, null);
		//draw background
		
		g.setColor(TextColor);
		g.drawString(Title, CenterTextX(Title), CenterTextY(Title) / 10 );
		
		g.setFont(new Font(textFont, Font.BOLD, textFontSize));
		g.drawString(hint, CenterTextX(hint), CenterTextY(hint) / 7 + 20 );
		g.drawString(hint2, CenterTextX(hint2), CenterTextY(hint2) / 5 + 20 );
		
		g.setFont(new Font(textFont, Font.BOLD, subTitleFontSize));
		g.drawString(PowerUps, CenterTextX(PowerUps), CenterTextY(PowerUps) / 3 );
		
		g.setFont(new Font(textFont, Font.BOLD, textFontSize));
		g.drawString(hint3, CenterTextX(hint3), (int)(CenterTextY(hint3) / 2.5) );
		
		
		//power up1 instruction
		g.setFont(new Font(textFont, Font.BOLD, TitleFontSize));
		g.drawString(Type1PowerUp, CenterTextX(Type1PowerUp) - 40, (int)(CenterTextY(Type1PowerUp) /1.5) );
		
		g.setFont(new Font(textFont, Font.BOLD, subTitleFontSize));
		g.setColor(type1);
		//g.fillOval( (int)(WIDTH / 1.15), (int)(CenterTextY(Type1PowerUp) /1.5 - 20) , powerUpR * 2, powerUpR * 2);
		g.drawImage(wear_mask ,(int)(WIDTH / 1.15), (int)(CenterTextY(Type1PowerUp) /1.5 - 30), powerUpR * 2, powerUpR * 2, null);
		g.setColor(TextColor);
		g.drawString(Type1PowerUpHint, CenterTextX(Type1PowerUpHint), (int)(CenterTextY(Type1PowerUpHint) /1.3) );
		
		//power up2 instruction
		
		g.setFont(new Font(textFont, Font.BOLD, TitleFontSize));
		g.drawString(Type2PowerUp, CenterTextX(Type2PowerUp) - 40, (int)(CenterTextY(Type2PowerUp)) - 20 );
		
		g.setFont(new Font(textFont, Font.BOLD, subTitleFontSize));
		g.setColor(type2);
		//g.fillOval( (int)(WIDTH / 1.15), (int)(CenterTextY(Type2PowerUp) - 40) , powerUpR * 2, powerUpR * 2);
		g.drawImage(stay_home ,(int)(WIDTH / 1.15), (int)(CenterTextY(Type2PowerUp) - 50) , powerUpR * 2, powerUpR * 2, null);
		g.setColor(TextColor);
		g.drawString(Type2PowerUpHint, CenterTextX(Type2PowerUpHint), (int)(CenterTextY(Type2PowerUpHint) /0.9 - 20) );
		
		//power up3 instruction
		
		g.setFont(new Font(textFont, Font.BOLD, TitleFontSize));
		g.drawString(Type3PowerUp, CenterTextX(Type3PowerUp) - 40, (int)(CenterTextY(Type3PowerUp) + (CenterTextY(Type3PowerUp) / 3.5 - 10) ) );

		g.setFont(new Font(textFont, Font.BOLD, subTitleFontSize));
		g.setColor(type3);
		//g.fillOval( (int)(WIDTH / 1.15), (int)(CenterTextY(Type3PowerUp) + (CenterTextY(Type3PowerUp) / 3.5) - 25) , powerUpR * 2, powerUpR * 2);
		g.drawImage(sleep ,(int)(WIDTH / 1.15), (int)(CenterTextY(Type3PowerUp) + (CenterTextY(Type3PowerUp) / 3.5) - 35) , powerUpR * 2, powerUpR * 2, null);
		g.setColor(TextColor);
		
		g.drawString(Type3PowerUpHint, CenterTextX(Type3PowerUpHint), (int)(CenterTextY(Type3PowerUpHint) + (CenterTextY(Type3PowerUpHint) / 2.5) - 10 ) );
		
		//power up4 instruction
		
		g.setFont(new Font(textFont, Font.BOLD, TitleFontSize));
		g.drawString(Type4PowerUp, CenterTextX(Type4PowerUp) - 40, (int)(CenterTextY(Type4PowerUp) + (CenterTextY(Type4PowerUp) / 1.75) ) );
						
		g.setFont(new Font(textFont, Font.BOLD, subTitleFontSize));
		g.setColor(type4);
		//g.fillOval( (int)(WIDTH / 1.15), (int)(CenterTextY(Type4PowerUp) + (CenterTextY(Type4PowerUp) / 1.75) - 15) , powerUpR * 2, powerUpR * 2);
		g.drawImage(wash_hand ,(int)(WIDTH / 1.15), (int)(CenterTextY(Type4PowerUp) + (CenterTextY(Type4PowerUp) / 1.75) - 25) , powerUpR * 2, powerUpR * 2, null);
		g.setColor(TextColor);
		
		g.drawString(Type4PowerUpHint, CenterTextX(Type4PowerUpHint), (int)(CenterTextY(Type4PowerUpHint) + (CenterTextY(Type4PowerUpHint) / 1.5) + 20 ) );
		
	}
	//instruction menu.
	
	private void pauseScreen() {

		//g.setColor(Color.black);
		//g.fillRect(0,0,WIDTH,HEIGHT);

		gameRender();

		g.setFont(new Font(textFont, Font.BOLD, TitleFontSize));
		
		String Title = "< PAUSE >";
		
		g.setColor(Color.cyan);
		
		//g.fillRect(0,0,WIDTH,HEIGHT);
		//g.drawImage(background , 0 ,0, backgroundWidth, backgroundHeight, null);

		//draw background
		
		g.setColor(TextColor);
		g.drawString(Title, CenterTextX(Title), CenterTextY(Title) );
		
		if(Cheat) {
			
			
			g.setColor(Color.RED.brighter());
			g.setFont(new Font(textFont, Font.BOLD, 30));
			g.drawString(cheat, CenterTextX(cheat), HEIGHT / 3);
			
			g.setFont(new Font(textFont, Font.PLAIN, 15));
			
		}
		
		//cheat display

		if(gameInstruction == true) {
			instructionScreen();
		}

	}
	//pause screen.
	
	private void deadScreen() {

		g.setColor(Color.black);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setFont(new Font(textFont, Font.BOLD, TitleFontSize + 10));
		
		g.setColor(Color.cyan);
		
		String Title = "YOU";
		String Text = "DEAD!";
		String Hint = "press R to restart!";
		
		//g.fillRect(0,0,WIDTH,HEIGHT);		
		g.drawImage(background , 0 , bgY_0 , backgroundWidth, backgroundHeight, null);
		g.drawImage(background , 0 , bgY_1 , backgroundWidth, backgroundHeight, null);
		//draw background
		
		g.setColor(TextColor);
		
		g.drawString(Title, CenterTextX(Title), CenterTextY(Title) - 20);
		g.drawString(Text, CenterTextX(Text), CenterTextY(Text)+ 50 );
		
		g.setFont(new Font(textFont, Font.BOLD, textFontSize + 10));
		g.drawString(Hint, CenterTextX(Hint), CenterTextY(Hint)+ 130 );

		g.drawImage(dead_icon ,(int)(WIDTH / 5), 50, 250, 250, null);

		g.drawString("Your Score: " , CenterTextX(Hint), CenterTextY(Hint)+ 200);
		g.drawString("" + (int)Score , CenterTextX(Hint), CenterTextY(Hint)+ 250);

		//draw Score
		
	}
	
	//dead Screen.
	
	private void gameRender() {
		//all the graphics.

		g.setColor(Color.black);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.drawImage(background , 0 , bgY_0 , backgroundWidth, backgroundHeight, null);
		g.drawImage(background , 0 , bgY_1 , backgroundWidth, backgroundHeight, null);
		//draw background
		g.setFont(new Font(textFont, Font.PLAIN, 15));

		g.setColor(TextColor);
		g.drawString("FPS:" + (int)averageFPS, 10, 20);
		//draw FPS
		
		g.setColor(TextColor);
		g.drawString("Score:" + (int)Score, player.getx() - 20, player.gety() + 20);
		//draw Score
		
		
		if(waveStartTime != 0) {
			// start if the generate process began.
			
			g.setFont(new Font(textFont, Font.BOLD, 40));
			//set the font of the string
			
			String s = "< W A V E " + waveNumber + " >";
			//set the context of the string
			
			int length = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
			//total length of the string in pixel.
			
			int alpha = (int)(255 * Math.sin(3.14 * waveStartDelay / waveDelay));
			// transparency of the string
			
			if(alpha > 255) {alpha = 255;}
			//Maximum value of alpha is 255
			
			g.setColor(new Color(189,252,201,alpha));
			//set the color of the string
			
			g.drawString(s, CenterTextX(s),CenterTextY(s));
			//g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);
		
		}
		//draw wave number
		g.setFont(new Font(textFont, Font.PLAIN, 10));
		
		/**
		for(int i = 0; i < player.getLives(); i ++) {
			g.setColor(player.color1);
			//set it as the same color as the player.
			g.fillOval(player.getx() + (20 * i), player.gety() + 50 , player.getr() , player.getr() );
			//
			g.setStroke(new BasicStroke(2));
			//set the stroke;
			g.setColor(player.color1.darker());
			//set the color
			g.drawOval(player.getx() + (20 * i), player.gety() + 50 , player.getr() , player.getr() );
			g.setStroke(new BasicStroke(1));
			//set the Stroke to default after used it :)
		}
		//draw player life
		 * 
		 */
		
		PlayerLifeDrawer playerLifeDrawer = new PlayerLifeDrawer(
				(int)player.getx(), 
				(int)player.gety() + 10 * player.getr(), 
				player.getLives(),
				player.getMaxLives()
				);
		playerLifeDrawer.draw(g);
		
		//health bar for player
		
		AbilityBarDrawer abilityBarDrawer = new AbilityBarDrawer(
				(int)player.getx(), 
				(int)player.gety() + 12 * player.getr(), 
				player.getAbilityTime(),
				player.getMaxAbilityTime()
				);
		abilityBarDrawer.draw(g);
		//ability bar for player
		
		
		if(deathEffect.size() != 0) {
			for(int i = 0; i < deathEffect.size(); i ++) {
				
				deathEffect.get(i).draw(g);
				
				
			}
		}
		//draw death effect;
		
		
		
		//theme render;
		
		
		player.draw(g);
		//player render;
		
		
		
		
		
		for(int i = 0; i < bullets.size(); i ++) {
			bullets.get(i).draw(g);
			
		}
		//bullet render;
		
		
		for(int i = 0; i < powerUps.size(); i ++) {
			powerUps.get(i).draw(g);
			
		}
		//powerups' render;
		
		for(int i = 0; i < enemyBoss.size(); i ++) {
			enemyBoss.get(i).draw(g);
			
		}		
		//Bosses' render
		
		for(int i = 0; i < bossBullet.size(); i ++) {
			bossBullet.get(i).draw(g);
			
		}
		//Bosses' bullet render
		
		for(int i = 0; i < enemies.size(); i ++) {
			enemies.get(i).draw(g);
			
		}
		//enemy render;
		for(int i = 0; i < enemies.size(); i ++) {
			//if (!enemies.get(i).isDead()) {
			Enemy e = enemies.get(i);
				LifeDrawer lifeDrawer=new LifeDrawer(
						(int)e.getr() * 2,
						(int)e.getx(), 
						(int)e.gety(), 
						e.getCurrentLife(),
						e.getMaxLife()
						);
				lifeDrawer.draw(g);
				
				
			//}
			
		}
		
		// enemy health render;
		
		/**
		for(int i = 0; i < enemies.size(); i ++) {
			Enemy e = enemies.get(i);
			g.setColor(Color.RED);
			g.setFont(new Font(textFont, Font.BOLD, 20));
			g.drawString(e.getDamageDisplay(), (int)(e.getx() + 1.2 * e.getr()), (int)(e.gety() - 1.2 * e.getr()));
		}
		//enemies damage display
		 
		 */
		
		
		for(int i = 0; i < enemyBoss.size(); i ++) {
			
			EnemyBoss e = enemyBoss.get(i);
			//if (!enemies.get(i).isDead()) {
				LifeDrawer lifeDrawer=new LifeDrawer(
						(int)e.getr() * 2,
						(int)e.getx(), 
						(int)e.gety(), 
						e.getCurrentLife(),
						e.getMaxLife()
						);
				lifeDrawer.draw(g);
			//}
			
		}
		
		// enemy boss health render;
		
		g.setColor(TextColor);
		g.setFont(new Font(textFont, Font.PLAIN, 15));
		g.drawString("Enemies' number:" + (int)enemies.size(), 10, 40);
		//debug: draw enemies amount
		
		g.setColor(TextColor);
		g.setFont(new Font(textFont, Font.PLAIN, 15));
		g.drawString("power ups' number:" + (int)powerUps.size(), 10, 80);
		//debug: draw enemies amount
		
		g.setColor(TextColor);
		g.setFont(new Font(textFont, Font.PLAIN, 15));
		g.drawString("Bullets' number:" + (int)bullets.size(), 10, 60);
		//debug: draw bullets amount 
		
		g.setColor(TextColor);
		g.setFont(new Font(textFont, Font.PLAIN, 15));
		g.drawString("Death effect block:" + (int)deathEffect.size(), 10, 100);
		//debug: draw Death Effect amount 
		
	}
	
	private void gameDraw() {
		//draw all the Graphics.
		//if(Scaled == false) {g.scale(scale, scale);Scaled = true;}
		//scaling of the window
		
		Graphics g2 = this.getGraphics();
		g2.drawImage(image,0,0,null);
		g2.dispose();
		
	}
	
	public static int getRandom(int range) {
		double roll = Math.random();
		double result;
		double dice = Math.random();
		
		if(dice > 0.5) {
			
			result = roll * range;
			
		}
		else if(dice < 0.5) {
			
			result = roll *( - range);
			
		}
		else {
			
			result = 0;
			
		}
		
		return (int)result;
		
		
	}
	
	
	private void createNewEnemies() {
		
		enemies.clear();
		//to start a new wave, it needed to clean all the old enemies
		//normally it will not(as been checked many times with
		//enemies.size() == 0), but for insurance. 
		Enemy e;
		EnemyBoss b;
		//create a instance of the enemy.
		
		
		
		waveRest = waveNumber % 10;
		waveDevide = waveNumber / 10;
		
		Score += 5;
	    //each waves you will get 5 score bonus.
		//if this method been called,
		//so it means that a new wave started.
		
		
		//enemyBoss.add(new EnemyBoss(waveDevide, waveDevide));
		//enemyBoss.add(new EnemyBoss(waveDevide, waveDevide));
		
		if(waveRest == 0) {
			for(int i = 0; i < 1 + (int)((waveDevide - 1) * Math.random()); i ++) {
				enemyBoss.add(new EnemyBoss(waveNumber * 10, waveDevide));
			}
		}
		//every 10 waves spawn a Boss.

		else {
			for(int i = 0; i < waveRest * 2 + (int)(Math.random() * 100.0); i ++) {
				
				enemies.add(new Enemy(waveDevide * 2, waveRest + waveDevide));
				
			}
		}
		//if spawned a boss, then don't spawn the enemies.
		//fully automatic 

		/**
		if(waveNumber == 1) {
			for(int i = 0; i < 10; i ++) {
				enemies.add(new Enemy(1,1));
				//create 4 enemies with type 1, rank 1; 
			}
			
		}
		// wave 1
		if(waveNumber == 2) {
			for(int i = 0; i < 20; i ++) {
				enemies.add(new Enemy(1,1));
				//create 4 enemies with type 1, rank 1; 
			}
			
		}
		//wave 2
		if(waveNumber == 3) {
			for(int i = 0; i < 40; i ++) {
				enemies.add(new Enemy(1,1));
				//create 4 enemies with type 1, rank 1; 
			}
			
		}
		**/
		
	}
	
	
	public void keyTyped(KeyEvent key) {	
		
		
	};
	
	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_C) {	
			CHEATCHECK("C");
		}
		else if(keyCode == KeyEvent.VK_H) {	
			CHEATCHECK("H");
		}
		else if(keyCode == KeyEvent.VK_E) {	
			CHEATCHECK("E");
		}
		else if(keyCode == KeyEvent.VK_A) {	
			CHEATCHECK("A");
		}
		else if(keyCode == KeyEvent.VK_T) {	
			CHEATCHECK("T");
		}
		else if(keyCode == KeyEvent.VK_O) {	
			CHEATCHECK("O");
		}
		else if(keyCode == KeyEvent.VK_N) {	
			CHEATCHECK("N");
			CHEATCHECK();
		}
		else if(keyCode == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		else if(keyCode == KeyEvent.VK_UP) {
			player.setUp(true);
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			player.setDown(true);
		}
		else if(keyCode == KeyEvent.VK_SPACE) {
			player.setFiring(true);
		}
		else if(keyCode == KeyEvent.VK_ESCAPE) {
			if(pause == false) {
				pause = true;
			}
			else if(pause == true) {
				pause = false;
			}
		}
		else if(keyCode == KeyEvent.VK_ENTER) {
			startGame = true;
		}
		
		else if(keyCode == KeyEvent.VK_R) {
			
			
			replay = true;
		}
		
		else if(keyCode == KeyEvent.VK_F1) {
			
			if(gameInstruction == false) {
				gameInstruction = true;
			}
			else if(gameInstruction == true) {
				gameInstruction = false;
			}
		}
		
		else if(keyCode == KeyEvent.VK_B) {
			player.setAbilityFiringTrue();
		}
		else {
        	
        	CHEATCHECK("N");
        	
        }
		
		
	};
	
	public void keyReleased(KeyEvent key) {
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if(keyCode == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if(keyCode == KeyEvent.VK_UP) {
			player.setUp(false);
		}
		if(keyCode == KeyEvent.VK_DOWN) {
			player.setDown(false);
		}
		if(keyCode == KeyEvent.VK_SPACE) {
			player.setFiring(false);
		}
		
        if(keyCode == KeyEvent.VK_B) {
			
			player.setAbilityFiringFalse();
			
		}
        
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
