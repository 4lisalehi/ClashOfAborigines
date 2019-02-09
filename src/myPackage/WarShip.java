package myPackage;

import java.util.Random;
import java.util.Stack;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import interfaces.*;

public class WarShip extends Ship implements Runnable,Selectable,Data{
	private ImageIcon pic;
	private float health;
	private final int maxHealth;
	private int x,y;
	private int paintX,paintY;
	private int tempX,tempY;
	private boolean isMoving;
	private int targetX,targetY;
	private boolean isSelected;
	private boolean isAlive;
	private boolean isFighting;
	private Country country;
	private GamePanel parent;
	private int[][] gameMatrix;
	private Stack<Integer>movePathX;
	private Stack<Integer>movePathY;
	private int xStack,yStack;
	private int runInput;
	private Ship opponentShip;
	private TimerTask checkAround;
	int speed;
	
	public WarShip(int inputX,int inputY,GamePanel gp,Country c) {
		parent = gp;
		country = c;
		health = WARSHIP_HEALTH;
		maxHealth = WARSHIP_HEALTH;
		gameMatrix = parent.getGameMatrix();
		pic = new ImageIcon("pics/warship1.png");
		isSelected = false;
		isAlive = true;
		speed = 1;
		movePathX = new Stack<Integer>();
		movePathY = new Stack<Integer>();
		Random rndm = new Random();

		x = inputX;
		y = inputY;
		paintX = x*10;
		paintY = y*10;
		checkAround = new TimerTask() {
			@Override
			public void run() {
				boolean tmp1 = false;
				for(Country c : parent.getCountries()){
					if(c != country){
						for(WarShip ws : c.getWarShips()){
							if(Math.abs(x-ws.getX())<2 && Math.abs(y-ws.getY())<2){
								tmp1 = true;
								setRunInput(2);
								setOpponent(ws);
								(new Thread(this)).start();
								break;
							}
						}

						if(tmp1)
							break;
						for(TransportShip ts : c.getTransportShips()){
							if(Math.abs(x-ts.getX())<2 && Math.abs(y-ts.getY())<2){
								tmp1 = true;
								setRunInput(2);
								setOpponent(ts);
								(new Thread(this)).start();
								break;
							}
						}
						if(tmp1)
							break;
						for(FishingShip fs : c.getFShips()){
							if(Math.abs(x-fs.getX())<2 && Math.abs(y-fs.getY())<2){
								tmp1 = true;
								setRunInput(2);
								setOpponent(fs);
								(new Thread(this)).start();
								break;
							}
						}
					}
				}
			}
		};
	}

