package uk.gov.hmcts.futurehearings.hmi.intializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import uk.gov.hmcts.futurehearings.hmi.intializer.exception.HmiProcessException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class HmiApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String executeProcess(String key, String value) throws
            IOException, InterruptedException, HmiProcessException {

        final Path currentDir = Paths.get(".");
        log.debug(currentDir.normalize().toAbsolutePath() + "/script");

        ProcessBuilder processBuilder = new ProcessBuilder("./read-vault.sh", key, value);
        processBuilder = processBuilder.directory(new File(Paths.get(".")
                .normalize().toAbsolutePath() + "/script"));
        log.info("The Set Path : " + processBuilder.directory().getAbsolutePath());

        final Process process = processBuilder.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        processBuilder.environment();
        final int exitValue = process.waitFor();

        if (exitValue != 0) {
            // check for errors
            log.error("There was an error while executing the process ");
            throw new HmiProcessException("Execution of script failed! : "
                    + new String(new BufferedInputStream(process.getErrorStream()).readAllBytes()));
        }

        String secretValue = null;
        while (bufferedReader.ready()) {
            secretValue = bufferedReader.readLine();
            log.info("Received from script: " + secretValue);
        }
        return secretValue;
    }

    public static void main(String[] args) {
        HmiApplicationContextInitializer initializer = new HmiApplicationContextInitializer();
        initializer.initialize(null);
    }

    private static boolean isLocalMachine() {
        final String environment = System.getenv("EXECUTION_ENVIRONMENT");
        return Objects.nonNull(environment) && !"local".equals(environment);
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {

        if (!isLocalMachine()) {
            return;
        }

        final Properties property = new Properties();
        try (InputStream fis = new FileInputStream(System.getProperty("user.home")
                + "/lookup_local.properties")) {
            property.load(fis);
        } catch (IOException ioException) {
            log.error("ERROR while reading the Local Properties", ioException.getLocalizedMessage());
        }

        try {
            executeProcess(property.getProperty("key"),
                    property.getProperty("value"));
        } catch (Exception exception) {
            log.error("ERROR while executing the process of the shell file", exception.getLocalizedMessage());
        }
    }
}
