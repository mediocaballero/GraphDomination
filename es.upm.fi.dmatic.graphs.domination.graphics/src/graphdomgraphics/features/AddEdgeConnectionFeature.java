package graphdomgraphics.features;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

import graphdom.Edge;
import graphdomgraphics.common.IColorConstants;

public class AddEdgeConnectionFeature extends AbstractAddFeature implements IAddFeature {

	public AddEdgeConnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return context instanceof IAddConnectionContext && context.getNewObject() instanceof Edge;
	}

	@Override
	public PictogramElement add(IAddContext context) {

		IAddConnectionContext addConContext = (IAddConnectionContext) context;
		Edge addedEdge = (Edge) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		FreeFormConnection connection = peCreateService.createFreeFormConnection(getDiagram());
		connection.setStart(addConContext.getSourceAnchor());
		connection.setEnd(addConContext.getTargetAnchor());

		Polyline polyline = gaService.createPolyline(connection);
		polyline.setLineWidth(2);
		if (addedEdge.getConnectedNodes().get(0).isDominating()
				&& addedEdge.getConnectedNodes().get(1).isDominating()) {
			polyline.setForeground(manageColor(IColorConstants.EDGE_FOREGROUND_DOMINATING));
		} else {
			polyline.setForeground(manageColor(IColorConstants.EDGE_FOREGROUND));
		}
		link(connection, addedEdge);

		return connection;
	}

}
