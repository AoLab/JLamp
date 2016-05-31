package ir.ac.aut.ceit.aolab.jlamp.rpi.model;

import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.OnIEvent;
import ir.ac.aut.ceit.aolab.TurnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by root on 5/17/16.
 */
public class DefaultEventListener implements LampEventFamily.Listener {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultEventListener.class);

	@Override
	public void onEvent(TurnEvent turnEvent, String s) {
		LOG.info("Received turn event");
		Lamp.getLampById(turnEvent.getId()).sendLampCommand(turnEvent.getStatus() ? 1 : 0);
	}

	@Override
	public void onEvent(OnIEvent onIEvent, String s) {
		LOG.info("Received onI event");
		Lamp.getLampById(onIEvent.getId()).sendLampCommand(1);
		try {
			Thread.sleep(onIEvent.getCommand() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Lamp.getLampById(onIEvent.getId()).sendLampCommand(0);
	}
}
