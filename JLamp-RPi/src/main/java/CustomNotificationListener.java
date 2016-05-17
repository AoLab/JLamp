import ir.ac.aut.ceit.aolab.lamp_notification;
import org.kaaproject.kaa.client.notification.NotificationListener;

/**
 * Created by root on 5/17/16.
 */
public class CustomNotificationListener implements NotificationListener {
    @Override
    public void onNotification(long l, lamp_notification lamp_notification) {
        Serial.getSerialInstance().sendLampCommand(lamp_notification.getId(), 1);
        try {
            Thread.sleep(lamp_notification.getInterval() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Serial.getSerialInstance().sendLampCommand(lamp_notification.getId(), 0);
    }
}
