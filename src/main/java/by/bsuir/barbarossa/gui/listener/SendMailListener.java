package by.bsuir.barbarossa.gui.listener;

import by.bsuir.barbarossa.gui.MainShell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class SendMailListener implements Listener {
    private MainShell mainShell;

    public SendMailListener(MainShell mainShell) {
        this.mainShell = mainShell;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.type == SWT.Selection) {
            System.out.println("sending");
            mainShell.sendData();
        }
    }

}
