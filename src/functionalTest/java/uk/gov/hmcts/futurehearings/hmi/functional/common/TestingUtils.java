package uk.gov.hmcts.futurehearings.hmi.functional.common;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestingUtils {

    public static String readFileContents(final String path) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        System.out.println("File Found : " + file.exists());
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    private TestingUtils() {
    }
}
