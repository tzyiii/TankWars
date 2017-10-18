import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	Tank myTank = new Tank(50, 50);

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
		myTank.draw(g);
	}

	public void LaunchFrame() {
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
					Thread.sleep(15);
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