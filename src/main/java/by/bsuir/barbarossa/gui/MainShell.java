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

public class MainShell {
    static int number = 0;
    private Shell mainShell;
    public MainShell(){
        Display display = new Display();

        mainShell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        initMainView();
        mainShell.pack();
    }
    private void initMainView() {
        mainShell.setText(TextConstant.MAIN_SHELL_TITLE);
        GridLayout shellLayout = new GridLayout(8, false);
        shellLayout.verticalSpacing =10;

        mainShell.setLayout(shellLayout);


        placeTextWithLabel(TextConstant.SERVER,3);

        Label portLabel = new Label(mainShell, SWT.NULL);
        portLabel.setText(TextConstant.PORT_NUMBER);

        Spinner spinner = new Spinner(mainShell, SWT.BORDER);
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridData.horizontalSpan = 3;
        spinner.setSelection(25);
        spinner.setLayoutData(gridData);

        placeTextWithLabel(TextConstant.FROM_LABEL,7);
        placeTextWithLabel(TextConstant.TO_LABEL,7);
        placeTextWithLabel(TextConstant.LETTER_SUBJECT,7);

        placeMemoWithLabel(TextConstant.LETTER_CONTENT, 7);

        Button sendButton = new Button(mainShell, SWT.PUSH);
        sendButton.setText(TextConstant.SEND_BUTTON);
        GridData buttonGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        buttonGridData.horizontalSpan = 8;
        buttonGridData.horizontalAlignment = GridData.CENTER;
        sendButton.setLayoutData(buttonGridData);

        placeMemoWithLabel(TextConstant.FEEDBACK, 7);

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
    private void placeTextWithLabel(String labelText, int horizontalSpan){
        Label label = new Label(mainShell, SWT.NULL);
        label.setText(labelText);

        Text textField = new Text(mainShell, SWT.SINGLE | SWT.BORDER);
        textField.addKeyListener(new SelectAllEventHandler(textField));
        GridData textGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        textGridData.horizontalSpan = horizontalSpan;
        textGridData.widthHint = 150;
        textField.setLayoutData(textGridData);
    }

    private void placeMemoWithLabel(String labelText, int horizontalSpan){
        Label label = new Label(mainShell, SWT.NULL);
        GridData labelData = new GridData(GridData.FILL_VERTICAL);
        label.setData(labelData);
        label.setText(labelText);

        Text textMemo = new Text(mainShell, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        textMemo.addKeyListener(new SelectAllEventHandler(textMemo));

        GridData textGridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
        textGridData.horizontalSpan = horizontalSpan;
        textGridData.verticalSpan = 10;
        textGridData.grabExcessVerticalSpace = true;
        textMemo.setLayoutData(textGridData);
    }
}
