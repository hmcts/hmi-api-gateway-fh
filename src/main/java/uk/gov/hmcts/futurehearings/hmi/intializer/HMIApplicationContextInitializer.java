package uk.gov.hmcts.futurehearings.hmi.intializer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class HMIApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String executeProcess (String key,String value) throws Exception {

        final Path currentDir = Paths.get(".");
        System.out.println(currentDir.normalize().toAbsolutePath()+"/script");

        ProcessBuilder processBuilder = new ProcessBuilder("./read-vault.sh",key,value);
        processBuilder = processBuilder.directory(new File(Paths.get(".")
                .normalize().toAbsolutePath()+"/script"));
        log.info("The Set Path : " + processBuilder.directory().getAbsolutePath());

        final Process process = processBuilder.start();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final Map<String, String> env = processBuilder.environment();
        final int exitValue = process.waitFor();

        if (exitValue != 0) {
            // check for errors
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getErrorStream());
            throw new RuntimeException("Execution of script failed! : " +
                    new String(new BufferedInputStream(process.getErrorStream()).readAllBytes()));
        }

        String secretValue = null;
        while (bufferedReader.ready()) {
            secretValue = bufferedReader.readLine();
            System.out.println("Received from script: " + secretValue);
        }
        return secretValue;
    }

    public static void main(String args[]) {
        HMIApplicationContextInitializer initializer = new HMIApplicationContextInitializer();
        initializer.initialize(null);
    }

    private static boolean isLocalMachine() {

        final String environment = System.getenv("EXECUTION_ENVIRONMENT");
        if (Objects.nonNull(environment) &&
                !environment.equals("local")) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {

        if (!isLocalMachine())
            return;

        final Properties property = new Properties();
        try (InputStream fis = new FileInputStream(System.getProperty("user.home")
                +"/lookup_local.properties")) {
            property.load(fis);
        }  catch (IOException ioException) {
            ioException.printStackTrace();
            return;
        }

        final String secretValue;
        try {
            secretValue = executeProcess(property.getProperty("key"),
                                            property.getProperty("value"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        //This Functionality is not Implemented yet.
        //System.setProperty("TEST_SUBSCRIPTION_KEY" , secretValue);
    }
}
