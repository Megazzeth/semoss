package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import prerna.om.DBCMEdge;
import prerna.om.DBCMVertex;
import prerna.ui.components.GraphPlaySheet;
import prerna.ui.components.api.IPlaySheet;
import prerna.ui.transformer.ArrowDrawPaintTransformer;
import prerna.ui.transformer.EdgeArrowStrokeTransformer;
import prerna.ui.transformer.EdgeStrokeTransformer;
import prerna.ui.transformer.SearchEdgeStrokeTransformer;
import prerna.ui.transformer.SearchVertexLabelFontTransformer;
import prerna.ui.transformer.SearchVertexPaintTransformer;
import prerna.ui.transformer.VertexLabelFontTransformer;
import prerna.ui.transformer.VertexPaintTransformer;
import prerna.util.Constants;
import edu.uci.ics.jung.visualization.picking.MultiPickedState;
import edu.uci.ics.jung.visualization.picking.PickedState;

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
		GraphPlaySheet ps2 = (GraphPlaySheet)ps;
		//Get the button name to understand whether to add upstream or downstream or both
		JMenuItem button = (JMenuItem) e.getSource();
		String buttonName = button.getName();
		// Get the DBCM edges from vertices and then add the edge
		
		Collection<Vector> allPlaySheetEdges = ps2.filterData.edgeTypeHash.values();
		Vector allEdgesVect = new Vector();
		for(Vector v: allPlaySheetEdges) allEdgesVect.addAll(v);
		logger.debug("Getting the base graph");
		
		//Get what edges are already highlighted so that we can just add to it
		EdgeStrokeTransformer tx = (EdgeStrokeTransformer)ps2.getView().getRenderContext().getEdgeStrokeTransformer();
		Hashtable <String, DBCMEdge> edgeHash = tx.getEdges();
		if(edgeHash==null) edgeHash = new Hashtable<String, DBCMEdge>();
		
		//Get what vertices are already painted so we can just add to it
		VertexPaintTransformer vtx = (VertexPaintTransformer)ps2.getView().getRenderContext().getVertexFillPaintTransformer();
		Hashtable <String, String> vertHash = vtx.getVertHash();
		if(vertHash == null) vertHash = new Hashtable<String, String>();
		PickedState state = new MultiPickedState();
		
		for(int vertIndex = 0;vertIndex < vertices.length;vertIndex++)
		{
			DBCMVertex vert = vertices[vertIndex];
			logger.debug("In Edges count is " + vert.getInEdges().size());
			logger.debug("Out Edges count is " + vert.getOutEdges().size());
			vertHash.put(vert.getURI(), vert.getURI());
			
			//if the button name contains upstream, get the upstream edges and vertices
			if(buttonName.contains("Downstream")){
				edgeHash = putEdgesInHash(vert.getInEdges(), edgeHash);
				for (DBCMEdge edge : vert.getInEdges()){
					if (allEdgesVect.contains(edge)){
						vertHash.put(edge.inVertex.getURI(), edge.inVertex.getURI());
						state.pick(edge.inVertex, true);
					}
				}
			}
			
			//if the button name contains downstream, get the downstream edges and vertices
			if(buttonName.contains("Upstream")){
				edgeHash = putEdgesInHash(vert.getOutEdges(), edgeHash);
				for (DBCMEdge edge : vert.getOutEdges()){
					if (allEdgesVect.contains(edge)){
						vertHash.put(edge.outVertex.getURI(), edge.outVertex.getURI());
						state.pick(edge.outVertex, true);
					}
				}
			}
			ps2.getView().setPickedVertexState(state);
		}
	
		if(ps2.searchPanel.btnHighlight.isSelected()){
			SearchEdgeStrokeTransformer tx1 = (SearchEdgeStrokeTransformer)ps2.getView().getRenderContext().getEdgeStrokeTransformer();
			tx1.setEdges(edgeHash);
			SearchVertexPaintTransformer vtx1 = (SearchVertexPaintTransformer)ps2.getView().getRenderContext().getVertexFillPaintTransformer();
			vtx1.setVertHash(vertHash);
			SearchVertexLabelFontTransformer vlft = (SearchVertexLabelFontTransformer)ps2.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(vertHash);
		}
		else{
			tx.setEdges(edgeHash);
			vtx.setVertHash(vertHash);
			VertexLabelFontTransformer vlft = (VertexLabelFontTransformer)ps2.getView().getRenderContext().getVertexFontTransformer();
			vlft.setVertHash(vertHash);
			ArrowDrawPaintTransformer atx = (ArrowDrawPaintTransformer)ps2.getView().getRenderContext().getArrowDrawPaintTransformer();
			atx.setEdges(edgeHash);
			EdgeArrowStrokeTransformer stx = (EdgeArrowStrokeTransformer)ps2.getView().getRenderContext().getEdgeArrowStrokeTransformer();
			stx.setEdges(edgeHash);
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
