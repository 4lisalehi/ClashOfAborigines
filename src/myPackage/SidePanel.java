package myPackage;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SidePanel extends JPanel implements  ActionListener{

	private static final long serialVersionUID = 1L;

	JButton landItem;
	JButton shallowSeaItem;
	JButton deepSeaItem;
	JButton load;
	JButton exit;
	JButton save;
	JButton preview;
	JButton treeItem;
	JButton mineItem;
	JButton smallFishItem;
	JButton bigFishItem;
	JButton hideSidePanel, hideSidePanel2,hideSidePanel3;
	JButton erase;
	JButton startGame;
	JButton[] selectedLabors;
	JPanel miniMap;
	JPanel miniMap1;
	JPanel previewMiniMap;
	JPanel gameMiniMap;
	JPanel gameTimerPanel;
	
	Image bground;
	
	//parent objects...
	Labor panelLabor;
	WarShip panelWarship;
	TransportShip panelTShip;
	FishingShip panelFShip;
	Country panelCountry1;
	Country panelCountry2;
	Tree panelTree;
	IronMine panelMine;
	
	JPanel laborBars;
	JPanel warshipBars;
	JPanel tshipBars;
	JPanel fshipBars;
	
	
	JButton buildWarShip;
	JButton buildFShip;
	JButton buildTShip;
	JButton buildLabor;
	
	//labor panel....
	
	ImageIcon laborItem;
	ImageIcon carryWood;
	ImageIcon carryIron;
	JLabel carryWoodText;
	JLabel carryIronText;
	JLabel hpText;
	JLabel hp;
	JLabel hungerText;
	JLabel hunger;
	

	//base items...

	JButton releaseLabor;
	JButton unLoadLabor;

	//Overall Health...

	JLabel panelTitle;

/*	private float overallHealth;
	private int overallMaxHealth;*/

	//WarshipPanel components
	ImageIcon warshipPic;
	JLabel warshipHPText;
	JLabel warshipHP;

	//TreePanel components

	ImageIcon treePic;
	JLabel treeWoodText;
	JLabel treeWood;

	//IronMinePanel components
	
	ImageIcon minePic;
	JLabel mineIrons;
	JLabel mineIronsText;
	
	//transportshipPanel components
	TransportShip parentTShip;
	ImageIcon tshipPic;
	JLabel tshipHP;
	JLabel tshipHPText;
	JLabel tshipLoadedText;
	JLabel tshipLoaded;
	
	//fshipPanel components
	
	FishingShip parentFShip;
	ImageIcon fshipPic;
	JLabel fshipHP;
	JLabel fshipLoaded;

	//init gamePanel components...
	
/*	private int foodz;
	private int woodz;
	private int ironz;*/

	JLabel title;
	JLabel foods;
	JLabel foodsNo;
	JLabel woods;
	JLabel woodsNo;
	JLabel irons;
	JLabel ironsNo;

	EditorPanel parent1;
	GamePanel parent2;
	Preview parent3;
	JButton resume, pause, back;
	private int miniMapWidth,miniMapHeight, gameMiniMapHeight, gameMiniMapWidth;
	private int miniDim;
	private int previewMiniMapWidth;
	private int previewMiniMapHeight;
	
	int mousePosX;
	int mousePosY;
	Preview myPreview;

	public SidePanel(GamePanel gp){

		exit = new JButton("Exit");
		parent2 = gp;
		bground = Toolkit.getDefaultToolkit().createImage("pics/sidepanel.png");

		parent2.overallSidePanel = this;
		gameMiniMapWidth = (parent2.dim.width/6);
		gameMiniMapHeight = (parent2.dim.height/6);
		mousePosX = 0;
		mousePosY = 0;

		miniDim = (parent2.mapsize==3) ? (int)Math.ceil(50d/(6*parent2.mapsize)) : (50/(6*parent2.mapsize));

		hideSidePanel2 = new JButton(new ImageIcon("pics/arrow_right"));
		panelTitle = new JLabel();
		title = new JLabel("The Game");
		irons = new JLabel("Irons: ");
		woods = new JLabel("Woods: ");
		foods = new JLabel("Foods: ");
		ironsNo = new JLabel();
		woodsNo = new JLabel();
		foodsNo = new JLabel();
		treeWood = new JLabel();
		treeWoodText = new JLabel("Woods: ");
		mineIrons = new JLabel();
		mineIronsText = new JLabel("Irons: ");

		buildWarShip = new JButton("WarShip");
		buildTShip = new JButton("TransportShip");
		buildFShip = new JButton("FishingShip");
		buildLabor = new JButton("Create Labor");
		
		buildWarShip.setSize(110, 30);
		buildFShip.setSize(110, 30);
		buildTShip.setSize(110, 30);
		buildLabor.setSize(110, 30);
		treeWood.setSize(110, 30);
		treeWoodText.setSize(110, 30);
		mineIrons.setSize(110, 30);
		mineIronsText.setSize(110, 30);

		buildWarShip.setLocation(30, 30);
		buildFShip.setLocation(140, 30);
		buildTShip.setLocation(30, 130);
		buildLabor.setLocation(140,130);
		treeWood.setLocation(140, 130);
		treeWoodText.setLocation(30, 130);
		mineIrons.setLocation(140, 130);
		mineIronsText.setLocation(30, 130);

		buildFShip.addActionListener(this);
		buildTShip.addActionListener(this);
		buildWarShip.addActionListener(this);
		buildLabor.addActionListener(this);

//		ironsNo.setText(ironz+"");
//		woodsNo.setText(woodz+"");
//		foodsNo.setText(foodz+"");
		
		releaseLabor = new JButton("Release Labor");
		releaseLabor.setSize(80, 40);
		releaseLabor.setLocation(85, 300);
		
		unLoadLabor = new JButton("Unload Labor");
		unLoadLabor.setSize(80, 40);
		unLoadLabor.setLocation(85, 300);
		unLoadLabor.addActionListener(this);
		
		laborItem = new ImageIcon("pics/laboritem.png");
		carryWood = new ImageIcon("pics/wooditem.png");
		carryIron = new ImageIcon("pics/ironitem.png");

		warshipPic = new ImageIcon("pics/warshipitem.png");
		fshipPic = new ImageIcon("pics/");
		tshipPic = new ImageIcon("");
		treePic = new ImageIcon("pics/treeitem.png");
		
		
		
		carryWoodText = new JLabel("");
		carryIronText = new JLabel("");

		warshipPic = new ImageIcon("pics/warshipitem.png");
		warshipHPText = new JLabel("HP: ");
		warshipHP = new JLabel();
		panelTitle.setText("The Game");
		
		this.setLayout(null);
		
//		treeItem = new ImageIcon("asd");
		
		
		
		gameTimerPanel = new JPanel(){

			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
//				System.out.println(parent2.seconds);
				g.setColor(Color.BLACK);
				g.setFont(getFont());
				g.drawString(parent2.hours+" : "+parent2.minutes+" : "+parent2.seconds, 0, 0);
			}
		};

		laborBars = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(panelLabor != null){
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(20));
					g2.setColor(Color.BLACK);
					g2.drawLine(20, 20, 200, 20);
					
					g2.setColor(Color.GREEN);
					if(panelLabor.getHealth()>0)
					g2.drawLine(20, 20, (int)((panelLabor.getHealth()/panelLabor.getMaxHealth())*200), 20);
					
					g2.setColor(Color.BLACK);
					g2.drawLine(20, 50, 200, 50);
					
					g2.setColor(Color.GREEN);
					if(panelLabor.getWeight()>0)
						g2.drawLine(20, 50, (int)((panelLabor.getWeight()/panelLabor.getCapacity())*200), 50);
				}
			}
		};

		warshipBars = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(panelWarship != null){
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(20));
					g2.setColor(Color.BLACK);
					g2.drawLine(20, 20, 200, 20);
					g2.setColor(Color.GREEN);
					if(panelWarship.getHealth()>0)
						g2.drawLine(20, 20, (int)((panelWarship.getHealth()/panelWarship.getMaxHealth())*200), 20);
				}
			}
		};
		
		fshipBars = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(panelFShip != null){
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(20));
					g2.setColor(Color.BLACK);
					g2.drawLine(20, 20, 200, 20);
					g2.setColor(Color.GREEN);
					if(panelFShip.getHealth()>0)
						g2.drawLine(20, 20, (int)((panelFShip.getHealth()/panelFShip.getMaxHealth())*200), 20);
				}
			}
		};
		
		tshipBars = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(panelTShip != null){
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(20));
					g2.setColor(Color.BLACK);
					g2.drawLine(20, 20, 200, 20);
					g2.setColor(Color.GREEN);
					if(panelTShip.getHealth()>0)
						g2.drawLine(20, 20, (int)((panelTShip.getHealth()/panelTShip.getMaxHealth())*200), 20);
				}
			}
		};

		gameMiniMap = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				// 1366
				// 768
				super.paint(g);
				for (int i = 1; i < parent2.getGameMap().length; i++) {
					for (int j = 1; j < parent2.getGameMap()[0].length; j++) {
						for (int k = 0; k < parent2.getGameMap()[i][j].getI(); k++) {
							g.drawImage(parent2.getGameMap()[i][j].getPic(parent2.getGameMap()[i][j].getLayers()[k]).getImage(),(i - 1) * miniDim, (j - 1) * miniDim,miniDim, miniDim, null);
						}
					}
				}

				for(Country c : parent2.getCountries()){
					for(Labor l : c.getLabors()){
						g.drawImage(l.getPic().getImage(), (l.getX()-1)*miniDim, (l.getY()-1)*miniDim, miniDim, miniDim, null);
					}

					for(WarShip ws : c.getWarShips()){
						g.drawImage(ws.getPic().getImage(), (ws.getX()-1)*miniDim, (ws.getY()-1)*miniDim, miniDim, miniDim, null);
					}

					for(TransportShip ts : c.getTransportShips()){
						g.drawImage(ts.getPic().getImage(), (ts.getX()-1)*miniDim, (ts.getY()-1)*miniDim, miniDim, miniDim, null);
					}

					for(FishingShip fs : c.getFShips()){
						g.drawImage(fs.getPic().getImage(), (fs.getX()-1)*miniDim, (fs.getY()-1)*miniDim, miniDim, miniDim, null);
					}
				}
				g.setColor(Color.RED);
				g.drawRect(mousePosX, mousePosY, (parent2.dim.width)/ (6 * parent2.mapsize), (parent2.dim.height)/ (6 * parent2.mapsize));
			}
		};

		releaseLabor.addActionListener(this);
		
		gameMiniMap.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if((e.getX() <= (int)(((double)(parent2.mapsize-1)/parent2.mapsize)*(gameMiniMapWidth))) && (e.getX()>0)){
					parent2.x0 = ((e.getX())/miniDim)+1;
					mousePosX = e.getX();
				}
				
				if((e.getY() <= (int)(((double)(parent2.mapsize-1)/parent2.mapsize)*(gameMiniMapHeight))) && (e.getY()>0)){
					parent2.y0 = ((e.getY())/miniDim)+1;
					mousePosY = e.getY();
				}
