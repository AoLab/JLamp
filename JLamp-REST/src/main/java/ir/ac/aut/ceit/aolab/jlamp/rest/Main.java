package ir.ac.aut.ceit.aolab.jlamp.rest;

import ir.ac.aut.ceit.aolab.jlamp.rest.kaa.KaaLampService;
import ir.ac.aut.ceit.aolab.jlamp.rest.routes.LampRoute;

public class Main {

    public static void main(String[] args) {
        LampRoute.registerRoutes(new KaaLampService());
    }

}

