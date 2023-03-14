package uk.gov.hmcts.futurehearings.hmi.acceptance.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public final class TestingUtils {

    public static String readFileContents(final String path) throws IOException {

        File file = ResourceUtils.getFile("classpath:" + path);
        log.debug("File Found : " + file.exists());
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    private TestingUtils() {
    }
}
