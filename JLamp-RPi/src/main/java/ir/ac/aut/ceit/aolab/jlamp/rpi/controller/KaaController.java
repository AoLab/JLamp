package ir.ac.aut.ceit.aolab.jlamp.rpi.controller;

import ir.ac.aut.ceit.aolab.jlamp.rpi.model.DefaultNotificationListener;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.logging.BucketInfo;
import org.kaaproject.kaa.client.logging.LogDeliveryListener;
import org.kaaproject.kaa.client.logging.RecordInfo;
import org.kaaproject.kaa.client.logging.future.RecordFuture;
import org.kaaproject.kaa.schema.base.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;

import static ir.ac.aut.ceit.aolab.jlamp.rpi.serial.Serial.getSerialInstance;

/**
 * Created by iman on 4/29/16.
 */
public class KaaController {

    private static KaaController instance;
    private final Logger LOG = LoggerFactory.getLogger(KaaController.class);
    private KaaClient kaaClient;

    private KaaController() {
        kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {
            @Override
            public void onStarted() {
                super.onStarted();
                LOG.info("Kaa Client Started!");
            }

        });

        try {
            LOG.info("Trying to connect to serial ...");
            getSerialInstance().connect("/dev/ttyACM0");
            LOG.info("Serial connection was successful");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOG.error(sw.toString());
        }
    }

    public void start() {
        kaaClient.start();
        kaaClient.attachUser("userExternalId",
                "userAccessToken", response -> System.out.println("Attach response" + response.getResult()));

        kaaClient.addNotificationListener(new DefaultNotificationListener());
        kaaClient.setLogDeliveryListener(new LogDeliveryListener() {
            @Override
            public void onLogDeliverySuccess(BucketInfo bucketInfo) { /* Called on success */ }

            @Override
            public void onLogDeliveryFailure(BucketInfo bucketInfo) { /* Called on failure */ }

            @Override
            public void onLogDeliveryTimeout(BucketInfo bucketInfo) { /* Called on timeout */ }
        });
        Log logRecord = new Log();
        logRecord.put("Salam", "Chetory");
// Push the record to the collector
        RecordFuture logDeliveryStatus = kaaClient.addLogRecord(logRecord);
// Get log delivery information
        try {
            RecordInfo logDeliveryReport = logDeliveryStatus.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static KaaController getInstance() {
        if (instance == null)
            instance = new KaaController();

        return instance;
    }

}

