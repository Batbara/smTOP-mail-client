package by.bsuir.barbarossa.gui;

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
import org.eclipse.swt.widgets.Text;


public class UserCredentialsDialog {
    private static final String CAPTION = "Log in";
    private static final String OK_BUTTON = "OK";
    private static final String CANCEL_BUTTON = "Cancel";

    private Shell dialog;
    private InputFieldMap inputFieldMap;
    private Text userNameText;
    private Text passwordText;

    public UserCredentialsDialog(Shell parent, InputFieldMap inputFieldMap) {
        this.inputFieldMap = inputFieldMap;
        dialog = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL |SWT.ON_TOP);
        dialog.setText(CAPTION);
        dialog.setSize(150, 150);
        centerDialog(parent);

        initDialogComponents();

    }

    public void open() {
        dialog.pack();
        dialog.open();
    }

    private void initDialogComponents() {
        dialog.setLayout(new GridLayout(2, true));

        placeUserCredentials();

        Button okButton = new Button(dialog, SWT.PUSH);
        //okButton.setEnabled(false);
        okButton.setText(OK_BUTTON);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        okButton.setLayoutData(data);
        okButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                inputFieldMap.addUserCredential(LabelText.USER_NAME, userNameText.getText());
                inputFieldMap.addUserCredential(LabelText.PASSWORD, passwordText.getText());
                dialog.close();
            }
        });

        Button cancel = new Button(dialog, SWT.PUSH);
        cancel.setText(CANCEL_BUTTON);
        data = new GridData(GridData.FILL_HORIZONTAL);

        cancel.setLayoutData(data);
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
        setUpVerification(userNameText, okButton);
        setUpVerification(passwordText, okButton);
    }

    private void placeUserCredentials() {
        Label userNameLabel = new Label(dialog, SWT.NONE);
        userNameLabel.setText(LabelText.USER_NAME);
        GridData labelData = new GridData();
        labelData.horizontalSpan = 1;
        userNameLabel.setLayoutData(labelData);

        userNameText = new Text(dialog, SWT.BORDER);

        GridData userNameGrid = new GridData(GridData.FILL_HORIZONTAL);
        userNameGrid.horizontalSpan = 1;
        userNameText.setLayoutData(userNameGrid);

        Label passwordLabel = new Label(dialog, SWT.NONE);
        passwordLabel.setText(LabelText.PASSWORD);
        GridData passwordLabelGrid = new GridData();
        passwordLabelGrid.horizontalSpan = 1;
        passwordLabel.setLayoutData(passwordLabelGrid);

        passwordText = new Text(dialog, SWT.PASSWORD | SWT.BORDER);

        GridData passwordTextGrid = new GridData(GridData.FILL_HORIZONTAL);
        passwordTextGrid.horizontalSpan = 1;
        userNameText.setLayoutData(passwordTextGrid);
    }

    private void centerDialog(Shell parent) {
        Rectangle shellBounds = parent.getBounds();
        Point dialogSize = dialog.getSize();

        dialog.setLocation(
                shellBounds.x + (shellBounds.width - dialogSize.x) / 2,
                shellBounds.y + (shellBounds.height - dialogSize.y) / 2);
    }

    private void closeApplication() {
        Shell parent = (Shell) dialog.getParent();
        parent.dispose();
    }

    private void setUpVerification(Text text, Button okButton) {
        userNameText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                String currentUserName = ((Text) modifyEvent.widget).getText();
                if (currentUserName == null || currentUserName.isEmpty() || !currentUserName.contains("@")) {

                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }
        });
        passwordText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent modifyEvent) {
                String currentPassword = ((Text) modifyEvent.widget).getText();
                if (currentPassword == null || currentPassword.isEmpty() || currentPassword.length() < 6) {

                    okButton.setEnabled(false);
                } else {
                    okButton.setEnabled(true);
                }
            }
        });
        // text.addModifyListener(new VerifyInputListener(okButton));

    }
}
