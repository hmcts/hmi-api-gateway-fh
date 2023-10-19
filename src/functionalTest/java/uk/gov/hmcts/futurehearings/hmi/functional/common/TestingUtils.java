package uk.gov.hmcts.futurehearings.hmi.functional.common;

import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
@SuppressWarnings("PMD")
public final class TestingUtils {

    public static String readFileContents(final String path) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        log.info("File found: " + file.exists());
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    public static String getHearingId(Response response) {
        String newlyCreatedHearing = response.getHeader("Location");
        return newlyCreatedHearing.substring(newlyCreatedHearing.lastIndexOf('/') + 1);
    }

    private TestingUtils() {
    }
}
