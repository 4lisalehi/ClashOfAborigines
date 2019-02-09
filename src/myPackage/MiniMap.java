package myPackage;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MiniMap extends JPanel{

	private static final long serialVersionUID = 1L;
	EditorPanel parent;
	public MiniMap(EditorPanel  ep){
		parent = ep;
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(int i=1;i<parent.getMap().length;i++){
			for(int j=1;j<parent.getMap()[i].length;j++){
//				g.drawImage(parent.getMap()[i][j], x, y, observer)
			}
		}
	}
	
	
}
