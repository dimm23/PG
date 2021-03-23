package serenity;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        //tags = "@no_tests",
        features = "src/test/resources/features",
        glue = "serenity.stepdefinitions"
)
public class CucumberTestSuite {}
