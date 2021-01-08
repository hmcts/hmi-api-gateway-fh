package uk.gov.hmcts.futurehearings.hmi.contract.consumer.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.util.ResourceUtils;

@Slf4j
public class TestingUtils {

    public static String readFileContents (final String path) throws IOException {

        File file = ResourceUtils.getFile("classpath:"+path);
        //File is found
        System.out.println("File Found : " + file.exists());
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    public static void comparePayloads(final String expectedPayloadPath, final Response response) {
        try {
            String output =
                    readFileContents(expectedPayloadPath);
            JSONAssert.assertEquals(output,
                    response.getBody().asString(), JSONCompareMode.STRICT);
        } catch (JSONException jsonException) {
            throw new AssertionError("Payloads have not matched");
        } catch (IOException ioException ) {
            throw new AssertionError("Response file cannot be read..");
        }
    }

    public static final String getRFC3339FormattedDateForwardDays(long numberOfDays) {
        LocalDateTime date = LocalDateTime.now().plusDays(numberOfDays);
        log.debug("The value of the Date : " + date);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS'Z'");
        String formatDateTime = date.format(format);
        log.debug("The value of the Formatted Date Time : " + formatDateTime);
        return formatDateTime;
    }
}
