package myPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{

	ObjectOutputStream oos;
	ObjectInputStream in;
	Socket socket;
	GamePanel parent;
	boolean f;

	public Client(GamePanel inputParent) {
		f = false;
		try {
			socket = new Socket(InetAddress.getByName("asus"), 4200);
			in = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			this.parent = inputParent;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int i = 0;
		Object o;
		while (true){
			try {
				try {
					o = in.readObject();
					if(o instanceof int[][]){
						parent.setGameMatrix((int[][])o);
						synchronized (parent) {
							parent.notify();
						}
					}else{
						if(o instanceof Integer){
							parent.setMyCountryID((int)o);
						}else if(o instanceof String){
							stringParse((String)o);
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(i == -1)
					continue;
				else{
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stringParse(String s){
		String[] splitted;
		splitted = s.split("[-]+");
		String actionType = splitted[0];
		if(actionType.equals("laborMove")){
			if(parent.getMyCountryID()!=Integer.parseInt(splitted[1])){
				try{
					Labor tempLbr = parent.getCountries().elementAt(Integer.parseInt(splitted[2])).getLabors().elementAt(Integer.parseInt(splitted[3]));
					int targetX = Integer.parseInt(splitted[4]);
					int targetY = Integer.parseInt(splitted[5]);
					tempLbr.setRunInput(1);
					tempLbr.setTarget(targetX, targetY);
					(new Thread(tempLbr)).start();
				}catch(ArrayIndexOutOfBoundsException e){
					
				}
			}else{
				System.out.println(parent.getMyCountryID()+":"+splitted[1]);
			}
		}else if(actionType.equals("basePlace")){
			parent.setOtherBaseX(Integer.parseInt(splitted[1]));
			parent.setOtherBaseY(Integer.parseInt(splitted[2]));
			parent.setOtherBaseDirection(Integer.parseInt(splitted[3]));
			f = true;
		}else if(actionType.equals("createlabor")){
			Country tmp = parent.getCountries().elementAt(Integer.parseInt(splitted[1]));
			tmp.createLabor();
		}else if(actionType.equals("laborFight")){
//			"countryID-laborID-countryID-laborID"
			if(parent.getMyCountryID()!=Integer.parseInt(splitted[1])){
				Labor me = parent.getCountries().elementAt(Integer.parseInt(splitted[2])).getLabors().elementAt(Integer.parseInt(splitted[3]));
				Labor opponent = parent.getCountries().elementAt(Integer.parseInt(splitted[2])).getLabors().elementAt(Integer.parseInt(splitted[3]));
				me.setRunInput(2);
				me.setTarget(opponent);
				(new Thread(me)).start();				
			}
		}else if(actionType.equals("backToBase")){
			if(parent.getMyCountryID()!=Integer.parseInt(splitted[1])){
				Labor l1 = parent.getCountries().elementAt(Integer.parseInt(splitted[2])).getLabors().elementAt(Integer.parseInt(splitted[3]));
				l1.setRunInput(5);
				(new Thread(l1)).start();
			}
		}else if(actionType.equals("logging")){
			Labor tempLbr1 = parent.getCountries().elementAt(Integer.parseInt(splitted[1])).getLabors().elementAt(Integer.parseInt(splitted[2]));
			Tree tempTree1 = parent.getTrees().elementAt(Integer.parseInt(splitted[3]));
			tempLbr1.setRunInput(3);
			tempLbr1.setTarget(tempTree1);
			(new Thread(tempLbr1)).start();
		}else if(actionType.equals("mining")){
			Labor tempLbr2 = parent.getCountries().elementAt(Integer.parseInt(splitted[1])).getLabors().elementAt(Integer.parseInt(splitted[2]));
			IronMine tempIM = parent.getMines().elementAt(Integer.parseInt(splitted[3]));
			tempLbr2.setRunInput(4);
			tempLbr2.setTarget(tempIM);
			(new Thread(tempLbr2)).start();
		}else if(actionType.equals("fishing")){
			FishingShip tempShip1 = parent.getCountries().elementAt(Integer.parseInt(splitted[1])).getFShips().elementAt(Integer.parseInt(splitted[2]));
			Fish tempF = parent.getFishes().elementAt(Integer.parseInt(splitted[3]));
			tempShip1.setRunInput(2);
			(new Thread(tempShip1)).start();
		}else if(actionType.equals("shipFightingWS")){
			WarShip ws1 = parent.getCountries().elementAt(Integer.parseInt(splitted[1])).getWarShips().elementAt(Integer.parseInt(splitted[2]));
			WarShip ws2 = parent.getCountries().elementAt(Integer.parseInt(splitted[3])).getWarShips().elementAt(Integer.parseInt(splitted[4]));
			ws1.setRunInput(2);
			ws1.setOpponent(ws2);
			(new Thread(ws1)).start();
		}else if(actionType.equals("shipFightingTS")){
			
		}else if(actionType.equals("shipFightingFS")){
			
		}else if(actionType.equals("shipMoving")){
			
		}else{
			
		}
	}
}