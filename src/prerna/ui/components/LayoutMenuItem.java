package prerna.ui.components;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import prerna.ui.components.api.IPlaySheet;
import prerna.util.Constants;
import prerna.util.DIHelper;
import prerna.util.Utility;

// abstract base class for performing all of the work around
// getting the specific queries and painting it etc. 

public class LayoutMenuItem extends JMenuItem{
	IPlaySheet ps = null;
	Logger logger = Logger.getLogger(getClass());
	String layout = null;
	
	public LayoutMenuItem(String layout, IPlaySheet ps)
	{
		super(layout);
		this.ps = ps;
		this.layout = layout;
	}
	
	public void paintLayout()
	{
		String oldLayout = ((GraphPlaySheet)ps).getLayoutName();
		((GraphPlaySheet)ps).setLayout(layout);
		boolean success = ((GraphPlaySheet)ps).createLayout();
		if (success) ((GraphPlaySheet)ps).refreshView();
		else {
			if(layout.equals(Constants.RADIAL_TREE_LAYOUT) || layout.equals(Constants.BALLOON_LAYOUT)){
				int response = showOptionPopup();
				if (response ==1)
				{
					((GraphPlaySheet)ps).searchPanel.treeButton.doClick();
				}
				else{
					((GraphPlaySheet)ps).setLayout(oldLayout);
				}
			}
			else{
				Utility.showError("This layout cannot be used with the current graph");
				((GraphPlaySheet)ps).setLayout(oldLayout);
			}
		}
	}
	private int showOptionPopup(){
		JFrame playPane = (JFrame) DIHelper.getInstance().getLocalProp(Constants.MAIN_FRAME);
		Object[] buttons = {"Cancel Layout", "Continue With Tree"};
		int response = JOptionPane.showOptionDialog(playPane, "This layout requires the graph to be in the format of a tree.\nWould you like to duplicate nodes so that it is in the fromat of a tree?\n\nPreferred root node must already be selected", 
				"Convert to Tree", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[1]);
		return response;
	}
}
