package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.transformer.EdgeStrokeTransformer;
import prerna.ui.transformer.SearchEdgeStrokeTransformer;
import prerna.ui.transformer.SearchVertexLabelFontTransformer;
import prerna.ui.transformer.SearchVertexPaintTransformer;
import prerna.ui.transformer.VertexLabelFontTransformer;
import prerna.ui.transformer.VertexPaintTransformer;
import prerna.util.Constants;

public class AdjacentPopupMenuListener implements ActionListener {

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
		// Get the DBCM edges from vertices and then add the edge
		
		GraphPlaySheet ps2 = (GraphPlaySheet)ps;
		Collection<Vector> allPlaySheetEdges = ps2.filterData.edgeTypeHash.values();
		Vector allEdgesVect = new Vector();
		for(Vector v: allPlaySheetEdges) allEdgesVect.addAll(v);
		logger.debug("Getting the base graph");
		Hashtable <String, DBCMEdge> edgeHash = new Hashtable<String, DBCMEdge>();
		Hashtable <String, String> vertHash = new Hashtable<String, String>();

		for(int vertIndex = 0;vertIndex < vertices.length;vertIndex++)
		{
			DBCMVertex vert = vertices[vertIndex];
			logger.debug("In Edges count is " + vert.getInEdges().size());
			logger.debug("Out Edges count is " + vert.getOutEdges().size());
			edgeHash = putEdgesInHash(vert.getInEdges(), edgeHash);
			edgeHash = putEdgesInHash(vert.getOutEdges(), edgeHash);
			vertHash.put(vert.getURI(), vert.getURI());
			for (DBCMEdge edge : vert.getInEdges()){
				if (allEdgesVect.contains(edge))
					vertHash.put(edge.inVertex.getURI(), edge.inVertex.getURI());
			}
			for (DBCMEdge edge : vert.getOutEdges()){
				if (allEdgesVect.contains(edge))
					vertHash.put(edge.outVertex.getURI(), edge.outVertex.getURI());
			}
		}
	
		if(ps2.searchPanel.btnHighlight.isSelected()){
			SearchEdgeStrokeTransformer tx = (SearchEdgeStrokeTransformer)ps2.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(edgeHash);
			SearchVertexPaintTransformer vtx = (SearchVertexPaintTransformer)ps2.getView().getRenderContext().getVertexFillPaintTransformer();
			vtx.setVertHash(vertHash);
			SearchVertexLabelFontTransformer vlft = (SearchVertexLabelFontTransformer)ps2.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(vertHash);
		}
		else{
			EdgeStrokeTransformer tx = (EdgeStrokeTransformer)ps2.getView().getRenderContext().getEdgeStrokeTransformer();
			tx.setEdges(edgeHash);
			VertexPaintTransformer vtx = (VertexPaintTransformer)ps2.getView().getRenderContext().getVertexFillPaintTransformer();
			vtx.setVertHash(vertHash);
			VertexLabelFontTransformer vlft = (VertexLabelFontTransformer)ps2.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(vertHash);
		}
		
		// repaint it
		ps2.getView().repaint();
	}
	
	private Hashtable <String, DBCMEdge> putEdgesInHash(Vector <DBCMEdge> edges, Hashtable <String, DBCMEdge> hash)
	{
		for(int edgeIndex = 0;edgeIndex < edges.size();edgeIndex++)
		{
			DBCMEdge edge = edges.elementAt(edgeIndex);
			hash.put((String)edge.getProperty(Constants.URI), edge);
		}
		return hash;
	}
		
}
