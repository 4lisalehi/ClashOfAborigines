package myPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable{
	
	Integer playerCounter;
	ServerSocket ss;
	Vector<WorkStation> myWorkstations;
	GamePanel parent;
	Vector<String> clientIPList;
	Socket client;

	public Server(int playerCounter, GamePanel myGame) {
		parent = myGame;
		myWorkstations = new Vector<WorkStation>();
		this.playerCounter = playerCounter;
		try {
			ss = new ServerSocket(4200, playerCounter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while (playerCounter > myWorkstations.size()) {
			try {
				client = ss.accept();
				System.out.println(ss.getInetAddress() + " Joined!");
				WorkStation w = new WorkStation(client, this);
				myWorkstations.add(w);
				Integer a = myWorkstations.size() - 1;
				w.oos.writeObject(a);
				w.start();
				sendClientList();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			sendObjectToAll(parent.getGameMatrix());
	}

	public synchronized void sendObjectToAll(Object msg){
		for (WorkStation w : myWorkstations) {
			try {
				w.oos.writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendToOthers(WorkStation ws,String ss){
		for(WorkStation v : myWorkstations){
			if(v != ws){
				try{
					v.oos.writeObject(ss);
				}catch(IOException e){
					e.printStackTrace();
				}				
			}
		}
	}

	public void sendClientList() {
		clientIPList = new Vector<String>();
		for (WorkStation ws : myWorkstations){
			clientIPList.add(ws.getClientIP());
		}
	}
}