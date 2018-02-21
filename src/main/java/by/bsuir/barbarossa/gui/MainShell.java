package by.bsuir.barbarossa.gui;

import by.bsuir.barbarossa.controller.ApplicationController;
import by.bsuir.barbarossa.gui.listener.SelectAllEventHandler;
import by.bsuir.barbarossa.gui.listener.SendMailListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import java.util.Observable;

public class MainShell extends Observable {
    private Display display;
    private Shell mainShell;

    private UserCredentialsDialog userCredentialsDialog;
    private InputFieldMap inputFieldMap;
    private Text logMemo;

    public MainShell() {
        display = new Display();
        //userCredentialsDialog = new UserCredentialsDialog(mainShell);
       // userCredentialsDialog.open();
        inputFieldMap = new InputFieldMap();

        mainShell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);


        initMainView();

        mainShell.pack();
    }

    public void addControllerAsObserver(ApplicationController controller) {
        addObserver(controller);
    }

    public Display getDisplay() {
        return display;
    }

    public Shell getMainShell() {
        return mainShell;
    }

    public Text getLogMemo() {
        return logMemo;
    }

    public InputFieldMap getInputFieldMap() {
        return inputFieldMap;
    }

    private void initMainView() {
        mainShell.setText(LabelText.MAIN_SHELL_TITLE);
        GridLayout shellLayout = new GridLayout(7, false);
        shellLayout.verticalSpacing = 10;

        mainShell.setLayout(shellLayout);

        int serverTextFieldSpan = 2;
        placeTextWithLabel(LabelText.SERVER, serverTextFieldSpan);
        placeSpinner();

        int textFieldHorizontalSpan = 7;
        placeTextWithLabel(LabelText.FROM_LABEL, textFieldHorizontalSpan);
        placeTextWithLabel(LabelText.TO_LABEL, textFieldHorizontalSpan);
        placeTextWithLabel(LabelText.LETTER_SUBJECT, textFieldHorizontalSpan);

        int memoHorizontalSpan = 7;
        int memoVerticalSpan = 10;

        Text messageMemo = new Text(mainShell, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

        inputFieldMap.addTextField(LabelText.LETTER_CONTENT, messageMemo);
        placeMemoWithLabel(messageMemo, LabelText.LETTER_CONTENT, memoHorizontalSpan, memoVerticalSpan);

        placeSendButton();

        logMemo = new Text(mainShell, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
        placeMemoWithLabel(logMemo, LabelText.FEEDBACK, memoHorizontalSpan, memoVerticalSpan);

    }

    public void start() {

        mainShell.open();

        Display display = mainShell.getDisplay();
        while (!mainShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void placeSendButton() {
        Button sendButton = new Button(mainShell, SWT.PUSH);
        setChanged();
        sendButton.addListener(SWT.Selection, new SendMailListener(this));

        sendButton.setText(LabelText.SEND_BUTTON);
        GridData buttonGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        buttonGridData.horizontalSpan = 7;
        buttonGridData.horizontalAlignment = GridData.CENTER;
        sendButton.setLayoutData(buttonGridData);
    }

    private void placeSpinner() {
        Label portLabel = new Label(mainShell, SWT.NULL);
        portLabel.setText(LabelText.PORT_NUMBER);

        Spinner spinner = new Spinner(mainShell, SWT.BORDER);
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.horizontalSpan = 3;
        spinner.setMaximum(65535);
        spinner.setSelection(465);
        spinner.setLayoutData(gridData);

        inputFieldMap.addSpinnerField(LabelText.PORT_NUMBER, spinner);
    }

    private void placeTextWithLabel(String labelText, int horizontalSpan) {

        Label label = new Label(mainShell, SWT.NULL);
        label.setText(labelText);

        Text textField = new Text(mainShell, SWT.SINGLE | SWT.BORDER);

        textField.addKeyListener(new SelectAllEventHandler(textField));

        GridData textGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        textGridData.horizontalSpan = horizontalSpan;
        textGridData.widthHint = 150;
        textField.setLayoutData(textGridData);

        inputFieldMap.addTextField(labelText, textField);
    }

    private void placeMemoWithLabel(Text textMemo, String labelText, int horizontalSpan, int verticalSpan) {
        Label label = new Label(mainShell, SWT.NULL);
        GridData labelData = new GridData(GridData.FILL_VERTICAL);
        label.setData(labelData);
        label.setText(labelText);

        textMemo.addKeyListener(new SelectAllEventHandler(textMemo));
        // textMemo.setSize(50, 250);
        GridData textGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
        // GridData textGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL , GridData.VERTICAL_ALIGN_FILL, true, true, 20, 20);
        //textGridData.horizontalSpan = horizontalSpan;
        textGridData.verticalSpan = verticalSpan;
        textGridData.grabExcessVerticalSpace = false;
        textMemo.setLayoutData(textGridData);
    }
}
