import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame {
	int x = 50;
	int y = 50;
	 
	Image offScreenImage = null;
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(800, 600);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, 800, 600);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null );
	}
	
	
	public void paint (Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		x += 4;
		y += 4;
	}
	public void LaunchFrame() {
		this.setLocation(240, 100);
		this.setSize(800, 600);
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		} );
		this.setTitle("Tank War");
		this.setResizable(false);
		this.setBackground(Color.green);
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
					Thread.sleep(20);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			
		}
	}
}

