package myPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

public class Menu extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	GameFrame parent;
	JButton jb;
	JRadioButton jrb;
	JRadioButtonMenuItem jrbmi;
	JRadioButton[] rds;
	JLabel[] rdz;
	ButtonGroup bg;
	EditorPanel parentEP;
	SidePanel parentSP;
	public Menu(GameFrame gf){
/*		for(LookAndFeelInfo a : UIManager.getInstalledLookAndFeels()){
			if("Nimbus".equals(a.getName())){
				try {
					UIManager.setLookAndFeel(a.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		}*/
		parent = gf;
		this.setSize(400,200);
		this.setLayout(null);
		this.setLocation(450,210);
		jb = new JButton();
		jb.setSize(80,30);
		jb.setLocation(110,120);
		jb.setText("Start");
		jb.addActionListener(this);
/*		jb.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					new GameFrame(name.getText(),1);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});*/
		jrb = new JRadioButton();
		this.add(jb);
		
		rdz = new JLabel[4];
		rds = new JRadioButton[4];
		for(int i=0;i<rds.length;i++){
			rds[i] = createRadio(20, 20, 140+(i*50),40,this, "");

		}
		for(int i=0;i<rds.length;i++){
			switch(i){
				case 0:
					rdz[i] = createLabel(60, 30, 120+(i*50),60,this, "1X");
					break;
				case 1:
					rdz[i] = createLabel(60, 30, 120+(i*50),60,this, "2X");
					break;
				case 2:
					rdz[i] = createLabel(60, 30, 120+(i*50),60,this, "3X");
					break;
				case 3:
					rdz[i] = createLabel(60, 30, 120+(i*50),60,this, "4X");
					break;
			}
		}
		

		rds[0].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			    	rdz[0].setForeground(Color.RED);
			    }else if (e.getStateChange() == ItemEvent.DESELECTED) {
			    	rdz[0].setForeground(Color.BLACK);
			    }
			}
		});
		
		rds[1].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			    	rdz[1].setForeground(Color.RED);
			    }else if (e.getStateChange() == ItemEvent.DESELECTED) {
			    	rdz[1].setForeground(Color.BLACK);
			    }
			}
		});
		
		rds[2].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			    	rdz[2].setForeground(Color.RED);
			    }else if (e.getStateChange() == ItemEvent.DESELECTED) {
			    	rdz[2].setForeground(Color.BLACK);
			    }
			}
		});
		rds[0].setSelected(true);
		bg = new ButtonGroup();
		for(JRadioButton jrb:rds)
			bg.add(jrb);
		setVisible(true);
	}
	
	public JRadioButton createRadio(int width,int height,int x,int y,JPanel jc,String name){
		JRadioButton jrbz = new JRadioButton();
		jrbz.setLocation(x,y);
		jrbz.setSize(width,height);
		jrbz.setName(name);
		jc.add(jrbz);
		return jrbz;
	}

	public JLabel createLabel(int width,int height,int x,int y,JPanel jc,String name){
		JLabel jrbz = new JLabel();
		jrbz.setLocation(x,y);
		jrbz.setSize(width,height);
		jrbz.setText(name);
		jrbz.setHorizontalAlignment(SwingConstants.CENTER);
		jc.add(jrbz);
		return jrbz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb){
			int diff=0;
			for(int i=0;i<4;i++){
				if(rds[i].isSelected()){
					diff = i+1;
				}
			}
			this.setVisible(false);
			parentEP = new EditorPanel(this,parent,diff);
			parentSP = new SidePanel(parentEP);
			parentSP.setLayout(null);
			parent.h3.add(parentSP,BorderLayout.EAST);
			parent.h3.add(parentEP,BorderLayout.CENTER);
			(new Thread(parentEP)).start();
		}
	}
}