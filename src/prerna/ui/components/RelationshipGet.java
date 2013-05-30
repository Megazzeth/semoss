package prerna.ui.components;

import java.util.Hashtable;
import java.util.Vector;

import prerna.util.Constants;
import prerna.util.DIHelper;

public class RelationshipGet {
	
	
	public static Vector getRelationship(String type)
	{
		Vector questionV = new Vector();
		Hashtable extendHash = (Hashtable)DIHelper.getInstance().getLocalProp(Constants.EXTEND_TABLE);
		questionV = (Vector) extendHash.get(type);
		Vector newQuestionV = new Vector();
		int vSize = questionV.size();
		for (int i=0;i<vSize;i++)
		{
			String qString = (String) questionV.get(i);
			String[] qStringArray = qString.split(";");
    		String newQString = qStringArray[1];
    		newQuestionV.addElement(newQString);
		}
		return newQuestionV;
		
	}
	
}
