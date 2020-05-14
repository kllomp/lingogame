package com.lingo.lingogame;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


@AnalyzeClasses(packages = "com.lingo.lingogame")
public class LayeredArchitectureTest {

    @ArchTest
    static final ArchRule dependencyRule = layeredArchitecture()
            .layer("domain").definedBy("..domain..")
            .layer("presentation").definedBy("..controller..")
            .layer("data").definedBy("..repository..")
            .layer("services").definedBy("..service..")

            .whereLayer("presentation").mayNotBeAccessedByAnyLayer()
            .whereLayer("domain").mayOnlyBeAccessedByLayers("services");

}
