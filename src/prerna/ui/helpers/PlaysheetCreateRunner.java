package prerna.ui.helpers;

import prerna.ui.components.api.IPlaySheet;

public class PlaysheetCreateRunner implements Runnable{

	IPlaySheet playSheet = null;
	
	public PlaysheetCreateRunner(IPlaySheet playSheet)
	{
		this.playSheet = playSheet;
	}
	
	@Override
	public void run() {
		playSheet.createView();

	}
	
	
	public void setPlaySheet(IPlaySheet playSheet)
	{
		this.playSheet = playSheet;
	}

}
