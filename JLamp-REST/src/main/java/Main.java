import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Main {
	public static void main(String[] args) {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 80),30);
			server.createContext("/lamp/Onl", new CustomHttpHandler());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static class CustomHttpHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String query = exchange.getRequestURI().getQuery();

			int interval = getInterval(query);
			String response = "Your requested time interval was sent to kaa: interval is " + interval + " second(s)";
			exchange.sendResponseHeaders(200, response.length());

			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response.getBytes());
			outputStream.close();
		}

		public int getInterval(String query){
			return Integer.valueOf(query.split("=")[1]);
		}

	}

}
