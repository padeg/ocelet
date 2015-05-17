/*
 * generated by Xtext
 */
package fr.ocelet.lang;

import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedFeatures;

import com.google.inject.name.Names;

import fr.ocelet.lang.jvmmodel.OceletCompiler;
import fr.ocelet.lang.scoping.OceletImplicitlyImportedFeatures;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
@SuppressWarnings("restriction")
public class OceletRuntimeModule extends fr.ocelet.lang.AbstractOceletRuntimeModule {

	public Class<? extends XbaseCompiler> bindXbaseCompiler() {
        return OceletCompiler.class;
    }


	public Class<? extends ImplicitlyImportedFeatures> bindImplicitlyImportedfeatures() {
		return OceletImplicitlyImportedFeatures.class;
	}
	
	@Override
	public void configureIScopeProviderDelegate(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.scoping.IScopeProvider.class).annotatedWith(Names.named(org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(fr.ocelet.lang.scoping.OceletImportedNamespaceScopeProvider.class);
	}
}
