import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(50, 50, true, Tank.Direction.STOP, this);
	
	List<Explosion> explodes = new ArrayList<>();
	List <Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	//Missile m = null;
	Image offScreenImage = null;

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void paint(Graphics g) {
		g.drawString("Missiles Count: " + missiles.size(), 60, 60);
		g.drawString("Exlplosion Count" + explodes.size(), 220, 60);
		g.drawString("Tanks Count" + tanks.size(), 480, 60);
		
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			//if (!m.isLive()) {
			//	missiles.remove(m);
			//}
			m.hitTanks(tanks);
			m.hitTank(myTank);  
			m.draw(g);
		}
		for(int i = 0; i < explodes.size(); i++) {
			Explosion e = explodes.get(i);
			e.draw(g);
		}
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
		}
		myTank.draw(g);
	}

	public void LaunchFrame() {
		for (int i = 0; i < 10; i++) {
			tanks.add(new Tank(50 + 40 * (i + 1), 50 , false, Tank.Direction.D, this));
		}
		
		
		this.setLocation(240, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		this.setTitle("Tank War");
		this.setResizable(false);
		this.setBackground(Color.green);
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.LaunchFrame();
	}

	private class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private class KeyMonitor extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
	}
}
