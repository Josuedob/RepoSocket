import java.net.*;
import java.io.*;

public class servidor {
    public static void main(String argv[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(5000);
        System.out.println("Servidor iniciado. Esperando conexiones...");

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Cliente conectado: " + connectionSocket.getInetAddress());

         
            new Thread(() -> {
                try {
                    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    String clientSentence;

                    while ((clientSentence = inFromClient.readLine()) != null) {
                        String respuesta;
                        try {
                            String[] parts = clientSentence.split("\\*");
                            if (parts.length == 2) {
                                int num1 = Integer.parseInt(parts[0].trim());
                                int num2 = Integer.parseInt(parts[1].trim());
                                int result = num1 * num2;
                                respuesta = "Resultado: " + result + '\n';
                            } else {
                                respuesta = "Operación inválida.\n";
                            }
                        } catch (NumberFormatException e) {
                            respuesta = "Error en los números. \n";
                        }

                        outToClient.writeBytes(respuesta);
                    }

                    connectionSocket.close();
                    System.out.println("Cliente desconectado.");
                } catch (IOException e) {
                    System.out.println("Error con el cliente: " + e.getMessage());
                }
            }).start();
        }
    }
}
