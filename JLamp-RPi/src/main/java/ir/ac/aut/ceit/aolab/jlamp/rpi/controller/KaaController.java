package ir.ac.aut.ceit.aolab.jlamp.rpi.controller;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

import ir.ac.aut.ceit.aolab.jlamp.rpi.model.DefaultNotificationListener;
import ir.ac.aut.ceit.aolab.jlamp.rpi.serial.Serial;

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
            //getSerialInstance().connect("/dev/ttyACM0");
            LOG.info("Serial connection was successful");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOG.error(sw.toString());
        }
    }

    public void start() {
        kaaClient.start();

        kaaClient.addNotificationListener(new DefaultNotificationListener());
        char c;
        c = Serial.getSerialInstance().readChar();
        LOG.info("READLINE " + c);
        c = Serial.getSerialInstance().readChar();
        LOG.info("READLINE " + c);
        c = Serial.getSerialInstance().readChar();
        LOG.info("READLINE " + c);
        c = Serial.getSerialInstance().readChar();
        LOG.info("READLINE " + c);
        c = Serial.getSerialInstance().readChar();
        LOG.info("READLINE " + c);

    }

    public static KaaController getInstance() {
        if (instance == null)
            instance = new KaaController();

        return instance;
    }
}
