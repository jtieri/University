import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private int port;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    private String queryPrefix;
    private String fileName;

    private Client(final int port, final String queryPrefix, final String fileName) {
        this.port = port;
        this.queryPrefix = queryPrefix;
        this.fileName = fileName;
    }

    /**
     * init() will attempt to create the socket on the specified port and if successful, will begin to send commands
     * to the remote server.
     */
    private void init() {
        try {
            try {
                socket = new Socket("localhost", port);
                System.out.println( "Socket created on the local port " + socket.getLocalPort());
                System.out.println( "A connection established with the remote port " + socket.getPort() + " at "+socket.getInetAddress().toString() );

                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream());
                System.out.println( "I/O setup done." );

                sendCommands();
            } finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sendCommands() sends the query-prefix followed by the file name and then the QUIT command which breaks the connection.
     */
    private void sendCommands(){
        out.println("PRINT "+queryPrefix);
        out.println(fileName);
        out.flush();
        System.out.println(in.nextLine());
        sendCommand( "QUIT\n" );
    }

    /**
     * sendCommand() sends a single command out over the wire to the remote server and then closes the connection to the remote server
     * @param command the command being sent from the client to the remote server
     */
    private void sendCommand(String command){
        out.print(command);
        out.flush();
        try{
            socket.close();
            System.out.println( "A connection closed." );
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Please run Client again and pass in a port number, query prefix, and input file name.");
        }

        // read port, query prefix, and filename in from the command line
        final int port = Integer.parseInt(args[0]);
        final String queryPrefix = args[1];
        final String filename = args[2];

        // open socket on same port that server has opened up on
        Client client = new Client(port, queryPrefix, filename); // create the client object
        client.init();
    }
}
