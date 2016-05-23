import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.OnIEvent;
import ir.ac.aut.ceit.aolab.TurnEvent;

/**
 * Created by root on 5/17/16.
 */
public class CustomEventListener implements LampEventFamily.Listener {
    @Override
    public void onEvent(TurnEvent turnEvent, String s) {
        Serial.getSerialInstance().sendLampCommand(turnEvent.getId(), turnEvent.getStatus() ? 1 : 0);
    }

    @Override
    public void onEvent(OnIEvent onIEvent, String s) {
        Serial.getSerialInstance().sendLampCommand(onIEvent.getId(), 1);
        try {
            Thread.sleep(onIEvent.getCommand()*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Serial.getSerialInstance().sendLampCommand(onIEvent.getId(), 0);
    }
}
