package prerna.ui.transformer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.Hashtable;

import org.apache.commons.collections15.Transformer;
import org.apache.log4j.Logger;

import prerna.om.DBCMEdge;
import prerna.util.Constants;

public class EdgePaintTransformer implements Transformer <DBCMEdge, Paint> {
	
	Hashtable<String, Paint> edgeHash = null;
	Logger logger = Logger.getLogger(getClass());
	public static VertexPaintTransformer tx = null;
	
	public EdgePaintTransformer()
	{
		
	}
	public void setEdgeHash(Hashtable<String, Paint> edgesHash)
	{
		this.edgeHash = edgesHash;
	}
	
	
	public static VertexPaintTransformer getInstance()
	{
		if(tx == null)
			tx = new VertexPaintTransformer();
		return tx;
	}

	@Override
	public Paint transform(DBCMEdge edge)
	{
		
		Paint p = Color.red;
		float dash[] = {10.0f};
		
		Stroke retStroke = new BasicStroke(1.0f);
		try
		{	
                if (edgeHash != null) {
                    if (edgeHash.containsKey(edge.getProperty(Constants.URI))) {
                          p=edgeHash.get(edge.getProperty(Constants.URI));
                    } else{
                          p=Color.black;
                          // System.out.println(count);
                    }
                }
                else
                {
                	p = Color.black;
                }
                

		}
		catch(Exception ex)
		{
			//ignore
		}
		return p;
	}
}
