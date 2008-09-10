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
												"This application implements Eric Albert's BrowserLauncher class, which is know not to work on many linux systems.<br><br>"+
												"Questions, comments, and bug reports can be submitted at: <BR>"+
												"<a href=\"http://biodiversityinformatics.amnh.org/content.php?content=contact_us\">"+
												"http://biodiversityinformatics.amnh.org/content.php?content=contact_us</a><br><br>"+
												"This work was made possible by The Spanish Ministry of Science and Innovation, "+
												"<a href=\"http://www.integrants.es/index.php\">INTEGRANTS</a> program, a nation-wide "+
												"grant program to give Spanish university graduates the opportunity of carrying out "+
												"internships in companies in the U.S. and Canada, with partial support from the "+
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