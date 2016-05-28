import ir.ac.aut.ceit.aolab.lamp_notification;
import org.kaaproject.kaa.client.notification.NotificationListener;

/**
 * Created by root on 5/17/16.
 */
public class CustomNotificationListener implements NotificationListener {
    @Override
    public void onNotification(long l, lamp_notification lamp_notification) {
        Lamp.getLampById(lamp_notification.getId()).sendLampCommand(1);
        try {
            Thread.sleep(lamp_notification.getInterval() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Lamp.getLampById(lamp_notification.getId()).sendLampCommand(0);
    }
}
