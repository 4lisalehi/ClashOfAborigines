package myPackage;

public class Pair <X,Y>{
	private X x;
	private Y y;
	
	public Pair(X inputX,Y inputY){
		x = inputX;
		y = inputY;
		
	}
	
	public X getX(){
		return x;
	}
	
	public Y getY(){
		return y;
	}
}