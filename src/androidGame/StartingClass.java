package androidGame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import androidGame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	/**By Jesi Vance (with contributions from the Internet. Thank you StackOverflow!)
	 * 5/01/15
	 * Bare Bones of code provided by http://www.kilobolt.com/. Heavily edited by Le Me
	 * Thank you to Kevin Ryan for help with the Sound class and to both he and Francis King for 
	 * help with the Sound of Death 
	 */
	private static final long serialVersionUID = 1L;

	enum GameState {
		Running, Dead, Win
	}

	GameState state = GameState.Running;

	private static Robot robot;
	public static Heliboy hb, hb2, hb3, hb4, hb5, hb6, hb7, hb8, hb9, hb10,
			hb11, hb12, hb13, hb14, hb15, hb16;
	public static int score = 0;
	public static int rHealth = 120;
	private Font font = new Font(null, Font.BOLD, 30);

	private Image image, currentSprite, character, character2, character3,
			characterDown, characterJumped, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;

	public static Image tilegrassTop, tilegrassBot, tilegrassLeft,
			tilegrassRight, tiledirt;

	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation anim, hanim;
	int level = 1;
	String levelSelect;

	private ArrayList<Tile> tilearray = new ArrayList<Tile>();

	@Override
	public void init() {		

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Robot Mania");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/character.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");

		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");

		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");
		
		Random rBackGround = new Random();
		int backGround = rBackGround.nextInt(4);

		if (backGround == 0) {
			levelSelect = "lava";
		} else if (backGround == 1) {
			levelSelect = "space";
		} else if (backGround == 2) {
			levelSelect = "trees";
		} else {
			levelSelect = "hills";
		}		
		background = getImage(base, "data/" + levelSelect + ".png");

		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		tilegrassBot = getImage(base, "data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "data/tilegrassleft.png");
		tilegrassRight = getImage(base, "data/tilegrassright.png");
		
		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		currentSprite = anim.getImage();
	}

	@Override
	public void start() {

		Sound.MAIN.loop();

		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);

		robot = new Robot();

		// Initialize Tiles

		Random rLevel = new Random();
		int level = rLevel.nextInt(4) + 1;

		try {
			loadMap("data/map" + level + ".txt");
			//loadMap("data/map4.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Random random = new Random();
		int randomInt = random.nextInt(500);

		hb = new Heliboy(700, 360);
		hb2 = new Heliboy(900 + randomInt, 360);
		hb3 = new Heliboy(2300 + randomInt, 360);
		hb4 = new Heliboy(2900 + randomInt, 360);
		hb5 = new Heliboy(3400 + randomInt, 360);
		hb6 = new Heliboy(3900 + randomInt, 360);
		hb7 = new Heliboy(4300 + randomInt, 360);
		hb8 = new Heliboy(4700 + randomInt, 360);
		hb9 = new Heliboy(5000 + randomInt, 360);
		hb10 = new Heliboy(5300 + randomInt, 360);
		hb11 = new Heliboy(5700 + randomInt, 360);
		hb12 = new Heliboy(6000 + randomInt, 360);
		hb13 = new Heliboy(6300 + randomInt, 360);
		hb14 = new Heliboy(6700 + randomInt, 360);
		hb15 = new Heliboy(7000 + randomInt, 360);
		hb16 = new Heliboy(7200 + randomInt, 360);

		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}

	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			while (true) {

				robot.update();
				if (robot.isJumped()) {
					currentSprite = characterJumped;
				} else if (robot.isJumped() == false
						&& robot.isDucked() == false) {

					currentSprite = anim.getImage();
				}

				ArrayList<?> projectiles = robot.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = (Projectile) projectiles.get(i);
					if (p.isVisible() == true) {
						p.update();

					} else {
						projectiles.remove(i);
					}
				}

				updateTiles();
				hb.update();
				hb2.update();
				hb3.update();
				hb4.update();
				hb5.update();
				hb6.update();
				hb7.update();
				hb8.update();
				hb9.update();
				hb10.update();
				hb11.update();
				hb12.update();
				hb13.update();
				hb14.update();
				hb15.update();
				hb16.update();
				bg1.update();
				bg2.update();
				animate();
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (rHealth == 0) {
					state = GameState.Dead;
					Sound.MAIN.stop();
					Sound.DIE.play();
				}

				if (robot.getCenterY() > 500) {
					state = GameState.Dead;
					robot.setCenterY(0);
					robot.setCenterX(0);
					Sound.MAIN.stop();
					Sound.DIE.play();
				}

				if (score == 45) {
					state = GameState.Win;
					score = 50;
					Sound.MAIN.stop();
					Sound.WIN.play();
				}
			}
		}
	}

	public void animate() {
		anim.update(10);
		hanim.update(50);
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

		if (state == GameState.Running) {

			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			paintTiles(g);

			ArrayList<?> projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				g.setColor(Color.BLUE);
				g.fillRect(p.getX(), p.getY(), 10, 5);
			}

			g.drawImage(currentSprite, robot.getCenterX() - 61,
					robot.getCenterY() - 63, this);
			g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
					hb.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
					hb2.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb3.getCenterX() - 48,
					hb3.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb4.getCenterX() - 48,
					hb4.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb5.getCenterX() - 48,
					hb5.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb6.getCenterX() - 48,
					hb6.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb7.getCenterX() - 48,
					hb7.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb8.getCenterX() - 48,
					hb8.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb9.getCenterX() - 48,
					hb9.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb10.getCenterX() - 48,
					hb10.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb11.getCenterX() - 48,
					hb11.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb12.getCenterX() - 48,
					hb12.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb13.getCenterX() - 48,
					hb13.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb14.getCenterX() - 48,
					hb14.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb15.getCenterX() - 48,
					hb15.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb16.getCenterX() - 48,
					hb16.getCenterY() - 48, this);

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Health: " + Integer.toString(rHealth), 5, 30);
			g.drawString("Score: " + Integer.toString(score), 650, 30);

		} else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("You're Dead!", 300, 200);
			g.drawString("Score: " + score, 300, 250);

		} else if (state == GameState.Win) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("You Beat this level!", 300, 200);
			g.drawString("Score: " + score, 300, 250);
		}
	}

	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}
	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (robot.isJumped() == false) {
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			robot.jump();
			break;

		case KeyEvent.VK_CONTROL:
			if (robot.isReadyToFire()) {
				robot.shoot();
				robot.setReadyToFire(false);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = anim.getImage();
			robot.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			robot.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			break;

		case KeyEvent.VK_CONTROL:
			robot.setReadyToFire(true);
			Sound.GUN.play();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Robot getRobot() {
		return robot;
	}
}
