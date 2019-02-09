package myPackage;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class GameFrame extends JFrame{

	private static final long serialVersionUID = 1L;
//	SidePanel sp;
	StartMenu sm;
	Holder h1;
	Holder h2;
	Holder h3;
	Menu mnu;
	JTabbedPane jtp;
	public GameFrame(){
		h1= new Holder();
		h2 = new Holder();
		h3 = new Holder();
		jtp = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		sm = new StartMenu(this);
		mnu = new Menu(this);
		h2.add(sm,BorderLayout.CENTER);
		h3.add(mnu,BorderLayout.CENTER);
		jtp.addTab("Menu",null,h1 ,"Menu");
		jtp.addTab("Game",null,h2,"Game");
		jtp.addTab("Map Editor",null,h3,"Map Editor");
		setTitle("FinalGame");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().add(jtp);
		this.pack();
		setVisible(true);
	}

	public static void main(String args[]){
		new GameFrame();
	}
}
