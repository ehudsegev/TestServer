


import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {


    public static void main(String[] args) throws Exception {


        int port = 9090;
        int NumOfSRs = 1;
        int delayBTsessionsMin = 15;
        int delayBTSRsMilliSec = 1000;
        String srMapString = "";
        String srOutboundMapString = "";
        if (args.length > 0) {
            try {
                if (args.length > 0) {
                    port = Integer.valueOf(args[0]);
                }
                if (args.length > 1) {
                    srMapString = args[1];
                }
                if (args.length > 2) {
                    NumOfSRs = Integer.valueOf(args[2]);
                }
                if (args.length > 3) {
                    delayBTSRsMilliSec = Integer.valueOf(args[3]);
                }
                if (args.length > 4) {
                    delayBTsessionsMin = Integer.valueOf(args[4]);
                }
            } catch (NumberFormatException e) {
                System.err.println("Argument #1 Port " + args[0] + " must be an integer.");
                System.exit(1);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }

/*
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/uditool", new MyHttpHandler(srMapString));
        server.setExecutor(null); // creates a default executor

        System.out.println("Starting Server");
        server.start();*/
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new MyServlet(srMapString)),"/*");


        server.start();
        server.join();

    }


}

