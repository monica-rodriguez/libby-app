package com.starlight.app;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.starlight.app.StepDefinition",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)
public class CucumberIT {
}
