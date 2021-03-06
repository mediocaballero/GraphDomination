package graphdomgraphics.features;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import graphdom.GraphdomFactory;
import graphdom.Node;
import graphdomgraphics.common.GraphUtil;

public class CreateNodeFeature extends AbstractCreateFeature implements
		ICreateFeature {
	   
	public CreateNodeFeature(IFeatureProvider fp) {
		super(fp, "Node", "Creates a new Node");
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		
		Node newNode = GraphdomFactory.eINSTANCE.createNode();
		
		newNode.setNodeName(String.valueOf(GraphUtil.getRootGraph(getDiagram()).getNextNodeId()));
		newNode.setGuid(EcoreUtil.generateUUID());
		newNode.setXCoord(context.getX());
		newNode.setYCoord(context.getY());
		
		GraphUtil.getRootGraph(getDiagram()).getNodes().add(newNode);
		
		addGraphicalRepresentation(context, newNode);
		
		return new Object[] { newNode };
	}
}
