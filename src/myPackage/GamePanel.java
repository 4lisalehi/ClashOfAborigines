package myPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JPanel;



import com.sun.javafx.scene.control.SelectedCellsMap;


//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;
import interfaces.Data;

public class GamePanel extends JPanel implements Runnable, Data {

	private static final long serialVersionUID = 1L;

	Client gameClient;

	private Tile[][] gameMap;
	private int[][] gameMatrix;
	private int[][] gameForeMap;
	private Labor selectedLabor;
	private WarShip selectedWarship;
	private TransportShip selectedTransportShip;
	private FishingShip selectedFishingShip;
	protected int matrixWidth = 29, matrixHeight = 16;
	private int cnt;
	int x0, y0;
	int mapsize;
	GameFrame parent;
	StartMenu parentST;
	Country myCountry;
	private int myCountryID;
	private int otherBaseX,otherBaseY;
	private int otherBaseDirection;
	private Vector<Country> countries;
	private Vector<Tree> trees;
	private Vector<IronMine> mines;
	private Vector<Fish> fishes;
	private Island[][] islands;
//	private List<Pair<Integer,Integer>> beaches = new ArrayList<Pair<Integer,Integer>>();
	private Vector<Integer> beachesX;
	private Vector<Integer> beachesY;
	private Timer gameTimer;
	private TimerTask showTime;
	private TimerTask changeSeason;
//	private TimerTask checkNature;
	private TimerTask incrementWoods;
	private boolean isServer;
	int seasonCounter;
	Dimension dim;
	public SidePanel overallSidePanel;

	public int seconds;
	public int minutes;
	public int hours;
	
