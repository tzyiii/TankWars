import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetClient {
	TankClient tc;
	private static int UDP_PORT_START = 2223;
	private int udpPort = 100;
	
	
	
	public NetClient() {
		udpPort = UDP_PORT_START++;
		this.tc = tc;
	}

	public void connect(String IP, int port) {
		Socket s = null;
		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int ID = dis.readInt();
			tc.myTank.ID = ID;
			System.out.println("Connected to server ! and server give me a ID :" + ID);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
			try {
				s.close();
				} catch (IOException e){
				e.printStackTrace();
			}
			}
		}
		System.out.println("Connected to server");
	}
}

