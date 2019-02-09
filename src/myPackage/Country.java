package myPackage;

import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;

import interfaces.Data;

public class Country implements Data{
	private Vector<Labor> labors;
	private Vector<WarShip> warShips;
	private Vector<TransportShip> transportShips;
	private Vector<FishingShip> fShips;
	private String countryName;
	private int[][] countryMap;
	private int maxLabors;
	private int foodResource;
	public int woodResource;
	private int ironResource;
	private int baseX,baseY;
	private int basePaintX,basePaintY;
	private int baseDirection;
	public Vector<Labor> inBaseLabor;
	private ImageIcon[][] basePics;
	private int ID;

	GamePanel parent;
	Labor tempLabor;
	public Country(GamePanel gp) {
		basePics = new ImageIcon[6][4];
		basePics[0][0] = new ImageIcon("pics/base11.png");
		basePics[0][1] = new ImageIcon("pics/base12.png");
		basePics[0][2] = new ImageIcon("pics/base13.png");
		basePics[0][3] = new ImageIcon("pics/base14.png");
		basePics[1][0] = new ImageIcon("pics/base21.png");
		basePics[1][1] = new ImageIcon("pics/base22.png");
		basePics[1][2] = new ImageIcon("pics/base23.png");
		basePics[1][3] = new ImageIcon("pics/base24.png");
		basePics[2][0] = new ImageIcon("pics/base31.png");
		basePics[2][1] = new ImageIcon("pics/base32.png");
		basePics[2][2] = new ImageIcon("pics/base33.png");
		basePics[2][3] = new ImageIcon("pics/base34.png");
		basePics[3][0] = new ImageIcon("pics/base41.png");
		basePics[3][1] = new ImageIcon("pics/base42.png");
		basePics[3][2] = new ImageIcon("pics/base43.png");
		basePics[3][3] = new ImageIcon("pics/base44.png");
		basePics[4][0] = new ImageIcon("pics/base51.png");
		basePics[4][1] = new ImageIcon("pics/base52.png");
		basePics[4][2] = new ImageIcon("pics/base53.png");
		basePics[4][3] = new ImageIcon("pics/base54.png");

		foodResource = COUNTRY_INIT_FOOD;
		woodResource = 10000;//change to 0
		ironResource = 10000;//change to 0
		parent = gp;
		fShips = new Vector<FishingShip>();
		labors = new Vector<Labor>();
		inBaseLabor = new Vector<Labor>();
		warShips = new Vector<WarShip>();
		transportShips = new Vector<TransportShip>();

		
	}

	public ImageIcon getBasePic(int index) {
		return basePics[baseDirection][index];
	}

	public int getBaseX() {
		return baseX;
	}

	public int getBaseY() {
		return baseY;
	}

