package myPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorPanel extends JPanel implements Serializable,Runnable{

	private static final long serialVersionUID = 1L;
	GameFrame parent;
	Menu parentMenu;
	private int itemID;
	private Tile[][] map;
	public int mapsize, startPositionX = 1, startPositionY = 1;
	JButton showSidePanel;
	Dimension dim;
	protected int matrixWidth=29,matrixHeight=16;
	public EditorPanel(Menu inputMenu,GameFrame gf,int mapSize){
		parent = gf;
		parentMenu = inputMenu;
		this.mapsize = mapSize;
		itemID = 0;
		showSidePanel = new JButton();
		showSidePanel.setSize(20,80);
		showSidePanel.setLocation(1340, 320);
		this.add(showSidePanel);
		showSidePanel.setVisible(false);
		map = new Tile[(matrixWidth*mapsize)+2][(matrixHeight*mapsize)+2];
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){
				if(i != 0 && j != 0 && i != (map.length-1) && j != (map[0].length-1)){
					map[i][j] = new Tile();
				}else{
					map[i][j] = new Tile(true);
				}
			}
		}
		//deep_sea: 0
		//shallow_sea : 1
		//island : 2
		//tree : 3
		//mine : 4
		//big fish: 5
		//small fish: 6
		//eraser : 7
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLayout(null);
		setLocation(0,0);
		setSize(dim.width,dim.height);
		setBackground(Color.BLUE);
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
//				System.out.println(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI());
				if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getLayers()[map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI()-1] != itemID && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].checkTile(itemID)){
					if(itemID==2){
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
						
					}else if(itemID==1){
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
					}else if(itemID==7){
						if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getTopLayer()==4){
							map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].removeLayer();
							if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==1 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].value3==2 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].value3==3 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].value3 == 4){

								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].removeLayer();
								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].removeLayer();
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].removeLayer();

							}else if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==2 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].value3==1 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].value3==3 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].value3 == 4){
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].removeLayer();
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].removeLayer();
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].removeLayer();
							}else if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==4 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].value3==1 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].value3==2 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].value3 == 3){
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].removeLayer();
							}else if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==3 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].value3==4 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].value3==2 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].value3 == 1){
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].removeLayer();							
							}
						}else{
							map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].removeLayer();
						}
					}else if(itemID == 4){
						setIronMine((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY);
					}
					if(itemID != 7 && itemID != 4){
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setLayer(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI(), itemID);
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setI(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI()+1);
//						System.out.println(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI());
					}
					
					try{
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY+1));
					
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY+1));
					
					}catch(ArrayIndexOutOfBoundsException e1){
						e1.printStackTrace();
					}
				}
				parent.h3.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getLayers()[map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI()-1] != itemID && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].checkTile(itemID)){
					if(itemID==2){
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
						
					}else if(itemID==1){
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
					}else if(itemID==7){
						if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getTopLayer()==4){
							map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].removeLayer();
							if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==1 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].value3==2 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].value3==3 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].value3 == 4){

								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].removeLayer();
								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].removeLayer();
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].removeLayer();

							}else if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==2 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].value3==1 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].value3==3 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].value3 == 4){
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].removeLayer();
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].removeLayer();
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].removeLayer();
							}else if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==4 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].value3==1 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].value3==2 && map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].value3 == 3){
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].removeLayer();
							}else if(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].value3==3 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].value3==4 && map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].value3==2 && map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].value3 == 1){
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].removeLayer();
								map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].removeLayer();							
							}
						}else{
							map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].removeLayer();
						}
					}else if(itemID == 4){
						setIronMine((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY);
					}
					if(itemID != 7 && itemID != 4){
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setLayer(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI(), itemID);
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setI(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI()+1);
//						System.out.println(map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].getI());
					}
					
					try{
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].setIslandPic(checkNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY+1));
					
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY+1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY+1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY+1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY));
						map[(e.getX()/50)+startPositionX][(e.getY()/50)+startPositionY-1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY-1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX+1][(e.getY()/50)+startPositionY-1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX+1, (e.getY()/50)+startPositionY-1));
						map[(e.getX()/50)+startPositionX-1][(e.getY()/50)+startPositionY+1].setShallowSeaPic(checkSeaNeighbors((e.getX()/50)+startPositionX-1, (e.getY()/50)+startPositionY+1));
					
					}catch(ArrayIndexOutOfBoundsException e1){
					
					}
				}
				parent.h3.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {

				if(e.getX()>=0 && e.getX()<10){
					
				}
				
				if(e.getY()>=0 && e.getY()<10){
					
				}
				
				if(e.getX()<=1000 && e.getX()>990){
					
				}
				
				if(e.getY()<=1000 && e.getY()>990){
					
				}
			}
		});
		
		
	}
	
	public int checkSeaNeighbors(int i, int j){
		int state = 0;
		if(i != 0 && j != 0 && i != matrixWidth*mapsize+1 && j != matrixHeight*mapsize+1){
			if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] !=1){
				state = 1;
			}else if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] ==1){
				state = 2;
			}else if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] !=1){
				state = 6;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] !=1){
				state = 4;
			}else if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] !=1){
				state = 5;
			}else if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] ==1){
				state = 3;
			}else if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] ==1){
				state = 7;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] !=1){
				state = 8;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] !=1){
				state = 9;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] ==1){
				state = 10;
			}else if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] ==1){
				state = 11;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] ==1){
				state = 12;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] !=1){
				state = 13;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] ==1){
				state = 14;
			}else if(map[i-1][j].getLayers()[0] !=1 && map[i][j-1].getLayers()[0] ==1 && map[i][j+1].getLayers()[0] ==1 && map[i+1][j].getLayers()[0] !=1){
				state = 15;
			}else if(map[i-1][j].getLayers()[0] ==1 && map[i][j-1].getLayers()[0] !=1 && map[i][j+1].getLayers()[0] !=1 && map[i+1][j].getLayers()[0] ==1){
				state = 16;
			}
		}
		return state;
	}
	
	public int checkNeighbors(int i,int j){
		int state = 0;
		if(i != 0 && j != 0 && i != matrixWidth*mapsize+1 && j != matrixHeight*mapsize+1){
			if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] !=2){
				state = 1;
			}else if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] ==2){
				state = 2;
			}else if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] !=2){
				state = 3;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] !=2){
				state = 4;
			}else if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] !=2){
				state = 5;
			}else if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] ==2){
				state = 6;
			}else if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] ==2){
				state = 7;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] !=2){
				state = 8;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] !=2){
				state = 9;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] ==2){
				state = 10;
			}else if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] ==2){
				state = 11;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] ==2){
				state = 12;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] !=2){
				state = 13;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] ==2){
				state = 14;
			}else if(map[i-1][j].getLayers()[1] !=2 && map[i][j-1].getLayers()[1] ==2 && map[i][j+1].getLayers()[1] ==2 && map[i+1][j].getLayers()[1] !=2){
				state = 15;
			}else if(map[i-1][j].getLayers()[1] ==2 && map[i][j-1].getLayers()[1] !=2 && map[i][j+1].getLayers()[1] !=2 && map[i+1][j].getLayers()[1] ==2){
				state = 16;
			}
		}
		return state;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public Tile[][] loadFile() {
		int mapWidth, mapHeight;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ser & txt Files", "ser", "txt");
		chooser.setFileFilter(filter);
		String userhome = System.getProperty("user.home");
		 chooser = new JFileChooser(userhome +"\\workspace\\FinalGame\\Saves");
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
		    try {
				FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
				ObjectInputStream in = new ObjectInputStream(fis);
//				int [][][] matrix = new int[matrixWidth*mapsize+2][matrixHeight*mapsize+2][4];
				int[][][] matrix = (int[][][])in.readObject();
				mapWidth = matrix.length;
				mapHeight = matrix[0].length;
				if((mapHeight-2)/mapsize != 16 || (mapWidth-2)/mapsize != 29 ){
					mapsize = (mapHeight-2)/15;
					
					map = new Tile[(matrixWidth*mapsize)+2][(matrixHeight*mapsize)+2];
					for(int i=0;i<map.length;i++){
						for(int j=0;j<map[i].length;j++){
							if(i != 0 && j != 0 && i != (map.length-1) && j != (map[0].length-1)){
								map[i][j] = new Tile();
							}else{
								map[i][j] = new Tile(true);
							}
						}
					}
				}
				
				
				
				for(int i=0;i<map.length;i++){
					for(int j=0;j<map[i].length;j++){
						map[i][j].setLayers(matrix[i][j]);
						for(int k =0; k<4;k++){
							if(map[i][j].getLayers()[k] != -1){
								map[i][j].setI(k+1);
							}
						}
						map[i][j].value3 = matrix[i][j][4];
					}
				}
				
				for(int i=0;i<map.length;i++){
					for(int j=0;j<map[i].length;j++){
						try{
							map[i+1][j+1].setIslandPic(checkNeighbors(i+1, j+1));
							map[i+1][j+2].setIslandPic(checkNeighbors(i+1, j+2));
							map[i+2][j+1].setIslandPic(checkNeighbors(i+2, j+1));
							map[i+2][j+2].setIslandPic(checkNeighbors(i+2, j+2));
							map[i+0][j+1].setIslandPic(checkNeighbors(i+0, j+1));
							map[i+1][j+0].setIslandPic(checkNeighbors(i+1, j+0));
							map[i+0][j+0].setIslandPic(checkNeighbors(i+0, j+0));
							map[i+2][j+0].setIslandPic(checkNeighbors(i+2, j+0));
							map[i+0][j+2].setIslandPic(checkNeighbors(i+0, j+2));
//							System.out.println(map[i+1][j+1].value3);
							if(map[i+1][j+1].getLayers()[2] == 4 && map[i+1][j+1].value3==1){
								System.out.println("kajsdijasd");
								setIronMine(i+1, j+1);
							}
						}catch(ArrayIndexOutOfBoundsException e1){
							
						}
					}
				}
				parentMenu.parentSP.setMiniDim((mapsize==3) ? (int)Math.ceil(50d/(6*mapsize)) : (50/(6*mapsize)));
				startPositionX = 1;
				startPositionY = 1;
				parent.h3.repaint();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public void saveFile() {
		 JFileChooser jf = new JFileChooser();
		 String userhome = System.getProperty("user.home");
		 jf = new JFileChooser(userhome +"\\workspace\\FinalGame\\Saves");
		 int c = jf.showSaveDialog(this);
		 if(c == JFileChooser.APPROVE_OPTION){
			try {
				int [][][] matrix = new int[matrixWidth*mapsize+2][matrixHeight*mapsize+2][5];
				for(int i=0;i<map.length;i++){
					for(int j=0;j<map[i].length;j++){
						for(int k=0;k<4;k++){
							matrix[i][j][k] = map[i][j].getLayers()[k];
						}
						matrix[i][j][4] = map[i][j].value3;
					}
				}
				FileOutputStream fos = new FileOutputStream(jf.getSelectedFile());
				ObjectOutputStream out = new ObjectOutputStream(fos);
				out.writeObject(matrix);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}
	public void setMap(Tile[][] map) {
		this.map = map;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(int i=startPositionX;i<(startPositionX + matrixWidth)-1;i++){
			for(int j=startPositionY;j<(startPositionY + matrixHeight)-1;j++){
				for(int k = 0; k<map[i][j].getI();k++){
					g.drawImage(map[i][j].getPic(map[i][j].getLayers()[k]).getImage(),(i-startPositionX)*50,(j-startPositionY)*50,null);
				}
			}
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(parent.jtp.getSelectedIndex()==2){
/*				if(parent.jtp.getMousePosition()==null)
					continue;
				if(parent.jtp.getMousePosition().x >1350){
					if(startPositionX<map.length - matrixWidth)
						startPositionX++;
				}
				
				if(parent.jtp.getMousePosition().x < 10){
					if(startPositionX > 1)
						startPositionX--;
				}
				
				if(parent.jtp.getMousePosition().y < 10){
					if(startPositionY>1)
						startPositionY--;
				}*/
			try{
				if(getMousePosition()==null)
					continue;
				if(getMousePosition().x < 10){
					if(startPositionX > 1){
						startPositionX--;
						parentMenu.parentSP.mousePosX -= (parentMenu.parentSP.getMiniDim());
					}
				}
				if(getMousePosition().y < 10){
					if(startPositionY>1){
						startPositionY--;
						parentMenu.parentSP.mousePosY -= (parentMenu.parentSP.getMiniDim());
					}
				}
				
				if(getMousePosition().x > 1350){
					if(startPositionX<map.length - matrixWidth){
						startPositionX++;
						parentMenu.parentSP.mousePosX += (parentMenu.parentSP.getMiniDim());
					}
						
				}
					if(getMousePosition().y > 630){
						if(startPositionY<(map[0].length-matrixHeight)){
							startPositionY++;
							parentMenu.parentSP.mousePosY += (parentMenu.parentSP.getMiniDim());
						}	
					}
				}catch(NullPointerException e){
					
				}
				parent.h3.repaint();
			}
		}
	}
	
	public Tile[][] getMap(){
		return map;
	}
	
	public void setIronMine(int i, int j){
		map[i][j].value3 = 1;
		if(map[i+1][j].getTopLayer() == 2 && map[i][j+1].getTopLayer() == 2 && map[i+1][j+1].getTopLayer() == 2){			
			map[i][j].setLayer(map[i][j].getI(), 4);
			map[i][j].setI(map[i][j].getI()+1);
			map[i][j].setIronMinePic(1);
			
			map[i+1][j].setLayer(map[i+1][j].getI(), 4);
			map[i+1][j].setI(map[i+1][j].getI()+1);
			map[i+1][j].setIronMinePic(2);

			map[i+1][j+1].setLayer(map[i+1][j+1].getI(), 4);
			map[i+1][j+1].setI(map[i+1][j+1].getI()+1);
			map[i+1][j+1].setIronMinePic(3);

			map[i][j+1].setLayer(map[i][j+1].getI(), 4);
			map[i][j+1].setI(map[i][j+1].getI()+1);
			map[i][j+1].setIronMinePic(4);
		}else if(map[i+1][j].getTopLayer() == 4 && map[i][j+1].getTopLayer() == 4 && map[i+1][j+1].getTopLayer() == 4){
			map[i][j].setLayer(map[i][j].getI(), 4);
			map[i][j].setI(map[i][j].getI()+1);
			map[i][j].setIronMinePic(1);

			map[i+1][j].setLayer(map[i+1][j].getI(), 4);
			map[i+1][j].setI(map[i+1][j].getI()+1);
			map[i+1][j].setIronMinePic(2);

			map[i+1][j+1].setLayer(map[i+1][j+1].getI(), 4);
			map[i+1][j+1].setI(map[i+1][j+1].getI()+1);
			map[i+1][j+1].setIronMinePic(3);

			map[i][j+1].setLayer(map[i][j+1].getI(), 4);
			map[i][j+1].setI(map[i][j+1].getI()+1);			
			map[i][j+1].setIronMinePic(4);
		}
	}
}