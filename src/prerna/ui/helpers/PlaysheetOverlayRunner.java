package prerna.ui.helpers;

import prerna.ui.components.api.IPlaySheet;

public class PlaysheetOverlayRunner implements Runnable{

	IPlaySheet playSheet = null;
	
	
	public PlaysheetOverlayRunner(IPlaySheet playSheet)
	{
		this.playSheet = playSheet;
	}

	
	@Override
	public void run() {
		playSheet.overlayView();
	}
	
	public void setPlaySheet(IPlaySheet playSheet)
	{
		this.playSheet = playSheet;
	}

}