//				System.out.println(parent1.startPositionX +"  "+parent1.startPositionY);
				parent2.parent.h2.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		gameMiniMap.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				if((e.getX() <= (int)(((double)(parent2.mapsize-1)/parent2.mapsize)*(gameMiniMapWidth))) && (e.getX()>=0)){
					parent2.x0 = ((e.getX())/miniDim)+1;
					mousePosX = e.getX();
				}
				if((e.getY() <= (int)(((double)(parent2.mapsize-1)/parent2.mapsize)*(gameMiniMapHeight))) && (e.getY()>=0)){
					parent2.y0 = ((e.getY())/miniDim)+1;
					mousePosY = e.getY();
				}
				parent2.parent.h2.repaint();
			}
		});
		

		
		ironsNo.setSize(100, 10);
		woodsNo.setSize(100, 10);
		foodsNo.setSize(100, 10);
		
		laborBars.setSize(220, 110);
		warshipBars.setSize(220, 110);
		fshipBars.setSize(220, 110);
		tshipBars.setSize(220, 110);
		gameTimerPanel.setSize(200, 30);
		
		irons.setSize(50, 10);
		woods.setSize(50, 10);
		foods.setSize(50, 10);
		hideSidePanel2.setSize(20, 80);
		gameMiniMap.setSize((parent2.dim.width)/6,(parent2.dim.height)/6);
		
		irons.setLocation(40, 150);
		ironsNo.setLocation(100, 150);
		woods.setLocation(130, 150);
		woodsNo.setLocation(190, 150);
		foods.setLocation(40, 200);
		foodsNo.setLocation(100, 200);
		hideSidePanel2.setLocation(0, 320);
		gameMiniMap.setLocation(8,510);
		
		laborBars.setLocation(10, 190);
		warshipBars.setLocation(10, 190);
		fshipBars.setLocation(10, 190);
		tshipBars.setLocation(10, 190);
		gameTimerPanel.setLocation(10, 700);

		this.add(irons);
		this.add(ironsNo);
		this.add(woods);
		this.add(woodsNo);
		this.add(foods);
		this.add(foodsNo);
		this.add(hideSidePanel2);
		this.add(gameMiniMap);
		this.add(gameTimerPanel);
		
		setSize(250,800);
		setLocation(1120,0);
