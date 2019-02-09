package myPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Preview extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	EditorPanel parent;
	int cnt = 1;
	
	Timer timer;
	boolean flag;
	Dimension dim;
	private Tile[][] previewMap;
	TimerTask myTask;
	int mapsize;
	int startPositionX;
	int startPositionY;
	JButton showSidePanel;
	public Preview(EditorPanel ep) {
		parent = ep;
		showSidePanel = new JButton();
		showSidePanel.setSize(20,80);
		showSidePanel.setLocation(1340, 320);
		this.add(showSidePanel);
		mapsize = parent.mapsize;
		setLayout(null);
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(0,0);
		setSize(dim.width,dim.height);
		setBackground(Color.BLUE);
		startPositionX=1;
		startPositionY=1;
		previewMap = new Tile[(parent.matrixWidth*parent.mapsize)+2][(parent.matrixHeight*parent.mapsize)+2];
		for(int i=0;i<previewMap.length;i++){
			for(int j=0;j<previewMap[i].length;j++){
				if(i != 0 && j != 0 && i != (previewMap.length-1) && j != (previewMap[0].length-1)){
					previewMap[i][j] = new Tile();
					previewMap[i][j].setLayers(parent.getMap()[i][j].getLayers());
					previewMap[i][j].setI(parent.getMap()[i][j].getI());
					previewMap[i][j].setIslandPic(parent.getMap()[i][j].value1);
					previewMap[i][j].setShallowSeaPic(parent.getMap()[i][j].value2);
					previewMap[i][j].setIronMinePic(parent.getMap()[i][j].value3);
				}else{
					previewMap[i][j] = new Tile(true);
					previewMap[i][j].setLayers(parent.getMap()[i][j].getLayers());
					previewMap[i][j].setI(parent.getMap()[i][j].getI());
					previewMap[i][j].setIslandPic(parent.getMap()[i][j].value1);
					previewMap[i][j].setShallowSeaPic(parent.getMap()[i][j].value2);
					previewMap[i][j].setIronMinePic(parent.getMap()[i][j].value3);
				}
			}
		}

		timer = new Timer();
		myTask = new TimerTask(){
				@Override
				public void run() {
					for (int i = 1; i < previewMap.length - 1; i++) {
						for (int j =1; j < previewMap[0].length - 1; j++) {
							previewMap[i][j].setSeason(cnt);
						}
					}
					if (cnt % 4 == 0) {
						cnt = 0;
					}
					cnt++;
					parent.parent.h3.repaint();
				}
		};
		timer.schedule(myTask, 0, 3000);
		
		new SidePanel(this);
		parent.parent.h3.add(this,BorderLayout.CENTER);
	}

	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		for(int i=startPositionX;i<(startPositionX + parent.matrixWidth)-1;i++){
			for(int j=startPositionY;j<(startPositionY + parent.matrixHeight)-1;j++){
				for(int k = 0; k<previewMap[i][j].getI();k++){
					g.drawImage(previewMap[i][j].getPic(previewMap[i][j].getLayers()[k]).getImage(),(i-startPositionX)*50,(j-startPositionY)*50,null);
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
//			if(parent.parent.jtp.getSelectedIndex()==1){
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
					}
				}
				if(getMousePosition().y < 10){
					if(startPositionY>1){
						startPositionY--;
					}
				}
				
				if(getMousePosition().x > 1350){
					if(startPositionX<parent.getMap().length - parent.matrixWidth){
						startPositionX++;
					}
				}
				
				if(getMousePosition().y > 630){
					if(startPositionY<(parent.getMap()[0].length-parent.matrixHeight)){
						startPositionY++;
					}	
				}
				
			}catch(NullPointerException e){	
				
				}
				parent.parent.h3.repaint();
//			}
		}
	}
	
	public Tile[][] getPreviewMap() {
		return previewMap;
	}
}
