/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package graphdomgraphics.common;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.PictogramsFactory;
import org.eclipse.graphiti.mm.pictograms.PictogramsPackage;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import graphdom.Edge;
import graphdom.Graph;
import graphdom.GraphdomFactory;
import graphdom.Node;
import graphdom.algorithms.FanTriangulationAlgorithm;

public class GraphUtil {

	/**
	 * Opens an simple input dialog with OK and Cancel buttons.
	 * <p>
	 * 
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param dialogMessage
	 *            the dialog message, or <code>null</code> if none
	 * @param initialValue
	 *            the initial input value, or <code>null</code> if none
	 *            (equivalent to the empty string)
	 * @return the string
	 */
	public static String askString(String dialogTitle, String dialogMessage, String initialValue) {
		String ret = null;
		Shell shell = getShell();
		InputDialog inputDialog = new InputDialog(shell, dialogTitle, dialogMessage, initialValue, null);
		int retDialog = inputDialog.open();
		if (retDialog == Window.OK) {
			ret = inputDialog.getValue();
		}
		return ret;
	}

	/**
	 * Opens a dialog to change the color.
	 * 
	 * @param color
	 *            the color to change
	 * @return the changed color
	 */
	public static Color editColor(Color color) {
		if (color != null && color.eContainer() instanceof Diagram) {
			Shell shell = getShell();
			ColorDialog colorDialog = new ColorDialog(shell);
			colorDialog.setText(Messages.ExampleUtil_ChooseColorTitel);
			colorDialog.setRGB(new RGB(color.getRed(), color.getGreen(), color.getBlue()));

			RGB retRgb = colorDialog.open();
			if (retRgb == null) {
				return null;
			}

			Diagram diagram = (Diagram) color.eContainer();
			Color newColor = Graphiti.getGaService().manageColor(diagram, retRgb.red, retRgb.green, retRgb.blue);
			return newColor;

		}

		return null;
	}

	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	public static EObject getDiagramDO(final Diagram d) {
		return getRootGraph(d);
	}

	public static void saveToModelFile(final EObject obj, final Diagram d) throws CoreException, IOException {
		URI uri = d.eResource().getURI();
		uri = uri.trimFragment();
		uri = uri.trimFileExtension();
		uri = uri.appendFileExtension("model");
		ResourceSet rSet = d.eResource().getResourceSet();
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IResource file = workspaceRoot.findMember(uri.toPlatformString(true));
		if (file == null || !file.exists()) {
			Resource createResource = rSet.createResource(uri);
			createResource.save(new HashMap());
			createResource.setTrackingModification(true);
		}
		final Resource resource = rSet.getResource(uri, true);
		resource.getContents().add(obj);

	}

	public static Graph getRootGraph(Diagram diagram) {

		PictogramLink link = diagram.getLink();

		if (link == null) {

			EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(diagram);
			Command command;

			// Create base domain object (eObject)
			Graph diagramBO = GraphdomFactory.eINSTANCE.createGraph();

			// Add the Graph to the diagram resource
			command = new AddCommand(editingDomain, diagram.eResource().getContents(), diagramBO);
			editingDomain.getCommandStack().execute(command);

			// Create the pictogram link object
			link = PictogramsFactory.eINSTANCE.createPictogramLink();
			link.getBusinessObjects().add(diagramBO);

			// Set the pictogram link object for the diagram object
			command = new SetCommand(editingDomain, diagram,
					PictogramsPackage.eINSTANCE.getDiagram().getEStructuralFeature(PictogramsPackage.DIAGRAM__LINK),
					link);
			editingDomain.getCommandStack().execute(command);
		}

		if (link.getBusinessObjects().size() == 1 && link.getBusinessObjects().get(0) instanceof Graph) {
			// Return the diagram object
			return (Graph) link.getBusinessObjects().get(0);
		} else {
			throw new IllegalStateException();
		}
	}

	public static void setRootGraph(Diagram diagram, Graph graph) {

		PictogramLink link = diagram.getLink();

		if (link == null) {

			// Create the pictogram link object
			link = PictogramsFactory.eINSTANCE.createPictogramLink();
		}

		EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(diagram);
		Command command;

		// Remove the Graph from the diagram resource
		command = new RemoveCommand(editingDomain, diagram.eResource().getContents(), link.getBusinessObjects());
		editingDomain.getCommandStack().execute(command);

		// Add the Graph to the diagram resource
		link.getBusinessObjects().clear();
		link.getBusinessObjects().add(graph);

		command = new AddCommand(editingDomain, diagram.eResource().getContents(), graph);
		editingDomain.getCommandStack().execute(command);

		// Set the pictogram link object for the diagram object
		command = new SetCommand(editingDomain, diagram,
				PictogramsPackage.eINSTANCE.getDiagram().getEStructuralFeature(PictogramsPackage.DIAGRAM__LINK), link);
		editingDomain.getCommandStack().execute(command);
	}

	public static boolean equalsColorAndConstant(Color color, IColorConstant constant) {

		return ((color.getBlue() == constant.getBlue()) 
				&& (color.getRed() == constant.getRed())
				&& (color.getGreen() == constant.getGreen()));
	}

	public static Graph createTempRoundTriangledGraph(int nNodes) {

		Graph theGraph = GraphdomFactory.eINSTANCE.createGraph();

		Node firstNode = null;
		Node currentNode = null;
		Node previousNode = null;
		EList<Node> nodeList = theGraph.getNodes();
		EList<Edge> edgeList = theGraph.getEdges();

		for (int i = 0; i < nNodes; i++) {

			currentNode = GraphdomFactory.eINSTANCE.createNode();
			currentNode.setNodeName(String.valueOf(theGraph.getNextNodeId()));
			currentNode.setGuid(EcoreUtil.generateUUID());
			nodeList.add(currentNode);

			if (i == 0) {
				// Save the first node (to connect it with the last one)
				firstNode = currentNode;
			} else {
				edgeList.add(createEdge(currentNode, previousNode));
			}

			previousNode = currentNode;

		}

		if (firstNode != null && currentNode != null && !firstNode.equals(currentNode)) {
			edgeList.add(createEdge(currentNode, firstNode));
		}

		// Now for the triangulation...

		// Instance the related algorithm
		FanTriangulationAlgorithm gda = new FanTriangulationAlgorithm(theGraph, firstNode, nodeList);

		// Run the algorithm to the end
		gda.runToEnd();

		return theGraph;
	}

	/**
	 * Creates a Edge between two Nodees.
	 */
	private static Edge createEdge(Node source, Node target) {
		Edge myEdge = GraphdomFactory.eINSTANCE.createEdge();

		myEdge.getConnectedNodes().add(source);
		myEdge.getConnectedNodes().add(target);

		return myEdge;
	}
}
