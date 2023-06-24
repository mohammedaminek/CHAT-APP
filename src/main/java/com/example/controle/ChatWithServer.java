package com.example.controle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;


public class ChatWithServer extends Thread {
    private int nbrClient;
    private List<Conversation> Clients=new ArrayList<Conversation>();

    public ChatWithServer() {
    }

    public static void main(String[] args) {
        (new ChatWithServer()).start();
    }
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Demarrage de serveur");
            while(true) {
                Socket socket = ss.accept();
                ++this.nbrClient;
                Conversation Newconversation= new Conversation(socket, this.nbrClient);
                Clients.add(Newconversation);
                Newconversation.start();
            }
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    class Conversation extends Thread {
        private Socket socket;
        private int numClient;

        public Conversation(Socket socket, int n) {
            this.socket = socket;
            this.numClient = n;
        }

        @Override
        public void run() {
            try {
                InputStream is = this.socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = this.socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String IP = this.socket.getRemoteSocketAddress().toString();
                System.out.println("connextion du client " + this.numClient + " IP:" + IP);
                pw.println("vou etes le client" + this.numClient);
                pw.println("Envoyer votre Message :");



/*                // Convert the list to a JSON string
                Gson gson = new Gson();
                String jsonString = gson.toJson(Clients);
                // Send the JSON string over the socket
                pw.println(jsonString);

*/

                while(true) {
                    for(Conversation c:Clients){
                        pw.println("#client"+c.numClient);
                    }
                    String req = br.readLine();

                    if(req.contains("=>")){
                        String[] tab=req.split("=>");
                        if(tab.length==2){
                            String message= tab[1];
                            int clt=Integer.parseInt(tab[0]);
                            send(message,socket,clt);
                        }
                    }else{
                        send(req,socket,-1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String send(String Request,Socket s,int num) throws IOException {
            for(Conversation client :Clients){
                if(client.socket!=s){
                    if(client.numClient == num || num==-1){
                        PrintWriter Pw= new PrintWriter(client.socket.getOutputStream(),true);
                        Pw.println(Request);
                    }
                }
            }
            return Request;
        }
    }
}
