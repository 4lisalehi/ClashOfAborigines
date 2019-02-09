package myPackage;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Tile extends ImageIcon implements Serializable {

	private static final long serialVersionUID = 1L;

	private int[] layers;
	private int i, season = 1;
	int  value1, value2,value3;
	private String treeFile;
	private ImageIcon[] pics;

	public Tile() {
		treeFile = "pics/autumn_tree.png";

		layers = new int[4];
		pics = new ImageIcon[8];
		pics[0] = new ImageIcon("pics/deep_sea1.png");
		pics[1] = new ImageIcon("pics/shallow_sea1.png");
		pics[2] = new ImageIcon("pics/SpringIsland1.png");
		pics[3] = new ImageIcon(treeFile);
		pics[4] = new ImageIcon("pics/SpringIronMine1.png");
		pics[5] = new ImageIcon("pics/big_fish.png");
		pics[6] = new ImageIcon("pics/small_fish.png");
		i = 1;
		for (int i = 1; i < 4; i++) {
			layers[i] = -1;
		}
		layers[0] = 0;
	}

	public Tile(boolean f) {
		treeFile = "pics/autumn_tree.png";
		// deepSeaFile = "pics/deep_sea1.png";

		layers = new int[4];
		pics = new ImageIcon[7];
		pics[0] = new ImageIcon("pics/deep_sea1.png");
		pics[1] = new ImageIcon("pics/shallow_sea1.png");
		pics[2] = new ImageIcon("pics/SpringIsland1.png");
		pics[3] = new ImageIcon(treeFile);
		pics[4] = new ImageIcon("pics/SpringIronMine1.png");
		pics[5] = new ImageIcon("pics/big_fish.png");
		pics[6] = new ImageIcon("pics/small_fish.png");
		i = 1;
		for (int i = 1; i < 4; i++) {
			layers[i] = -1;
		}
		layers[0] = 0;
		layers[1] = 2;
	}

	public void drawTile() {

	}

	public ImageIcon getPic(int x) {
		return pics[x];
	}

	public int[] getLayers() {
		return layers;
	}

	public void setIslandPic(int value) {
		String path;
		value1 = value;
//		if (season == 4)
		switch(season){
			case 1:
				switch (value) {
				case 1:
					path = "pics/SpringIsland1.png";
					break;
				case 2:
					path = "pics/SpringIsland2.png";
					break;
				case 3:
					path = "pics/SpringIsland3.png";
					break;
				case 4:
					path = "pics/SpringIsland4.png";
					break;
				case 5:
					path = "pics/SpringIsland5.png";
					break;
				case 6:
					path = "pics/SpringIsland6.png";
					break;
				case 7:
					path = "pics/SpringIsland7.png";
					break;
				case 8:
					path = "pics/SpringIsland8.png";
					break;
				case 9:
					path = "pics/SpringIsland9.png";
					break;
				case 10:
					path = "pics/SpringIsland10.png";
					break;
				case 11:
					path = "pics/SpringIsland11.png";
					break;
				case 12:
					path = "pics/SpringIsland12.png";
					break;
				case 13:
					path = "pics/SpringIsland13.png";
					break;
				case 14:
					path = "pics/SpringIsland14.png";
					break;
				case 15:
					path = "pics/SpringIsland15.png";
					break;
				case 16:
					path = "pics/SpringIsland16.png";
					break;
				default:
					path = "pics/SpringIsland1.png";
					break;
				}				
				break;
			case 2:
				switch (value) {
				case 1:
					path = "pics/SummerIsland1.png";
					break;
				case 2:
					path = "pics/SummerIsland2.png";
					break;
				case 3:
					path = "pics/SummerIsland3.png";
					break;
				case 4:
					path = "pics/SummerIsland4.png";
					break;
				case 5:
					path = "pics/SummerIsland5.png";
					break;
				case 6:
					path = "pics/SummerIsland6.png";
					break;
				case 7:
					path = "pics/SummerIsland7.png";
					break;
				case 8:
					path = "pics/SummerIsland8.png";
					break;
				case 9:
					path = "pics/SummerIsland9.png";
					break;
				case 10:
					path = "pics/SummerIsland10.png";
					break;
				case 11:
					path = "pics/SummerIsland11.png";
					break;
				case 12:
					path = "pics/SummerIsland12.png";
					break;
				case 13:
					path = "pics/SummerIsland13.png";
					break;
				case 14:
					path = "pics/SummerIsland14.png";
					break;
				case 15:
					path = "pics/SummerIsland15.png";
					break;
				case 16:
					path = "pics/SummerIsland16.png";
					break;
				default:
					path = "pics/SummerIsland1.png";
					break;
				}
				break;
			case 3:
				switch (value) {
				case 1:
					path = "pics/FallIsland1.png";
					break;
				case 2:
					path = "pics/FallIsland2.png";
					break;
				case 3:
					path = "pics/FallIsland3.png";
					break;
				case 4:
					path = "pics/FallIsland4.png";
					break;
				case 5:
					path = "pics/FallIsland5.png";
					break;
				case 6:
					path = "pics/FallIsland6.png";
					break;
				case 7:
					path = "pics/FallIsland7.png";
					break;
				case 8:
					path = "pics/FallIsland8.png";
					break;
				case 9:
					path = "pics/FallIsland9.png";
					break;
				case 10:
					path = "pics/FallIsland10.png";
					break;
				case 11:
					path = "pics/FallIsland11.png";
					break;
				case 12:
					path = "pics/FallIsland12.png";
					break;
				case 13:
					path = "pics/FallIsland13.png";
					break;
				case 14:
					path = "pics/FallIsland14.png";
					break;
				case 15:
					path = "pics/FallIsland15.png";
					break;
				case 16:
					path = "pics/FallIsland16.png";
					break;
				default:
					path = "pics/FallIsland1.png";
					break;
				}
				break;
			case 4:
				switch (value) {
				case 1:
					path = "pics/WinterIsland1.png";
					break;
				case 2:
					path = "pics/WinterIsland2.png";
					break;
				case 3:
					path = "pics/WinterIsland3.png";
					break;
				case 4:
					path = "pics/WinterIsland4.png";
					break;
				case 5:
					path = "pics/WinterIsland5.png";
					break;
				case 6:
					path = "pics/WinterIsland6.png";
					break;
				case 7:
					path = "pics/WinterIsland7.png";
					break;
				case 8:
					path = "pics/WinterIsland8.png";
					break;
				case 9:
					path = "pics/WinterIsland9.png";
					break;
				case 10:
					path = "pics/WinterIsland10.png";
					break;
				case 11:
					path = "pics/WinterIsland11.png";
					break;
				case 12:
					path = "pics/WinterIsland12.png";
					break;
				case 13:
					path = "pics/WinterIsland13.png";
					break;
				case 14:
					path = "pics/WinterIsland14.png";
					break;
				case 15:
					path = "pics/WinterIsland15.png";
					break;
				case 16:
					path = "pics/WinterIsland16.png";
					break;
				default:
					path = "pics/WinterIsland1.png";
					break;
				}
				break;
				default:
					path = "pics/SpringIsland1.png";
					break;
		}
		pics[2] = new ImageIcon(path);
	}

	public void setShallowSeaPic(int value) {
		String path;
		value2 = value;

		if (season == 4)
			switch (value) {
			case 1:
				path = "pics/shallow_sea1.png";
				break;
			case 2:
				path = "pics/shallow_sea2.png";
				break;
			case 3:
				path = "pics/shallow_sea3.png";
				break;
			case 4:
				path = "pics/shallow_sea4.png";
				break;
			case 5:
				path = "pics/shallow_sea5.png";
				break;
			case 6:
				path = "pics/shallow_sea6.png";
				break;
			case 7:
				path = "pics/shallow_sea7.png";
				break;
			case 8:
				path = "pics/shallow_sea8.png";
				break;
			case 9:
				path = "pics/shallow_sea9.png";
				break;
			case 10:
				path = "pics/shallow_sea10.png";
				break;
			case 11:
				path = "pics/shallow_sea11.png";
				break;
			case 12:
				path = "pics/shallow_sea12.png";
				break;
			case 13:
				path = "pics/shallow_sea13.png";
				break;
			case 14:
				path = "pics/shallow_sea14.png";
				break;
			case 15:
				path = "pics/shallow_sea15.png";
				break;
			case 16:
				path = "pics/shallow_sea16.png";
				break;
			default:
				path = "pics/shallow_sea1.png";
				break;
			}
		else
			switch (value) {
			case 1:
				path = "pics/shallow_sea1.png";
				break;
			case 2:
				path = "pics/shallow_sea2.png";
				break;
			case 3:
				path = "pics/shallow_sea3.png";
				break;
			case 4:
				path = "pics/shallow_sea4.png";
				break;
			case 5:
				path = "pics/shallow_sea5.png";
				break;
			case 6:
				path = "pics/shallow_sea6.png";
				break;
			case 7:
				path = "pics/shallow_sea7.png";
				break;
			case 8:
				path = "pics/shallow_sea8.png";
				break;
			case 9:
				path = "pics/shallow_sea9.png";
				break;
			case 10:
				path = "pics/shallow_sea10.png";
				break;
			case 11:
				path = "pics/shallow_sea11.png";
				break;
			case 12:
				path = "pics/shallow_sea12.png";
				break;
			case 13:
				path = "pics/shallow_sea13.png";
				break;
			case 14:
				path = "pics/shallow_sea14.png";
				break;
			case 15:
				path = "pics/shallow_sea15.png";
				break;
			case 16:
				path = "pics/shallow_sea16.png";
				break;
			default:
				path = "pics/shallow_sea1.png";
				break;
			}
		pics[1] = new ImageIcon(path);
	}

	public void setIronMinePic(int value){
		String path = "pics/IronMine1.png";
		value3 = value;
		switch(season){
			case 1:
				if(value== 1){
					path ="pics/SpringIronMine1.png";
				} else if(value== 2){
					path ="pics/SpringIronMine2.png";
				} else if(value== 3){
					path ="pics/SpringIronMine3.png";
				} else if(value== 4){
					path ="pics/SpringIronMine4.png";
				}
				break;
			case 2:
				if(value== 1){
					path ="pics/SummerIronMine1.png";
				} else if(value== 2){
					path ="pics/SummerIronMine2.png";
				} else if(value== 3){
					path ="pics/SummerIronMine3.png";
				} else if(value== 4){
					path ="pics/SummerIronMine4.png";
				}
				break;
			case 3:
				if(value== 1){
					path ="pics/FallIronMine1.png";
				} else if(value== 2){
					path ="pics/FallIronMine2.png";
				} else if(value== 3){
					path ="pics/FallIronMine3.png";
				} else if(value== 4){
					path ="pics/FallIronMine4.png";
				}
				break;
			case 4:
				if(value== 1){
					path ="pics/WinterIronMine1.png";
				} else if(value == 2){
					path ="pics/WinterIronMine2.png";
				} else if(value== 3){
					path ="pics/WinterIronMine3.png";
				} else if(value== 4){
					path ="pics/WinterIronMine4.png";
				}
				break;
				default:
					if(value== 1){
						path ="pics/FallIronMine1.png";
					} else if(value== 2){
						path ="pics/FallIronMine2.png";
					} else if(value== 3){
						path ="pics/FallIronMine3.png";
					} else if(value== 4){
						path ="pics/FallIronMine4.png";
					}
					break;
		}
		pics[4] = new ImageIcon(path);
	}

	public void setTreeFile(String treeFile) {
		this.treeFile = treeFile;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getTopLayer() {
		return layers[i - 1];
	}

	public void setLayer(int index, int value) {
		if (value == 1 || value == 0) {
			layers[index - 1] = value;
			i--;
		} else {
			layers[index] = value;
		}
	}

	public void removeLayer() {
		if (i > 1) {
			layers[i - 1] = -1;
			i--;
		} else if (i == 1 && layers[i - 1] == 1) {
			layers[i - 1] = 0;
		}
	}

	public int getI() {
		return i;
	}

	public void setLayers(int[] layers) {
		this.layers = layers;
	}

	public boolean checkTile(int itemID) {
		boolean flag = true;
		switch (layers[i - 1]) {
		case 0:
			if (itemID == 3 || itemID == 4) {
				flag = false;
			}
			break;
		case 1:
			if (itemID == 3 || itemID == 4 || itemID == 5) {
				flag = false;
			}
			break;
		case 2:
			if (itemID == 5 || itemID == 6 || itemID == 0 || itemID == 1) {
				flag = false;
			}
			break;
		case 3:
			if (itemID != 7)
				flag = false;
			break;
		case 4:
			if (itemID != 7)
				flag = false;
			break;
		case 5:
			if (itemID != 7)
				flag = false;
			break;
		case 6:
			if (itemID != 7)
				flag = false;
			break;
		}
		return flag;
	}

	public void setSeason(int season) {
		this.season = season;
		this.setIslandPic(value1);
		this.setShallowSeaPic(value2);
		this.setIronMinePic(value3);
	}
	
}
