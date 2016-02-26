/**
 * generated by Xtext 2.9.1
 */
package fr.ocelet.lang.ocelet.impl;

import fr.ocelet.lang.ocelet.OceletPackage;
import fr.ocelet.lang.ocelet.Paradesc;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paradesc</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link fr.ocelet.lang.ocelet.impl.ParadescImpl#getPardesc <em>Pardesc</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParadescImpl extends ParampartImpl implements Paradesc
{
  /**
   * The default value of the '{@link #getPardesc() <em>Pardesc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPardesc()
   * @generated
   * @ordered
   */
  protected static final String PARDESC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPardesc() <em>Pardesc</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPardesc()
   * @generated
   * @ordered
   */
  protected String pardesc = PARDESC_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ParadescImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return OceletPackage.Literals.PARADESC;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getPardesc()
  {
    return pardesc;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPardesc(String newPardesc)
  {
    String oldPardesc = pardesc;
    pardesc = newPardesc;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, OceletPackage.PARADESC__PARDESC, oldPardesc, pardesc));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case OceletPackage.PARADESC__PARDESC:
        return getPardesc();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case OceletPackage.PARADESC__PARDESC:
        setPardesc((String)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case OceletPackage.PARADESC__PARDESC:
        setPardesc(PARDESC_EDEFAULT);
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case OceletPackage.PARADESC__PARDESC:
        return PARDESC_EDEFAULT == null ? pardesc != null : !PARDESC_EDEFAULT.equals(pardesc);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (pardesc: ");
    result.append(pardesc);
    result.append(')');
    return result.toString();
  }

} //ParadescImpl
