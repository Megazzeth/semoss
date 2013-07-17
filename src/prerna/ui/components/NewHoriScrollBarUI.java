package prerna.ui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class NewHoriScrollBarUI extends BasicScrollBarUI {
	private Image thumb;

	public NewHoriScrollBarUI() {
		try {
			String workingDir = System.getProperty("user.dir");
			String picFileURL = workingDir+"/pictures/rect2.png";
			File picFile = new File(picFileURL);
			thumb = ImageIO.read(picFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {        
		g.translate(thumbBounds.x, thumbBounds.y);
		((Graphics2D) g).drawImage(thumb, 0, 0, thumbBounds.width, thumbBounds.height, null);
		g.translate( -thumbBounds.x, -thumbBounds.y );
	}

	@Override
	protected JButton createIncreaseButton(int orientation)
	{
		if (incrButton == null)
			incrButton = new BasicArrowButton((orientation == SwingConstants.HORIZONTAL) ? SwingConstants.NORTH : SwingConstants.EAST);
		else
		{
			if (orientation == SwingConstants.HORIZONTAL)
				((BasicArrowButton) incrButton).setDirection(SwingConstants.NORTH);
			else
				((BasicArrowButton) incrButton).setDirection(SwingConstants.EAST);
		}
		incrButton.setOpaque(false);
		return incrButton;
	}

	@Override
	protected JButton createDecreaseButton(int orientation)
	{
		if (decrButton == null)
			decrButton = new BasicArrowButton((orientation == SwingConstants.HORIZONTAL) ? SwingConstants.SOUTH : SwingConstants.WEST);
		else
		{
			if (orientation == SwingConstants.HORIZONTAL)
				((BasicArrowButton) decrButton).setDirection(SwingConstants.SOUTH);
			else
				((BasicArrowButton) decrButton).setDirection(SwingConstants.WEST);
		}
		return decrButton;
	}
}
