package prerna.ui.swing.custom;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class CustomDesktopPane extends JDesktopPane{
	String workingDir = System.getProperty("user.dir");
	final String fileString = workingDir +"/pictures/desktop.png";
	ImageIcon icon = new ImageIcon(fileString);
    Image image = icon.getImage();

    private BufferedImage img;
    public CustomDesktopPane()  
    {  
    	File file = new File(fileString);
    	try {
			img = javax.imageio.ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }  
    
    @Override
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);  
        if(img != null) 
        	g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);  
        else g.drawString("Image not found", 50,50);  
        
    }

}