	public int getIronResource() {
		return ironResource;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public int getWoodResource() {
		return woodResource;
	}
	
	public Vector<Labor> getInBaseLabor() {
		return inBaseLabor;
	}
	
	public GamePanel getParent() {
		return parent;
	}
	
	public int getBasePaintX() {
		return basePaintX;
	}

	public int getBasePaintY() {
		return basePaintY;
	}

	public int[][] getCountryMap() {
		return countryMap;
	}

	public Vector<Labor> getLabors(){
		return labors;
	}

	public Vector<WarShip> getWarShips() {
		return warShips;
	}

	public Vector<TransportShip> getTransportShips() {
		return transportShips;
	}
	
/*	public void summonLabor(Labor x){
		x.setTarget(baseX, baseY);
		x.setRunInput(1);
		(new Thread(x)).start();
	}
*/

	public void releaseLabor(Labor y){
		//to be changed....
		int tempX = getBaseX()+1;
		int tempY = getBaseY()+1;
		do{
			tempX++;
		}while(parent.getGameMatrix()[tempX][tempY]!=2);
		y.setX(tempX);
		y.setY(tempY);
		y.setPaintX(y.getX()*10);
		y.setPaintY(y.getY()*10);
		labors.add(y);
		inBaseLabor.remove(y);
	}

/*	public void buildWarShip(int laborCount){
		//to be completed...
		if(ironResource>=1000 && woodResource>=500){
			try {
				Thread.sleep(180000/laborCount);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ironResource -= 1000;
			woodResource -= 500;
			int tempX = 0;
			int tempY = 0;
			Random rndm = new Random();
			do{
				tempX = rndm.nextInt(25);
				tempY = rndm.nextInt(18);
			}while(parent.getGameMatrix()[tempX][tempY]!=0);
			warShips.add(new WarShip(tempX,tempY,parent,this));
		}else{
			System.out.println("Wood needed!Iron needed!");
		}
	}*/

	public void createWarship(){
		int laborCount = inBaseLabor.size();
		if(laborCount>0){
			if(ironResource>=1000 && woodResource>=500){
				Country  thiz = this;
				TimerTask tt = new TimerTask() {
					@Override
					public void run() {
						Random rndm = new Random();
						int temp1,temp2;
						do{
							temp1 = rndm.nextInt(28);
							temp2 = rndm.nextInt(15);
						}while(parent.getGameMatrix()[temp1][temp2]!=0);
						woodResource-=500;
						ironResource-=1000;
						warShips.add(new WarShip(temp1,temp2,parent,thiz));		
					}
				};
				parent.getTimer().schedule(tt, 18000/laborCount);
			}
		}
	}
	public void buildTransportShip(){
		int laborCount = inBaseLabor.size();
		if(laborCount>0){
			if(ironResource>=300 && woodResource>=400){
				Country thizz = this;
				TimerTask tts = new TimerTask() {
					@Override
					public void run() {
						int temp1,temp2;
						Random rndm = new Random();
						do{
							temp1 = rndm.nextInt(28);
							temp2 = rndm.nextInt(15);
						}while(parent.getGameMatrix()[temp1][temp2]!=0);
						ironResource -= 300;
						woodResource -= 400;
						transportShips.add(new TransportShip(temp1,temp2,parent,thizz));
					}
				};
				parent.getTimer().schedule(tts,12000/laborCount);
			}
		}
	}
	
	public void buildFShip(){
		int laborCount = inBaseLabor.size();
		if(ironResource>=500 && woodResource>=500){
			Country thizz = this;
			TimerTask tts = new TimerTask() {
				@Override
				public void run() {
					int temp1,temp2;
					Random rndm = new Random();
					do{
						temp1 = rndm.nextInt(28);
						temp2 = rndm.nextInt(15);
					}while(parent.getGameMatrix()[temp1][temp2]!=0);
					ironResource -= 500;
					woodResource -= 500;
					fShips.add(new FishingShip(temp1,temp2,parent, thizz));
				}
			};
			parent.getTimer().schedule(tts,12000/laborCount);
		}
	}

	public void incrementWoodResource(int inc){
		woodResource += inc;
	}

	public void incrementFoodResource(int inc1){
		foodResource += inc1;
	}

	public void incrementIronResource(int inc2){
		ironResource += inc2;
	}
	

	public int getCountryID(){
		return ID;
	}

	public void createLabor(){
		if(ironResource>=1000 && woodResource>=1000 && foodResource>500){
			Country thizz = this;
			TimerTask tts = new TimerTask() {
				@Override
				public void run() {
					ironResource -= 1000;
					woodResource -= 1000;
					labors.add(new Labor(false,parent, thizz));
					String command = ("createlabor-"+parent.getCountries().indexOf(this));
					try {
						parent.getGameClient().oos.writeObject(command);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			parent.getTimer().schedule(tts,10000);
		}
	}
	
	public void createLabor(boolean a){
		if(ironResource>=1000 && woodResource>=1000 && foodResource>500){
			Country thizz = this;
			ironResource -= 1000;
			woodResource -= 1000;
			labors.add(new Labor(false,parent, thizz));
		}
	}
	
	public void setBasePlace(int m,int n){
		baseX = m;
		baseY = n;
		basePaintX = baseX * 50;
		basePaintY = baseY * 50;
		for(int k=0;k<5;k++){
			tempLabor = new Labor(true,parent,this);
			labors.add(tempLabor);
		}
	}

	public void setBasePlace(){
		//to be completed...
		/*baseDirection = 0;
		switch((ID)){
			case 0:
				baseX = 3;
				baseY = 3;
				break;
			case 1:
				baseX = 6;
				baseY = 6;
				break;
			default:
				baseX = 7;
				baseY = 5;
				break;
		}
		basePaintX = baseX * 50;
		basePaintY = baseY * 50;*/
		
		Random r = new Random();
		int i, j;
		do{
			i = r.nextInt(26*parent.mapsize)+1;
			j = r.nextInt(13*parent.mapsize)+1;
			if(parent.getGameMap()[i][j].getTopLayer() == 2 && parent.getGameMap()[i+1][j].getTopLayer() == 2 && ((parent.getGameMap()[i][j-1].getTopLayer() == 1 && parent.getGameMap()[i+1][j-1].getTopLayer() == 1)||(parent.getGameMap()[i][j-1].getTopLayer() == 0 && parent.getGameMap()[i+1][j-1].getTopLayer() == 0))){
				System.out.println("1");
				baseDirection = 2;
				baseX = i;
				baseY = j;
				break;
			} else if(parent.getGameMap()[i][j].getTopLayer() == 2 && parent.getGameMap()[i][j+1].getTopLayer() == 2 &&((parent.getGameMap()[i-1][j].getTopLayer() == 1 && parent.getGameMap()[i-1][j+1].getTopLayer() == 1)||(parent.getGameMap()[i-1][j].getTopLayer() == 0 && parent.getGameMap()[i-1][j+1].getTopLayer() == 0))){
				System.out.println("2");
				baseDirection = 4;
				baseX = i;
				baseY = j;
				break;
			}else if(parent.getGameMap()[i][j].getTopLayer() == 2 && parent.getGameMap()[i][j+1].getTopLayer() == 2 && ((parent.getGameMap()[i+1][j].getTopLayer() == 1 && parent.getGameMap()[i+1][j+1].getTopLayer() == 1)||(parent.getGameMap()[i+1][j].getTopLayer() == 0 && parent.getGameMap()[i+1][j+1].getTopLayer() == 0))){
				System.out.println("3");
				baseDirection = 1;
				baseX = i;
				baseY = j;
				break;
			} else if(parent.getGameMap()[i][j].getTopLayer() == 2 && parent.getGameMap()[i+1][j].getTopLayer() == 2 && ((parent.getGameMap()[i][j+1].getTopLayer() == 1 && parent.getGameMap()[i+1][j+1].getTopLayer() == 1)||(parent.getGameMap()[i][j+1].getTopLayer() == 0 && parent.getGameMap()[i+1][j+1].getTopLayer() == 0))){
				System.out.println("4");
				baseDirection = 0;
				baseX = i;
				baseY = j;
				break;
			}
		}while(true);
		basePaintX = baseX * 50;
		basePaintY = baseY * 50;
		for(int k=0;k<5;k++){
			tempLabor = new Labor(true,parent,this);
			labors.add(tempLabor);
		}
	}

	public void discoverIsland(){
		
	}

	public void setCountryMap(int[][] countryMap) {
		this.countryMap = countryMap;
	}

	public void setCountryID(int input){
		ID = input;
	}
	
	public void setBaseDirection(int baseDirection) {
		this.baseDirection = baseDirection;
	}

	public int getFoodResouce() {
		return foodResource;
	}
	
	public int getBaseDirection() {
		return baseDirection;
	}

	public void consumeFood(){
		foodResource-=10;
	}

	public Vector<FishingShip> getFShips() {
		return fShips;
	}
}