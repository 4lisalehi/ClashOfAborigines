package myPackage;

import java.util.Random;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Vector;

import javax.swing.ImageIcon;

import interfaces.*;

public class Labor implements Runnable,Data,Selectable {

	GamePanel parent;
	Country country;
	private int targetX,targetY;
	private int[][] gameMatrix;
//	private int[][] gameForeMap;
	private boolean isAlive;
	private float health;
	private final int maxHealth;
	private int nutrition;
	private int x,y;
	private int tempX,tempY;
	private Object target;
	private int paintX,paintY;
	private int runInput;
	private boolean isPrimary;
//	private boolean fighting;
	private boolean isMoving;
	private boolean isLogging;
	private boolean isFighting;
	private boolean isMining;
//	private int cnt = 0;
	private Stack<Integer> movePathX;
	private Stack<Integer> movePathY;
	private int xStack;
	private int yStack;
	private Labor tempOpponent;
	private int capacity;
	private float weight;
	private float woodWeight;
	private float ironWeight;

	int pathStatus[];
	int xPath[];
	int yPath[];
	int speed;
	Timer myTimer;
	TimerTask eatFood;
	TimerTask getWell;
	TimerTask checkHealth;
	TimerTask checkAround;
//	TimerTask syso;
	
	Random rndm;
	ImageIcon pic;
//	private boolean targetFound;
	public Labor(boolean input,GamePanel inputGP,Country c){
		parent = inputGP;
		country = c;
		isPrimary = input;
		movePathX = new Stack<Integer>();
		movePathY = new Stack<Integer>();
		gameMatrix = parent.getGameMatrix();
//		gameForeMap = parent.getGameForeMap();
		nutrition = NUTRITION;
		health = LABOR_HEALTH;
		capacity = LABOR_CAPACITY;
		health = LABOR_HEALTH;
		maxHealth = LABOR_HEALTH;
		speed = 1;
		weight = 0;
		woodWeight = 0;
		ironWeight = 0;
		myTimer = parent.getTimer();
		
		
		
		getWell = new TimerTask(){
			@Override
			public void run() {
				if(health < 10){
					health++;
				}
			}
		};

/*		syso = new TimerTask() {
			@Override
			public void run() {
				System.out.println(isMoving);
			}
		};*/
		
		eatFood = new TimerTask(){
			@Override
			public void run() {
				if(country.getFoodResouce() > 10){
					nutrition += 10;
					country.consumeFood();
//					System.out.println(country.getFoodResouce());
				}else{
//					System.out.println("hungry");
					health -= 2;
					if(isPrimary && health <= -2){
						health += 2;
					}
				}
			}
		};

		checkHealth = new TimerTask(){
			@Override
			public void run() {
				if(health<=0 && !isPrimary){
					isAlive = false;
				}
			}
		};

		
		
		checkAround = new TimerTask(){
			@Override
			public void run() {
				for(Country c : parent.getCountries()){
					if(c != country){
						for(Labor l : c.getLabors()){
							if(Math.abs(l.getX()-x) < 2 && Math.abs(l.getY()-y) < 2){
								tempOpponent = l;
								setRunInput(2);
								setTarget(tempOpponent);
								(new Thread(this)).start();
							}
						}
					}
				}
			}
		};

		myTimer.schedule(eatFood,0, 3000);
		myTimer.schedule(getWell,0, 30000);
		myTimer.schedule(checkHealth,0, 1000);
//		myTimer.schedule(checkAround, 0, 1000);
		
		isAlive = true;
		isMoving = false;
		isLogging = false;
		isFighting = false;
		isMining = false;
		runInput =1;
		pic = new ImageIcon("pics\\Labor5.png");
		x = country.getBaseX();
		y = country.getBaseY();
		paintX = x*10;
		paintY = y*10;
	}

