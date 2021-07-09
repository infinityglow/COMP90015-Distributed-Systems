import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SlideListenerClient implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent e) {
        DrawListenerClient.stroke = (float) ClientGUI.js.getValue() / 10;
    }
}
