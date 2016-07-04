package ir.ac.aut.ceit.aolab.jlamp.rest;

import com.sun.net.httpserver.HttpServer;
import ir.ac.aut.ceit.aolab.jlamp.rest.kaa.KaaLampService;
import ir.ac.aut.ceit.aolab.jlamp.rest.routes.LampRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		LampRoute.registerRoutes(new KaaLampService());

		try {
			HttpServer server = HttpServer.create(new InetSocketAddress("192.168.128.90", 4500), 30);
			server.createContext("/lamp/", new CustomHttpHandler(CustomKaaClient.getKaaClient(), null));
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

