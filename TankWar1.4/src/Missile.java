import java.awt.*;

public class Missile {
	int x;
	int y;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	Tank.Direction dir;

	private boolean Live = true;
	private TankClient tc;
	public boolean isLive() {
		return Live;
	}

	public Missile(int x, int y, Tank.Direction ptDir) {
		this.x = x;
		this.y = y;
		this.dir = ptDir;
	}
	public Missile(int x, int y, Tank.Direction ptDir, TankClient tc) {
		this(x, y, ptDir);
		this.tc = tc;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}

	private void move() {
		if (!Live) {
			tc.missiles.remove(this);
			return;
		}
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

		}
		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			Live = false;
			//tc.missiles.remove(this);
		}
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	public boolean hitTank(Tank t) {
		if (this.getRect().intersects(t.getRect()) && t.isLive()) {
			this.Live = false;
			t.setLive(false);
			return true; 
		}
		return false;
	}
}
