package prerna.ui.transformer;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Hashtable;

import org.apache.commons.collections15.Transformer;

import prerna.om.DBCMEdge;
import prerna.util.Constants;

public class EdgeArrowStrokeTransformer implements Transformer <DBCMEdge, Stroke> {
	
	
	Hashtable <String, DBCMEdge> edges = null;
	
	public EdgeArrowStrokeTransformer()
	{
		
	}
	
	public void setEdges(Hashtable edges)
	{
		this.edges = edges;
	}
	
	@Override
	public Stroke transform(DBCMEdge edge)
	{
		
		float selectedFontFloat =3.0f;
		float unselectedFontFloat =0.1f;
		
		float standardFontFloat = 0.3f;
		
		Stroke retStroke = new BasicStroke(1.0f);
		try
		{	
                if (edges != null) {
                    if (edges.containsKey(edge.getProperty(Constants.URI))) {
                    	Object val = edges.get(edge.getProperty(Constants.URI));
                    	try{
                    		double valDouble = (Double) val;
                    		float valFloat = (float) valDouble;
                    		float newFontFloat = selectedFontFloat * valFloat;
                            retStroke = new BasicStroke(newFontFloat, BasicStroke.CAP_BUTT,
                                          BasicStroke.JOIN_MITER, 10.0f);
                    	}catch (Exception e){
                        retStroke = new BasicStroke(selectedFontFloat, BasicStroke.CAP_BUTT,
                                      BasicStroke.JOIN_MITER, 10.0f);
                          // System.out.println("shortest path edges");
                    	}
                    } else{
                          retStroke = new BasicStroke(unselectedFontFloat);
                          // System.out.println(count);
                    }
                }
                else
                {
                	retStroke = new BasicStroke(standardFontFloat, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_ROUND);
                }
                

		}
		catch(Exception ex)
		{
			//ignore
		}
		return retStroke;
	}
}
