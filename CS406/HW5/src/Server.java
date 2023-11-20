import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Server {
    private int port;
    private ServerSocket serverSocket;

    private Server(final int port) {
        this.port = port;
    }

    /**
     * init() will attempt to create the ServerSocket on the provided port and if successful, it will begin to listen
     * for incoming requests.
     */
    private void init() {
        try {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Socket created.");
                listen();
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * listen() will attempt to accept incoming connections on the local port and process messages as they are sent
     * by the client.
     */
    private void listen() {
        while (true) {
            System.out.println( "Listening for a connection on the local port "+ port + "..." );

            try {
                Socket socket = this.serverSocket.accept();
                System.out.println( "\nA connection established with the remote port "+socket.getPort()+" at "+socket.getInetAddress().toString());
                executeCommand(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * executeCommand() handles messages received over the wire by remote connections.
     * messages will quit being handled for a remote connection if they send the "QUIT" message.
     * @param socket is the remote connection whos messages are being handled.
     */
    private void executeCommand(Socket socket) {
        try {
            try (socket) {
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                System.out.println("I/O setup done");

                while (true) {
                    if (in.hasNext()) {
                        String command = in.next();
                        if (command.equals("QUIT")) {
                            System.out.println("QUIT: Connection being closed.");
                            out.print("QUIT accepted. Connection being closed.\n");
                            out.flush();
                            return;
                        } else {
                            accessAutocomplete(in, out);
                        }
                    }
                }
            } finally {
                System.out.println("A connection is closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * For the exercise in the HW, replace this method with a method called accessAutocomplete.
     * Create a new object of Autocomplete and take cue from the main method of Autocomplete and
     * the following method to send query results to Client's console
     * @param in the scanner representing the data incoming from the client
     * @param out the writer representing the data outgoing to the client
     */
    private void accessAutocomplete(Scanner in, PrintWriter out) {
        String queryPrefix = in.next();
        String fileName = in.next();
        System.out.println("Hello Client! You sent filename: " + fileName + " and queryprefix: " + queryPrefix);

        In fileInput = new In(fileName);
        int N = fileInput.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = fileInput.readLong();
            fileInput.readChar();
            String query = fileInput.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);

        Term[] results = autocomplete.allMatches(queryPrefix);
        System.out.println("Sending results: "+ Arrays.deepToString(results));
        out.println("From Server: "+ Arrays.deepToString(results));
        out.flush();
    }

    public static void main(String[] args) {
        // Throw exception if the user does not pass in a port from the command line
        if (args.length != 1) {
            throw new IllegalArgumentException("Please run Server again and pass in a valid port number. ");
        }

        final int port = Integer.parseInt(args[0]); // read port number in from command line

        Server server = new Server(port); // create Server object
        server.init(); // initialize the server and begin to listen for connections
    }
}
