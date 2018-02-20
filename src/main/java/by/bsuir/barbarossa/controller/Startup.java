package by.bsuir.barbarossa.controller;

import org.apache.log4j.PropertyConfigurator;

import java.net.URL;

public class Startup {

    private final static String INCORRECT_DRIVE_LETTERS_REGEXP = "^/(.:/)";
    private static final String GROUP_TO_CAPTURE = "$1";

    public static void main(String... args) {
        String log4jConfigFile = "log4j.properties";
        PropertyConfigurator.configure(getSourcePath(log4jConfigFile));

        ApplicationController controller = new ApplicationController();
      //  controller.startApplication();

    }

    private static String getSourcePath(String path) {

        ClassLoader classLoader = Startup.class.getClassLoader();
        URL sourceURL = classLoader.getResource(path);
        assert sourceURL != null;
        String rawPath = sourceURL.getPath();

        return rawPath.replaceFirst(INCORRECT_DRIVE_LETTERS_REGEXP, GROUP_TO_CAPTURE);


    }
}