//		setBackground(Color.ORANGE);
//		setVisible(true);
	}

	public SidePanel(Preview pr){
		parent3 = pr;
		hideSidePanel3 = new JButton();
		back = new JButton("Back");
		hideSidePanel = new JButton();
		previewMiniMapWidth = (parent3.dim.width/6);
		previewMiniMapHeight = (parent3.dim.height/6);
		mousePosX = 0;
		mousePosY = 0;
		miniDim = (parent3.mapsize==3) ? (int)Math.ceil(50d/(6*parent3.mapsize)) : (50/(6*parent3.mapsize));
		previewMiniMap = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				//1366
				//768
				super.paint(g);
				for(int i=1;i<parent3.getPreviewMap().length;i++){
					for(int j=1;j<parent3.getPreviewMap()[0].length;j++){
						for(int k = 0; k<parent3.getPreviewMap()[i][j].getI();k++){
							g.drawImage(parent3.getPreviewMap()[i][j].getPic(parent3.getPreviewMap()[i][j].getLayers()[k]).getImage(),(i-1)*miniDim,(j-1)*miniDim,miniDim,miniDim,null);
						}
					}
				}
				g.setColor(Color.RED);
//				g.drawRect((int)(((double)parent1.startPositionX/parent1.matrixWidth)*(miniMapWidth)), (int)(((double)parent1.startPositionY/parent1.matrixHeight)*(miniMapHeight)), (parent1.dim.width)/(6*parent1.mapsize), (parent1.dim.height)/(6*parent1.mapsize));
				g.drawRect(mousePosX, mousePosY, (parent3.dim.width)/(6*parent3.mapsize), (parent3.dim.height)/(6*parent3.mapsize));
			}
		};
		
		
		
		previewMiniMap.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if((e.getX() <= (int)(((double)(parent3.mapsize-1)/parent3.mapsize)*(previewMiniMapWidth))) && (e.getX()>0)){
					parent3.startPositionX = ((e.getX())/miniDim)+1;
					mousePosX = e.getX();
				}
				
				if((e.getY() <= (int)(((double)(parent3.mapsize-1)/parent3.mapsize)*(previewMiniMapHeight))) && (e.getY()>0)){
					parent3.startPositionY = ((e.getY())/miniDim)+1;
					mousePosY = e.getY();
				}
