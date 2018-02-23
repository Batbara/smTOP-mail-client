package by.bsuir.barbarossa.gui.listener;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;


public class VerifyInputListener implements ModifyListener {
    private Button button;

    public VerifyInputListener(Button button) {
        this.button = button;
    }


    @Override
    public void modifyText(ModifyEvent modifyEvent) {
        String currentUserName = ((Text) modifyEvent.widget).getText();
        if (currentUserName == null || currentUserName.isEmpty()) {

            button.setEnabled(false);
        }
    }
}
