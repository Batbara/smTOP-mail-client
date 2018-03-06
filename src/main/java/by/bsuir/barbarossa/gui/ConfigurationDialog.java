package by.bsuir.barbarossa.gui;

import by.bsuir.barbarossa.gui.listener.EmptyInputListener;
import by.bsuir.barbarossa.gui.listener.SelectAllEventHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class ConfigurationDialog {
    private static final String CAPTION = "Log in";
    private static final String OK_BUTTON = "OK";
    private static final String CANCEL_BUTTON = "Cancel";

    private MainShell mainShell;
    private Shell dialog;
    private InputFieldMap inputFieldMap;
    private Text serverNameField;
    private Spinner portNumberSpinner;
    private Text userNameText;
    private Text passwordText;
    private Map<String, Boolean> fieldStatuses;
    private Button okButton;


    private ResourceBundle bundle = ResourceBundle.getBundle(ApplicationProperty.BUNDLE_NAME);

    public ConfigurationDialog(MainShell mainShell, InputFieldMap inputFieldMap) {
        this.mainShell = mainShell;
        this.inputFieldMap = inputFieldMap;
        fieldStatuses = new LinkedHashMap<>();

        dialog = new Shell(mainShell.getMainShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.ON_TOP);
        dialog.setText(CAPTION);
        centerDialog();

        initDialogComponents();

        setUpVerificationListeners();
        dialog.pack();

    }

    public void open() {
        dialog.open();
    }

    public Button getOkButton() {
        return okButton;
    }

    private void initDialogComponents() {
        dialog.setLayout(new GridLayout(4, false));
        placeServerConfigFields();
        placeUserCredentialFields();

        okButton = new Button(dialog, SWT.PUSH);
        // okButton.addSelectionListener(new SenderFieldChanger(mainShell));
        okButton.setEnabled(true);
        okButton.setText(OK_BUTTON);

        GridData okButtonGrid = new GridData(GridData.FILL_HORIZONTAL);
        okButtonGrid.horizontalSpan = 2;
        okButton.setLayoutData(okButtonGrid);
        okButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                inputFieldMap.addConfigurationInput(LabelText.USER_NAME, userNameText.getText());
                inputFieldMap.addConfigurationInput(LabelText.PASSWORD, passwordText.getText());
                inputFieldMap.addConfigurationInput(LabelText.PORT_NUMBER, portNumberSpinner.getText());
                inputFieldMap.addConfigurationInput(LabelText.SERVER, serverNameField.getText());
                Text fromTextField = inputFieldMap.getTextField(LabelText.FROM_LABEL);
                String userNameValue;
                try {
                    userNameValue = inputFieldMap.getTextOf(LabelText.USER_NAME);
                } catch (NoSuchTextFieldException e) {
                    userNameValue = "NONE";
                }
                fromTextField.setText(userNameValue);
                dialog.setVisible(false);
            }
        });

        Button cancel = new Button(dialog, SWT.PUSH);
        cancel.setText(CANCEL_BUTTON);
        GridData cancelButtonGrid = new GridData(GridData.FILL_HORIZONTAL);
        cancelButtonGrid.horizontalSpan = 2;

        cancel.setLayoutData(cancelButtonGrid);
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                closeApplication();
            }
        });

        dialog.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {

                //  closeApplication();
            }
        });

        dialog.setDefaultButton(okButton);
    }

    private void placeUserCredentialFields() {
        Label userNameLabel = new Label(dialog, SWT.NONE);
        userNameLabel.setText(LabelText.USER_NAME);
        GridData labelData = new GridData();
        labelData.horizontalSpan = 4;
        userNameLabel.setLayoutData(labelData);

        userNameText = new Text(dialog, SWT.BORDER);
        setDefaultTextValue(LabelText.USER_NAME, userNameText);
        fieldStatuses.put(LabelText.USER_NAME, false);

        GridData userNameGrid = new GridData();
        userNameGrid.horizontalSpan = 4;
        userNameGrid.widthHint = 200;
        userNameText.setLayoutData(userNameGrid);

        Label passwordLabel = new Label(dialog, SWT.NONE);
        passwordLabel.setText(LabelText.PASSWORD);

        GridData passwordLabelGrid = new GridData();
        passwordLabelGrid.horizontalSpan = 4;
        passwordLabel.setLayoutData(passwordLabelGrid);

        passwordText = new Text(dialog, SWT.PASSWORD | SWT.BORDER);
        setDefaultTextValue(LabelText.PASSWORD, passwordText);
        fieldStatuses.put(LabelText.PASSWORD, false);

        GridData passwordTextGrid = new GridData();
        passwordTextGrid.horizontalSpan = 4;
        passwordTextGrid.widthHint = 200;
        passwordText.setLayoutData(passwordTextGrid);
    }

    private void centerDialog() {
        Rectangle parentSize = dialog.getParent().getBounds();
        Rectangle shellSize = dialog.getBounds();
        int locationX = (parentSize.x - parentSize.width + shellSize.width) / 2;
        int locationY = (parentSize.y * 2);
        dialog.setLocation(new Point(locationX + 100, locationY));

    }

    private void closeApplication() {
        Shell parent = (Shell) dialog.getParent();
        parent.dispose();
    }

    private void placeServerConfigFields() {
        Label label = new Label(dialog, SWT.NULL);
        GridData labelGrid = new GridData(GridData.VERTICAL_ALIGN_FILL);
        labelGrid.horizontalSpan = 1;
        label.setText(LabelText.SERVER);

        serverNameField = new Text(dialog, SWT.SINGLE | SWT.BORDER);
        fieldStatuses.put(LabelText.SERVER, true);
        serverNameField.addKeyListener(new SelectAllEventHandler(serverNameField));
        setDefaultTextValue(LabelText.SERVER, serverNameField);

        GridData textGridData = new GridData();
        textGridData.horizontalSpan = 1;
        textGridData.widthHint = 150;
        serverNameField.setLayoutData(textGridData);

        inputFieldMap.addConfigurationInput(LabelText.SERVER, serverNameField.getText());
        placePortNumberSpinner();

    }

    private void placePortNumberSpinner() {
        Label portLabel = new Label(dialog, SWT.NULL);
        GridData labelGrid = new GridData();
        labelGrid.horizontalSpan = 1;
        portLabel.setText(LabelText.PORT_NUMBER);

        portNumberSpinner = new Spinner(dialog, SWT.BORDER);
        setSpinnerSelection(LabelText.PORT_NUMBER, portNumberSpinner);
        fieldStatuses.put(LabelText.PORT_NUMBER, true);

        GridData gridData = new GridData();
        gridData.horizontalSpan = 1;
        portNumberSpinner.setMaximum(65535);
        portNumberSpinner.setSelection(465);
        portNumberSpinner.setLayoutData(gridData);

        inputFieldMap.addConfigurationInput(LabelText.PORT_NUMBER, portNumberSpinner.getText());
    }

    private void setUpVerificationListeners() {
        userNameText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                String currentUserName = ((Text) modifyEvent.widget).getText();
                if (currentUserName == null || currentUserName.isEmpty() || !currentUserName.contains("@")) {
                    changeVerificationStatus(LabelText.USER_NAME, false);
                    okButton.setEnabled(false);
                } else {
                    changeVerificationStatus(LabelText.USER_NAME, true);
                }
                if (isAllFieldsValid()) {
                    okButton.setEnabled(true);
                }
            }
        });
        passwordText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                String currentPassword = ((Text) modifyEvent.widget).getText();
                if (currentPassword == null || currentPassword.isEmpty() || currentPassword.length() < 6) {

                    changeVerificationStatus(LabelText.PASSWORD, false);
                    okButton.setEnabled(false);
                } else {
                    changeVerificationStatus(LabelText.PASSWORD, true);
                }
                if (isAllFieldsValid()) {
                    okButton.setEnabled(true);
                }
            }
        });
        serverNameField.addModifyListener(new EmptyInputListener(this, LabelText.SERVER));
        portNumberSpinner.addModifyListener(new EmptyInputListener(this, LabelText.PORT_NUMBER));

    }

    public boolean isAllFieldsValid() {
        for (Boolean status : fieldStatuses.values()) {
            if (!status) {
                return status;
            }
        }
        return true;
    }

    public void changeVerificationStatus(String key, Boolean value) {
        fieldStatuses.replace(key, value);
    }

    public void reOpen() {
        dialog.setVisible(true);
    }

    private void setDefaultTextValue(String label, Text text) {

        String bundleKey = ApplicationProperty.getPropertyFor(label);
        if (bundleKey != null) {
            String defaultValue = bundle.getString(bundleKey);

            text.setText(defaultValue);
        }
    }

    private void setSpinnerSelection(String label, Spinner spinner) {

        String bundleKey = ApplicationProperty.getPropertyFor(label);
        if (bundleKey != null) {
            String defaultValue = bundle.getString(bundleKey);

            spinner.setSelection(Integer.parseInt(defaultValue));
        }
    }
}
