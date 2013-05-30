package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.ui.components.api.IPlaySheet;

public class HideVertexPopupMenuListener implements ActionListener {

	IPlaySheet ps = null;
	DBCMVertex [] vertices = null;
	
	Logger logger = Logger.getLogger(getClass());
	
	public void setPlaysheet(IPlaySheet ps)
	{
		this.ps = ps;
	}
	
	public void setDBCMVertex(DBCMVertex [] vertices)
	{
		logger.debug("Set the vertices " + vertices.length);
		this.vertices = vertices;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// get the engine
		// execute the neighbor hood 
		// paint it
		// get the query from the 
		
		for(int vertIndex = 0;vertIndex < vertices.length;vertIndex++)
		{
			// take the vertex and add it to the sheet
			ps.getFilterData().addNodeToFilter(vertices[vertIndex]);
		}
		ps.refineView();
		//ps.createView();
	}
}
