import java.net.*;
import java.io.*;

public class servidor {
    public static void main(String argv[]) throws Exception {
        // Se crea el socket de servidor en el puerto 5000
        ServerSocket welcomeSocket = new ServerSocket(5000);
        System.out.println("Servidor iniciado. Esperando conexiones...");

       
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Cliente conectado: " + connectionSocket.getInetAddress());

            try {
                // Canales de entrada y salida para comunicarse con el cliente
                BufferedReader inFromClient = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                String clientSentence;

                // Leer la operación enviada por el cliente
                while ((clientSentence = inFromClient.readLine()) != null) {
                    String respuesta;
                    try {
                        String[] parts = clientSentence.split("\\*");
                        if (parts.length == 2) {
                            // parseo de los dos valores
                            int num1 = Integer.parseInt(parts[0].trim());
                            int num2 = Integer.parseInt(parts[1].trim());
                            // Realizar la multiplicación
                            int result = num1 * num2;
                            respuesta = "Resultado: " + result + '\n';
                        } else {
                            respuesta = "Operación inválida.\n";
                        }
                    } catch (NumberFormatException e) {
                        respuesta = "Error en los números. \n";
                    }

                    // Enviar la respuesta al cliente
                    outToClient.writeBytes(respuesta);
                }

                // Se cierra la conexión con el cliente
                connectionSocket.close();
                System.out.println("Cliente desconectado.");
            } catch (IOException e) {
                System.out.println("Error con el cliente: " + e.getMessage());
            }
        }
    }
}
