package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Project06F_MultiChatServer {
  HashMap clients;

  Project06F_MultiChatServer() {
    clients = new HashMap();
    Collections.synchronizedMap(clients);
  }

  public void start() {
    ServerSocket serverSocket = null;
    Socket socket = null;

    try {
      serverSocket = new ServerSocket(9999);
      System.out.println("start server...");
      while(true) {
        socket = serverSocket.accept();
        System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "connect!");
        ServerReceiver thread = new ServerReceiver(socket);
        thread.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void sendToAll(String msg) {
    Iterator iterator = clients.keySet().iterator();

    while (iterator.hasNext()) {
      try {
        DataOutputStream out = (DataOutputStream) clients.get(iterator.next());
        out.writeUTF(msg);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    new Project06F_MultiChatServer().start();
  }

  class ServerReceiver extends Thread {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    ServerReceiver(Socket socket) {
      this.socket = socket;

      try  {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void run() {
      String name = "";

      try {
        name = in.readUTF();
        if(clients.get(name) != null) {
          out.writeUTF("#Already exist name : " + name);
          out.writeUTF("#Please reconnect by other name");
          System.out.println(socket.getInetAddress() + ":" + socket.getPort() + " disconnect!");
          in.close();
          out.close();
          socket.close();
          socket = null;
        } else {
          sendToAll("#" + name + " join!");
          clients.put(name, out);
          while (in != null) {
            sendToAll(in.readUTF());
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if(socket != null) {
          sendToAll("#" + name + " exit!");
          clients.remove(name);
          System.out.println(socket.getInetAddress() + ":" + socket.getPort() + " disconnect!");
        }
      }
    }
  }

}
