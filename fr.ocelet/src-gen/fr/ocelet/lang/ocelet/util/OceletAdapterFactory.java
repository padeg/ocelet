/**
 * generated by Xtext 2.12.0
 */
package fr.ocelet.lang.ocelet.util;

import fr.ocelet.lang.ocelet.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see fr.ocelet.lang.ocelet.OceletPackage
 * @generated
 */
public class OceletAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static OceletPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OceletAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = OceletPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OceletSwitch<Adapter> modelSwitch =
    new OceletSwitch<Adapter>()
    {
      @Override
      public Adapter caseModel(Model object)
      {
        return createModelAdapter();
      }
      @Override
      public Adapter caseModEln(ModEln object)
      {
        return createModElnAdapter();
      }
      @Override
      public Adapter caseMetadata(Metadata object)
      {
        return createMetadataAdapter();
      }
      @Override
      public Adapter caseParameter(Parameter object)
      {
        return createParameterAdapter();
      }
      @Override
      public Adapter caseParampart(Parampart object)
      {
        return createParampartAdapter();
      }
      @Override
      public Adapter caseParamunit(Paramunit object)
      {
        return createParamunitAdapter();
      }
      @Override
      public Adapter caseParamdefa(Paramdefa object)
      {
        return createParamdefaAdapter();
      }
      @Override
      public Adapter caseRangevals(Rangevals object)
      {
        return createRangevalsAdapter();
      }
      @Override
      public Adapter caseParadesc(Paradesc object)
      {
        return createParadescAdapter();
      }
      @Override
      public Adapter caseParaopt(Paraopt object)
      {
        return createParaoptAdapter();
      }
      @Override
      public Adapter caseEntity(Entity object)
      {
        return createEntityAdapter();
      }
      @Override
      public Adapter caseEntityElements(EntityElements object)
      {
        return createEntityElementsAdapter();
      }
      @Override
      public Adapter casePropertyDef(PropertyDef object)
      {
        return createPropertyDefAdapter();
      }
      @Override
      public Adapter caseServiceDef(ServiceDef object)
      {
        return createServiceDefAdapter();
      }
      @Override
      public Adapter caseConstructorDef(ConstructorDef object)
      {
        return createConstructorDefAdapter();
      }
      @Override
      public Adapter caseRelation(Relation object)
      {
        return createRelationAdapter();
      }
      @Override
      public Adapter caseRole(Role object)
      {
        return createRoleAdapter();
      }
      @Override
      public Adapter caseRelElements(RelElements object)
      {
        return createRelElementsAdapter();
      }
      @Override
      public Adapter caseRelPropertyDef(RelPropertyDef object)
      {
        return createRelPropertyDefAdapter();
      }
      @Override
      public Adapter caseInteractionDef(InteractionDef object)
      {
        return createInteractionDefAdapter();
      }
      @Override
      public Adapter caseComitexpr(Comitexpr object)
      {
        return createComitexprAdapter();
      }
      @Override
      public Adapter caseFilterdef(Filterdef object)
      {
        return createFilterdefAdapter();
      }
      @Override
      public Adapter caseStrucdef(Strucdef object)
      {
        return createStrucdefAdapter();
      }
      @Override
      public Adapter caseStrucEln(StrucEln object)
      {
        return createStrucElnAdapter();
      }
      @Override
      public Adapter caseStrucVarDef(StrucVarDef object)
      {
        return createStrucVarDefAdapter();
      }
      @Override
      public Adapter caseStrucFuncDef(StrucFuncDef object)
      {
        return createStrucFuncDefAdapter();
      }
      @Override
      public Adapter caseDatafacer(Datafacer object)
      {
        return createDatafacerAdapter();
      }
      @Override
      public Adapter caseMatch(Match object)
      {
        return createMatchAdapter();
      }
      @Override
      public Adapter caseMatchtype(Matchtype object)
      {
        return createMatchtypeAdapter();
      }
      @Override
      public Adapter caseMdef(Mdef object)
      {
        return createMdefAdapter();
      }
      @Override
      public Adapter caseAgregdef(Agregdef object)
      {
        return createAgregdefAdapter();
      }
      @Override
      public Adapter caseScenario(Scenario object)
      {
        return createScenarioAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Model
   * @generated
   */
  public Adapter createModelAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.ModEln <em>Mod Eln</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.ModEln
   * @generated
   */
  public Adapter createModElnAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Metadata <em>Metadata</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Metadata
   * @generated
   */
  public Adapter createMetadataAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Parameter
   * @generated
   */
  public Adapter createParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Parampart <em>Parampart</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Parampart
   * @generated
   */
  public Adapter createParampartAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Paramunit <em>Paramunit</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Paramunit
   * @generated
   */
  public Adapter createParamunitAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Paramdefa <em>Paramdefa</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Paramdefa
   * @generated
   */
  public Adapter createParamdefaAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Rangevals <em>Rangevals</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Rangevals
   * @generated
   */
  public Adapter createRangevalsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Paradesc <em>Paradesc</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Paradesc
   * @generated
   */
  public Adapter createParadescAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Paraopt <em>Paraopt</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Paraopt
   * @generated
   */
  public Adapter createParaoptAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Entity <em>Entity</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Entity
   * @generated
   */
  public Adapter createEntityAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.EntityElements <em>Entity Elements</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.EntityElements
   * @generated
   */
  public Adapter createEntityElementsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.PropertyDef <em>Property Def</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.PropertyDef
   * @generated
   */
  public Adapter createPropertyDefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.ServiceDef <em>Service Def</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.ServiceDef
   * @generated
   */
  public Adapter createServiceDefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.ConstructorDef <em>Constructor Def</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.ConstructorDef
   * @generated
   */
  public Adapter createConstructorDefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Relation <em>Relation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Relation
   * @generated
   */
  public Adapter createRelationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Role <em>Role</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Role
   * @generated
   */
  public Adapter createRoleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.RelElements <em>Rel Elements</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.RelElements
   * @generated
   */
  public Adapter createRelElementsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.RelPropertyDef <em>Rel Property Def</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.RelPropertyDef
   * @generated
   */
  public Adapter createRelPropertyDefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.InteractionDef <em>Interaction Def</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.InteractionDef
   * @generated
   */
  public Adapter createInteractionDefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Comitexpr <em>Comitexpr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Comitexpr
   * @generated
   */
  public Adapter createComitexprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Filterdef <em>Filterdef</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Filterdef
   * @generated
   */
  public Adapter createFilterdefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Strucdef <em>Strucdef</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Strucdef
   * @generated
   */
  public Adapter createStrucdefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.StrucEln <em>Struc Eln</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.StrucEln
   * @generated
   */
  public Adapter createStrucElnAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.StrucVarDef <em>Struc Var Def</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.StrucVarDef
   * @generated
   */
  public Adapter createStrucVarDefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.StrucFuncDef <em>Struc Func Def</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.StrucFuncDef
   * @generated
   */
  public Adapter createStrucFuncDefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Datafacer <em>Datafacer</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Datafacer
   * @generated
   */
  public Adapter createDatafacerAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Match <em>Match</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Match
   * @generated
   */
  public Adapter createMatchAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Matchtype <em>Matchtype</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Matchtype
   * @generated
   */
  public Adapter createMatchtypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Mdef <em>Mdef</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Mdef
   * @generated
   */
  public Adapter createMdefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Agregdef <em>Agregdef</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Agregdef
   * @generated
   */
  public Adapter createAgregdefAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link fr.ocelet.lang.ocelet.Scenario <em>Scenario</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see fr.ocelet.lang.ocelet.Scenario
   * @generated
   */
  public Adapter createScenarioAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //OceletAdapterFactory
