package edu.uniquindio.runners;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/registro.feature",
        glue = {"edu.uniquindio.stepDefinitions"}, 
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Reportes
        monochrome = true,
        publish = true
)
public class Runner {

}