//				System.out.println(parent1.startPositionX +"  "+parent1.startPositionY);
				parent3.parent.parent.h3.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		previewMiniMap.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				if((e.getX() <= (int)(((double)(parent3.mapsize-1)/parent3.mapsize)*(previewMiniMapWidth))) && (e.getX()>=0)){
					parent3.startPositionX = ((e.getX())/miniDim)+1;
					mousePosX = e.getX();
				}
				if((e.getY() <= (int)(((double)(parent3.mapsize-1)/parent3.mapsize)*(previewMiniMapHeight))) && (e.getY()>=0)){
					parent3.startPositionY = ((e.getY())/miniDim)+1;
					mousePosY = e.getY();
				}
				parent3.parent.parent.h3.repaint();
			}
		});

		this.add(back);
		this.add(previewMiniMap);
		this.add(hideSidePanel3);

		back.addActionListener(this);
		hideSidePanel3.addActionListener(this);
		parent3.showSidePanel.addActionListener(this);
		
		
		back.setSize(80, 30);
		hideSidePanel3.setSize(20, 80);
		previewMiniMap.setSize((parent3.parent.dim.width)/6,(parent3.parent.dim.height)/6);

		back.setLocation(40, 470);
		hideSidePanel3.setLocation(0, 320);
		previewMiniMap.setLocation(8, 510);

		setLayout(null);
		
		setSize(250,800);
		setLocation(1120,0);
		setBackground(Color.ORANGE);
		parent3.parent.parent.h3.add(this,BorderLayout.EAST);
	}

	public SidePanel(EditorPanel ep){
//		parentMenu = inputMenu;
		parent1 = ep;
		deepSeaItem = new JButton();
		shallowSeaItem = new JButton();
		landItem = new JButton();
		treeItem = new JButton();
		mineItem = new JButton();
		smallFishItem = new JButton();
		bigFishItem = new JButton();
		load = new JButton("Load");
		save = new JButton("Save");
		exit = new JButton("Exit");
		preview = new JButton("Preview");
		hideSidePanel = new JButton();
		startGame = new JButton("Play Game!");
		miniDim = (parent1.mapsize==3) ? (int)Math.ceil(50d/(6*parent1.mapsize)) : (50/(6*parent1.mapsize));
		miniMapWidth = (parent1.dim.width/6);
		miniMapHeight = (parent1.dim.height/6);
		mousePosX = 0;
		mousePosY = 0;
		erase = new JButton();
		miniMap = new JPanel(){
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				//1366
				//768
				super.paint(g);
				for(int i=1;i<parent1.getMap().length;i++){
					for(int j=1;j<parent1.getMap()[0].length;j++){
						for(int k = 0; k<parent1.getMap()[i][j].getI();k++){
							g.drawImage(parent1.getMap()[i][j].getPic(parent1.getMap()[i][j].getLayers()[k]).getImage(),(i-1)*miniDim,(j-1)*miniDim,miniDim,miniDim,null);
						}
					}
				}
				g.setColor(Color.RED);
//				g.drawRect((int)(((double)parent1.startPositionX/parent1.matrixWidth)*(miniMapWidth)), (int)(((double)parent1.startPositionY/parent1.matrixHeight)*(miniMapHeight)), (parent1.dim.width)/(6*parent1.mapsize), (parent1.dim.height)/(6*parent1.mapsize));
				g.drawRect(mousePosX, mousePosY, (parent1.dim.width)/(6*parent1.mapsize), (parent1.dim.height)/(6*parent1.mapsize));
			}
		};
		
		
		smallFishItem.setOpaque(false);
		smallFishItem.setContentAreaFilled(false);
		smallFishItem.setBorderPainted(false);
		 
		bigFishItem.setOpaque(false);
		bigFishItem.setContentAreaFilled(false);
		bigFishItem.setBorderPainted(false);
		 
		landItem.setOpaque(false);
		landItem.setContentAreaFilled(false);
		 landItem.setBorderPainted(false);

		 deepSeaItem.setOpaque(false);
		 deepSeaItem.setContentAreaFilled(false);
		 deepSeaItem.setBorderPainted(false);
		 
		 shallowSeaItem.setOpaque(false);
		 shallowSeaItem.setContentAreaFilled(false);
		 shallowSeaItem.setBorderPainted(false);
		 
		 erase.setOpaque(false);
		 erase.setContentAreaFilled(false);
		 erase.setBorderPainted(false);
		 
		 mineItem.setOpaque(false);
		 mineItem.setContentAreaFilled(false);
		 mineItem.setBorderPainted(false);
		 
		 treeItem.setOpaque(false);
		 treeItem.setContentAreaFilled(false);
		 treeItem.setBorderPainted(false);
		 
		 hideSidePanel.setOpaque(false);
		 hideSidePanel.setContentAreaFilled(false);
		 hideSidePanel.setBorderPainted(false);
		
		deepSeaItem.setIcon(new ImageIcon("pics/DeepSeaButton.png"));
		landItem.setIcon(new ImageIcon("pics/IslandButton.png"));
		shallowSeaItem.setIcon(new ImageIcon("pics/ShallowSeaButton.png"));
		treeItem.setIcon(new ImageIcon("pics/TreeButton.png"));
		mineItem.setIcon(new ImageIcon("pics/MineButton.png"));
		smallFishItem.setIcon(new ImageIcon("pics/SmallFishButton.png"));
		bigFishItem.setIcon(new ImageIcon("pics/BigFishButton.png"));
		erase.setIcon(new ImageIcon("pics/ErasorButton.png"));
		hideSidePanel.setIcon(new ImageIcon("pics/HideSidePanel.png"));

		deepSeaItem.addActionListener(this);
		shallowSeaItem.addActionListener(this);
		landItem.addActionListener(this);
		treeItem.addActionListener(this);
		mineItem.addActionListener(this);
		smallFishItem.addActionListener(this);
		bigFishItem.addActionListener(this);
		hideSidePanel.addActionListener(this);
		parent1.showSidePanel.addActionListener(this);
		preview.addActionListener(this);
		erase.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		miniMap.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if((e.getX() <= (int)(((double)(parent1.mapsize-1)/parent1.mapsize)*(miniMapWidth))) && (e.getX()>0)){
					parent1.startPositionX = ((e.getX())/miniDim)+1;
					mousePosX = e.getX();
				}
				
				if((e.getY() <= (int)(((double)(parent1.mapsize-1)/parent1.mapsize)*(miniMapHeight))) && (e.getY()>0)){
					parent1.startPositionY = ((e.getY())/miniDim)+1;
					mousePosY = e.getY();
				}
