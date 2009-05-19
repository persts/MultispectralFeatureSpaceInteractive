package GUI;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.*;

public class AppletLoader extends Applet implements Runnable, AppletStub 
{
  Thread appletThread;

  public void init() 
  {
    setBackground(Color.white);
  }

  public void paint(Graphics g) 
  {
    g.drawString("Loading FSI...", 30, 30);
  } 

  public void run() 
  {
    try 
    {
      Class appletClass = Class.forName("GUI.GraphicalEnvironment");
      Applet realApplet = (Applet)appletClass.newInstance();
      realApplet.setStub(this);
      setLayout( new GridLayout(1,0));
      add(realApplet);
      realApplet.init();
      realApplet.start();
    }
    catch (Exception e) 
    {
      System.out.println( e );
    }
      validate();
  }

  public void start()
  {
    appletThread = new Thread(this);
    appletThread.start();
  }

  public void stop() 
  {
    appletThread.stop();
    appletThread = null;
  }

  public void appletResize( int width, int height )
  {
    resize( width, height );
  }
}