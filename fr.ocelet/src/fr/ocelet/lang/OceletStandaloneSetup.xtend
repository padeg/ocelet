/*
 * generated by Xtext 2.9.1
 */
package fr.ocelet.lang


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class OceletStandaloneSetup extends OceletStandaloneSetupGenerated {

	def static void doSetup() {
		new OceletStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
