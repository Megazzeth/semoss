package prerna.ui.transformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import prerna.om.DBCMVertex;

import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.PolarPoint;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformerDecorator;

public class RadialTreeLayoutRings implements VisualizationServer.Paintable {
	
	RadialTreeLayout radialLayout;
	Forest graph;
	VisualizationViewer vv;
	Collection<Double> depths;
	
	public RadialTreeLayoutRings() {
	}

	public void setViewer(VisualizationViewer view){
		this.vv = view;
	}
	
	public void setForest(Forest forest){
		this.graph = forest;
	}
	
	public void setLayout(Layout layout){
		this.radialLayout = (RadialTreeLayout) layout;
	}
	
	public Collection<Double> getDepths() {
		depths = new HashSet<Double>();
		Map<String,PolarPoint> polarLocations = radialLayout.getPolarLocations();
		for(DBCMVertex v : (Iterable<DBCMVertex>) graph.getVertices()) {
			PolarPoint pp = polarLocations.get(v);
			depths.add(pp.getRadius());
		}
		return depths;
	}

	public void paint(Graphics g) {
		getDepths();
		g.setColor(Color.gray);
		Graphics2D g2d = (Graphics2D)g;
		Point2D center = radialLayout.getCenter();

		Ellipse2D ellipse = new Ellipse2D.Double();
		for(double d : depths) {
			ellipse.setFrameFromDiagonal(center.getX()-d, center.getY()-d, 
					center.getX()+d, center.getY()+d);
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
			Shape shape = at.createTransformedShape(ellipse);
			//Shape shape = 
			//	vv.getRenderContext().getMultiLayerTransformer().transform(ellipse);
//				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).transform(ellipse);

			MutableTransformer viewTransformer =
				vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW);
			
			if(viewTransformer instanceof MutableTransformerDecorator) {
				shape = vv.getRenderContext().getMultiLayerTransformer().transform(shape);
			} else {
				shape = vv.getRenderContext().getMultiLayerTransformer().transform(Layer.LAYOUT,shape);
			}
			g2d.draw(shape);
		}
	}

	public boolean useTransform() {
		return true;
	}
}