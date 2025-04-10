package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread
{	
    
  public static void main (String args[])
  {
    ServerSocket sfd = null;
    try
    {
      sfd = new ServerSocket(7000);
    }
    catch (IOException ioe)
    {
      System.out.println("Comunicacion rechazada."+ioe);
      System.exit(1);
    }

    while (true)
    {
      try
      {
        Socket nsfd = sfd.accept();
        System.out.println("Conexion aceptada de: "+nsfd.getInetAddress());
        DataInputStream oos = new DataInputStream(nsfd.getInputStream());
        DataOutputStream escritor = new DataOutputStream(new FileOutputStream(new File("SerieNacionalBasketball_respaldo.dat")));
        int unByte;
        try {
			while ((unByte = oos.read()) != -1)
			   escritor.write(unByte);
			oos.close();
	        escritor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
      } 
      catch(IOException ioe)
      {
        System.out.println("Error: "+ioe);
      }
    }
  }
}

