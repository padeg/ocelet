/*
 * generated by Xtext
 */
package fr.ocelet.lang;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

public class OceletUiInjectorProvider implements IInjectorProvider {
	
	@Override
	public Injector getInjector() {
		return fr.ocelet.lang.ui.internal.OceletActivator.getInstance().getInjector("fr.ocelet.lang.Ocelet");
	}
	
}
