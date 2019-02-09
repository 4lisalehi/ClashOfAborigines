package myPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorkStation extends Thread {

	Server s;
	Socket client;
	ObjectOutputStream oos;
	ObjectInputStream in;

	public WorkStation(Socket client, Server s) {
		this.client = client;
		this.s = s;
		try {
			oos = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Object i = null;
		while (true) {
			try {
				i = in.readObject();
				if (i == null) {
					continue;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			s.sendToOthers(this, (String)i);
		}
	}
	public String getClientIP () {
		return client.getInetAddress().toString();
	}
}
