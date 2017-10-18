import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
	private static int XSPEED = 5;
	private static int YSPEED = 5;

	int x;
	int y;
	private boolean bL = false;
	private boolean bR = false;
	private boolean bU = false;
	private boolean bD = false;

	enum Direction {
		L, R, U, D, LU, LD, RU, RD, STOP
	};

	private Direction dir = Direction.STOP;

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
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
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		move();
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
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
	public Missile void fire() {
		Missile m = new Missile(x, y, dir);
	}
}