// package com.starlight.app;

// import org.junit.platform.suite.api.ConfigurationParameter;
// import org.junit.platform.suite.api.IncludeEngines;
// import org.junit.platform.suite.api.SelectClasspathResource;
// import org.junit.platform.suite.api.Suite;

// @Suite
// @IncludeEngines("cucumber")
// @SelectClasspathResource("features") // src/test/resources/features
// @ConfigurationParameter(key = "cucumber.glue", value = "com.starlight.app.steps")
// @ConfigurationParameter(
       //  key = "cucumber.plugin",
        // value = "pretty,summary,html:target/cucumber-report.html,json:target/cucumber.json,junit:target/cucumber/cucumber.xml"
)
// @ConfigurationParameter(key = "cucumber.filter.tags", value = "not @ignored")
// public class CucumberTest { }


package com.starlight.app;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.starlight.app.steps",
    plugin = {
        "pretty",
        "summary",
        "html:target/cucumber-report.html",
        "json:target/cucumber.json",
        "junit:target/cucumber/cucumber.xml"
    },
    tags = "not @ignored"
)
public class CucumberTest {
}
