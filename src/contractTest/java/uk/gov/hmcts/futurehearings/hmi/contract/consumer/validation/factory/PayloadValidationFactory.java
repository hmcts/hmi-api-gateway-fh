package uk.gov.hmcts.futurehearings.hmi.contract.consumer.validation.factory;

import static uk.gov.hmcts.futurehearings.hmi.contract.consumer.common.TestingUtils.readFileContents;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.regexp.RE2JRegexpFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

@Slf4j
public class PayloadValidationFactory {

    public static final String PAYLOAD_SCHEMA_DIR_PATH = "uk/gov/hmcts/futurehearings/hmi/thirdparty/schema/S&L/V1.2.0";

    private PayloadValidationFactory() {

    }

    public static final void validateHMIPayload(JSONObject payloadObject, String schemaFileName)
            throws IOException, URISyntaxException,JSONException {

        JSONObject jsonSchemaReusable = new JSONObject(
                new JSONTokener(readFileContents(PAYLOAD_SCHEMA_DIR_PATH + "/reusable.json")));
        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(readFileContents(PAYLOAD_SCHEMA_DIR_PATH + schemaFileName)));

        Schema schema = SchemaLoader.builder()
                .useDefaults(true)
                .regexpFactory(new RE2JRegexpFactory())
                .registerSchemaByURI(new URI("http://www.gov.uk/reusable.json"), jsonSchemaReusable)
                .schemaJson(jsonSchema)
                .resolutionScope("classpath://uk/gov/hmcts/futurehearings/hmi/contract/consumer/payload")
                .build()
                .load().build();

        try {
            schema.validate(payloadObject);
        } catch (ValidationException validationException) {
           log.info(validationException.getMessage());
            validationException.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(log::info);
            throw validationException;
        }

    }
}
