/**
 * generated by Xtext 2.9.1
 */
package fr.ocelet.lang.ocelet;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link fr.ocelet.lang.ocelet.Relation#getName <em>Name</em>}</li>
 *   <li>{@link fr.ocelet.lang.ocelet.Relation#getRoles <em>Roles</em>}</li>
 *   <li>{@link fr.ocelet.lang.ocelet.Relation#getRelelns <em>Relelns</em>}</li>
 * </ul>
 *
 * @see fr.ocelet.lang.ocelet.OceletPackage#getRelation()
 * @model
 * @generated
 */
public interface Relation extends ModEln
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see fr.ocelet.lang.ocelet.OceletPackage#getRelation_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link fr.ocelet.lang.ocelet.Relation#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
   * The list contents are of type {@link fr.ocelet.lang.ocelet.Role}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Roles</em>' containment reference list.
   * @see fr.ocelet.lang.ocelet.OceletPackage#getRelation_Roles()
   * @model containment="true"
   * @generated
   */
  EList<Role> getRoles();

  /**
   * Returns the value of the '<em><b>Relelns</b></em>' containment reference list.
   * The list contents are of type {@link fr.ocelet.lang.ocelet.RelElements}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Relelns</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relelns</em>' containment reference list.
   * @see fr.ocelet.lang.ocelet.OceletPackage#getRelation_Relelns()
   * @model containment="true"
   * @generated
   */
  EList<RelElements> getRelelns();

} // Relation
