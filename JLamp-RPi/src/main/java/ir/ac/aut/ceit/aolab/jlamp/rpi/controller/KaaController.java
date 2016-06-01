package ir.ac.aut.ceit.aolab.jlamp.rpi.controller;

import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.jlamp.rpi.model.DefaultEventListener;
import ir.ac.aut.ceit.aolab.jlamp.rpi.model.DefaultNotificationListener;
import ir.ac.aut.ceit.aolab.jlamp.rpi.model.Lamp;
import ir.ac.aut.ceit.aolab.jlamp.rpi.serial.Serial;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

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
			Serial.getSerialInstance().connect("/dev/ttyACM1");
			LOG.info("Serial connection was successful");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LOG.error(sw.toString());
		}

	}

	public void start() {
		kaaClient.start();
		EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
		LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
		lampEventFamily.addListener(new DefaultEventListener());

		kaaClient.attachUser("userExternalId",
				"userAccessToken", response -> System.out.println("Attach response" + response.getResult()));

		kaaClient.addNotificationListener(new DefaultNotificationListener());

		System.out.println(Lamp.getLampById("11").getLampStatus());
	}

	public static KaaController getInstance() {
		if (instance == null)
			instance = new KaaController();

		return instance;
	}


}
