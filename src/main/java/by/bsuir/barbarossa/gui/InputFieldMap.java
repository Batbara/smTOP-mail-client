package by.bsuir.barbarossa.gui;

import org.eclipse.swt.widgets.Text;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InputFieldMap {
    private Map<String, Text> textFields;
    private Map<String, String> configInputs;

    private ResourceBundle bundle = ResourceBundle.getBundle(ApplicationProperty.BUNDLE_NAME);

    public InputFieldMap() {
        textFields = new LinkedHashMap<>();
        configInputs = new LinkedHashMap<>();
    }

    public void addTextField(String label, Text text) {
        String bundleKey = ApplicationProperty.getPropertyFor(label);
        if (bundleKey != null) {

            String defaultValue = (isFromField(bundleKey)) ?
                    configInputs.get(LabelText.USER_NAME)
                    :
                    bundle.getString(bundleKey);

            text.setText(defaultValue);
        }
        textFields.put(label, text);
    }

    public void addConfigurationInput(String label, String value) {

        configInputs.put(label, value);
    }

    public String getTextOf(String key) throws NoSuchTextFieldException {

        if (isConfigInput(key)) {
            return configInputs.get(key);
        }
        Text textField = textFields.get(key);
        if (textField == null) {
            throw new NoSuchTextFieldException(key + " is not valid text field key");
        }
        return textField.getText();
    }
    public Text getTextField(String key){
        return textFields.get(key);
    }

    private boolean isFromField(String inputKey){
        return inputKey.equals(LabelText.FROM_LABEL);
    }
    private boolean isConfigInput(String inputKey) {
        for (String key : configInputs.keySet()) {
            if (key.equals(inputKey)) {
                return true;
            }
        }
        return false;
    }


}
