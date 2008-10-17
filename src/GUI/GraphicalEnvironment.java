package GUI;

//import java.awt.Dimension;
//import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;


public class GraphicalEnvironment extends JApplet implements ActionListener
{
  JFrame cvFrame;
  JButton cvCloseBtn;
  JButton cvOpenBtn;

  public void init () 
  {
  	Font font = new Font("Arial",Font.BOLD,13);
  	
    cvCloseBtn = new JButton();
  	cvOpenBtn = new JButton();
  	cvCloseBtn.setText("close");
  	cvCloseBtn.setFont(font);
  	cvOpenBtn.setText("open");
  	cvOpenBtn.setFont(font);
  	cvCloseBtn.addActionListener(this);
  	cvOpenBtn.addActionListener(this);
  	
  	JPanel lvPanel = new JPanel();
    JLabel lvWindow= new JLabel("Applet is open in a separate window");
   
  	lvPanel.setBorder(new TitledBorder("Space interactive tool"));
  	lvPanel.setFont(font);
  	
  	this.setLayout(new GridBagLayout());
  	lvPanel.setLayout(new GridBagLayout());
  	
  	GridBagConstraints lvConstraints = new GridBagConstraints();
  	
  	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 2;
  	lvConstraints.gridheight = 2;
  	lvConstraints.gridx = 0;
  	lvConstraints.gridy = 2;
  	
  	this.add(lvWindow, lvConstraints);
  	
  	
  	lvConstraints.fill = GridBagConstraints.HORIZONTAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 2;
  	lvConstraints.gridheight = 1;
  	lvConstraints.gridx = 0;
  	lvConstraints.gridy = 0;
  	
  	this.add(lvPanel,lvConstraints);
  	
  	lvPanel.add(cvCloseBtn);
  	lvPanel.add(cvOpenBtn);
  	
  } 


  public void actionPerformed (ActionEvent e) {
 
    Object obj = e.getSource();
    
    if (obj == cvCloseBtn) {
        close ();
    } else { // Open
        if (cvFrame == null) {
            cvFrame = new FsiMainFrame (this);
            cvFrame.setVisible (true);
        }
    }
  } 

  /** Close the frame. **/
  public void close () {
  	if(cvFrame != null)
  	{
      cvFrame.dispose ();
      cvFrame = null;
  	}
  }

}

