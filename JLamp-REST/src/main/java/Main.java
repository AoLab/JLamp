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
			server.createContext("/lamp/Onl", new MyHandler());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static class MyHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "This is the response";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
		
	}
	
}
