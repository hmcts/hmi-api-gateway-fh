package uk.gov.hmcts.futurehearings.hmi.unit.testing.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtilities {

    public static String readFileContents (final String path) throws IOException {
        final File file = ResourceUtils.getFile("classpath:" + path);
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

}
