package by.bsuir.barbarossa.gui.listener;

import by.bsuir.barbarossa.gui.ConfigurationDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;


public class EmptyInputListener implements ModifyListener {
    private ConfigurationDialog dialog;
    private Button button;
    private String key;

    public EmptyInputListener(ConfigurationDialog dialog, String key) {
        this.dialog = dialog;
        this.key = key;
        button = dialog.getOkButton();
    }


    @Override
    public void modifyText(ModifyEvent modifyEvent) {
        Widget widget = modifyEvent.widget;
        String currentText = null;
        if (widget instanceof Text) {
            currentText = ((Text) widget).getText();

        }
        if (widget instanceof Spinner) {
            currentText = ((Spinner) widget).getText();
        }
        if (currentText == null || currentText.isEmpty()) {

            dialog.changeVerificationStatus(key, false);
            button.setEnabled(false);
        } else {
            dialog.changeVerificationStatus(key, true);
        }
        if (dialog.isAllFieldsValid()) {
            button.setEnabled(true);
        }
    }
}
