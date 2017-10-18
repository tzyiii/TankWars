import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*
;public class Tank {
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
	
	int x;
	int y;
	private boolean bL = false;
	private boolean bR = false;
	private boolean bU = false;
	private boolean bD = false;
	
	private int step = r.nextInt(7) + 3;
	
	enum Direction {
		L, R, U, D, LU, LD, RU, RD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this (x, y, good);
		this.dir = dir;
		this.tc = tc;
	}

	void move() {
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
		if (this.dir != Direction.STOP) {
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
			Direction[] dirs = Direction.values();
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
			dir = Direction.L;
		}
		else if (!bL && bR && !bU && !bD) {
			dir = Direction.R;
		}
		else if (!bL && !bR && bU && !bD) {
			dir = Direction.U;
		}
		else if (!bL && !bR && !bU && bD) {
			dir = Direction.D;
		}
		else if (bL && !bR && bU && !bD) {
			dir = Direction.LU;
		}
		else if (bL && !bR && !bU && bD) {
			dir = Direction.LD;
		}
		else if (!bL && bR && bU && !bD) {
			dir = Direction.RU;
		}
		else if (!bL && bR && !bU && bD) {
			dir = Direction.RD;
		}
		else if (!bL && !bR && !bU && !bD) {
			dir = Direction.STOP;
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
}