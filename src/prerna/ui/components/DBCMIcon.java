package prerna.ui.components;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class DBCMIcon extends ImageIcon {
	
	public DBCMIcon(String fileName)
	{
		super(fileName);
	}
	@Override
    public void paintIcon(Component c, Graphics g, int x, int y ) {
        g.drawImage(getImage(), 5, 6, c.getWidth(), c.getHeight(), c);
    }

}
