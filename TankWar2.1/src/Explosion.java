import java.awt.*;
public class Explosion {
	int x;
	int y;
	private boolean live = true;
	int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 24, 6};//the explosion diameter
	int step = 0;       //which step of explosion are you in
	private TankClient tc;
	
	public Explosion(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc; 
	}
	
	public void draw(Graphics g) {
		if (!live) {
			tc.explodes.remove(this);
			return;
		}
		if (step == diameter.length) {
			live = false;
			step = 0;
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}
}
