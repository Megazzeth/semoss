package prerna.ui.main;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.ColorUIResource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import prerna.rdf.engine.api.IEngine;
import prerna.ui.components.PlayPane;
import prerna.util.Constants;
import prerna.util.DIHelper;

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
				//UIManager.put("nimbusSelectionBackground", new Color(67,144,212)); //Light blue for selection
				//UIManager.put("nimbusBase", new Color(102,161,210)); //Light blue for everything else
				//UIManager.put("control", new Color(225,225,225)); //Light gray for the top bars behind the tabs
				//UIManager.put("nimbusBlueGrey", new Color(150,150,150)); //Separator bar, and disabled fields.
				UIManager.put("controlDkShadow", new Color(100,100,100)); //Color of scroll icons arrows
				UIManager.put("controlHighlight", new Color(100,100,100)); //Color of scroll icons highlights
				//UIManager.put("text", new Color(50,50,50)); //Color of text
				
				
				
				
				
				
				// comment this for nimbus
				//UIManager.setLookAndFeel ( WebLookAndFeel.class.getCanonicalName () );	
				UIDefaults defaults = UIManager.getLookAndFeelDefaults();
				
				UIDefaults tabPaneDefaults = new UIDefaults();
			    	tabPaneDefaults.put("TabbedPane.background", new ColorUIResource(Color.red));
		  	    //UIUtils.setPreferredLookAndFeel();

				defaults.put("nimbusOrange",defaults.get("nimbusInfoBlue"));
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
		String engines = DIHelper.getInstance().getProperty(Constants.ENGINES);
		DIHelper.getInstance().setLocalProperty(Constants.ENGINES, engines);
		
		StringTokenizer tokens = new StringTokenizer(engines,";");
		while(tokens.hasMoreTokens())
		{		
			
			String engineName = tokens.nextToken();//DIHelper.getInstance().getProperty(Constants.ENGINE);
			String engineClass = DIHelper.getInstance().getProperty(engineName);
			logger.info("Trying to start engine " + engineName + "<<>>" + engineClass);
			IEngine engine = (IEngine)Class.forName(engineClass).newInstance();
			// startup the engine etc. - need to change it later to only what you need
			String enginePropFile = DIHelper.getInstance().getProperty(engineName + "_PROP");
			engine.openDB(enginePropFile);
			
			// set the reference
			DIHelper.getInstance().setLocalProperty(engineName, engine);
		}
		
		//DIHelper.getInstance().loadPerspectives();
		PlayPane frame = new PlayPane();
		//PlayPane pane = new PlayPane();
		
		frame.start();
		frame.setVisible(true);
	}
}
