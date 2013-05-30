package prerna.ui.transformer;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Hashtable;

import org.apache.commons.collections15.Transformer;

import prerna.om.DBCMVertex;

public class VertexStrokeTransformer implements Transformer <DBCMVertex, Stroke> {
	
	
	Hashtable <String, DBCMVertex> vertices = null;
	
	public VertexStrokeTransformer()
	{
		
	}
	
	public void setEdges(Hashtable <String, DBCMVertex> edges)
	{
		this.vertices = vertices;
	}
	

	@Override
	public Stroke transform(DBCMVertex vertex)
	{
		
		
		Stroke retStroke = new BasicStroke(1.0f);
		try
		{	
			retStroke = new BasicStroke(0f);
                

		}
		catch(Exception ex)
		{
			//ignore
		}
		return retStroke;
	}
}
