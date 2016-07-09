/*
 * In The Name Of God
 * ======================================
 * [] Project Name : JLamp 
 * 
 * [] Package Name : ir.ac.aut.ceit.aolab.jlamp.rest.routes
 *
 * [] Creation Date : 05-07-2016
 *
 * [] Created By : Parham Alvani (parham.alvani@gmail.com)
 * =======================================
*/

package ir.ac.aut.ceit.aolab.jlamp.rest.routes;

import ir.ac.aut.ceit.aolab.jlamp.rest.http.JsonTransformer;
import ir.ac.aut.ceit.aolab.jlamp.rest.services.LampService;
import spark.Spark;

public class LampRoute {
    public static void registerRoutes(LampService service) {
        Spark.post("/lamp/boot", "application/json", service::onBootRequest, new JsonTransformer());
        Spark.post("/lamp/turn", "application/json", service::onTurnRequest, new JsonTransformer());
        Spark.post("/lamp/status", "application/json", service::onStatusRequest, new JsonTransformer());
    }
}
