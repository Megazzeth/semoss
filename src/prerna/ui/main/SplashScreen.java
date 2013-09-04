package prerna.ui.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicProgressBarUI;


public class SplashScreen extends JWindow {

	private static JProgressBar progressBar = new JProgressBar();
	private static int count;

	public SplashScreen() {
		Container container = getContentPane();
		container.setLayout(null);

		BufferedImage image;
		JLabel picLabel = new JLabel();
		try {
			String workingDir = System.getProperty("user.dir");
			String picFileURL = workingDir + "\\pictures\\semosslogo.jpg";
			image = ImageIO.read(new File(picFileURL));
			picLabel = new JLabel(new ImageIcon(image));
			picLabel.setSize(661, 335);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		progressBar.setUI(new MyProgressUI());
		progressBar.setForeground(Color.blue);
		progressBar.setMaximum(60);
		progressBar.setBounds(270, 335, 120, 15);
		progressBar.setIndeterminate(true);
		
		JLabel lblLicense = new JLabel("\u00A9 Distributed under the GNU General Public License");
		lblLicense.setBounds(210, 360, 350, 12);
		
		container.add(progressBar);
		container.add(picLabel);
		container.add(lblLicense);
		
		setSize(660, 385);
		setLocationRelativeTo(null);
		container.requestFocus();
		setVisible(true);
//		loadProgressBar();
	}

	private void loadProgressBar() {
		ActionListener progressListener = new ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				count++;
				progressBar.setValue(count);
			}
		};
	}

	private class MyProgressUI extends BasicProgressBarUI {

		private Rectangle r = new Rectangle();

		@Override
		protected void paintIndeterminate(Graphics g, JComponent c) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			r = getBox(r);
			g.setColor(progressBar.getForeground());
			g.fillRect(r.x, r.y, r.width, r.height);
		}
	}
}