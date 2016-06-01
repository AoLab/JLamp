package ir.ac.aut.ceit.aolab.jlamp.rpi.model;

import ir.ac.aut.ceit.aolab.jlamp.PIREventClassFamily;
import ir.ac.aut.ceit.aolab.jlamp.rpi.controller.KaaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iman on 6/1/16.
 */
public class DefaultPIREventListener implements PIREventClassFamily.Listener {

    private static Logger LOG = LoggerFactory.getLogger(DefaultPIREventListener.class);

    @Override
    public void onEvent(ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent statusEvent, String s) {
        LOG.info("Received status of pir event");
        int status = PIR.getbyId(statusEvent.getId()).getStatus();
        ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent statusEventResponse = new ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent(String.valueOf(status));
        KaaController.getInstance().sendPIRStatusEvent(statusEventResponse);

    }
}
