import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class cliente {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Calculadora Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

       
        JTextField inputField = new JTextField();
        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JButton sendButton = new JButton("Enviar");
        sendButton.setPreferredSize(new Dimension(100, 40));

      
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Ingresa la operación (ej. 3*4):"), BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

       
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.setVisible(true);

        
        Socket clientSocket = new Socket("127.0.0.1", 5000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sentence = inputField.getText().trim();

                try {
                    outToServer.writeBytes(sentence + '\n');
                    String respuesta = inFromServer.readLine();
                    resultArea.append("→ " + sentence + "\n" + respuesta + "\n");
                    inputField.setText("");
                } catch (IOException ex) {
                    resultArea.append("Error de conexión con el servidor.\n");
                }
            }
        });
    }
}
