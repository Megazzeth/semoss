package prerna.ui.transformer;

import java.awt.Paint;
import java.util.Hashtable;

import org.apache.commons.collections15.Transformer;
import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.util.Constants;

public class VertexPaintTransformer implements Transformer <DBCMVertex, Paint> {

	Hashtable <String, String> verticeURI2Show = null;
	Logger logger = Logger.getLogger(getClass());
	public static VertexPaintTransformer tx = null;
	
	public VertexPaintTransformer()
	{
		
	}
	public void setVertHash(Hashtable verticeURI2Show)
	{
		this.verticeURI2Show = verticeURI2Show;
	}
	
	

	@Override
	public Paint transform(DBCMVertex arg0) {
		// get the DI Helper to find what is the property we need to get for vertex
		// based on that get that property and return it
		
		Paint type = null;
		if(verticeURI2Show == null){
			String propType = (String)arg0.getProperty(Constants.VERTEX_TYPE);
			String vertName = (String)arg0.getProperty(Constants.VERTEX_NAME);
			
			type = TypeColorShapeTable.getInstance().getColor(propType, vertName);
		}
		else if(verticeURI2Show != null)
		{
			String URI = (String)arg0.getProperty(Constants.URI);
			logger.debug("URI " + URI);
			if(verticeURI2Show.containsKey(URI))
			{
				String propType = (String)arg0.getProperty(Constants.VERTEX_TYPE);
				String vertName = (String)arg0.getProperty(Constants.VERTEX_NAME);
				logger.debug("Found the URI");
				type = TypeColorShapeTable.getInstance().getColor(propType, vertName);
			}
		}
		//type.
		
		//type = new GradientPaint(0, 0, color ,100,0,color.WHITE);
		/*Paint type = null;
		//DIHelper.getInstance().getLocalProp(key)
		if(propType != null && DIHelper.getInstance().getProperty(propType + "_COLOR") != null && DIHelper.getInstance().getLocalProp(DIHelper.getInstance().getProperty(propType+"_COLOR")) != null)
			type = (Paint)DIHelper.getInstance().getLocalProp(DIHelper.getInstance().getProperty(propType+"_COLOR"));

		if(type == null)
			type = (Paint)DIHelper.getInstance().getLocalProp(Constants.RED);
		//if(propName != null)
		//	return (String)arg0.getProperty(propName);
		
		//System.out.println(" SHape is " + type);
		
		//return "";Paint type = TypeColorShapeTable.getInstance().getColor(Constants.TRANSPARENT);*/

		return type;
	}
}
