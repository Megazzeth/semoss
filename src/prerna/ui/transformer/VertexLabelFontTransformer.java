package prerna.ui.transformer;

import java.awt.Font;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.collections15.Transformer;
import org.apache.log4j.Logger;

import prerna.om.DBCMVertex;
import prerna.util.Constants;

public class VertexLabelFontTransformer implements Transformer <DBCMVertex, Font> {
	

	Hashtable <String, String> verticeURI2Show = null;
	Logger logger = Logger.getLogger(getClass());
	int initialDefaultSize=8;
	int currentDefaultSize;
	int maxSize = 55;
	int minSize = 0;
	//This stores all font size data about the nodes.  Different than verticeURI2Show because need to remember size information when vertex label is unhidden
	Hashtable<String, Integer> nodeSizeData;
	

	public VertexLabelFontTransformer()
	{
		nodeSizeData = new Hashtable();
		currentDefaultSize = initialDefaultSize;
	}
	
	public void setVertHash(Hashtable verticeURI2Show)
	{
		this.verticeURI2Show = verticeURI2Show;
	}

	public void clearSizeData()
	{
		nodeSizeData.clear();
		currentDefaultSize = initialDefaultSize;
	}
	
	public void increaseFontSize(){
		if(currentDefaultSize<maxSize)
			currentDefaultSize++;
		Iterator vertURIs = nodeSizeData.keySet().iterator();
		while(vertURIs.hasNext()){
			String vertURI = (String) vertURIs.next();
			int size = nodeSizeData.get(vertURI);
			if(size<maxSize)
				size = size+1;
			nodeSizeData.put(vertURI, size);
		}
	}

	public void decreaseFontSize(){
		if(currentDefaultSize>minSize)
			currentDefaultSize--;
		Iterator vertURIs = nodeSizeData.keySet().iterator();
		while(vertURIs.hasNext()){
			String vertURI = (String) vertURIs.next();
			int size = nodeSizeData.get(vertURI);
			if(size>minSize)
				size = size-1;
			nodeSizeData.put(vertURI, size);
		}
	}

	public void increaseFontSize(String nodeURI){
		if(nodeSizeData.containsKey(nodeURI)){
			int size = nodeSizeData.get(nodeURI);
			if(size<maxSize)
				size = size+1;
			nodeSizeData.put(nodeURI, size);
		}
		else{
			int size = currentDefaultSize;
			if(size<maxSize)
				size = size +1;
			nodeSizeData.put(nodeURI, size);
		}
	}

	public void decreaseFontSize(String nodeURI){
		if(nodeSizeData.containsKey(nodeURI)){
			int size = nodeSizeData.get(nodeURI);
			if(size>minSize)
				size = size-1;
			nodeSizeData.put(nodeURI, size);
		}
		else{
			int size = currentDefaultSize;
			if(size>minSize)
				size = size -1;
			nodeSizeData.put(nodeURI, size);
		}
	}
	

	@Override
	public Font transform(DBCMVertex arg0)
	{
		int customSize = currentDefaultSize;
		if(nodeSizeData.containsKey(arg0.getURI()))
			customSize = nodeSizeData.get(arg0.getURI());
		Font font = new Font("Plain", Font.PLAIN, customSize);

		if(verticeURI2Show != null)
		{
			String URI = (String)arg0.getProperty(Constants.URI);
			logger.debug("URI " + URI);
			if(verticeURI2Show.containsKey(URI))
			{
				font = new Font("Plain", Font.PLAIN, customSize);
			}
			else font = new Font("Plain", Font.PLAIN, 0);
		}
		return font;
	}
}
