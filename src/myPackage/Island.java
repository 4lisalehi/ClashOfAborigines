package myPackage;

import javax.swing.ImageIcon;

public class Island {
	
	private ImageIcon[] pics;
	
	public Island(int position) {
		pics = new ImageIcon[4];
		switch(position){
			case -1:
				pics[0] = new ImageIcon("pics/base33.png");
				pics[1] = new ImageIcon("pics/base33.png");
				pics[2] = new ImageIcon("pics/base33.png");
				pics[3] = new ImageIcon("pics/base33.png");
				break;
			case 1:
				pics[0] = new ImageIcon("pics/SpringIsland1.png");
				pics[1] = new ImageIcon("pics/SummerIsland1.png");
				pics[2] = new ImageIcon("pics/FallIsland1.png");
				pics[3] = new ImageIcon("pics/WinterIsland1.png");
				break;
			case 2:
				pics[0] = new ImageIcon("pics/SpringIsland2.png");
				pics[1] = new ImageIcon("pics/SummerIsland2.png");
				pics[2] = new ImageIcon("pics/FallIsland2.png");
				pics[3] = new ImageIcon("pics/WinterIsland2.png");
				break;
			case 3:
				pics[0] = new ImageIcon("pics/SpringIsland3.png");
				pics[1] = new ImageIcon("pics/SummerIsland3.png");
				pics[2] = new ImageIcon("pics/FallIsland3.png");
				pics[3] = new ImageIcon("pics/WinterIsland3.png");
				break;
			case 4:
				pics[0] = new ImageIcon("pics/SpringIsland4.png");
				pics[1] = new ImageIcon("pics/SummerIsland4.png");
				pics[2] = new ImageIcon("pics/FallIsland4.png");
				pics[3] = new ImageIcon("pics/WinterIsland4.png");
				break;
			case 5:
				pics[0] = new ImageIcon("pics/SpringIsland5.png");
				pics[1] = new ImageIcon("pics/SummerIsland5.png");
				pics[2] = new ImageIcon("pics/FallIsland5.png");
				pics[3] = new ImageIcon("pics/WinterIsland5.png");
				break;
			case 6:
				pics[0] = new ImageIcon("pics/SpringIsland6.png");
				pics[1] = new ImageIcon("pics/SummerIsland6.png");
				pics[2] = new ImageIcon("pics/FallIsland6.png");
				pics[3] = new ImageIcon("pics/WinterIsland6.png");
				break;
			case 7:
				pics[0] = new ImageIcon("pics/SpringIsland7.png");
				pics[1] = new ImageIcon("pics/SummerIsland7.png");
				pics[2] = new ImageIcon("pics/FallIsland7.png");
				pics[3] = new ImageIcon("pics/WinterIsland7.png");
				break;
			case 8:
				pics[0] = new ImageIcon("pics/SpringIsland8.png");
				pics[1] = new ImageIcon("pics/SummerIsland8.png");
				pics[2] = new ImageIcon("pics/FallIsland8.png");
				pics[3] = new ImageIcon("pics/WinterIsland8.png");
				break;
			case 9:
				pics[0] = new ImageIcon("pics/SpringIsland9.png");
				pics[1] = new ImageIcon("pics/SummerIsland9.png");
				pics[2] = new ImageIcon("pics/FallIsland9.png");
				pics[3] = new ImageIcon("pics/WinterIsland9.png");
				break;
			case 10:
				pics[0] = new ImageIcon("pics/SpringIsland10.png");
				pics[1] = new ImageIcon("pics/SummerIsland10.png");
				pics[2] = new ImageIcon("pics/FallIsland10.png");
				pics[3] = new ImageIcon("pics/WinterIsland10.png");
				
				break;
			case 11:
				pics[0] = new ImageIcon("pics/SpringIsland11.png");
				pics[1] = new ImageIcon("pics/SummerIsland11.png");
				pics[2] = new ImageIcon("pics/FallIsland11.png");
				pics[3] = new ImageIcon("pics/WinterIsland11.png");
				break;
			case 12:
				pics[0] = new ImageIcon("pics/SpringIsland12.png");
				pics[1] = new ImageIcon("pics/SummerIsland12.png");
				pics[2] = new ImageIcon("pics/FallIsland12.png");
				pics[3] = new ImageIcon("pics/WinterIsland12.png");
				break;
			case 13:
				pics[0] = new ImageIcon("pics/SpringIsland13.png");
				pics[1] = new ImageIcon("pics/SummerIsland13.png");
				pics[2] = new ImageIcon("pics/FallIsland13.png");
				pics[3] = new ImageIcon("pics/WinterIsland13.png");
				break;
			case 14:
				pics[0] = new ImageIcon("pics/SpringIsland14.png");
				pics[1] = new ImageIcon("pics/SummerIsland14.png");
				pics[2] = new ImageIcon("pics/FallIsland14.png");
				pics[3] = new ImageIcon("pics/WinterIsland14.png");
				break;
			case 15:
				pics[0] = new ImageIcon("pics/SpringIsland15.png");
				pics[1] = new ImageIcon("pics/SummerIsland15.png");
				pics[2] = new ImageIcon("pics/FallIsland15.png");
				pics[3] = new ImageIcon("pics/WinterIsland15.png");
				break;
			case 16:
				pics[0] = new ImageIcon("pics/SpringIsland16.png");
				pics[1] = new ImageIcon("pics/SummerIsland16.png");
				pics[2] = new ImageIcon("pics/FallIsland16.png");
				pics[3] = new ImageIcon("pics/WinterIsland16.png");
				break;
		}
	}
	
	public ImageIcon getPic(int inputSeason){
		return pics[inputSeason];
	}
}
