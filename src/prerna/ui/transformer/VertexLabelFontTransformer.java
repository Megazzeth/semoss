package prerna.ui.transformer;

import java.awt.Font;
import java.util.Hashtable;

import org.apache.commons.collections15.Transformer;
import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.util.Constants;

public class VertexLabelFontTransformer implements Transformer <DBCMVertex, Font> {
	

	Hashtable <String, String> verticeURI2Show = null;
	Logger logger = Logger.getLogger(getClass());
	

	public VertexLabelFontTransformer()
	{
		//this.verticeURI2Show = verticeURI2Show;
	}
	
	public void setVertHash(Hashtable verticeURI2Show)
	{
		this.verticeURI2Show = verticeURI2Show;
	}
	

	@Override
	public Font transform(DBCMVertex arg0)
	{
		

		Font font = new Font("Plain", Font.PLAIN, 8);

		if(verticeURI2Show != null)
		{
			String URI = (String)arg0.getProperty(Constants.URI);
			logger.debug("URI " + URI);
			if(verticeURI2Show.containsKey(URI))
			{
				font = new Font("Plain", Font.PLAIN, 8);
			}
			else font = new Font("Plain", Font.PLAIN, 0);
		}
		return font;
	}
}
