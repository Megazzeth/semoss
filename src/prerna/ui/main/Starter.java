package prerna.ui.main;

import java.awt.Color;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.ColorUIResource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import prerna.ui.components.PlayPane;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

import com.ibm.icu.util.StringTokenizer;

public class Starter {

	// read the properties file - DBCM_RDF_Map.Prop
	/* create the PlayPane
	 * 1. Read the perspective properties to get all the perspectives
	 * 2. For each of the perspectives read all the question numbers
	 * 3. For each question get the description
	 * 4. Convert this into a 2 dimensional Hashtable Hash1 - Perspective Questions, Hash2 for each question description and layout classes
	 * 5. Set this information into the util DIHelper class
	 * 6. Populate the perspective combo-boxes with all the information retrieved on perspectives
	 * 7. Create the UI
	*/
	
	public static void main(String [] args) throws Exception
	{
		String workingDir = System.getProperty("user.dir");
		String propFile = workingDir + "/RDF_Map.prop";
		Logger logger = Logger.getLogger(prerna.ui.main.Starter.class);
		
		//logger.setLevel(Level.INFO);
		PropertyConfigurator.configure(workingDir + "/log4j.prop");
		
		// Nimbus me
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				logger.info("Got the nimbus");
				UIManager.setLookAndFeel(info.getClassName());
				//Pretty Colors.
				UIManager.put("nimbusSelectionBackground", new Color(67,144,212)); //Light blue for selection
				//UIManager.put("nimbusBase", new Color(102,161,210)); //Light blue for everything else
				//UIManager.put("control", new Color(225,225,225)); //Light gray for the top bars behind the tabs
				//UIManager.put("nimbusBlueGrey", new Color(150,150,150)); //Separator bar, and disabled fields.
				UIManager.put("controlDkShadow", new Color(100,100,100)); //Color of scroll icons arrows
				UIManager.put("controlHighlight", new Color(100,100,100)); //Color of scroll icons highlights 
				UIManager.put("ProgressBar.repaintInterval", new Integer(100));//speed of indeterminate progress bar
				UIManager.put("ProgressBar.cycleTime", new Integer(1300));

				//UIManager.put("text", new Color(50,50,50)); //Color of text
				
				
				
				
				
				
				// comment this for nimbus
				//UIManager.setLookAndFeel ( WebLookAndFeel.class.getCanonicalName () );	
				UIDefaults defaults = UIManager.getLookAndFeelDefaults();
				
				UIDefaults tabPaneDefaults = new UIDefaults();
			    	tabPaneDefaults.put("TabbedPane.background", new ColorUIResource(Color.red));
		  	    //UIUtils.setPreferredLookAndFeel();

				//defaults.put("nimbusOrange",defaults.get("nimbusInfoBlue"));
				//defaults.put("Button.background",  Color.white);
				//defaults.put("TabbedPane.background", new Color(0,0,0));
				defaults.put("ToolTip.background", Color.LIGHT_GRAY);
				defaults.put("ToolTip[Enabled].backgroundPainter", null);
				defaults.put("ToolTip.contentMargins", new Insets(3,3,3,3));
				
				
				 
				break;
				}
			}
		} catch (Exception ignored) 
		{
			// handle exception
		} 

		// first get the engine file
		DIHelper.getInstance().loadCoreProp(propFile);
		
		// get the engine name
		//String engines = DIHelper.getInstance().getProperty(Constants.ENGINES);
		String engines = "";
		DIHelper.getInstance().setLocalProperty(Constants.ENGINES, engines);
		
		StringTokenizer tokens = new StringTokenizer(engines,";");
		
		File dir = new File("./db");
		String [] fileNames = dir.list(new prerna.util.PropFilter());
		for(int fileIdx = 0;fileIdx < fileNames.length;fileIdx++)
		{
			try{
				String fileName = "./db/" + fileNames[fileIdx];
				Properties prop = new Properties();
				prop.load(new FileInputStream(fileName));

				Utility.loadEngine(fileName, prop);				
			}catch(Exception ex)
			{
				logger.fatal("Engine Failed " + "./db/" + fileNames[fileIdx]);
			}
		}		
		PlayPane frame = new PlayPane();
		//PlayPane pane = new PlayPane();
		
		frame.start();
		frame.setVisible(true);
		
		WatchService watcher = FileSystems.getDefault().newWatchService();
		
		Path dir2Watch = Paths.get("./db");
		
		try
		{
			WatchKey key = dir2Watch.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
			
			while(true)
			{
				//WatchKey key2 = watcher.poll(1, TimeUnit.MINUTES);
				WatchKey key2 = watcher.take();
				
				for(WatchEvent<?> event: key2.pollEvents())
				{
					WatchEvent.Kind kind = event.kind();
					if(kind == StandardWatchEventKinds.ENTRY_CREATE)
					{
						String newFile = event.context() + "";
						if(newFile.endsWith("smss"))
						{
							Thread.sleep(2000);	
							try
							{
								loadNewDB(newFile);
							}catch(Exception ex)
							{
								ex.printStackTrace();
							}
						}else
							logger.info("Ignoring File " + newFile);
					}
				}
				key2.reset();

			}
			
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			// do nothing
		}

	}
	
	public static void loadNewDB(String newFile) throws Exception
	{
		Properties prop = new Properties();
		prop.load(new FileInputStream("./db/" +  newFile));

		Utility.loadEngine("./db/" +  newFile, prop);
		String engineName = prop.getProperty(Constants.ENGINE);
		JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
		DefaultListModel listModel = (DefaultListModel) list.getModel();
		listModel.addElement(engineName);
		//list.setModel(listModel);
		list.setSelectedIndex(0);
		list.repaint();
		JFrame frame2 = (JFrame) DIHelper.getInstance().getLocalProp(
				Constants.MAIN_FRAME);
		frame2.repaint();
	}
}
