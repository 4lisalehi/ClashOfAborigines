package myPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StartMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	DefaultComboBoxModel<String> teamNumber;
	JComboBox<String> dropdown;
	JButton submit;
	JButton join;
	JButton host;
//	private boolean isServer;
	Tile[][] gameMap;
	protected int boxWidth, boxHeight;
	GameFrame parent;
	GamePanel parentGP;
	SidePanel parentSP;

	public StartMenu(GameFrame gf) {
		parent = gf;
		boxWidth = 300;
		boxHeight = 100;
		teamNumber = new DefaultComboBoxModel<String>();
		teamNumber.addElement("2 Players");
		teamNumber.addElement("3 Players");
		teamNumber.addElement("4 Players");
		teamNumber.addElement("5 Players");
		teamNumber.addElement("6 Players");
		dropdown = new JComboBox<String>(teamNumber);
		dropdown.setSelectedIndex(0);
		submit = new JButton("Start Game!");
		host = new JButton("Play as a host");
		join = new JButton("Join the game");
		submit.setSize(80, 30);
		submit.addActionListener(this);
		host.addActionListener(this);
		join.addActionListener(this);

		join.setSize(80, 30);
		host.setSize(80, 30);
		join.setLocation(40, 35);
		host.setLocation(160, 35);
		dropdown.setLocation(75, 70);
		
		this.add(dropdown);
		this.add(host);
		this.add(join);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width / 2) - (boxWidth / 2), (dim.height / 2) - (boxHeight / 2));
		setSize(boxWidth, boxHeight);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==join){
			int countryCount = dropdown.getSelectedIndex()+2;
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("ser & txt Files", "ser", "txt");
			chooser.setFileFilter(filter);
			String userhome = System.getProperty("user.home");
			chooser = new JFileChooser(userhome+ "\\workspace\\FinalGame\\Saves");
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
					ObjectInputStream in = new ObjectInputStream(fis);
					int[][][] matrix = (int[][][]) in.readObject();
					gameMap = new Tile[matrix.length][matrix[0].length];
					for (int i = 0; i < gameMap.length; i++) {
						for (int j = 0; j < gameMap[i].length; j++) {
							if (i != 0 && j != 0 && i != (gameMap.length - 1)&& j != (gameMap[0].length - 1)) {
								gameMap[i][j] = new Tile();
							} else {
								gameMap[i][j] = new Tile(true);
							}
						}
					}
					for (int i = 0; i < gameMap.length; i++) {
						for (int j = 0; j < gameMap[i].length;j++) {
							gameMap[i][j].setLayers(matrix[i][j]);
							for (int k = 0; k < 4; k++) {
								if (gameMap[i][j].getLayers()[k] != -1) {
									gameMap[i][j].setI(k + 1);
								}
							}
						}
					}
					parent.h2.repaint();
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				parent.h2.remove(0);
				parentGP = new GamePanel(parent,gameMap,countryCount,this,false);
				parentSP = new SidePanel(parentGP);
				parent.h2.add(parentSP,BorderLayout.EAST);
				parent.h2.add(parentGP,BorderLayout.CENTER);
			}
		}else if(e.getSource()==host){
			int countryCount = dropdown.getSelectedIndex()+2;
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("ser & txt Files", "ser", "txt");
			chooser.setFileFilter(filter);
			String userhome = System.getProperty("user.home");
			chooser = new JFileChooser(userhome+ "\\workspace\\FinalGame\\Saves");
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
					ObjectInputStream in = new ObjectInputStream(fis);
					int[][][] matrix = (int[][][]) in.readObject();
					gameMap = new Tile[matrix.length][matrix[0].length];
					for (int i = 0; i < gameMap.length; i++) {
						for (int j = 0; j < gameMap[i].length; j++) {
							if (i != 0 && j != 0 && i != (gameMap.length - 1)&& j != (gameMap[0].length - 1)) {
								gameMap[i][j] = new Tile();
							} else {
								gameMap[i][j] = new Tile(true);
							}
						}
					}
					for (int i = 0; i < gameMap.length; i++) {
						for (int j = 0; j < gameMap[i].length;j++) {
							gameMap[i][j].setLayers(matrix[i][j]);
							for (int k = 0; k < 4; k++) {
								if (gameMap[i][j].getLayers()[k] != -1) {
									gameMap[i][j].setI(k + 1);
								}
							}
						}
					}
					parent.h2.repaint();
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				parent.h2.remove(0);
				parentGP = new GamePanel(parent,gameMap,countryCount,this,true);
				parentSP = new SidePanel(parentGP);
				parent.h2.add(parentSP,BorderLayout.EAST);
				parent.h2.add(parentGP,BorderLayout.CENTER);
			}
		}else if (e.getSource() == submit) {
			int countryCount = dropdown.getSelectedIndex()+2;
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("ser & txt Files", "ser", "txt");
			chooser.setFileFilter(filter);
			String userhome = System.getProperty("user.home");
			chooser = new JFileChooser(userhome+ "\\workspace\\FinalGame\\Saves");
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
					ObjectInputStream in = new ObjectInputStream(fis);
					int[][][] matrix = (int[][][]) in.readObject();
					gameMap = new Tile[matrix.length][matrix[0].length];
					for (int i = 0; i < gameMap.length; i++) {
						for (int j = 0; j < gameMap[i].length; j++) {
							if (i != 0 && j != 0 && i != (gameMap.length - 1)&& j != (gameMap[0].length - 1)) {
								gameMap[i][j] = new Tile();
							} else {
								gameMap[i][j] = new Tile(true);
							}
						}
					}
					for (int i = 0; i < gameMap.length; i++) {
						for (int j = 0; j < gameMap[i].length;j++) {
							gameMap[i][j].setLayers(matrix[i][j]);
							for (int k = 0; k < 4; k++) {
								if (gameMap[i][j].getLayers()[k] != -1) {
									gameMap[i][j].setI(k + 1);
								}
							}
						}
					}
					parent.h2.repaint();
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				parent.h2.remove(0);
				parentGP = new GamePanel(parent,gameMap,countryCount,this,true);
				parentSP = new SidePanel(parentGP);
				parent.h2.add(parentSP,BorderLayout.EAST);
				parent.h2.add(parentGP,BorderLayout.CENTER);
			}
		}
	}
}