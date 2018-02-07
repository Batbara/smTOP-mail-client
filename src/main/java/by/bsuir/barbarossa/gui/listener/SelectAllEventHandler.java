package by.bsuir.barbarossa.gui.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Text;


public class SelectAllEventHandler extends KeyAdapter {
    private Text text;
    public SelectAllEventHandler(Text text){
        this.text = text;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.stateMask == SWT.CTRL && e.keyCode == 'a'){
            text.selectAll();
            e.doit = false;
        }
    }
}
