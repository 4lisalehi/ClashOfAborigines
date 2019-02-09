package myPackage;

import java.util.Random;
import java.util.Stack;

import javax.swing.ImageIcon;

import interfaces.Data;
import interfaces.Selectable;

public class FishingShip extends Ship implements Data,Runnable,Selectable {

	private GamePanel parent;
	private int[][] gameMatrix;
	private ImageIcon[] pics;
	private ImageIcon pic;
//	private boolean isSelected;
	private boolean isAlive;
	private boolean isMoving;
	private boolean isFishing;
	private int xStack;
	private int yStack;
	private final int capacity;
	private int loadedfish;
	private int targetX,targetY;
	private int tempX,tempY;
	private int direction;
	private int runInput;
	private Stack<Integer> movePathX;
	private Stack<Integer> movePathY;
	private int x,y;
	private int paintX,paintY;
	private float health;
	private int speed;
	private final int maxHealth;
	private Fish opponentFish;
	Country country;

	public FishingShip(int inputX,int inputY,GamePanel gp, Country country) {
		this.country = country;
		parent = gp;
		isMoving = false;
		gameMatrix = parent.getGameMatrix();
		pics = new ImageIcon[4];
		pics[0] = new ImageIcon("pics/fship1.png");
		pics[1] = new ImageIcon("pics/fship2.png");
		pics[2] = new ImageIcon("pics/fship3.png");
		pics[3] = new ImageIcon("pics/fship4.png");
		pic = new ImageIcon("pics/fship1.png");
//		isSelected = false;
		isAlive = true;
		loadedfish = 0;
		capacity = FSHIP_CAPACITY;
		
		x = inputX;
		y = inputY;

		paintX = x*10;
		paintY = y*10;

		speed = 1;
		movePathX = new Stack<Integer>();
		movePathY = new Stack<Integer>();
		health = FSHIP_HEALTH;
		maxHealth = FSHIP_HEALTH;
	}
	
	public ImageIcon getPic() {
		return pic;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void hurt(){
		health -= 100;
	}
	
	public boolean Live(){
		return (health>0);
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setRunInput(int input){
		runInput = input;
	}

	public void setOpponent(Fish ff){
		opponentFish = ff;
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

	public void fish(Fish f){
		if(loadedfish<capacity-100){
			int[] tempz = getClosestNeighbor(f.getX(), f.getY());
			setTarget(tempz[0],tempz[1]);
			move();
			while(f.exists()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				f.reduce();
				loadedfish+=100;
			}
		}
	}
	
	public Country getCountry() {
		return country;
	}

	public void setTarget(int inputTargetX,int inputTargetY) {
		targetX = inputTargetX;
		targetY = inputTargetY;
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
	
	public void releaseToBase(){
		int[] tempz = getClosestNeighbor(country.getBaseX(), country.getBaseY());
		setTarget(tempz[0], tempz[1]);
		move();
		country.incrementFoodResource((int)loadedfish);
		loadedfish = 0;
	}

	public void setPic(int inputX){
		switch(direction){
		case 1:
			pic = new ImageIcon("pics/fship4.png");
			break;
		case 2:
			pic = new ImageIcon("pics/fship1.png");
			break;
		case 3:
			pic = new ImageIcon("pics/fship2.png");
			break;
		case 4:
			pic = new ImageIcon("pics/fship3.png");
			break;
		default:
			Random r;
			int c;
			r = new Random();
			c = r.nextInt(2);
			if(c == 0)
				pic = new ImageIcon("pics/fship5.png");
			else if(c == 1)
				pic = new ImageIcon("pics/fship6.png");
			break;
		}
	}
	
	public boolean isAlive(){
		return isAlive;
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

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		switch(runInput){
			case 1:
				if(isMoving || isFishing){
					isMoving = false;
					isFishing = false;
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
				if(isMoving || isFishing){
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					fish(opponentFish);
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
			case 3:
				if(isMoving || isFishing){
					movePathX.removeAllElements();
					movePathY.removeAllElements();
					Thread.currentThread().stop();
					(new Thread(this)).start();
				}else{
					releaseToBase();
					movePathX.removeAllElements();
					movePathY.removeAllElements();
				}
				break;
		}
	}

	@Override
	public void showPanel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hidePanel() {
		// TODO Auto-generated method stub
		
	}
}
