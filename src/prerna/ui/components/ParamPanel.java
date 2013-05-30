package prerna.ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.StyleException;

import prerna.rdf.engine.api.IEngine;
import prerna.ui.helpers.EntityFiller;
import prerna.util.Constants;
import prerna.util.DIHelper;
import java.awt.SystemColor;

public class ParamPanel extends JPanel implements Runnable{
	public ParamPanel() {
		setBackground(SystemColor.control);
	}
	
	Hashtable params = null;
	Hashtable paramType = null;
	
	public void setParams(Hashtable params)
	{
		this.params = params;
	}
	
	public void setParamType(Hashtable paramType)
	{
		this.paramType = paramType;
	}
	
	public void setParam(String paramName, String paramValue)
	{
		params.put(paramName, paramValue);
	}
	
	public Hashtable getParams()
	{
		return params;
	}
	
	public void paintParam()
	{
		// paints all the params 
		// to be implemented
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0,0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		this.setLayout(gridBagLayout);
		
		Enumeration keys = params.keys();
		GridBagConstraints gbc_element = new GridBagConstraints();
		int elementInt=0;
		while(keys.hasMoreElements())
		{
			
			String key = (String)keys.nextElement();
			JLabel label = new JLabel(key);
	            label.setForeground(Color.DARK_GRAY);
	            //JTextField field = new ParamField(key);
	            // execute the logic for filling the information here
	            String entityType = (String)paramType.get(key);
	            String [] fetching = {"Fetching"};
	            ParamComboBox field = new ParamComboBox(fetching);
	            field.setParamName(key);
	            Style.registerTargetClassName(this, ".paramPane");
	            try {
	                  Style.apply(this, new Style(getClass().getResource("styles.css")));
	            } catch (StyleException e) {
	                  e.printStackTrace();
            }

			
			field.setEditable(false);
			//PlayTextField pField = new PlayTextField(field);
			
			// get the list
			JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
			// get the selected repository
			Object [] repos = (Object [])list.getSelectedValues();
			
			System.out.println("Repository is " + repos);
			
			for(int repoIndex = 0;repoIndex < repos.length;repoIndex++)
			{
				IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(repos[repoIndex]+"");
				System.out.println("Repository is " + repos[repoIndex]);
				
				EntityFiller filler = new EntityFiller();
				filler.engineName = repos[repoIndex]+"";
				//filler.engine = engine;
				filler.box = field;
				filler.type = entityType;
				Thread aThread = new Thread(filler);
				aThread.run();
			}
			
			gbc_element = new GridBagConstraints();	
			gbc_element.anchor = GridBagConstraints.WEST;
			//gbc_element.fill = GridBagConstraints.BOTH;
			gbc_element.insets = new Insets(0, 5, 5, 5);
			gbc_element.gridx = 0;
			gbc_element.gridy = elementInt;
			this.add(label, gbc_element);
			
			
			field.setPreferredSize(new Dimension(150, 25));
			field.setMinimumSize(new Dimension(150, 25));
			field.setMaximumSize(new Dimension(200, 32767));
			field.setBackground(Color.white);
			
			gbc_element = new GridBagConstraints();
			gbc_element.anchor = GridBagConstraints.NORTH;
			gbc_element.fill = GridBagConstraints.HORIZONTAL;
			gbc_element.insets = new Insets(0, 5, 5, 5);
			gbc_element.gridx = 1;
			gbc_element.gridy = elementInt;
			this.add(field, gbc_element);
			elementInt++;

		}
		/*
		GridLayout layout = new GridLayout(0,2);
		this.setLayout(layout);
		
		Enumeration keys = params.keys();
		while(keys.hasMoreElements())
		{
			String key = (String)keys.nextElement();
			JLabel label = new JLabel(key);
			//JTextField field = new ParamField(key);
			// execute the logic for filling the information here
			String entityType = (String)paramType.get(key);
			String [] fetching = {"Fetching"};
			ParamComboBox field = new ParamComboBox(fetching);
		
			field.setParamName(key);
			
			field.setEditable(false);
			//PlayTextField pField = new PlayTextField(field);
			
			// get the list
			JList list = (JList)DIHelper.getInstance().getLocalProp(Constants.REPO_LIST);
			// get the selected repository
			Object [] repos = (Object [])list.getSelectedValues();
			
			System.out.println("Repository is " + repos);
			
			for(int repoIndex = 0;repoIndex < repos.length;repoIndex++)
			{
				IEngine engine = (IEngine)DIHelper.getInstance().getLocalProp(repos[repoIndex]+"");
				System.out.println("Repository is " + repos[repoIndex]);
				
				EntityFiller filler = new EntityFiller();
				filler.engineName = repos[repoIndex]+"";
				//filler.engine = engine;
				filler.box = field;
				filler.type = entityType;
				Thread aThread = new Thread(filler);
				aThread.run();
			}
			
			label.setPreferredSize(new Dimension(60	, 40));
			field.setPreferredSize(new Dimension(150, 40));
			add(label, "cell 1 0,growx,aligny top");
			add(field, "cell 1 0,growx,aligny top");
		}
		*/
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		paintParam();
		
	}
}
