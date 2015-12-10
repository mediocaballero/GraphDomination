/**
 */
package graphdom.impl;

import graphdom.Edge;
import graphdom.GraphdomPackage;
import graphdom.Node;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link graphdom.impl.EdgeImpl#getConnectedNodes <em>Connected Nodes</em>}</li>
 *   <li>{@link graphdom.impl.EdgeImpl#isMarked <em>Marked</em>}</li>
 *   <li>{@link graphdom.impl.EdgeImpl#getGuid <em>Guid</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeImpl extends MinimalEObjectImpl.Container implements Edge {
	/**
	 * The cached value of the '{@link #getConnectedNodes() <em>Connected Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectedNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> connectedNodes;

	/**
	 * The default value of the '{@link #isMarked() <em>Marked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMarked()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MARKED_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isMarked() <em>Marked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMarked()
	 * @generated
	 * @ordered
	 */
	protected boolean marked = MARKED_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuid() <em>Guid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuid()
	 * @generated
	 * @ordered
	 */
	protected String guid = GUID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphdomPackage.Literals.EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getConnectedNodes() {
		if (connectedNodes == null) {
			connectedNodes = new EObjectWithInverseResolvingEList.ManyInverse<Node>(Node.class, this, GraphdomPackage.EDGE__CONNECTED_NODES, GraphdomPackage.NODE__CONNECTED_EDGES);
		}
		return connectedNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMarked(boolean newMarked) {
		boolean oldMarked = marked;
		marked = newMarked;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphdomPackage.EDGE__MARKED, oldMarked, marked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuid(String newGuid) {
		String oldGuid = guid;
		guid = newGuid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphdomPackage.EDGE__GUID, oldGuid, guid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GraphdomPackage.EDGE__CONNECTED_NODES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConnectedNodes()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GraphdomPackage.EDGE__CONNECTED_NODES:
				return ((InternalEList<?>)getConnectedNodes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GraphdomPackage.EDGE__CONNECTED_NODES:
				return getConnectedNodes();
			case GraphdomPackage.EDGE__MARKED:
				return isMarked();
			case GraphdomPackage.EDGE__GUID:
				return getGuid();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GraphdomPackage.EDGE__CONNECTED_NODES:
				getConnectedNodes().clear();
				getConnectedNodes().addAll((Collection<? extends Node>)newValue);
				return;
			case GraphdomPackage.EDGE__MARKED:
				setMarked((Boolean)newValue);
				return;
			case GraphdomPackage.EDGE__GUID:
				setGuid((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case GraphdomPackage.EDGE__CONNECTED_NODES:
				getConnectedNodes().clear();
				return;
			case GraphdomPackage.EDGE__MARKED:
				setMarked(MARKED_EDEFAULT);
				return;
			case GraphdomPackage.EDGE__GUID:
				setGuid(GUID_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GraphdomPackage.EDGE__CONNECTED_NODES:
				return connectedNodes != null && !connectedNodes.isEmpty();
			case GraphdomPackage.EDGE__MARKED:
				return marked != MARKED_EDEFAULT;
			case GraphdomPackage.EDGE__GUID:
				return GUID_EDEFAULT == null ? guid != null : !GUID_EDEFAULT.equals(guid);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (marked: ");
		result.append(marked);
		result.append(", guid: ");
		result.append(guid);
		result.append(')');
		return result.toString();
	}

} //EdgeImpl
