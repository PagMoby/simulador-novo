package br.com.pagmoby.simulador;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.com.pagmoby.simulador");

        noClasses()
            .that()
                .resideInAnyPackage("br.com.pagmoby.simulador.service..")
            .or()
                .resideInAnyPackage("br.com.pagmoby.simulador.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..br.com.pagmoby.simulador.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
