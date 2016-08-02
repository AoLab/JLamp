package ir.ac.aut.ceit.aolab.jlamp.rest;

import ir.ac.aut.ceit.aolab.jlamp.rest.kaa.KaaLampService;
import ir.ac.aut.ceit.aolab.jlamp.rest.routes.LampRoute;
import spark.Spark;

public class Main {

    public static void main(String[] args) {
        Spark.port(1373);
        LampRoute.registerRoutes(new KaaLampService());
    }

}