//				System.out.println(parent1.startPositionX +"  "+parent1.startPositionY);
				parent1.parent.h3.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		miniMap.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				if((e.getX() <= (int)(((double)(parent1.mapsize-1)/parent1.mapsize)*(miniMapWidth))) && (e.getX()>=0)){
					parent1.startPositionX = ((e.getX())/miniDim)+1;
					mousePosX = e.getX();
				}
				if((e.getY() <= (int)(((double)(parent1.mapsize-1)/parent1.mapsize)*(miniMapHeight))) && (e.getY()>=0)){
					parent1.startPositionY = ((e.getY())/miniDim)+1;
					mousePosY = e.getY();
				}
				parent1.parent.h3.repaint();
			}
		});
		
		this.setLayout(null);

		this.add(deepSeaItem);
		this.add(shallowSeaItem);
		this.add(landItem);
		this.add(treeItem);
		this.add(mineItem);
		this.add(smallFishItem);
		this.add(bigFishItem);
		this.add(load);
		this.add(save);
		this.add(preview);
		this.add(exit);
		this.add(hideSidePanel);
		this.add(erase);
		this.add(miniMap);
		this.add(startGame);
		
		deepSeaItem.setSize(80,80);
		shallowSeaItem.setSize(80,80);
		landItem.setSize(80,80);
		treeItem.setSize(80,80);
		mineItem.setSize(80,80);
		smallFishItem.setSize(80,80);
		bigFishItem.setSize(80,80);
		exit.addActionListener(this);
		load.setSize(80, 30);
		save.setSize(80, 30);
		exit.setSize(80, 30);
		preview.setSize(80, 30);
		hideSidePanel.setSize(20, 80);
		erase.setSize(80,80);
		miniMap.setSize((ep.dim.width)/6,(ep.dim.height)/6);
		startGame.setSize(120,30);
		
		deepSeaItem.setLocation(40, 30);
		shallowSeaItem.setLocation(130, 30);
		landItem.setLocation(40, 130);
		treeItem.setLocation(130,130);
		mineItem.setLocation(40,230);
		smallFishItem.setLocation(40,330);
		bigFishItem.setLocation(130,330);
		load.setLocation(40,430);
		save.setLocation(130,430);
		preview.setLocation(40,470);
		exit.setLocation(130, 470);
		hideSidePanel.setLocation(0, 320);
		erase.setLocation(130,230);
		miniMap.setLocation(8,510);
		startGame.setLocation(60, 650);

		setSize(250,800);
		setLocation(1120,0);
	}

	public void switchToBasePanel(Country c){
		panelCountry1 = c;
		this.removeAll();
		panelTitle.setText(c.getCountryName());
		this.setLayout(null);
		this.add(panelTitle);
		this.add(buildWarShip);
		this.add(buildFShip);
		this.add(buildTShip);
		this.add(buildLabor);
		this.add(gameMiniMap);
		this.add(hideSidePanel2);
		if(c.getInBaseLabor().size()==0){
			buildWarShip.setEnabled(false);
			buildFShip.setEnabled(false);
			buildTShip.setEnabled(false);
		}else{
			buildWarShip.setEnabled(true);
			buildFShip.setEnabled(true);
			buildTShip.setEnabled(true);
		}
		if(panelCountry1.getInBaseLabor().size()>0){
			this.add(releaseLabor);
		}
	}

	public void switchToDefault(Country c){
//		System.out.println("default");
		this.removeAll();
		panelTitle.setText(c.getCountryName());
		foodsNo.setText(c.getFoodResouce()+"");
		woodsNo.setText(c.getWoodResource()+"");
		ironsNo.setText(c.getIronResource()+"");
		this.setLayout(null);
		this.add(panelTitle);
		this.add(irons);
		this.add(ironsNo);
		this.add(woods);
		this.add(woodsNo);
		this.add(foods);
		this.add(foodsNo);
		this.add(hideSidePanel2);
		this.add(gameMiniMap);
	}

	public void switchToWarshipPanel(WarShip ws){
		panelWarship = ws;
/*		parentWarship = w;
		overallHealth = w.getHealth();
		overallMaxHealth = w.getMaxHealth();*/
		this.removeAll();
		warshipHP.setText(panelWarship.getHealth()+"");
		this.add(panelTitle);
		this.add(hideSidePanel2);
		this.add(warshipHP);
		this.add(warshipBars);
		this.add(gameMiniMap);
		warshipPic = new ImageIcon("pics/warshipItem.png");
//		warshipHP = new JLabel((parentWarship.getHealth()/parentWarship.getMaxHealth())*100+"");
	}

	public void switchToLaborPanel(Labor l){
		panelLabor = l;
		this.removeAll();
		panelTitle.setText("Labor");
		this.add(panelTitle);
		this.add(gameMiniMap);
		this.add(laborBars);
		this.add(hideSidePanel2);
	}

	public void switchToTShipPanel(TransportShip ts){
		this.removeAll();
		panelTShip = ts;
		panelTitle.setText("TransportShip");
		this.add(panelTitle);
//		this.add(tshipHPText);
//		this.add(tshipHP);
		this.add(unLoadLabor);
		this.add(gameMiniMap);
		this.add(tshipBars);
		this.add(hideSidePanel2);
		if(ts.getInsideLabors().size()==0)
			unLoadLabor.setEnabled(false);
		else
			unLoadLabor.setEnabled(true);
	}

	public void switchToFShip(FishingShip fs){
		this.removeAll();
		panelFShip = fs;
		panelTitle.setText("Fishing Ship");
		this.add(panelTitle);
		this.add(gameMiniMap);
		this.add(fshipBars);
		this.add(hideSidePanel2);
	}
	
	public void switchToTreePanel(Tree t){
		this.removeAll();
		panelTree = t;
		this.add(panelTitle);
		treeWood.setText(panelTree.getWoods()+"");
		this.add(treeWood);
		this.add(treeWoodText);
		this.add(gameMiniMap);
		this.add(hideSidePanel2);
	}
	
	public void switchToMinePanel(IronMine im){
		panelMine = im;
		this.removeAll();
		panelTitle.setText("Iron Mine");
		this.add(panelTitle);
		mineIrons.setText(panelMine.getIrons()+"");
		this.add(mineIrons);
		this.add(mineIronsText);
		this.add(hideSidePanel2);
		this.add(gameMiniMap);
	}
