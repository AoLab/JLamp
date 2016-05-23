import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
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