	@Override
	public void run() {
		if(Live()){
			switch(runInput){
			case 1:
				if(isMoving || isLogging || isFighting || isMining){
					isMoving = false;
					isLogging = false;
					isFighting = false;
					isMining = false;
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
				if(isMoving || isFighting || isMining || isLogging){
					isMoving = false;
					isFighting = false;
					isMining = false;
					isLogging = false;
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					fight((Labor)target);
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
			case 3:
				if(isLogging || isMoving || isFighting || isMining){
					isLogging = false;
					isMoving = false;
					isFighting = false;
					isMining = false;
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					logging((Tree)target);
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
			case 4:
				if(isMining || isMoving || isFighting || isLogging){
					isMining = false;
					isMoving = false;
					isFighting = false;
					isLogging = false;
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					mining((IronMine)target);
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
			case 5:
				if(isMining || isMoving || isFighting || isLogging){
					isMining = false;
					isMoving = false;
					isFighting = false;
					isLogging = false;
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					getIntoBase();
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
			case 6:
				if(isMining || isMoving || isFighting || isLogging){
					isMining = false;
					isMoving = false;
					isFighting = false;
					isLogging = false;
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					getIntoShip((TransportShip)target);
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
			}
		}
	}

	public void setHealth(int input) {
		health = input;
	}
	
	public float getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setNutrition(int input) {
		nutrition = input;
	}
	
	public int getNutrition() {
		return nutrition;
	}
	
	public boolean Live(){
		return isAlive;
	}

	public ImageIcon getPic(){
		return pic;
	}
	
	public void setPic(int direction){
		switch(direction){
		case 1:
			pic = new ImageIcon("pics\\labor1.png");
			break;
		case 2:
			pic = new ImageIcon("pics\\labor2.png");
			break;
		case 3:
			pic = new ImageIcon("pics\\labor3.png");
			break;
		case 4:
			pic = new ImageIcon("pics\\labor4.png");
			break;
		default:
			Random r;
			int c;
			r = new Random();
			c = r.nextInt(2);
			if(c == 0)
				pic = new ImageIcon("pics\\labor5.png");
			else if(c == 1)
				pic = new ImageIcon("pics\\labor6.png");
			break;
		}
	}
	
	public void setTarget(Object inputObj){
		target = inputObj;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPaintX() {
		return paintX;
	}

	public int getPaintY() {
		return paintY;
	}

	public void setPaintX(int inputPaintX) {
		paintX = inputPaintX;
	}
	
	public void setPaintY(int inputPaintY) {
		paintY = inputPaintY;
	}
	
	public boolean isPrimary(){
		return isPrimary;
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

	public void fight(Labor opponent) {
		int[] tempz = getClosestNeighbor(opponent.getX(),opponent.getY());
		setTarget(getClosestNeighbor(opponent.getX(),opponent.getY())[0],getClosestNeighbor(opponent.getX(),opponent.getY())[1]);
		move();
		while(opponent.Live()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			opponent.hurt();
		}
	}

	public void logging(Tree tree){
		int[] temp;
		while(tree.exists()){
			if(weight<=80){
				isLogging = true;
				temp = getClosestNeighbor(tree.getX(),tree.getY());
				setTarget(temp[0],temp[1]);
				move();
				for(int i=0;i<10;i++){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					tree.reduceWoods();
					woodWeight += 2;
					weight += 2;
				}
				temp = getClosestNeighbor(country.getBaseX(), country.getBaseY());
				setTarget(temp[0], temp[1]);
				move();
				woodWeight -= 20;
				weight -= 20;
				country.woodResource+=20;
			}
		}
		isLogging = false;
	}
	
	public void getIntoShip(TransportShip ts){
		int[] tmpz = getClosestNeighbor(ts.getX(), ts.getY());
		setTarget(tmpz[0],tmpz[1]);
		move();
		ts.getInsideLabors().add(this);
		country.getLabors().remove(this);
		if(weight>0){
			if(weight+ts.getWoodResource()+ts.getIronResource()<ts.getCapacity()){
				ts.incrementWoodResource((int)woodWeight);
				woodWeight = 0;
				ts.incrementIronResource((int)ironWeight);
				ironWeight = 0;
			}
		}
	}

	public void getIntoBase(){

		int[] tmpz = getClosestNeighbor(country.getBaseX(), country.getBaseY());
		setTarget(tmpz[0],tmpz[1]);
		move();
		country.getInBaseLabor().add(this);
		country.getLabors().remove(this);
		if(weight>0){
			country.incrementWoodResource((int)woodWeight);
			woodWeight = 0;
			country.incrementIronResource((int)ironWeight);
			ironWeight = 0;
		}
	}
	
	public void mining(IronMine im){
		int[] temp;
		while(im.exists()){
			if(weight<=80){
				temp = getClosestNeighbor(im.getX(),im.getY());
				setTarget(temp[0],temp[1]);
				move();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				im.reduceIrons();
				weight += 20;
				temp = getClosestNeighbor(country.getBaseX(), country.getBaseY());
				setTarget(temp[0],temp[1]);
				move();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isFull(){
		return (weight>=capacity);
	}
	
	public void setRunInput(int runInput) {
		this.runInput = runInput;
	}
	
	public int[] getClosestNeighbor(int inputX,int inputY){
		int tempMin = Math.abs(Math.abs(x-(inputX - 1))+Math.abs(y-inputY));
		int index = 0;
		int[] result = new int[2];
		int h[] = {Math.abs(x-(inputX - 1))+Math.abs(y-inputY),Math.abs(x-inputX)+Math.abs(y-(inputY-1)),Math.abs(x-(inputX + 1))+Math.abs(y-inputY),Math.abs(x-inputX)+Math.abs(y - (inputY + 1))};	
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
	
	public void hurt(){
		health -= 2;
	}

	public Country getCountry(){
		return country;
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
		if((x != xMatrix || y != yMatrix) && (gameMatrix[xMatrix][yMatrix] == 2)){
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
			}else{
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
				}else{
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
				        (gameMatrix[myAdjacentSquare[k].x][myAdjacentSquare[k].y] != 2)||
//				        (gameForeMap[myAdjacentSquare[k].x][myAdjacentSquare[k].y] == 1)||
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
					if ((myClosedList[placeOfCurrentSquare].g + 1) < myOpenList[temp].g){
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
					        (gameMatrix[myAdjacentSquare[k].x][myAdjacentSquare[k].y] != 2)){
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

/*	public void findPath(int inputX,int inputY){
		boolean targetFound = false;
		int positionX = x,positionY=y;
//		int[][][] path = new int[100][100][2];
		Vector<Block> blocks = new Vector<Block>();

		Vector<Block> openBlocks = new Vector<Block>();
		Vector<Block> closeBlocks = new Vector<Block>();

		int[][][] tempGameMatrix = new int[gameMatrix.length][gameMatrix[0].length][6];
		//{value,f,g,h,parent,checked}
		//parent direction:
		//1 2 3
		//4   5
		//6 7 8
		Block tempBlock;
		Block currentBlock = null;
		Block targetBlock = null;
		for(int i=0;i<tempGameMatrix.length;i++){
			for(int j=0;j<tempGameMatrix[0].length;j++){
				tempBlock = new Block(i,j);
				tempBlock.setVal(gameMatrix[i][j]);
				tempBlock.setH(Math.abs(i-inputX)+Math.abs(j-inputY));
				blocks.add(tempBlock);
				if(i==x && j==y){
					tempBlock.setG(0);
					tempBlock.setF(tempBlock.getG()+tempBlock.getH());
					openBlocks.add(tempBlock);
					currentBlock = tempBlock;
				}
			}
		}

//		System.out.println("start with:" +currentBlock.getX()+" "+currentBlock.getY());
		
		if(gameMatrix[inputX][inputY]!=2){
			System.out.println("Unwalkable path");
			return;
		}

		if(x==inputX && y==inputY){
			System.out.println("labor is already in target!");
			return;
		}

		while(!openBlocks.isEmpty()){
				int min = openBlocks.lastElement().getF();
				for(Block r : openBlocks){
					if(min>r.getF()){
						min = r.getF();
						currentBlock = r;
					}
				}

				openBlocks.remove(currentBlock);
				closeBlocks.add(currentBlock);

				if(closeBlocks.lastElement().getX()==inputX && closeBlocks.lastElement().getY()==inputY){
					
					System.out.println("inif");
					targetFound = true;
					System.out.println(closeBlocks.size());
					currentBlock.setParent(closeBlocks.get(closeBlocks.size()-2));
					targetBlock = currentBlock;
					break;
				}

				tempBlock = getBlock(blocks, currentBlock.getX()-1, currentBlock.getY()-1);
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+14);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
						if(tempBlock.getG() > currentBlock.getG() + 14){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 14);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}

				tempBlock = getBlock(blocks, currentBlock.getX(), currentBlock.getY()-1);
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+10);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
						if(tempBlock.getG() > currentBlock.getG() + 10){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 10);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}

				tempBlock = getBlock(blocks, currentBlock.getX()+1, currentBlock.getY()-1);
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+14);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
						if(tempBlock.getG() > currentBlock.getG() + 14){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 14);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}

				tempBlock = getBlock(blocks, currentBlock.getX()-1, currentBlock.getY());
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+10);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
//						System.out.println("IF555");
						if(tempBlock.getG() > currentBlock.getG() + 10){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 10);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}

				tempBlock = getBlock(blocks, currentBlock.getX()+1, currentBlock.getY());
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
//					System.out.println("if6");
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+10);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
						if(tempBlock.getG() > currentBlock.getG() + 10){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 10);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}

				tempBlock = getBlock(blocks, currentBlock.getX()-1, currentBlock.getY()+1);
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
//					System.out.println("if7");
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+14);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
						if(tempBlock.getG() > currentBlock.getG() + 14){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 14);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}

				tempBlock = getBlock(blocks, currentBlock.getX(), currentBlock.getY()+1);
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+10);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
						if(tempBlock.getG() > currentBlock.getG() + 10){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 10);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}

				tempBlock = getBlock(blocks, currentBlock.getX()+1, currentBlock.getY()+1);
				if(tempBlock.getVal() == 2 && !closeBlocks.contains(tempBlock)){
					if(!openBlocks.contains(tempBlock)){
						openBlocks.add(tempBlock);
						tempBlock.setParent(currentBlock);
						tempBlock.setG(currentBlock.getG()+14);
						tempBlock.setF(tempBlock.getG()+tempBlock.getH());						
					}else{
						if(tempBlock.getG() > currentBlock.getG() + 14){
							tempBlock.setParent(currentBlock);
							tempBlock.setG(currentBlock.getG() + 14);
							tempBlock.setF(tempBlock.getG()+tempBlock.getH());
						}
					}
				}
		}

		if(targetFound){
			Block parentz = targetBlock;
			while(parentz.getParent().getX()!=x || parentz.getParent().getY()!= y){
				movePathX.push(parentz.getX());
				movePathY.push(parent.getY());
				parentz = parentz.getParent();
			}
		}else{
			System.out.println("no path to the target");
		}
	}*/

	@Override
	public void showPanel() {
		
	}

	public void setTarget(int inputTargetX,int inputTargetY) {
		targetX = inputTargetX;
		targetY = inputTargetY;
	}

	
	public boolean isMoving(){
		return isMoving;
	}
	
	public Block getBlock(Vector<Block> inputBlocks,int x,int y){
		for(Block b : inputBlocks){
			if(b.getX()==x && b.getY() == y){
				return b;
			}
		}
		return null;
	}

	@Override
	public void hidePanel() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setX(int inputX) {
		x = inputX;
	}
	
	public void setY(int inputY) {
		y = inputY;
	}
}

class ClosedList{
	public int x;
	public int y;
	public int g;
	public int h;
	public int f;
}

class OpenList{
	public int x;
	public int y;
	public int f;
	public int g;
	public int h;
}

class AdjacentSquare{
	public int x;
	public int y;
	public int f;
	public int g;
	public int h;
}

class Block{
	
	private int x;
	private int y;
	private int f,g,h;
	private Block parent;
	private int val;

	public Block(int inputX,int inputY) {
		x = inputX;
		y = inputY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Block getParent() {
		return parent;
	}
	
	public int getF() {
		return f;
	}
	
	public int getG() {
		return g;
	}
	
	public int getH() {
		return h;
	}
	
	public int getVal() {
		return val;
	}
	
	public void setParent(Block parent) {
		this.parent = parent;
	}

	public void setF(int f) {
		this.f = f;
	}

	public void setG(int g) {
		this.g = g;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVal(int val) {
		this.val = val;
	}
}