/*	public SidePanel(Country c) {
		System.out.println("construct");
		buildWarShip = new JButton("WarShip");
		buildTShip = new JButton("WarShip");
		buildFShip = new JButton("WarShip");
		
		buildWarShip.setSize(80, 30);
		buildFShip.setSize(80, 30);
		buildTShip.setSize(80, 30);
		
		buildWarShip.setLocation(40, 30);
		buildFShip.setLocation(130, 30);
		buildTShip.setLocation(40, 130);
		this.setLayout(null);
		this.add(buildWarShip);
		this.add(buildFShip);
		this.add(buildTShip);
		
		setSize(250,800);
		setLocation(1120, 0);
		setBackground(Color.ORANGE);
//		setVisible(true);
	}

	public SidePanel(Labor l){
		
	}

	public SidePanel(WarShip w){
		parentWarship = w;
		overallHealth = w.getHealth();
		overallMaxHealth = w.getMaxHealth();
		warshipPic = new ImageIcon("pics/warshipItem.png");
//		warshipHP = new JLabel((parentWarship.getHealth()/parentWarship.getMaxHealth())*100+"");
		this.setLayout(null);
		setSize(250, 800);
		setLocation(1120, 0);
		setBackground(Color.ORANGE);
		repaint();
	}

	public SidePanel(TransportShip ts){
		overallHealth = ts.getHealth();
		overallMaxHealth = ts.getMaxHealth();
	}

	public SidePanel(FishingShip fs){
		overallHealth = fs.getHealth();
		overallMaxHealth = fs.getMaxHealth();
	}*/

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bground, 0, 0, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			int c = JOptionPane.showConfirmDialog(null, "Do you want to save the game?","Exit",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null);
			switch(c){
				case 0:
					//yes
					//save...
					parent1.saveFile();
					System.exit(0);
					break;
				case 1:
					//no
					System.exit(0);
					break;
				case 2:
					//cancel
					break;
			}
		}else if(e.getSource() == buildFShip){
			panelCountry1.buildFShip();
		}else if(e.getSource() == buildTShip){
			panelCountry1.buildTransportShip();
		}else if(e.getSource() == buildWarShip){
			System.out.println("panelif");
			panelCountry1.createWarship();
		}else if(e.getSource() == buildLabor){
			panelCountry1.createLabor();
		}else if(e.getSource() == releaseLabor){
			Labor tmpLabor;
			tmpLabor = panelCountry1.getInBaseLabor().lastElement();
			panelCountry1.releaseLabor(panelCountry1.getInBaseLabor().lastElement());
		}else if(e.getSource() == unLoadLabor){
			Labor tmpLabor;
			tmpLabor = panelTShip.getInsideLabors().lastElement();
			panelTShip.unLoadLabors(panelTShip.getInsideLabors().lastElement());;
		}else if(e.getSource() == save){
			parent1.saveFile();
		}else if(e.getSource() == load){
			parent1.setMap(parent1.loadFile());
			parent1.parent.h3.repaint();
		}else if(e.getSource() == deepSeaItem){
			parent1.setItemID(0);
		}else if(e.getSource() == shallowSeaItem){
			parent1.setItemID(1);
		}else if(e.getSource() == landItem){
			parent1.setItemID(2);
		}else if(e.getSource() == treeItem){
			parent1.setItemID(3);
		}else if(e.getSource() == mineItem){
			parent1.setItemID(4);
		}else if(e.getSource() == bigFishItem){
			parent1.setItemID(5);
		}else if(e.getSource() == smallFishItem){
			parent1.setItemID(6);
		}else if(e.getSource() == hideSidePanel){
			setVisible(false);
			parent1.showSidePanel.setVisible(true);
		}else if(parent1 != null && e.getSource() == parent1.showSidePanel){
			setVisible(true);
			parent1.showSidePanel.setVisible(false);
		}else if(e.getSource() == erase){
			parent1.setItemID(7);
		}else if(e.getSource() == preview){
			myPreview = new Preview(parent1);
			(new Thread(myPreview)).start();
			setVisible(false);
			parent1.setVisible(false);
			
			parent1.parent.h3.repaint();
		}else if(e.getSource() == startGame){
//			GamePanel gpz = new GamePanel(parent1);
//			SidePanel epz = new SidePanel(gpz);
//			parent1.parent.h2.add(gpz,BorderLayout.CENTER);
//			parent1.parent.h2.add(epz,BorderLayout.EAST);
		}else if(e.getSource() == resume){
			synchronized (parent3.myTask) {
				parent3.myTask.notify();
			}
		}else if(e.getSource() == pause){
			synchronized (parent3.myTask) {
				try {
					parent3.myTask.wait();
				} catch (InterruptedException e1 ) {
					e1.printStackTrace();
				}
			}
		}else if(e.getSource() == back){
			parent3.timer.cancel();
			setVisible(false);
			parent3.setVisible(false);
//			parent3.parent.parent.sp.setVisible(true);
			parent3.parent.showSidePanel.setVisible(true);
			parent3.parent.setVisible(true);
		}
	}

	public int getMiniDim() {
		return miniDim;
	}

	public void setMiniDim(int miniDim) {
		this.miniDim = miniDim;
	}
}	