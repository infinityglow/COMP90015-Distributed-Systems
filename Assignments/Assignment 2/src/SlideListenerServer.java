import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SlideListenerServer implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
        DrawListenerServer.stroke = (float) ManagerGUI.js.getValue() / 10;
    }
}
