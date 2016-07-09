/*
 * In The Name Of God
 * ======================================
 * [] Project Name : JLamp 
 * 
 * [] Package Name : ir.ac.aut.ceit.aolab.jlamp.rest.kaa
 *
 * [] Creation Date : 05-07-2016
 *
 * [] Created By : Parham Alvani (parham.alvani@gmail.com)
 * =======================================
*/

package ir.ac.aut.ceit.aolab.jlamp.rest.kaa;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import ir.ac.aut.ceit.aolab.jlamp.TurnEvent;
import ir.ac.aut.ceit.aolab.jlamp.rest.http.IResponse;
import ir.ac.aut.ceit.aolab.jlamp.rest.services.LampService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;

public class KaaLampService implements LampService {
    private static final Logger logger = LoggerFactory.getLogger(KaaLampService.class);

    private Gson gson = new Gson();

    @Override
    public IResponse onTurnRequest(Request request, Response response) {
        KaaTurnRequest ktr;
        IResponse iResponse = new IResponse();

        try {
            ktr = gson.fromJson(request.body(), KaaTurnRequest.class);
        } catch (JsonParseException e) {
            iResponse.setError(e.toString());
            iResponse.setStatus("Failed");
            response.status(400);

            logger.error(e.toString());

            return iResponse;
        }

        TurnEvent te = new TurnEvent(ktr.getId(), ktr.isStatus());

        KaaClientHelper.sendTurnEvent(te);

        iResponse.setStatus("Success");
        response.status(200);

        logger.info("Turn event sent successfully");

        return iResponse;
    }

    @Override
    public IResponse onStatusRequest(Request request, Response response) {
        return null;
    }

    @Override
    public IResponse onBootRequest(Request request, Response response) {
        KaaClientHelper.initiate();
        IResponse iResponse = new IResponse();
        iResponse.setStatus("Success");
        response.status(200);

        return iResponse;
    }
}
