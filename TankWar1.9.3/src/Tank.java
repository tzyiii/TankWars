import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class Tank {
	int ID;
	
	private static int XSPEED = 5;
	private static int YSPEED = 5;
	private static int WIDTH = 30;
	private static int HEIGHT = 30;
	TankClient tc;
	private boolean good;
	
	public boolean isGood() {
		return good;
	}

	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	private static Random r = new Random();
	
	private int x;
	private int y;
	private int oldX;
	private int oldY;
	
	private boolean bL = false;
	private boolean bR = false;
	private boolean bU = false;
	private boolean bD = false;
	
	private int step = r.nextInt(7) + 3;
	
	

	private Dir dir = Dir.STOP;
	private Dir ptDir = Dir.D;

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.oldX = x;
		this.oldY = y; 
	}
	
	public Tank(int x, int y, boolean good, Dir dir, TankClient tc) {
		this (x, y, good);
		this.dir = dir;
		this.tc = tc;
	}

	void move() {
		this.oldX = x;
		this.oldY = y;
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case STOP:
			break;  
		}
		if (this.dir != Dir.STOP) {
			ptDir = dir; 
		}
		if (x < 0) {
			x = 0;
		}
		if (y < 20) {
			y = 20;
		}
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH) {
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		}
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT) {
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		}
		
		if (!good) {
			Dir[] dirs = Dir.values();
			if (step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn]; 
			}
			if (r.nextInt(40) > 38) {
				tc.missiles.add(this.fire());
			}
			step--;
		}
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if (good) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.blue);
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.drawString("id: " + ID, x, y - 10);
		g.setColor(c);
		switch (ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT / 2);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + WIDTH, y + Tank.HEIGHT / 2);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + WIDTH/2, y );
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + WIDTH/2, y + Tank.HEIGHT);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y );
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + WIDTH, y + Tank.HEIGHT);
			break;
		}
		move();
		
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}

	void locateDirection() {
		if (bL && !bR && !bU && !bD) {
			dir = Dir.L;
		}
		else if (!bL && bR && !bU && !bD) {
			dir = Dir.R;
		}
		else if (!bL && !bR && bU && !bD) {
			dir = Dir.U;
		}
		else if (!bL && !bR && !bU && bD) {
			dir = Dir.D;
		}
		else if (bL && !bR && bU && !bD) {
			dir = Dir.LU;
		}
		else if (bL && !bR && !bU && bD) {
			dir = Dir.LD;
		}
		else if (!bL && bR && bU && !bD) {
			dir = Dir.RU;
		}
		else if (!bL && bR && !bU && bD) {
			dir = Dir.RD;
		}
		else if (!bL && !bR && !bU && !bD) {
			dir = Dir.STOP;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SPACE:
			tc.missiles.add(fire());
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}
	
	public Missile fire() {
		if (!live) {
			return null;
		}
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x, y, this.good, ptDir,  this.tc);
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean collidesWithWall(Wall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	private void stay () {
		x = oldX;
		y = oldY;
	}
	
}