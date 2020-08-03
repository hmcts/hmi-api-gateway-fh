package uk.gov.hmcts.futurehearings.hmi.integration.config;

import uk.gov.hmcts.futurehearings.hmi.config.SwaggerConfiguration;

import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * Built-in feature which saves service's swagger specs in temporary directory.
 * Each travis run on master should automatically save and upload (if updated) documentation.
 */
@ActiveProfiles("integration")
@ContextConfiguration(classes = SwaggerConfiguration.class)
class SwaggerPublisherTest {

    @DisplayName("Generate swagger documentation")
    @Test
    @Ignore
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void generateDocs() throws Exception {
        /*byte[] specs = mvc.perform(get("/v2/api-docs"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsByteArray();

        try (OutputStream outputStream = Files.newOutputStream(Paths.get("/tmp/swagger-specs.json"))) {
            outputStream.write(specs);
        }*/

    }
}
