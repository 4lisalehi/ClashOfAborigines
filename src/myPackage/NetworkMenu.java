//package myPackage;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JPanel;
//
//public class NetworkMenu extends JPanel implements ActionListener {
//
//	private static final long serialVersionUID = 1L;
//	DefaultComboBoxModel<String> teamNumber;
//	JComboBox<String> dropdown;
//	JButton join;
//	JButton host;
//	Tile[][] gameMap;
//	protected int boxWidth, boxHeight;
//	GameFrame parent;
//	GamePanel parentGP;
//	SidePanel parentSP;
//
//	public NetworkMenu(GameFrame gf) {
//		parent = gf;
//		
//		boxWidth = 300;
//		boxHeight = 100;
//		
//		join = new JButton("Join");
//		host = new JButton("Host");
//		join.setSize(80, 30);
//		host.setSize(80, 30);
//		join.setLocation(40, 35);
//		host.setLocation(160, 35);
//		setLayout(null);
//		this.add(join);
//		this.add(host);
//
//		join.addActionListener(this);
//		host.addActionListener(this);
//
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation((dim.width / 2) - (boxWidth / 2), (dim.height / 2)- (boxHeight / 2));
//		setSize(boxWidth, boxHeight);
//		setVisible(true);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == host){
//			parent.h2.removeAll();
//			parent.h2.add(parent.sm);
//		}else if(e.getSource() == join){
//			
//			parentGP = new GamePanel(parent,gameMap,0,sm,true);
//			parentSP = new SidePanel(parentGP);
//			parent.h2.add(parentSP,BorderLayout.EAST);
//			parent.h2.add(parentGP,BorderLayout.CENTER);
//		}
//	}
//}