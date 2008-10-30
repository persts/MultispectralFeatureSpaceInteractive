package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;


public class GraphicalEnvironment extends JApplet implements ActionListener
{
  JButton cvOpenBtn;

  public void init () 
  {
  	Font font = new Font("Arial",Font.BOLD,13);
  	
  	cvOpenBtn = new JButton();
  	cvOpenBtn.setText("Launch interactive");
  	cvOpenBtn.setFont(font);
  	cvOpenBtn.addActionListener(this);
  	
  	JPanel lvPanel = new JPanel();
   
  	lvPanel.setBorder(new TitledBorder("Feature Space Interactive"));
  	lvPanel.setFont(font);
  	
  	this.add(lvPanel);
  	
  	lvPanel.add(cvOpenBtn);
  	
  } 


  public void actionPerformed (ActionEvent e) 
  {
    Object obj = e.getSource();
    if (obj == cvOpenBtn) 
    { // Open
	    FsiMainFrame lvFrame = new FsiMainFrame();
	    lvFrame.setVisible (true);
    }
  }

}

