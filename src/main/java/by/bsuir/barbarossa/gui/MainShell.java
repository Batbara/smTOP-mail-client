package by.bsuir.barbarossa.gui;

import by.bsuir.barbarossa.gui.listener.SelectAllEventHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainShell {
    private Shell mainShell;
    private Map<String, Text> textFields;
    private Text logMemo;
    private Button sendButton;
    public MainShell() {
        Display display = new Display();

        mainShell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);

        textFields = new LinkedHashMap<String, Text>();
        initMainView();

        mainShell.pack();
    }


    private void initMainView() {
        mainShell.setText(TextConstant.MAIN_SHELL_TITLE);
        GridLayout shellLayout = new GridLayout(7, false);
        shellLayout.verticalSpacing = 10;

        mainShell.setLayout(shellLayout);

        int serverTextFieldSpan = 2;
        placeTextWithLabel(TextConstant.SERVER, serverTextFieldSpan);
        placeSpinner();

        int textFieldHorizontalSpan = 7;
        placeTextWithLabel(TextConstant.FROM_LABEL, textFieldHorizontalSpan);
        placeTextWithLabel(TextConstant.TO_LABEL, textFieldHorizontalSpan);
        placeTextWithLabel(TextConstant.LETTER_SUBJECT, textFieldHorizontalSpan);

        int memoHorizontalSpan = 7;
        int memoVerticalSpan = 10;
        placeMemoWithLabel(TextConstant.LETTER_CONTENT, memoHorizontalSpan, memoVerticalSpan);

        placeSendButton();

        placeMemoWithLabel(TextConstant.FEEDBACK, memoHorizontalSpan, memoVerticalSpan);

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

    private void placeSendButton(){
        sendButton = new Button(mainShell, SWT.PUSH);
        sendButton.setText(TextConstant.SEND_BUTTON);
        GridData buttonGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        buttonGridData.horizontalSpan = 7;
        buttonGridData.horizontalAlignment = GridData.CENTER;
        sendButton.setLayoutData(buttonGridData);
    }
    private void placeSpinner() {
        Label portLabel = new Label(mainShell, SWT.NULL);
        portLabel.setText(TextConstant.PORT_NUMBER);

        Spinner spinner = new Spinner(mainShell, SWT.BORDER);
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.horizontalSpan = 3;
        spinner.setSelection(25);
        spinner.setLayoutData(gridData);

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

        textFields.put(labelText, textField);
    }

    private void placeMemoWithLabel(String labelText, int horizontalSpan, int verticalSpan) {
        Label label = new Label(mainShell, SWT.NULL);
        GridData labelData = new GridData(GridData.FILL_VERTICAL);
        label.setData(labelData);
        label.setText(labelText);

        Text textMemo = new Text(mainShell, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        textMemo.addKeyListener(new SelectAllEventHandler(textMemo));

        GridData textGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
        textGridData.horizontalSpan = horizontalSpan;
        textGridData.verticalSpan = verticalSpan;
        textGridData.grabExcessVerticalSpace = true;
        textMemo.setLayoutData(textGridData);

        textFields.put(labelText, textMemo);
    }
}
