/*
** File: GraphicalEnvironment.java
** Author(s): Roberto Garcia Yunta
** Creation Date: 2008-10-17
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

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class GraphicalEnvironment extends JApplet implements ActionListener
{
  JButton cvOpenBtn;

  /**
   * inits the JApplet
   */
  public void init () 
  {
  	Font font = new Font("Arial",Font.BOLD,13);
  	cvOpenBtn = new JButton();
  	cvOpenBtn.setText("Launch interactive");
  	cvOpenBtn.setFont(font);
  	cvOpenBtn.addActionListener(this);
  	
  	JPanel lvPanel = new JPanel();

  	this.add(lvPanel);

  	lvPanel.add(cvOpenBtn);
  	
  } 



  /**
   * Actions performed when the buttons open and close are clicked
   */
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