	public GamePanel(GameFrame gf, Tile[][] inputMap, int countryCount,StartMenu st,boolean inputServer) {
		//Labor : 1, Transport Ship : 2, Warship : 3 , FShip : 4, Fish : 5
		parent = gf;
		parentST = st;
		gameMap = inputMap;

		hours = 0;
		minutes = 0;
		seconds = 0;
		seasonCounter = 1;
//		spring : 1 , summer : 2 , fall : 3 , winter : 4
//		season = 1;
		overallSidePanel = parentST.parentSP;
		trees = new Vector<Tree>();
		mines = new Vector<IronMine>();
		fishes = new Vector<Fish>();
		islands = new Island[gameMap.length][gameMap[0].length];
		dim = Toolkit.getDefaultToolkit().getScreenSize();

		gameForeMap = new int[gameMap.length][gameMap[0].length];
		gameMatrix = new int[gameMap.length][gameMap[0].length];
		mapsize = (gameMap.length/matrixWidth);
		for(int i=0;i<gameForeMap.length;i++){
			for(int j=0;j<gameForeMap[0].length;j++){
				gameForeMap[i][j] = 0;
			}
		}

		selectedLabor = null;
		selectedWarship = null;
		selectedTransportShip = null;
		gameTimer = new Timer();
		showTime = new TimerTask(){
			@Override
			public void run() {
				seconds++;
				if(seconds == 60){
					minutes++;
					seconds = 0;
				}
				if(minutes == 60){
					hours++;
					minutes = 0;
				}
			}
		};

		changeSeason = new TimerTask(){
			@Override
			public void run() {
				seasonCounter++;
				for (int i = 1; i < gameMap.length - 1; i++) {
					for (int j =1; j < gameMap[0].length - 1; j++) {
						gameMap[i][j].setSeason(seasonCounter%4 + 1);
					}
				}
//				if (seasonCounter % 4 == 0) {
//					seasonCounter = 0;
//				}
			}
		};

		incrementWoods = new TimerTask(){
			@Override
			public void run() {
				for(Tree t : trees){
					t.increaseWoods();
				}
			}
		};

/*		incrementIrons = new TimerTask(){
			@Override
			public void run() {
				
			};
		};*/

		
		otherBaseX =0;
		otherBaseY=0;
		otherBaseDirection = -1;
		cnt = 0;
		x0 = 1;
		y0 = 1;

		for (int i = 0; i < gameMatrix.length; i++) {
			for (int j = 0; j < gameMatrix[0].length; j++) {
				gameMatrix[i][j] = gameMap[i][j].getTopLayer();
				if(gameMap[i][j].getTopLayer()==3){
					trees.add(new Tree(i,j));
				}else if(gameMap[i][j].getTopLayer()==5){
					fishes.add(new Fish(i,j,true));
				}else if(gameMap[i][j].getTopLayer()==6){
					fishes.add(new Fish(i,j,false));
				}else if(gameMap[i][j].getLayers()[2]==4 && gameMap[i + 1][j].getLayers()[2]==4 && gameMap[i][j+1].getLayers()[2]==4 && gameMap[i+1][j+1].getLayers()[2]==4){
					mines.add(new IronMine(i,j));
				}
			}
		}

/*		for(int l=1;l<gameMatrix.length-1;l++){
			for(int m=1;m<gameMatrix[0].length-1;m++){
				if(isBeach(l, m)){
					beachesX.add(l);
					beachesY.add(m);
				}
			}
		}*/

		for(int i=1;i<gameMap.length-1;i++){
			for(int j=1;j<gameMap[i].length-1;j++){
				if(gameMap[i][j].getLayers()[1]==2){
					islands[i][j] = new Island(checkNeighbors(i, j));	
				}else{
					islands[i][j] = new Island(-1);
				}
			}
		}

		for (int i = 1; i < gameMatrix.length; i++) {
			for (int j = 1; j < gameMatrix[0].length; j++) {
				if (gameMap[i][j].getTopLayer() == 2&& (gameMap[i - 1][j].getTopLayer() == 1 || gameMap[i-1][j].getTopLayer() == 0) && (gameMap[i][j - 1].getTopLayer() == 1 || gameMap[i][j-1].getTopLayer() == 0)) {
					cnt++;
					checkIslands(i, j);
				}
			}
		}

		
		
		isServer = inputServer;

		if(isServer){
			System.out.println("isServer");
			(new Thread(new Server(countryCount,this))).start();
			gameClient = new Client(this);
			(new Thread(gameClient)).start();
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			};
		}else{
			System.out.println("isnt Server");
			gameClient = new Client(this);
			(new Thread(gameClient)).start();
			try {
				synchronized (this) {
					wait();
				};
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		gameTimer.schedule(showTime, 0, 1000);
		gameTimer.schedule(changeSeason, 0, 10000);
		gameTimer.schedule(incrementWoods, 0, YEAR_SECONDS);
		countries = new Vector<Country>();
		Country tempCountry;
		for(int p=0;p<countryCount;p++){
			tempCountry = new Country(this);
			tempCountry.setCountryID(p);
			if(isServer){
				tempCountry.setBasePlace();
				String tempStr = ("basePlace-"+tempCountry.getBaseX()+"-"+tempCountry.getBaseY()+"-"+tempCountry.getBaseDirection());
				try {
					gameClient.oos.writeObject(tempStr);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(tempCountry.getBaseX()+" ");
			}else{
				while(otherBaseX==0 || otherBaseY==0 || otherBaseDirection==-1){
					System.out.println("waiting");
				}
				tempCountry.setBaseDirection(otherBaseDirection);
				tempCountry.setBasePlace(otherBaseX,otherBaseY);
			}
			countries.add(tempCountry);
			if(myCountryID==p){
				myCountry = tempCountry;
			}
			System.out.println(otherBaseX+" "+otherBaseY+"|" +tempCountry.getBaseX()+" "+tempCountry.getBaseY());
		}
		for(Country tc : countries){
			System.out.println(tc.getCountryID());
		}
		

		//Labor : 1, Transport Ship : 2, Warship : 3 , FShip : 4, BigFish : 5,SmallFish : 6,Base : 7 		
		for(Country c : countries){
			
			gameForeMap[c.getBaseX()][c.getBaseY()] = 7;
			
			for(Labor l : c.getLabors()){
				gameForeMap[l.getX()][l.getY()] = 1;
			}

			for(TransportShip ts : c.getTransportShips()){
				gameForeMap[ts.getX()][ts.getY()] = 2;
			}

			for(WarShip ws : c.getWarShips()){
				gameForeMap[ws.getX()][ws.getY()] = 3;
			}

			for(FishingShip fs : c.getFShips()){
				gameForeMap[fs.getX()][fs.getY()] = 4;
			}
		}

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim.width, dim.height);
		setLocation(0, 0);
		setVisible(true);

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				switch(e.getModifiers()){
					case InputEvent.BUTTON1_MASK:
						Labor tmp = null;
						WarShip tmp1 = null;
						TransportShip tmp2 = null;
						boolean tmp3 = false;
						boolean tmp4 = false;
						for(Tree t : trees){
							if((e.getX()/50) == t.getX()-x0 && (e.getY()/50) == t.getY()-y0){
								tmp3 = true;
								overallSidePanel.switchToTreePanel(t);
								break;
							}else{
								if(overallSidePanel!=null)
									overallSidePanel.switchToDefault(myCountry);
							}
						}
						if(tmp3)
							break;
						for(IronMine im : mines){
							if(((e.getX()/50) == im.getX()-x0 && (e.getY()/50) == im.getY()-y0) || ((e.getX()/50) == im.getX()-x0+1 && (e.getY()/50) == im.getY()-y0) || ((e.getX()/50) == im.getX()-x0+1 && (e.getY()/50) == im.getY()-y0+1) || ((e.getX()/50) == im.getX()-x0 && (e.getY()/50) == im.getY()-y0+1)){
								tmp4 = true;
								overallSidePanel.switchToMinePanel(im);
								break;
							}else{
								if(overallSidePanel!=null)
									overallSidePanel.switchToDefault(myCountry);
							}
						}
						if(tmp4)
							break;
						for(Country c : countries){
							if(c==myCountry){
								for(Labor l : c.getLabors()){
									if((e.getX()/50) == l.getX()-x0 && (e.getY()/50) == l.getY()-y0){
										selectedLabor = l;
										tmp = l;
										overallSidePanel.switchToLaborPanel(l);
										break;
									}else{
										selectedLabor = null;
										overallSidePanel.switchToDefault(myCountry);
									}
								}
								if(tmp!=null)
									break;
								if(((e.getX()/50) == c.getBaseX()-x0 && (e.getY()/50) == c.getBaseY()-y0) || ((e.getX()/50) == c.getBaseX()+1-x0 && (e.getY()/50) == c.getBaseY()-y0) || ((e.getX()/50) == c.getBaseX()+1-x0 && (e.getY()/50) == c.getBaseY()+1-y0) || ((e.getX()/50) == c.getBaseX()-x0 && (e.getY()/50) == c.getBaseY()+1-y0)){
									overallSidePanel.switchToBasePanel(c);
									break;
								}else{
									if(overallSidePanel!=null)
										overallSidePanel.switchToDefault(myCountry);
								}

								
								for(WarShip ws : c.getWarShips()){
									if((e.getX()/50) == ws.getX()-x0 && (e.getY()/50) == ws.getY()-y0){
										ws.setSelected();
										selectedWarship = ws;
										tmp1 = ws;
										overallSidePanel.switchToWarshipPanel(ws);
										break;
									}else{
										selectedWarship = null;
										overallSidePanel.switchToDefault(myCountry);
									}
								}
								if(tmp1!=null)
									break;
								for(TransportShip ts : c.getTransportShips()){
									if((e.getX()/50) == ts.getX()-x0 && (e.getY()/50) == ts.getY()-y0){
										ts.setSelected();
										selectedTransportShip = ts;
										tmp2 = ts;
										overallSidePanel.switchToTShipPanel(ts);
										break;
									}else{
										selectedTransportShip = null;
										overallSidePanel.switchToDefault(myCountry);
									}
								}
								if(tmp2 != null)
									break;
								for(FishingShip fs : c.getFShips()){
									if((e.getX()/50) == fs.getX()-x0 && (e.getY()/50) == fs.getY()-y0){
//										fs.setSelected();
										selectedFishingShip = fs;
//										tmp2 = fs;
										overallSidePanel.switchToFShip(fs);
										break;
									}else{
										selectedFishingShip = null;
										overallSidePanel.switchToDefault(myCountry);
									}
								}
							}
						}
						break;
					case InputEvent.BUTTON3_MASK:
						if(selectedLabor != null){
							String commandStr = null;
							boolean tmpp = false;
							Labor tempLabor = selectedLabor;

							for(Country c : countries){
								if((c.getBaseX()-x0==(e.getX()/50) && c.getBaseY()-y0==(e.getY()/50)) || (c.getBaseX()-x0+1==(e.getX()/50) && c.getBaseY()-y0==(e.getY()/50)) || (c.getBaseX()-x0+1==(e.getX()/50) && c.getBaseY()-y0+1==(e.getY()/50)) || (c.getBaseX()-x0==(e.getX()/50) && c.getBaseY()-y0+1==(e.getY()/50))){
									if(c==tempLabor.getCountry()){
										if(c.getInBaseLabor().size()<5){
											commandStr = ("backToBase-"+getMyCountryID()+"-"+countries.indexOf(c)+"-"+tempLabor.getCountry().getLabors().indexOf(tempLabor));
/*											try {
												gameClient.oos.writeObject(commandStr);
											} catch (IOException e1) {
												e1.printStackTrace();
											}*/
											tempLabor.setRunInput(5);
											(new Thread(tempLabor)).start();
										}
									}
									tmpp = true;
									break;
								}
								//check if opponent exists and fight...
								for(Labor l : c.getLabors()){
									if(l.getX()-x0==(e.getX()/50) && l.getY()-y0==(e.getY()/50)){
										commandStr = ("laborFight-"+getMyCountryID()+"-"+countries.indexOf(tempLabor.getCountry())+"-"+tempLabor.getCountry().getLabors().indexOf(tempLabor)+"-"+countries.indexOf(l.getCountry())+"-"+l.getCountry().getLabors().indexOf(l));
										tempLabor.setRunInput(2);
										tempLabor.setTarget(l);
										(new Thread(tempLabor)).start();
										tmpp = true;
										break;
									}
								}
								if(tmpp)
									break;
							}
							if(tmpp)
								break;
							//if tree exists
							for(Tree t : trees){
								if(t.getX()-x0==(e.getX()/50) && t.getY()-y0==(e.getY()/50)){
									commandStr = ("logging-"+getMyCountryID()+"-"+countries.indexOf(tempLabor.getCountry())+"-"+tempLabor.getCountry().getLabors().indexOf(tempLabor)+"-"+trees.indexOf(t));
									
									try {
										gameClient.oos.writeObject(commandStr);
									} catch (IOException e1){
										e1.printStackTrace();
									}
									tmpp = true;
									tempLabor.setRunInput(3);
									tempLabor.setTarget(t);
									(new Thread(tempLabor)).start();
									break;
								}
							}
							if(tmpp)
								break;
							for(IronMine im : mines){
								if((im.getX()-x0 == (e.getX()/50) && im.getY()-y0 == (e.getY()/50)) || (im.getX()-x0+1 == (e.getX()/50) && im.getY()-y0 == (e.getY()/50)) || (im.getX()-x0+1 == (e.getX()/50) && im.getY()-y0+1 == (e.getY()/50)) || (im.getX()-x0 == (e.getX()/50) && im.getY()-y0+1 == (e.getY()/50))){
									commandStr = ("mining-"+getMyCountryID()+"-"+countries.indexOf(tempLabor.getCountry())+"-"+tempLabor.getCountry().getLabors().indexOf(tempLabor)+"-"+mines.indexOf(im));
									try {
										gameClient.oos.writeObject(commandStr);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									tmpp = true;
									tempLabor.setRunInput(4);
									tempLabor.setTarget(im);
									(new Thread(tempLabor)).start();
									break;
								}
							}
							if(tmpp)
								break;
							for(TransportShip ts : tempLabor.getCountry().getTransportShips()){
								if(ts.getX()-x0 == (e.getX()/50) && ts.getY()-y0 == (e.getY()/50)){
									commandStr = "";
									try {
										gameClient.oos.writeObject(commandStr);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									tmpp = true;
									tempLabor.setRunInput(6);
									tempLabor.setTarget(ts);
									(new Thread(tempLabor)).start();
									break;
								}
							}
							if(tmpp)
								break;
							
							//if nothing exists and labor should just move...
							commandStr = ("laborMove-"+getMyCountryID()+"-"+countries.indexOf(tempLabor.getCountry())+"-"+tempLabor.getCountry().getLabors().indexOf(tempLabor)+"-"+((e.getX()/50)+x0)+"-"+((e.getY()/50)+y0));
							try {
								gameClient.oos.writeObject(commandStr);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							tempLabor.setRunInput(1);
							tempLabor.setTarget((e.getX()/50)+x0,(e.getY()/50)+y0);
							(new Thread(tempLabor)).start();
						}else if(selectedWarship != null){
							String commandStr1 = null;
							boolean tmpr = false;
							WarShip tempWS = selectedWarship;
							for(Country c : countries){
								if(c != tempWS.getCountry()){
									for(WarShip ws : c.getWarShips()){
										if(ws.getX()-x0+2==(e.getX()/50) && ws.getY()-y0+2==(e.getY()/50)){
											commandStr1 = ("shipFightingWS-"+getMyCountryID()+"-"+countries.indexOf(tempWS.getCountry())+"-"+tempWS.getCountry().getWarShips().indexOf(tempWS)+"-"+countries.indexOf(ws.getCountry())+"-"+ws.getCountry().getWarShips().indexOf(ws));
											try {
												gameClient.oos.writeObject(commandStr1);
											} catch (IOException e1) {
												e1.printStackTrace();
											}
											tempWS.setRunInput(2);
											tempWS.setOpponent(ws);
											(new Thread(tempWS)).start();
											tmpr = true;
											break;
										}
									}
									
									if(tmpr)
										break;

									for(TransportShip ts : c.getTransportShips()){
										if(ts.getX()-x0+2==(e.getX()/50) && ts.getY()-y0+2==(e.getY()/50)){
										try {
											gameClient.oos.writeObject(commandStr1);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
											tempWS.setRunInput(2);
											tempWS.setOpponent(ts);
											(new Thread(tempWS)).start();
											tmpr = true;
											break;
										}
									}
									
									if(tmpr)
										break;

									for(FishingShip fs : c.getFShips()){
										if(fs.getX()-x0+2==(e.getX()/50) && fs.getY()-y0+2==(e.getY()/50)){
											tempWS.setRunInput(2);
											tempWS.setOpponent(fs);
											(new Thread(tempWS)).start();
											tmpr = true;
											break;
										}
									}
								}
								if(tmpr)
									break;
							}
							if(tmpr)
								break;
							//just move...
							tempWS.setRunInput(1);
							tempWS.setTarget((e.getX()/50)+x0,(e.getY()/50)+y0);
							(new Thread(tempWS)).start();
						}else if(selectedTransportShip != null){
							TransportShip tempTS = selectedTransportShip;
							
							if(tempTS.getCountry().getBaseX()-x0==(e.getX()/50) && tempTS.getCountry().getBaseY()-y0==(e.getY()/50)){
								tempTS.setRunInput(2);
								(new Thread(tempTS)).start();
								break;
							}
							tempTS.setRunInput(1);
							tempTS.setTarget((e.getX()/50)+x0,(e.getY()/50)+y0);
							(new Thread(tempTS)).start();
						}else if(selectedFishingShip != null){
							
							boolean tmpz = false;
							FishingShip tempFS = selectedFishingShip;
							
							if(tempFS.getCountry().getBaseX()-x0 == (e.getX()/50) && tempFS.getCountry().getBaseY()-y0 == (e.getY()/50)){
								tempFS.setRunInput(3);
								(new Thread(tempFS)).start();
//								tmpz = true;
								break;
							}
							for(Fish f : fishes){
								if(f.getX()-x0 == (e.getX()/50) && f.getY()-y0 == (e.getY()/50)){
									tempFS.setRunInput(2);
									tempFS.setOpponent(f);
									(new Thread(tempFS)).start();
									tmpz = true;
									break;
								}
							}
							if(tmpz)
								break;
							
							tempFS.setRunInput(1);
							tempFS.setTarget((e.getX()/50)+x0,(e.getY()/50)+y0);
							(new Thread(tempFS)).start();
						}
						break;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});
		new Thread(this).start();
	}
	
	public void setGameMatrix(int[][] inputMatrix){
		gameMatrix = inputMatrix;
	}
	
	public Client getGameClient() {
		return gameClient;
	}

	public Country getCountry(int index){
		for(Country c : countries){
			if(c.getCountryID()==index){
				return c;
			}
		}
		return null;
	}

	public Tile[][] loadMap() {
		return null;
	}
	
	public void setOtherBaseDirection(int otherBaseDirection) {
		this.otherBaseDirection = otherBaseDirection;
	}

	public void setGameMap(Tile[][] inputMap){
		gameMap = inputMap;
	}

	public Vector<Tree> getTrees(){
		return trees;
	}

	public Vector<Fish> getFishes() {
		return fishes;
	}

	public void setIronMine(int i, int j){
		gameMap[i][j].value3 = 1;
		if(gameMap[i+1][j].getTopLayer() == 2 && gameMap[i][j+1].getTopLayer() == 2 && gameMap[i+1][j+1].getTopLayer() == 2){			
			gameMap[i][j].setLayer(gameMap[i][j].getI(), 4);
			gameMap[i][j].setI(gameMap[i][j].getI()+1);
			gameMap[i][j].setIronMinePic(1);

			gameMap[i+1][j].setLayer(gameMap[i+1][j].getI(), 4);
			gameMap[i+1][j].setI(gameMap[i+1][j].getI()+1);
			gameMap[i+1][j].setIronMinePic(2);

			gameMap[i+1][j+1].setLayer(gameMap[i+1][j+1].getI(), 4);
			gameMap[i+1][j+1].setI(gameMap[i+1][j+1].getI()+1);
			gameMap[i+1][j+1].setIronMinePic(3);

			gameMap[i][j+1].setLayer(gameMap[i][j+1].getI(), 4);
			gameMap[i][j+1].setI(gameMap[i][j+1].getI()+1);
			gameMap[i][j+1].setIronMinePic(4);
		}else if(gameMap[i+1][j].getTopLayer() == 4 && gameMap[i][j+1].getTopLayer() == 4 && gameMap[i+1][j+1].getTopLayer() == 4){
			gameMap[i][j].setLayer(gameMap[i][j].getI(), 4);
			gameMap[i][j].setI(gameMap[i][j].getI()+1);
			gameMap[i][j].setIronMinePic(1);

			gameMap[i+1][j].setLayer(gameMap[i+1][j].getI(), 4);
			gameMap[i+1][j].setI(gameMap[i+1][j].getI()+1);
			gameMap[i+1][j].setIronMinePic(2);

			gameMap[i+1][j+1].setLayer(gameMap[i+1][j+1].getI(), 4);
			gameMap[i+1][j+1].setI(gameMap[i+1][j+1].getI()+1);
			gameMap[i+1][j+1].setIronMinePic(3);

			gameMap[i][j+1].setLayer(gameMap[i][j+1].getI(), 4);
			gameMap[i][j+1].setI(gameMap[i][j+1].getI()+1);	
			gameMap[i][j+1].setIronMinePic(4);
		}
	}

	public Vector<IronMine> getMines(){
		return mines;
	}
	
	public void setOtherBaseX(int otherBaseX) {
		this.otherBaseX = otherBaseX;
	}
	
	public void setOtherBaseY(int otherBaseY) {
		this.otherBaseY = otherBaseY;
	}

	public void setBasePlace(int countryCount){
		for(int i=0;i<beachesX.size();i++){
			
			for(int j=0;j<beachesX.size();j++){
				if(true){
					
				}
			}
		}
	}

/*	public boolean isBeach(int inputX,int inputY){
		if(gameMap[inputX][inputY].getTopLayer() == 2 && gameMap[inputX][inputY+1].getTopLayer() == 2 &&((gameMap[inputX-1][inputY].getTopLayer() == 1 && gameMap[inputX-1][inputY+1].getTopLayer() == 1)||(gameMap[inputX-1][inputY].getTopLayer() == 0 && gameMap[inputX-1][inputY+1].getTopLayer() == 0))){
			
		}
		return false;
	}*/
	
	public void setMyCountryID(int inputCountryID) {
		myCountryID = inputCountryID;
	}
	
	public int getMyCountryID() {
		return myCountryID;
	}
/*	public void setIsland(){
		for(int i=0;i<30;i++){
			for(int j=0;j<20;j++){
				if(gameMatrix[i][j]==2){
					
				}
			}
		}
	}*/

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = x0; i < (x0 + matrixWidth) - 1; i++){
			for (int j = y0; j < (y0 + matrixHeight) - 1; j++){
				g.drawImage(gameMap[i][j].getPic(gameMap[i][j].getLayers()[0]).getImage(), (i - x0) * 50, (j - y0) * 50,null);
				g.drawImage(islands[i][j].getPic(seasonCounter%4).getImage(), (i-x0)*50, (j-y0)*50, null);
			}
		}
/*
		for(int i=x0;i<(x0+matrixWidth);i++){
			for(int j=y0;j<(y0+matrixHeight);j++){
				
			}
		}*/
		
		for(Tree t : trees){
			if(t.exists())
				g.drawImage(t.getPic(seasonCounter%4).getImage(), (t.getPaintX()-(x0*50)), (t.getPaintY()-(y0*50)), null);
			else
				g.drawImage(t.getPic((seasonCounter%4)+4).getImage(), (t.getPaintX()-(x0*50)), (t.getPaintY()-(y0*50)), null);
		}

		for(IronMine im : mines){
			if(im.exists()){
				g.drawImage(im.getPic(seasonCounter%4,0).getImage(), (im.getPaintX()-(x0*50)), (im.getPaintY()-(y0*50)), null);
				g.drawImage(im.getPic(seasonCounter%4,1).getImage(), (im.getPaintX()-(x0*50)+50), (im.getPaintY()-(y0*50)), null);
				g.drawImage(im.getPic(seasonCounter%4,2).getImage(), (im.getPaintX()-(x0*50)+50), (im.getPaintY()-(y0*50)+50), null);
				g.drawImage(im.getPic(seasonCounter%4,3).getImage(), (im.getPaintX()-(x0*50)), (im.getPaintY()-(y0*50)+50), null);
			}
		}
		
		for(Fish f : fishes){
			if(f.exists()){
				g.drawImage(f.getPic().getImage(), (f.getPaintX()-(x0*50)), (f.getPaintY()-(y0*50)), null);
			}
		}

		for(Country c : countries){
			g.drawImage(c.getBasePic(0).getImage(), (c.getBasePaintX()-(x0*50)), (c.getBasePaintY()-(y0*50)), null);
			g.drawImage(c.getBasePic(1).getImage(), (c.getBasePaintX()-(x0*50)+50), (c.getBasePaintY()-(y0*50)), null);
			g.drawImage(c.getBasePic(2).getImage(), (c.getBasePaintX()-(x0*50)+50), (c.getBasePaintY()-(y0*50)+50), null);
			g.drawImage(c.getBasePic(3).getImage(), (c.getBasePaintX()-(x0*50)), (c.getBasePaintY()-(y0*50)+50), null);
			for(Labor l : c.getLabors()){
				if(l.Live() || l.isPrimary()){
					g.drawImage(l.getPic().getImage(), (l.getPaintX()-(x0*10))*5, (l.getPaintY()-(y0*10))*5, null);
					g.setColor(Color.BLACK);
					g.drawLine(l.getPaintX()*5-(x0*50), l.getPaintY()*5-(y0*50)-25, l.getPaintX()*5-(x0*50)+50, l.getPaintY()*5-(y0*50)-25);
					g.setColor(Color.GREEN);
					g.drawLine(l.getPaintX()*5-(x0*50), l.getPaintY()*5-(y0*50)-25, l.getPaintX()*5-(x0*50) + (int)((l.getHealth()/l.getMaxHealth())*50), l.getPaintY()*5-(y0*50)-25);
				}
			}

			for(FishingShip fs : c.getFShips()){
				if(fs.isAlive()){
					g.drawImage(fs.getPic().getImage(), (fs.getPaintX()-(x0*10))*5, (fs.getPaintY()-(y0*10))*5,null);
				}
			}

			for(WarShip ws : c.getWarShips()){
				if(ws.isAlive()){
					g.drawImage(ws.getPic(0).getImage(), (ws.getPaintX()-(x0*10))*5, (ws.getPaintY()-(y0*10))*5, null);
				}
			}

			for(TransportShip ts : c.getTransportShips()){
				if(ts.isAlive()){
					g.drawImage(ts.getPic().getImage(), (ts.getPaintX()-(x0*10))*5, (ts.getPaintY()-(y0*10))*5, null);				
				}
			}
		}
	}

	private boolean isBeach(int inputX,int inputY){
		if(gameMatrix[inputX-1][inputY]==1 && gameMatrix[inputX][inputY-1]==1 && gameMatrix[inputX][inputY]==1 && gameMatrix[inputX][inputY]==1){
			return true;
		}
		return false;
	}

	public int checkNeighbors(int i,int j){
		int state = 0;
		if(i != 0 && j != 0 && i != matrixWidth*mapsize+1 && j != matrixHeight*mapsize+1){
			if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 1;
			}else if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 2;
			}else if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 3;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 4;
			}else if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 5;
			}else if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 6;
			}else if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 7;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 8;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 9;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 10;
			}else if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 11;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 12;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 13;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 14;
			}else if(gameMap[i-1][j].getLayers()[1] !=2 && gameMap[i][j-1].getLayers()[1] ==2 && gameMap[i][j+1].getLayers()[1] ==2 && gameMap[i+1][j].getLayers()[1] !=2){
				state = 15;
			}else if(gameMap[i-1][j].getLayers()[1] ==2 && gameMap[i][j-1].getLayers()[1] !=2 && gameMap[i][j+1].getLayers()[1] !=2 && gameMap[i+1][j].getLayers()[1] ==2){
				state = 16;
			}
		}
		return state;
	}

	public Vector<Country> getCountries(){
		return countries;
	}
	
	private void checkIslands(int i, int j) {

		if (gameMap[i + 1][j].getTopLayer() == 2) {
			if (gameMatrix[i + 1][j] != 0) {
				cnt = gameMatrix[i + 1][j];
			}
			checkIslands(i + 1, j);
		}

		if (gameMap[i][j + 1].getTopLayer() == 2) {
			if (gameMatrix[i][j + 1] != 0) {
				cnt = gameMatrix[i][j + 1];
			}
			checkIslands(i, j + 1);
		}
		gameMatrix[i][j] = cnt;
	}

	public int[][] getGameMatrix() {
		return gameMatrix;
	}

	public int[][] getGameForeMap(){
		return gameForeMap;
	}

	public Timer getTimer(){
		return gameTimer;
	}

	public Tile[][] getGameMap(){
		return gameMap;
	}

	@Override
	public void run() {
		Player musicPlayer;
		while (true) {
/*			try {
				musicPlayer = new Player(new FileInputStream("Pirates.mp3"));
				musicPlayer.play();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (JavaLayerException e1) {
				e1.printStackTrace();
			}*/
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (parent.jtp.getSelectedIndex() == 1) {
				/*
				 * 
				if(parent.jtp.getMousePosition()==null) continue;
				if(parent.jtp.getMousePosition().x >1350){ if(x0<map.length -
				matrixWidth) x0++; } 
				if(parent.jtp.getMousePosition().x < 10){ if(x0 > 1) x0--; }
				if(parent.jtp.getMousePosition().y < 10){ if(y0>1) y0--; }
				*/
				try {
					if (getMousePosition() == null)
						continue;
					if (getMousePosition().x < 10) {
						if (x0 > 1) {
							x0--;
							parentST.parentSP.mousePosX -= (parentST.parentSP.getMiniDim());
						}
					}

					if (getMousePosition().y < 10) {
						if (y0 > 1) {
							y0--;
							parentST.parentSP.mousePosY -= (parentST.parentSP.getMiniDim());
						}
					}
					if (getMousePosition().x > 1350) {
						if (x0 < gameMap.length - matrixWidth) {
							x0++;
							parentST.parentSP.mousePosX += (parentST.parentSP.getMiniDim());
						}
					}
					if (getMousePosition().y > 630) {
						if (y0 < (gameMap[0].length - matrixHeight)) {
							y0++;
							parentST.parentSP.mousePosY += (parentST.parentSP.getMiniDim());
						}
					}
				} catch (NullPointerException e) {
					
				}
				parent.h2.repaint();
			}
		}
	}
}