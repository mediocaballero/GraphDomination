package graphdomgraphics.features.custom;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;

import graphdom.Graph;
import graphdom.Node;
import graphdom.algorithms.FanTriangulationAlgorithm;
import graphdom.algorithms.RandomFlipsByNumberAlgorithm;
import graphdomgraphics.common.GraphUtil;
import graphdomgraphics.features.CreateNodeFeature;

public class GenerateRoundTriangGraphCustomFeature extends GraphdomAbstractCustomFeature {

	private static final int NODE_COUNT_FACTOR = 500;

	private static final int BASE_RADIUS = 50;

	private static final String TITLE = "Create round triangled graph";

	private static final String USER_QUESTION = "Enter number of nodes to create:";

	private static final String TITLE2 = "Randomize newly created graph";

	private static final String USER_QUESTION2 = "Enter number of flips for graph randomization (0 for no randomization):";

	private boolean hasDoneChanges = false;

	public GenerateRoundTriangGraphCustomFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Generate round triangled graph";
	}

	@Override
	public String getDescription() {
		return "Generate a round n-vertex triangled graph";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {

		// Ask user number of nodes

		String numberOfNodes = GraphUtil.askString(TITLE, USER_QUESTION, "10");
		int nNodes = 0;
		try {
			nNodes = Integer.parseUnsignedInt(numberOfNodes);
		} catch (NumberFormatException e) {
			nNodes = 0;
		}

		double angleIncrement = 360.0 / nNodes;
		double circleRadius = BASE_RADIUS + (nNodes * NODE_COUNT_FACTOR / 100);
		double x = 0;
		double y = 0;

		CreateNodeFeature cnf = new CreateNodeFeature(getFeatureProvider());

		Node firstNode = null;
		Node currentNode = null;
		Node previousNode = null;
		EList<Node> nodeList = new BasicEList<>();

		for (int i = 0; i < nNodes; i++) {
			CreateContext cc = new CreateContext();
			x = (circleRadius * Math.cos((angleIncrement * i) * (Math.PI / 180)));
			y = (circleRadius * Math.sin((angleIncrement * i) * (Math.PI / 180)));

			cc.setTargetContainer(getDiagram());
			cc.setLocation(context.getX() + (int) x, context.getY() + (int) y);

			currentNode = (Node) cnf.create(cc)[0];
			nodeList.add(currentNode);

			
			if (i == 0) {
				// Save the first node (to connect it with the last one)
				firstNode = currentNode;
			} else {
				createEdge(currentNode, previousNode);
			}
			
			previousNode = currentNode;

		}
		
		if (firstNode != null && currentNode!=null && !firstNode.equals(currentNode)) {
			createEdge(currentNode, firstNode);
		}

		hasDoneChanges = nNodes > 0;
		
		// Now for the triangulation...
		
		// Access the graph
		Graph theGraph = GraphUtil.getRootGraph(getDiagram());

		// Instance the related algorithm
		FanTriangulationAlgorithm gda = new FanTriangulationAlgorithm(theGraph, firstNode, nodeList);


		// Run the algorithm to the end
		gda.runToEnd();

		// Model is modified now, with new edges
		updateEdges();
		
		String numberOfFlips = GraphUtil.askString(TITLE2, USER_QUESTION2, "50");
		int nFlips = 0;
		try {
			nFlips = Integer.parseUnsignedInt(numberOfFlips);
		} catch (NumberFormatException e) {
			nFlips = 0;
		}

		// Now for the randomization...

		// Instance the related algorithm
		RandomFlipsByNumberAlgorithm rfbna = new RandomFlipsByNumberAlgorithm(theGraph, nFlips);

		// Run the algorithm to the end
		rfbna.runToEnd();

		// Model is modified now, with new edges
		updateAllEdges();
		updateGraph(theGraph);
	}
	
	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}
}
