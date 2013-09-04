package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.log4j.Logger;
import org.openrdf.model.vocabulary.RDF;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.PropertySpecData;
import prerna.ui.components.api.IChakraListener;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.QuestionPlaySheetStore;

import com.hp.hpl.jena.vocabulary.RDFS;

public class SaveOWLListener implements IChakraListener {

	// quite simple
	// when refresh is pressed
	// get the active sheet and call the repaint

	Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void actionPerformed(ActionEvent actionevent) {
		System.err.println("Saving the OWL");
		GraphPlaySheet ps = (GraphPlaySheet)QuestionPlaySheetStore.getInstance().getActiveSheet();
		saveIt();
		
	}
	
	// temporary placeholder
	// once complete, this will be written into the prop file back
	public void saveIt()
	{
		GraphPlaySheet ps = (GraphPlaySheet)QuestionPlaySheetStore.getInstance().getActiveSheet();
		String engineName = ps.engine.getEngineName();
		// get the core properties
		ps.exportDB();
		ps.engine.saveConfiguration();
	}
	
	@Override
	public void setView(JComponent view) {

	}
}