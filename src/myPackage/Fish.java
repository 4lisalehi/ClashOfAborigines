package myPackage;

import javax.swing.ImageIcon;

import interfaces.Data;

public class Fish implements Data{

	private int x,y;
	private int paintX,paintY;
	private int fishes;
	private ImageIcon pic;
	private boolean isBig;
	public Fish(int inputX,int inputY,boolean inputIsBig) {
		x = inputX;
		y = inputY;
		paintX = x*50;
		paintY = y*50;
		pic = (isBig) ? new ImageIcon("pics/small_fish.png") : new ImageIcon("pics/big_fish.png");

		isBig = inputIsBig;
		fishes = (isBig) ? BIG_FISH_RESOURCE : SMALL_FISH_RESOURCE;
	}

	public void reduce(){
		fishes -= 100;
	}

	public boolean exists(){
		return (fishes>0);
	}
	
	public ImageIcon getPic(){
		return pic;
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
}