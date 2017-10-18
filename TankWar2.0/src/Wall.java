import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;

public class Wall {
	int x;
	int y;
	int w;
	int h; 
	
	TankClient tc;

	public Wall(int x, int y, int w, int h, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	public void draw (Graphics g) {
		g.fillRect(x, y, x, h);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h); 
	}
}
