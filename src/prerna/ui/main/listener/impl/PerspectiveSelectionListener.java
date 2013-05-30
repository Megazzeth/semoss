package prerna.ui.main.listener.impl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.apache.log4j.Logger;

import prerna.ui.components.ComboboxToolTipRenderer;
import prerna.util.DIHelper;
import prerna.util.StringNumericComparator;

public class PerspectiveSelectionListener extends AbstractListener {

	public JComponent view = null;
	
	// will have 2 string arrays one for the perspective and one for the question
	Hashtable model = null;
	Logger logger = Logger.getLogger(getClass());
	
	// needs to find what is being selected from event
	// based on that refresh the view of questions for that given perspective
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JComboBox bx = (JComboBox)e.getSource();
		String perspective = bx.getSelectedItem() + "";
		logger.info("Selected " + perspective + " <> " + view);
		JComboBox qp = (JComboBox)view;
		
		ArrayList tTip = new ArrayList();
		qp.removeAllItems();
		
		Hashtable questions = DIHelper.getInstance().getQuestions(perspective);
		StringNumericComparator comparator = new StringNumericComparator();
		if(questions != null){
			Vector questionsV = new Vector();
			for(Enumeration qs = questions.keys(); qs.hasMoreElements(); questionsV.add(qs.nextElement()));
			Collections.sort(questionsV, comparator);
			for(int itemIndex = 0;itemIndex < questionsV.size();itemIndex++)
			{
		    	tTip.add(questionsV.get(itemIndex));
		    	ComboboxToolTipRenderer renderer = new ComboboxToolTipRenderer();
		    	qp.setRenderer(renderer);
		    	renderer.setTooltips(tTip);
		    	renderer.setBackground(Color.WHITE);
				qp.addItem(questionsV.get(itemIndex) );
			}
		}
		logger.debug(questions);

	}

	@Override
	public void setView(JComponent view) {
		// TODO Auto-generated method stub
		logger.debug("View is set " + view);
		this.view = view;
		
	}


}
