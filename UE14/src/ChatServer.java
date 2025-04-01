import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 10023;
    private static final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private static final Set<String> nicknames = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server läuft auf Port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender && !message.trim().isEmpty()) {
                    client.sendMessage(message);
                }
            }
        }
    }

    static void addClient(ClientHandler client) {
        synchronized (clients) {
            clients.add(client);
            nicknames.add(client.getNickname());
        }
    }

    static void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
            nicknames.remove(client.getNickname());
        }
    }

    static boolean isNicknameTaken(String nickname) {
        synchronized (nicknames) {
            return nicknames.contains(nickname);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String nickname;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.print("Willkommen! Gib deinen Spitznamen ein: ");
            out.flush();
            while (true) {
                nickname = in.readLine().trim();
                if (nickname.isEmpty()) {
                    out.print("Spitzname darf nicht leer sein. Bitte erneut eingeben: ");
                } else if (ChatServer.isNicknameTaken(nickname)) {
                    out.print("Spitzname bereits vergeben. Bitte einen anderen wählen: ");
                } else {
                    break;
                }
                out.flush();
            }

            ChatServer.addClient(this);
            ChatServer.broadcast("\n" + nickname + " hat den Chat betreten.", this);
            sendPrompt();

            String message;
            while ((message = in.readLine()) != null) {
                if (message.trim().isEmpty()) {
                    sendPrompt();
                    continue;
                }
                if (message.equalsIgnoreCase("quit")) {
                    break;
                }
                ChatServer.broadcast("\r\n" + nickname + ": " + message, this);
                sendPrompt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatServer.broadcast("\n" + nickname + " hat den Chat verlassen.", this);
            ChatServer.removeClient(this);
        }
    }

    void sendMessage(String message) {
        out.println(message);
    }

    String getNickname() {
        return nickname;
    }

    private void sendPrompt() {
        out.print(nickname + "> ");
        out.flush();
    }
}
