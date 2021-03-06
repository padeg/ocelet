/*
 * generated by Xtext 2.12.0
 */
package fr.ocelet.lang.ide

import com.google.inject.Guice
import fr.ocelet.lang.OceletRuntimeModule
import fr.ocelet.lang.OceletStandaloneSetup
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */

class OceletIdeSetup extends OceletStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new OceletRuntimeModule, new OceletIdeModule))
	}
	
}
