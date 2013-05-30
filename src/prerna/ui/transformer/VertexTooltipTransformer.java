package prerna.ui.transformer;

import java.util.Vector;

import org.apache.commons.collections15.Transformer;

import prerna.om.DBCMVertex;
import prerna.ui.components.ControlData;
import prerna.util.Constants;

public class VertexTooltipTransformer implements Transformer <DBCMVertex, String> {
	
	ControlData data = null;
	
	public VertexTooltipTransformer(ControlData data)
	{
		this.data = data;
	}
	

	@Override
	public String transform(DBCMVertex arg0) {
		// get the DI Helper to find what is the property we need to get for vertex
		// based on that get that property and return it
		
		
		// get the DI Helper to find what is the property we need to get for vertex
		// based on that get that property and return it		
		String propName = "";//(String)arg0.getProperty(Constants.VERTEX_NAME);

		Vector props = this.data.getSelectedPropertiesTT(arg0.getProperty(Constants.VERTEX_TYPE)+"");
		if(props != null && props.size() > 0)
		{
			propName = "<html><body style=\"border:0px solid white; box-shadow:1px 1px 1px #000; padding:2px; background-color:white;\">" +
					"<font size=\"3\" color=\"black\"><i>"+arg0.getProperty(props.elementAt(0)+"");
			for(int propIndex=1;propIndex < props.size();propIndex++)
				propName = propName + "<br>" + arg0.getProperty(props.elementAt(propIndex)+"");
			propName = propName + "</i></font></body></html>";
		}
		//System.out.println("Prop Name " + propName);
		
		if(propName != null)
			return propName;		
		return "";
	}
}
