package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GraphicalEnvironment 
{

  public GraphicalEnvironment() 
  {
    FsiMainFrame lvMainFrame = new FsiMainFrame();

    //Centrar la ventana
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension lvMainFrameSize = lvMainFrame.getSize();
    
    if (lvMainFrameSize.height > screenSize.height) {
      lvMainFrameSize.height = screenSize.height;
    }
    if (lvMainFrameSize.width > screenSize.width) {
      lvMainFrameSize.width = screenSize.width;
    }
    
    //Set the location on the screen, validation, and set visible
    lvMainFrame.setLocation((screenSize.width - lvMainFrameSize.width) / 2, (screenSize.height - lvMainFrameSize.height) / 2);
    lvMainFrame.validate();
    lvMainFrame.setVisible(true);
  }

  public static void main(String[] args) {
    new GraphicalEnvironment();
  }
}