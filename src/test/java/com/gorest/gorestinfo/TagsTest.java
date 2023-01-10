package com.gorest.gorestinfo;

import com.gorest.testbase.TestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)

public class TagsTest extends TestBase {

    @WithTag ("userfeature: NEGATIVE")
    @Title("Provide a 404 status code when incorrect HTTP method is used to access resource")
    @Test
    public void invalidMethod() {
        SerenityRest.rest()
                .given()
                .when()
                .post("/users")
                .then()
                .statusCode(404)
                .log().all();

        }

    }




