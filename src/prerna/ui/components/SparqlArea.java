package prerna.ui.components;

import javax.swing.JTextArea;

public class SparqlArea extends JTextArea {
	
	String questionName = null;
	String keyName = null;
	
	public void setQuestionName(String questionName)
	{
		this.questionName = questionName;
	}
	
	public String getQuestionName()
	{
		return questionName;
	}

}