	@Override
	public void run() {
		switch(runInput){
			case 1:
				if(isMoving || isFighting){
					isMoving = false;
					isFighting = false;
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					move();
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
			case 2:
				fight((Ship)opponentShip);
				break;
			case 3:
				
				break;
			case 4:
				
				break;
		}
	}

	public int[] getClosestNeighbor(int inputX,int inputY){
		int tempMin = 0;
		int index = 0;
		int[] result = new int[2];
		int h[] = {Math.abs(x-1-inputX)+Math.abs(y-inputY),Math.abs(x-inputY)+Math.abs(y-1-inputY),Math.abs(x+1-inputX)+Math.abs(y-inputY),Math.abs(x-inputX)+Math.abs(y+1-inputY)};	
		int r[][] = {{inputX-1,inputY},{inputX,inputY-1},{inputX+1,inputY},{inputX,inputY+1}};
		for(int i=0;i<h.length;i++){
			if(tempMin > h[i]){
				tempMin = h[i];
				index = i;
			}
		}
		result[0] = r[index][0];
		result[1] = r[index][1];
		return result;
	}
	
	public ImageIcon getPic(){
		return pic;
	}

	@Override
	public void move() {
		if(findShortestPath(targetX,targetY)){
			isMoving = true;
			while(!movePathX.isEmpty()){
				tempX = movePathX.pop();
				tempY = movePathY.pop();
				for(int i=0; i < speed * 5;i++){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(tempX - x == -1 && tempY - y == 0){
						setPic(1);
					}else if(tempY - y == -1 && tempX - x == 0){
						setPic(2);
					}else if(tempX - x == 1 && tempY - y == 0){
						setPic(3);
					}else if(tempY - y == 1 && tempX - x == 0){
						setPic(4);
					}
					paintX += (tempX-x)*(3 - speed);
					paintY += (tempY-y)*(3 - speed);
				}
				if(parent.seasonCounter == 3){
					speed = 2;
				} else{
					speed = 1;
				}
				x = tempX;
				y = tempY;
				if(isMoving == false){
					setPic(5);
					break;
				}
			}
			isMoving = false;
			setPic(5);
		}
	}

	@Override
	public void showPanel() {
		
	}

	public void hurt(){
		health-=100;
	}

	public boolean Live(){
		return (health>0);
	}

	public void fight(Ship s){
		int[] temp;
		if(s instanceof WarShip){
			WarShip ss = (WarShip)s;
			temp = getClosestNeighbor(ss.getX(),ss.getY());
			setTarget(temp[0],temp[1]);
			move();
			while(ss.Live()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ss.hurt();
			}
		}else if(s instanceof TransportShip){
			TransportShip sa = (TransportShip) s;
			temp = getClosestNeighbor(sa.getX(),sa.getY());
			setTarget(temp[0],temp[1]);
			move();
			while(sa.Live()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sa.hurt();
			}
		}else if(s instanceof FishingShip){
			FishingShip as = (FishingShip)s;
			temp = getClosestNeighbor(as.getX(),as.getY());
			setTarget(temp[0],temp[1]);
			move();
			while(as.Live()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				as.hurt();
			}
		}
		
	}

	public Country getCountry() {
		return country;
	}
	
	public void setOpponent(Object obj){
		opponentShip = (Ship)obj;
	}
	
	public ImageIcon getPic(int x){
		return pic;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public int getPaintX() {
		return paintX;
	}

	public int getPaintY() {
		return paintY;
	}

	public void setSelected() {
		isSelected = true;
	}

	public void setRunInput(int x){
		runInput = x;
	}

	public void setTarget(int inputX,int inputY){
		targetX = inputX;
		targetY = inputY;
	}
	
	public boolean isAlive(){
		return (health>0);
	}

	public void setPic(int direction){
		switch(direction){
		case 1:
			pic = new ImageIcon("pics/warship4.png");
			break;
		case 2:
			pic = new ImageIcon("pics/warship1.png");
			break;
		case 3:
			pic = new ImageIcon("pics/warship2.png");
			break;
		case 4:
			pic = new ImageIcon("pics/warship3.png");
			break;
		default:
			Random r;
			int c;
			r = new Random();
			c = r.nextInt(2);
			if(c == 0)
				pic = new ImageIcon("pics/warship5.png");
			else if(c == 1)
				pic = new ImageIcon("pics/warship6.png");
			break;
		}
	}

	public boolean findShortestPath(int xMatrix, int yMatrix){
		int isInClosedList, isInOpenList;
		int openListSize, closedListSize, temp = 0, hs;
		int tempG;
		int lowestF, placeOfLowestF, placeOfCurrentSquare = 0;
		boolean isWay = false;
		ClosedList[] myClosedList = new ClosedList[gameMatrix.length * gameMatrix[0].length];
		OpenList[] myOpenList = new OpenList[gameMatrix.length * gameMatrix[0].length];
		AdjacentSquare[] myAdjacentSquare = new AdjacentSquare[4];

		for(int i = 0 ; i < gameMatrix.length * gameMatrix[0].length; i++){
			myClosedList[i] = new ClosedList();
			myOpenList[i] = new OpenList();
		}

		for(int i = 0 ; i < 4 ; i++){
			myAdjacentSquare[i] = new AdjacentSquare();
		}

		tempG = 0;
		openListSize = 0;
		closedListSize = 0;
		myOpenList[0].f = -1;
		if((x != xMatrix || y != yMatrix) && (gameMatrix[xMatrix][yMatrix] == 0)){
			movePathX.push(xMatrix);
			movePathY.push(yMatrix);
		}
		
		do{
			if(closedListSize == 0){
				xStack = x;
				yStack = y;

				myClosedList[0].x = xStack;
				myClosedList[0].y = yStack;
				myClosedList[0].h = Math.abs(xMatrix - x) + Math.abs(yMatrix - y);
				myClosedList[0].g = 0;
				myClosedList[0].f = myClosedList[0].g + myClosedList[0].h;
				closedListSize++;
			}
			else{
				lowestF = myOpenList[openListSize - 1].f;
				placeOfLowestF = openListSize - 1;
				for(int m = openListSize - 2 ; m >= 0 ; m--){
					if(myOpenList[m].f < lowestF){
						lowestF = myOpenList[m].f;
						placeOfLowestF = m;
					}
				}
				xStack = myOpenList[placeOfLowestF].x;
				yStack = myOpenList[placeOfLowestF].y;
				
				myClosedList[closedListSize].x = xStack;
				myClosedList[closedListSize].y = yStack;
				myClosedList[closedListSize].f = myOpenList[placeOfLowestF].f;
				myClosedList[closedListSize].g = myOpenList[placeOfLowestF].g;
				myClosedList[closedListSize].h = myOpenList[placeOfLowestF].h;

				placeOfCurrentSquare = closedListSize;
				closedListSize++;

				tempG = myOpenList[placeOfLowestF].g;

				if(placeOfLowestF == (openListSize - 1)){

					myOpenList[openListSize - 1].g = -1;
					myOpenList[openListSize - 1].h = -1;
					myOpenList[openListSize - 1].f = -1;
					myOpenList[openListSize - 1].x = -1;
					myOpenList[openListSize - 1].y = -1;

					openListSize--;
				}
				else{
					for(int m = placeOfLowestF ; m <= (openListSize - 2) ; m++){
						
						myOpenList[m].g = myOpenList[m + 1].g;
						myOpenList[m].h = myOpenList[m + 1].h;
						myOpenList[m].f = myOpenList[m + 1].f;
						myOpenList[m].x = myOpenList[m + 1].x;
						myOpenList[m].y = myOpenList[m + 1].y;
					}
					
					myOpenList[openListSize - 1].g = -1;
					myOpenList[openListSize - 1].h = -1;
					myOpenList[openListSize - 1].f = -1;
					myOpenList[openListSize - 1].x = -1;
					myOpenList[openListSize - 1].y = -1;

					openListSize--;
				}
				isInClosedList = 0;
				for(int m = 0 ; m < closedListSize ; m++){
					if ((xMatrix == myClosedList[m].x) &&
					        (yMatrix == myClosedList[m].y)){
						isInClosedList = 1;
						break;
					}
				}
				if (isInClosedList == 1){
					isWay = true;
					break;
				}
			}
			myAdjacentSquare[0].x = xStack;
			myAdjacentSquare[0].y = yStack - 1;

			myAdjacentSquare[1].x = xStack - 1;
			myAdjacentSquare[1].y = yStack;

			myAdjacentSquare[2].x = xStack;
			myAdjacentSquare[2].y = yStack + 1;

			myAdjacentSquare[3].x = xStack + 1;
			myAdjacentSquare[3].y = yStack;

			for (int k = 0 ; k <= 3 ; k++){
				isInClosedList = 0;
				for (int m = 0 ; m < closedListSize ; m++){
					if ((myAdjacentSquare[k].x == myClosedList[m].x) &&
					        (myAdjacentSquare[k].y == myClosedList[m].y)){
						isInClosedList = 1;
						break;
					}
				}
				if ((myAdjacentSquare[k].x < 0) ||
				        (myAdjacentSquare[k].y < 0) ||
				        (myAdjacentSquare[k].x >= gameMatrix.length) ||
				        (myAdjacentSquare[k].y >= gameMatrix[0].length) ||
				        ((gameMatrix[myAdjacentSquare[k].x][myAdjacentSquare[k].y] != 0))||
				        (isInClosedList == 1)){
					continue;
				}

				isInOpenList = 0;
				for (int m = 0 ; m < openListSize ; m++){
					if ((myAdjacentSquare[k].x == myOpenList[m].x) &&
					        (myAdjacentSquare[k].y == myOpenList[m].y)){
						isInOpenList = 1;
						temp = m;
						break;
					}
				}


				if (isInOpenList != 1){
					myOpenList[openListSize].h = Math.abs(xMatrix - myAdjacentSquare[k].x) + Math.abs(yMatrix - myAdjacentSquare[k].y);
					myOpenList[openListSize].g = tempG + 1;
					myOpenList[openListSize].f = myOpenList[openListSize].h + myOpenList[openListSize].g;
					myOpenList[openListSize].x = myAdjacentSquare[k].x;
					myOpenList[openListSize].y = myAdjacentSquare[k].y;
					openListSize++;
				}else{  
					if((myClosedList[placeOfCurrentSquare].g + 1) < myOpenList[temp].g){
						myOpenList[temp].g = myClosedList[placeOfCurrentSquare].g + 1;
						myOpenList[temp].f = myOpenList[temp].g + myOpenList[temp].h;
					}
				}
			}

		}while(myOpenList[0].f != -1);
		
		if(isWay){
			hs = 0;
			xStack = myClosedList[closedListSize - 1].x;
			yStack = myClosedList[closedListSize - 1].y;
			tempG = myClosedList[closedListSize - 1].g;
			do{
				if(hs > 0){
					movePathX.push(xStack);
					movePathY.push(yStack);
				}

				myAdjacentSquare[0].x = xStack;
				myAdjacentSquare[0].y = yStack - 1;

				myAdjacentSquare[1].x = xStack - 1;
				myAdjacentSquare[1].y = yStack;

				myAdjacentSquare[2].x = xStack;
				myAdjacentSquare[2].y = yStack + 1;

				myAdjacentSquare[3].x = xStack + 1;
				myAdjacentSquare[3].y = yStack;

				for (int k = 0 ; k <= 3 ; k++){
					if ((myAdjacentSquare[k].x < 0) ||
					        (myAdjacentSquare[k].y < 0) ||
					        (myAdjacentSquare[k].x >= gameMatrix.length) ||
					        (myAdjacentSquare[k].y >= gameMatrix[0].length) ||
					        (gameMatrix[myAdjacentSquare[k].x][myAdjacentSquare[k].y] != 0)){
						continue; 
					}
					

					isInClosedList = 0;
					for(int j = 0 ; j < closedListSize ; j++){
						if ((myAdjacentSquare[k].x == myClosedList[j].x) &&
						        (myAdjacentSquare[k].y == myClosedList[j].y)){
							isInClosedList = 1;
							temp = j;
							break;
						}
					}

					if (isInClosedList == 1){
						if (myClosedList[temp].g == (tempG - 1)){
							hs++;
							xStack = myClosedList[temp].x;
							yStack = myClosedList[temp].y;
							tempG = myClosedList[temp].g;
							break;
						}
					}
				}
			}while (tempG != 0);
		}

		if(xMatrix == x && yMatrix == y)
			isWay = false;
		return isWay;
	}

	@Override
	public void hidePanel() {
		// TODO Auto-generated method stub
		
	}
}