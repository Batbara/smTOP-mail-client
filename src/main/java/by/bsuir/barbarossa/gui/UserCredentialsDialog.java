package by.bsuir.barbarossa.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class UserCredentialsDialog{
    private static final String CAPTION = "Log in";
    public static final String USER_NAME = "User name:";
    public static final String PASSWORD = "Password:";
    private Shell dialog;
    private Text userNameText;
    private Text passwordText;
    private String userName;
    private String password;

    public UserCredentialsDialog(Shell parent) {
        dialog = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        dialog.setText(CAPTION);
        dialog.setSize(150, 150);
        initDialogComponents();
    }
    public void open(){
        dialog.open();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
    private void initDialogComponents(){
        dialog.setLayout(new GridLayout(4, true));
        placeTextWithLabel(USER_NAME, SWT.BORDER);
        placeTextWithLabel(PASSWORD, SWT.PASSWORD | SWT.BORDER);
        Button ok = new Button(dialog, SWT.PUSH);
        ok.setText("OK");
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        ok.setLayoutData(data);
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                userName = userNameText.getText();
                password = passwordText.getText();
                dialog.close();
            }
        });

        Button cancel = new Button(dialog, SWT.PUSH);
        cancel.setText("Cancel");
        data = new GridData(GridData.FILL_HORIZONTAL);
        cancel.setLayoutData(data);
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Shell parent =(Shell) dialog.getParent();
             //   dialog.close();
                parent.dispose();
            }
        });

        dialog.setDefaultButton(ok);

    }
    private void placeTextWithLabel(String labelInput, int textStyle){
        Label label = new Label(dialog, SWT.NONE);
        label.setText(labelInput);
        GridData labelData = new GridData();
        labelData.horizontalSpan = 2;
        label.setLayoutData(labelData);

        Text textData = new Text(dialog, textStyle);
        GridData passwordData = new GridData(GridData.FILL_HORIZONTAL);
        passwordData.horizontalSpan = 2;
        textData.setLayoutData(passwordData);
    }
}
