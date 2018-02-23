package by.bsuir.barbarossa.gui;

import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InputFieldMap {
    private Map<String, Text> textFields;
    private Map<String, Spinner> spinnerMap;
    private Map<String, String> userCredentials;

    private ResourceBundle bundle = ResourceBundle.getBundle(ApplicationProperty.BUNDLE_NAME);

    public InputFieldMap() {
        textFields = new LinkedHashMap<>();
        spinnerMap = new LinkedHashMap<>();
        userCredentials = new LinkedHashMap<>();
    }

    public void addSpinnerField(String label, Spinner spinner) {
        String bundleKey = ApplicationProperty.getPropertyFor(label);
        if (bundleKey != null) {
            String defaultValue = bundle.getString(bundleKey);
            spinner.setSelection(Integer.parseInt(defaultValue));
        }
        spinnerMap.put(label, spinner);
    }

    public void addTextField(String label, Text text) {
        String bundleKey = ApplicationProperty.getPropertyFor(label);
        if (bundleKey != null) {
            String defaultValue = bundle.getString(bundleKey);

            text.setText(defaultValue);
        }
        textFields.put(label, text);
    }

    public void addUserCredential(String label, String value) {

        userCredentials.put(label, value);
    }

    public String getTextOf(String key) throws NoSuchTextFieldException {
        if (isSpinnerField(key)) {
            Spinner spinner = spinnerMap.get(key);
            return spinner.getText();
        }
        if (isUserCredential(key)) {
            return userCredentials.get(key);
        }
        Text textField = textFields.get(key);
        if (textField == null) {
            throw new NoSuchTextFieldException(key + " is not valid text field key");
        }
        return textField.getText();
    }

    private boolean isUserCredential(String inputKey) {
        for (String key : userCredentials.keySet()) {
            if (key.equals(inputKey)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSpinnerField(String inputKey) {
        for (String key : spinnerMap.keySet()) {
            if (key.equals(inputKey)) {
                return true;
            }
        }
        return false;
    }
}
