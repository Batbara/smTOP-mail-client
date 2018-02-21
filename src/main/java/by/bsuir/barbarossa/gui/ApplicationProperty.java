package by.bsuir.barbarossa.gui;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApplicationProperty {
    public static final String BUNDLE_NAME = "application";
    public static final String SERVER_HOST = "application.server.host";
    public static final String SERVER_PORT = "application.server.port";
    public static final String SENDER = "application.sender";
    public static final String RECEIVER = "application.receiver";
    private static Map<String, String> labelValueMap = new LinkedHashMap<>();
    static {
        labelValueMap.put(LabelText.SERVER, SERVER_HOST);
        labelValueMap.put(LabelText.PORT_NUMBER, SERVER_PORT);
        labelValueMap.put(LabelText.FROM_LABEL, SENDER);
        labelValueMap.put(LabelText.TO_LABEL, RECEIVER);
    }
    private ApplicationProperty() {

    }

    public static String getPropertyFor(String label) {

        return labelValueMap.get(label);

    }
}
