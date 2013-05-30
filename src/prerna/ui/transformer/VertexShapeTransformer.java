package prerna.ui.transformer;

import java.awt.Shape;

import org.apache.commons.collections15.Transformer;

import prerna.om.DBCMVertex;
import prerna.ui.helpers.TypeColorShapeTable;
import prerna.util.Constants;

public class VertexShapeTransformer implements Transformer <DBCMVertex, Shape> {
	
	public static VertexShapeTransformer tx = null;
	
	public VertexShapeTransformer()
	{
		
	}
	
	public static VertexShapeTransformer getInstance()
	{
		if(tx == null)
			tx = new VertexShapeTransformer();
		return tx;
	}

	@Override
	public Shape transform(DBCMVertex arg0) {
		// get the DI Helper to find what is the property we need to get for vertex
		// based on that get that property and return it
		
		String propType = (String)arg0.getProperty(Constants.VERTEX_TYPE);
		String vertName = (String)arg0.getProperty(Constants.VERTEX_NAME);
		
		Shape type = null;
		type = TypeColorShapeTable.getInstance().getShape(propType, vertName);
		
		/*if(propType !=null && fileName != null)
		{
			//System.out.println("In here");
			ImageIcon icon = new ImageIcon(fileName);
			image = icon.getImage();
			//System.out.println("Got the image");
			//System.out.println("Image is " + image);
			//System.out.println("File name is " + fileName);
			//type = FourPassImageShaper.getShape(fileName, 30);
		}
		//System.out.println("Prop Name " + propType);
		if(type == null)
		{
			//System.out.println("Did not get the iamge");
			//DIHelper.getInstance().getLocalProp(key)
			if(propType != null && DIHelper.getInstance().getProperty(propType + "_SHAPE") != null && DIHelper.getInstance().getLocalProp(DIHelper.getInstance().getProperty(propType + "_SHAPE")) != null)
				type = (Shape)DIHelper.getInstance().getLocalProp(DIHelper.getInstance().getProperty(propType+"_SHAPE"));
	
			if(type == null)
				type = (Shape)DIHelper.getInstance().getLocalProp(Constants.RHOMBUS);
			//if(propName != null)
			//	return (String)arg0.getProperty(propName);
			
			//System.out.println(" SHape is " + type);
			
			//return "";
		}*/
		return type;
	}
}
