package prerna.ui.main.listener.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import prerna.ui.components.GraphPlaySheet;
import prerna.ui.swing.custom.ToggleButton;
import prerna.ui.transformer.BalloonLayoutRings;
import prerna.ui.transformer.RadialTreeLayoutRings;

import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class RingsButtonListener implements ActionListener {
	
	VisualizationViewer view;
	GraphPlaySheet ps;
	BalloonLayoutRings rings=new BalloonLayoutRings();
	RadialTreeLayoutRings treeRings = new RadialTreeLayoutRings();
	Layout lay;
	
	public RingsButtonListener(){
		this.view = view;
	}

	public void setViewer(VisualizationViewer view){
		this.view = view;
		this.rings.setViewer(view);
		this.treeRings.setViewer(view);
	}
	
	public void setGraph(Forest forest){
		treeRings.setForest(forest);
	}
	
	public void setLayout(Layout lay){
		this.lay = lay;
		if(lay instanceof BalloonLayout)
			this.rings.setLayout(lay);
		else if (lay instanceof RadialTreeLayout)
			this.treeRings.setLayout(lay);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// Get if the button is selected
		JToggleButton button = (JToggleButton) e.getSource();
		
		if(!button.isSelected()){
			button.setSelected(false);
			if(lay instanceof BalloonLayout)
				view.removePreRenderPaintable(rings);
			else if (lay instanceof RadialTreeLayout)
				view.removePreRenderPaintable(treeRings);
		}
		else{
			button.setSelected(true);
			if(lay instanceof BalloonLayout)
				view.addPreRenderPaintable(rings);
			else if (lay instanceof RadialTreeLayout){
				view.addPreRenderPaintable(treeRings);
			}
		}
		view.repaint();
		
	}
	
	

}
