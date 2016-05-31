package ir.ac.aut.ceit.aolab.jlamp.rpi.model;

import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.jlamp.OnIEvent;
import ir.ac.aut.ceit.aolab.jlamp.PIREventClassFamily;
import ir.ac.aut.ceit.aolab.jlamp.StatusEvent;
import ir.ac.aut.ceit.aolab.jlamp.TurnEvent;
import ir.ac.aut.ceit.aolab.jlamp.rpi.controller.KaaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ir.ac.aut.ceit.aolab.jlamp.rpi.model.Lamp.getLampById;

/**
 * Created by root on 5/17/16.
 */
public class DefaultEventListener implements LampEventFamily.Listener, PIREventClassFamily.Listener {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultEventListener.class);

	@Override
	public void onEvent(TurnEvent turnEvent, String s) {
		LOG.info("Received turn event");
		getLampById(turnEvent.getId()).sendLampCommand(turnEvent.getStatus() ? 1 : 0);
	}

	@Override
	public void onEvent(OnIEvent onIEvent, String s) {
		LOG.info("Received onI event");
		getLampById(onIEvent.getId()).sendLampCommand(1);
		try {
			Thread.sleep(onIEvent.getInterval() * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getLampById(onIEvent.getId()).sendLampCommand(0);
	}

	@Override
	public void onEvent(StatusEvent statusEvent, String s) {
		LOG.info("Received status event");
		Boolean status = Lamp.getLampById(statusEvent.getId()).getLampStatus();
        StatusEvent statusEventResponse = new StatusEvent(status ? "true" : "false");
        KaaController.getInstance().sendLampStatusEvent(statusEventResponse);
	}

    @Override
    public void onEvent(ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent statusEvent, String s) {
        LOG.info("Received status event");
        int status = PIR.getbyId(statusEvent.getId()).getStatus();
        ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent statusEventResponse = new ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent(String.valueOf(status));
        KaaController.getInstance().sendLampStatusEvent(statusEventResponse);

    }
}
