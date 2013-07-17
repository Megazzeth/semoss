package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import prerna.ui.components.GraphPlaySheet;

import com.google.gson.Gson;
import com.teamdev.jxbrowser.Browser;


// implements the minimum spanning tree

public class ChartPullDataListener implements ActionListener {
	GraphPlaySheet ps = null;
	Logger logger = Logger.getLogger(getClass());
	Browser browser = null;
	@Override
	public void actionPerformed(ActionEvent e)
	{
		callIt();
	}	
	
	public void setBrowser (Browser browser)
	{
		this.browser = browser;
	}
	
	  public void setPlaySheet(GraphPlaySheet ps)
	  {
		  this.ps = ps;
	  }

	public void callIt()
	{
		Hashtable nodeHash = ps.filterData.typeHash;
		Hashtable edgeHash = ps.filterData.edgeTypeHash;

		Hashtable newHash = new Hashtable();
		newHash.put("Nodes", nodeHash);
		//newHash.put("Edges", edgeHash);

		Gson gson = new Gson();
		logger.info("Converted " + gson.toJson(newHash));

		//webBrowser.executeJavascript("helloWorld('" + gson.toJson(newHash) + "');"); //Please tell me this is awesome !!!!!!');");
		//Please tell me this is awesome !!!!!!');");
	    browser.executeScript("start('" + gson.toJson(newHash) + "');");
	}


}
