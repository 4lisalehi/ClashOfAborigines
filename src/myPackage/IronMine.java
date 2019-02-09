package myPackage;

import javax.swing.ImageIcon;
import interfaces.Data;

public class IronMine implements Data{

	private int x,y;
	private int paintX,paintY;
	private int irons;
	private ImageIcon[][] pics;

	public IronMine(int inputX,int inputY) {
		x = inputX;
		y = inputY;
		paintX = x*50;
		paintY = y*50;
		irons = IRONMINE;
		pics = new ImageIcon[4][4];
		pics[0][0] = new ImageIcon("pics/SpringIronMine1.png");
		pics[0][1] = new ImageIcon("pics/SpringIronMine2.png");
		pics[0][2] = new ImageIcon("pics/SpringIronMine3.png");
		pics[0][3] = new ImageIcon("pics/SpringIronMine4.png");
		pics[1][0] = new ImageIcon("pics/SummerIronMine1.png");
		pics[1][1] = new ImageIcon("pics/SummerIronMine2.png");
		pics[1][2] = new ImageIcon("pics/SummerIronMine3.png");
		pics[1][3] = new ImageIcon("pics/SummerIronMine4.png");
		pics[2][0] = new ImageIcon("pics/FallIronMine1.png");
		pics[2][1] = new ImageIcon("pics/FallIronMine2.png");
		pics[2][2] = new ImageIcon("pics/FallIronMine3.png");
		pics[2][3] = new ImageIcon("pics/FallIronMine4.png");
		pics[3][0] = new ImageIcon("pics/WinterIronMine1.png");
		pics[3][1] = new ImageIcon("pics/WinterIronMine2.png");
		pics[3][2] = new ImageIcon("pics/WinterIronMine3.png");
		pics[3][3] = new ImageIcon("pics/WinterIronMine4.png");
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ImageIcon getPic(int season,int inputx){
		return pics[season][inputx];
	}
	
	public int getPaintX() {
		return paintX;
	}
	
	public int getPaintY() {
		return paintY;
	}
	
	public int getIrons() {
		return irons;
	}
	
	public void reduceIrons(){
		irons -= 40;
	}
	
	public boolean exists(){
		return (irons>0);
	}
}
