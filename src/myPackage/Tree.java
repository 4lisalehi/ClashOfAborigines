package myPackage;

/*import java.util.Timer;
import java.util.TimerTask;*/

import javax.swing.ImageIcon;

import interfaces.*;

public class Tree implements Data,Selectable{
	private ImageIcon[] trees;
	private int woods;
	private int x,y;
	private int paintX,paintY;

	public Tree(int inputX,int inputY){
		trees = new ImageIcon[8];
		trees[0] = new ImageIcon("pics/gametree1.png");
		trees[1] = new ImageIcon("pics/gametree2.png");
		trees[2] = new ImageIcon("pics/gametree3.png");
		trees[3] = new ImageIcon("pics/gametree4.png");
		trees[4] = new ImageIcon("pics/cuttree1.png");
		trees[5] = new ImageIcon("pics/cuttree2.png");
		trees[6] = new ImageIcon("pics/cuttree3.png");
		trees[7] = new ImageIcon("pics/cuttree4.png");
		woods = TREEWOODS;
		x = inputX;
		y = inputY;
		paintX = x*50;
		paintY = y*50;
	}

	public ImageIcon getPic(int index){
		return trees[index];
	}

	public int getWoods(){
		return woods;
	}

	public void setWoods(int input){
		woods = input;
	}

	public int getX(){
		return x;
	}


	public int getY() {
		return y;
	}
	
	public void reduceWoods(){
		woods -= 2;
	}
	
	public int getPaintX() {
		return paintX;
	}
	
	public int getPaintY() {
		return paintY;
	}
	
	public boolean exists(){
		return (woods>0);
	}

	@Override
	public void move() {
		
	}

	@Override
	public void showPanel() {
		
	}
	
	public void increaseWoods(){
		woods += 1000;
	}

	@Override
	public void hidePanel() {
		
	}
	
}
