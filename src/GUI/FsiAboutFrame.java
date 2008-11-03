/*
** File: FsiAboutFrame.java
** Author(s): Peter J. Ersts (ersts at amnh.org)
** Creation Date: 2008-09-10
** Copyright (c) 2008, American Museum of Natural History. All rights reserved.
** Creation Date: 2008-09-10
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
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import edu.stanford.ejalbert.BrowserLauncher;



public class FsiAboutFrame extends JDialog implements HyperlinkListener
{

  public FsiAboutFrame(FsiMainFrame parent, String version) 
  {
  	String messageText = "<HTML><BODY>"+
												"Version "+ version +" of the Feature Space Interactive<br>"+
												"Written by Roberto Garcia-Yunta with contributions from Peter J. Ersts.<br>"+
												"Feature Space concept by Ned Horning.<br><br>"+
												"This application implements Eric Albert's BrowserLauncher class, which is known not to work on many linux systems.<br><br>"+
												"Questions, comments, and bug reports can be submitted at: <BR>"+
												"<a href=\"http://biodiversityinformatics.amnh.org/content.php?content=contact_us\">"+
												"http://biodiversityinformatics.amnh.org/content.php?content=contact_us</a><br><br>"+
												"This work was made possible by The Spanish Ministry of Science and Innovation, "+
												"<a href=\"http://www.integrants.es/index.php\">INTEGRANTS</a> program, a nation-wide "+
												"grant program to give Spanish university graduates the opportunity of carrying out "+
												"internships with organizations in the U.S. and Canada. Additional support provided by the "+
												"<a HREF=\"http://cbc.amnh.org\">Center for Biodiversity and Conservation</a> at the "+
												"<a HREF=\"http://amnh.org\">American Museum of Natural History</a>."+
												"</BODY></HTML>";
		setSize(435,600);
		setTitle("About");
		
		getContentPane().setBackground(Color.WHITE);
		setBackground(new Color(255,255,255));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocation((int)parent.getLocationOnScreen().getX() + (parent.getWidth()/2) - (getWidth()/2), (int)parent.getLocationOnScreen().getY() + (parent.getHeight()/2) - (getHeight()/2));
		
		JEditorPane text = new JEditorPane();
		text.setContentType("text/html");
		text.addHyperlinkListener(this);
		text.setText(messageText);
		text.setCaretPosition(0);
		text.setEditable(false);
		JScrollPane textScrollPane = new JScrollPane(text);
		textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JPanel logos = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
		logos.setBackground(new Color(255, 255, 255));
		logos.add(new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("cbc-blue-sm.jpg"))));
		
		getContentPane().add(logos, BorderLayout.SOUTH);
		getContentPane().add(textScrollPane, BorderLayout.CENTER);
		setVisible(true);

  }

  /**
	 * Required method to implement HyperlinkListener
	 */
	public void hyperlinkUpdate(HyperlinkEvent evt) 
	{
		if(evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
		{
			try 
			{
				BrowserLauncher.openURL(evt.getURL().toString());
			}
			catch (IOException e) {}
		}
	}

}