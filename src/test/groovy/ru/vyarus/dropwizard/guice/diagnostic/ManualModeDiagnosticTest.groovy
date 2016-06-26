package ru.vyarus.dropwizard.guice.diagnostic

import ru.vyarus.dropwizard.guice.AbstractTest
import ru.vyarus.dropwizard.guice.diagnostic.support.ManualApp
import ru.vyarus.dropwizard.guice.diagnostic.support.features.FooInstaller
import ru.vyarus.dropwizard.guice.diagnostic.support.features.FooModule
import ru.vyarus.dropwizard.guice.diagnostic.support.features.FooResource
import ru.vyarus.dropwizard.guice.module.GuiceSupportModule
import ru.vyarus.dropwizard.guice.module.GuiceyConfigurationInfo
import ru.vyarus.dropwizard.guice.module.installer.feature.jersey.ResourceInstaller
import ru.vyarus.dropwizard.guice.test.spock.UseGuiceyApp

import javax.inject.Inject

/**
 * @author Vyacheslav Rusakov
 * @since 26.06.2016
 */
@UseGuiceyApp(ManualApp)
class ManualModeDiagnosticTest extends AbstractTest {

    @Inject
    GuiceyConfigurationInfo info

    def "Check diagnostic info correctness"() {

        expect: "correct bundles info"
        info.bundles.isEmpty()
        info.bundlesFromLookup.isEmpty()

        and: "correct installers info"
        info.installers == [ResourceInstaller]
        info.installersDisabled == [FooInstaller]
        info.installersFromScan.isEmpty()

        and: "correct extensions info"
        info.extensions == [FooResource]
        info.extensionsFromScan.isEmpty()
        info.getExtensions(ResourceInstaller) == [FooResource]

        and: "correct modules"
        info.modules as Set == [FooModule, GuiceSupportModule] as Set

    }
}