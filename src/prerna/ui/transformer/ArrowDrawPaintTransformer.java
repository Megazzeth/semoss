package prerna.ui.transformer;

import java.awt.Color;
import java.awt.Paint;
import java.util.Hashtable;

import org.apache.commons.collections15.Transformer;

import prerna.om.DBCMEdge;
import prerna.util.Constants;

public class ArrowDrawPaintTransformer implements Transformer <DBCMEdge, Paint> {
	
	
	Hashtable <String, DBCMEdge> edges = null;
	
	public ArrowDrawPaintTransformer()
	{
		
	}
	
	public void setEdges(Hashtable <String, DBCMEdge> edges)
	{
		this.edges = edges;
	}
	

	@Override
	public Paint transform(DBCMEdge edge)
	{

		Paint p = Color.white;
		if (edges != null) {
            if (edges.containsKey(edge.getProperty(Constants.URI))) {
                 p=Color.black;
            }
        }
        
		return p;
		
	}
}
