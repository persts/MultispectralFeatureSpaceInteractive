/*
** File: FsiRGBandSelectionFrame.java
** Author(s): Roberto Garcia Yunta
** Creation Date: 2008-09-05
** Copyright (c) 2008, American Museum of Natural History. All rights reserved.
** 
** This library/program is free software; you can redistribute it 
** and/or modify it under the terms of the GNU Library General Public
** License as published by the Free Software Foundation; either
** version 2 of the License, or (at your option) any later version.
** 
** This library/program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
** Library General Public License for more details.
**
** This work was made possible through the support from the 
** Center For Biodiversity and Conservation and The Spanish Ministry of 
** Science and Innovation's INTEGRANTS program.
**
**/
package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;



public class FsiRGBBandSelectionFrame extends JDialog implements ActionListener 
{
	
	FsiMainFrame parent;
	String cvXAxis[];
	JComboBox cvRedComboBox;
 	JComboBox cvGreenComboBox;
 	JComboBox cvBlueComboBox;

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JPanel insetsPanel2 = new JPanel();
  JPanel insetsPanel3 = new JPanel();
  JButton button1 = new JButton();
  JLabel imageLabel = new JLabel();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  ImageIcon image1 = new ImageIcon();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
 
  
  public FsiRGBBandSelectionFrame(FsiMainFrame theparent) 
  {
    super(theparent);
    parent = theparent;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initial operations to set the frame 
   */
  private void init() throws Exception  
  {
    this.setTitle("Select band for each axis");
    insetsPanel1.setLayout(new GridBagLayout());
  	GridBagConstraints lvConstraints = new GridBagConstraints();
  
    JPanel lvBandSelection = new JPanel();
   	lvBandSelection.setLayout(new GridLayout(3,3));
   	
   	lvBandSelection.setBorder(new TitledBorder("Band Selection"));
   	lvBandSelection.add(new JLabel("Red"));
   	
   	cvXAxis=parent.cvOpic.getBands();
   	
   	cvRedComboBox = new JComboBox(parent.vcBandList);
   	cvGreenComboBox = new JComboBox(parent.vcBandList);
   	cvBlueComboBox = new JComboBox(parent.vcBandList);
    
   	cvRedComboBox.addActionListener(this); 
   	lvBandSelection.add(cvRedComboBox);
   	lvBandSelection.add(new JLabel("Green"));
   	
   	cvGreenComboBox.addActionListener(this);
   	lvBandSelection.add(cvGreenComboBox);
   	lvBandSelection.add(new JLabel("Blue"));
   	
   	cvBlueComboBox.addActionListener(this);
   	lvBandSelection.add(cvBlueComboBox);
   	
   	lvConstraints.fill = GridBagConstraints.VERTICAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 1;
  	lvConstraints.gridheight = 1;
  	lvConstraints.gridx = 0;
  	lvConstraints.gridy = 0;
  	insetsPanel1.add(lvBandSelection, lvConstraints);
   	
  	button1.setText("OK");
    button1.addActionListener(this);
  	lvConstraints.fill = GridBagConstraints.VERTICAL;
  	lvConstraints.anchor = GridBagConstraints.NORTH;
  	lvConstraints.gridwidth = 1;
  	lvConstraints.gridheight = 1;
  	lvConstraints.gridx = 0;
  	lvConstraints.gridy = 10;
  	insetsPanel1.add(button1, lvConstraints);
  	
    this.getContentPane().add(insetsPanel1, null);

    setResizable(true);
    
    cvRedComboBox.setSelectedIndex(parent.cvOpic.getRedBand());
    cvGreenComboBox.setSelectedIndex(parent.cvOpic.getGreenBand());
    cvBlueComboBox.setSelectedIndex(parent.cvOpic.getBlueBand());
  }

  /**
   * Handles a WindowEvent
   */
  protected void processWindowEvent(WindowEvent e) 
  {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }

  /**
   * Closes the window
   */
  void cancel() 
  {
    dispose();
  }

  /**
   * Event handler for the different buttons and combo boxes
   */
  public void actionPerformed(ActionEvent e) 
  {
	  Object obj = e.getSource();
    if (e.getSource() == button1) {
      cancel();  
    }
    else if(obj == cvRedComboBox) 
    {
    	parent.cvOpic.setRedBand(cvRedComboBox.getSelectedIndex());
    	parent.cvOpic.repaint();
    	
    }
    else if(obj == cvGreenComboBox) 
    {
       parent.cvOpic.setGreenBand(cvGreenComboBox.getSelectedIndex());
       parent.cvOpic.repaint();
       
    }
    else if(obj == cvBlueComboBox) 
    {
       parent.cvOpic.setBlueBand(cvBlueComboBox.getSelectedIndex());
      parent.cvOpic.repaint();
      
    }
  }
}