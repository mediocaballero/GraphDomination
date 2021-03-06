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
package graphdomgraphics.ui;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import graphdom.Graph;
import graphdom.GraphdomFactory;
import graphdomgraphics.common.GraphdomProjectNature;

public class CreateGraphdomProjectWizard extends BasicNewProjectResourceWizard {

	private static final String DIAGRAM_PATH = "diagrams/";
	
	@Override
	public boolean performFinish() {
		if (!super.performFinish())
			return false;

		IProject newProject = getNewProject();
		try {
			IProjectDescription description = newProject.getDescription();
			description.setNatureIds(new String[] { GraphdomProjectNature.NATURE_ID });
			newProject.setDescription(description, null);
			createPredefinedContent(newProject);
		} catch (CoreException e) {
			return false;
		}
		
		return true;
	}

	private void createPredefinedContent(IProject newProject) throws CoreException {

		ResourceSet set = new ResourceSetImpl();
		URI uri = URI.createPlatformResourceURI(newProject.getFile(DIAGRAM_PATH + "newDiagram.diagram").getFullPath().toString(), true); //$NON-NLS-1$ //$NON-NLS-2$
		Resource resource = set.createResource(uri);
		Graph theGraph = GraphdomFactory.eINSTANCE.createGraph();
		
		Diagram theDiagram = Graphiti.getPeCreateService().createDiagram("GraphDomGraphics", "newDiagram", true);
		
		resource.getContents().add(theDiagram);
		resource.getContents().add(theGraph);
		
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, "GraphDomGraphics", 0, e.getMessage(), e); //$NON-NLS-1$
			throw new CoreException(status);
		}
	}
}
