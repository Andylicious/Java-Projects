// $Id: client.java,v 1.3 2011-04-19 23:14:55-07 - - $

import java.io.*;
import java.net.*;
import java.util.*;
import static java.lang.System.*;

class client {
   static class client_starter implements Runnable {
      chatter.options opts;
      client_starter (chatter.options opts) {
         this.opts = opts;
      }

      public void run () {
         try {
            Socket socket = new Socket (opts.server_hostname,
                                        opts.server_portnumber);
            out.printf ("socket OK%n");
            Thread sender = new Thread (new sender (opts, 
               new PrintWriter (socket.getOutputStream ())));
            Thread receiver = new Thread (new receiver (opts,
               new Scanner (socket.getInputStream ())));
            sender.start ();
            receiver.start ();
         }catch (IOException exn) {
            System.err.println("IOException error");
            exit(1);
         }
      }
   }

   // Sends username first over to the server for processing.
   // Scans the clients stdin and sends it over the socket via
   // a PrintWriter object.
   static class sender implements Runnable {
      chatter.options opts;
      PrintWriter writer;
      Scanner scanner;
      sender (chatter.options opts, PrintWriter writer) {
         this.opts = opts;
         this.writer = writer;
      }

      public void run () {
         out.printf ("cli sender starting.. %n");
         scanner = new Scanner (System.in);
         writer.printf ("%s: %n", opts.username);
         writer.flush ();
         while (scanner.hasNextLine ()) {
            writer.printf ("%s%n", scanner.nextLine ());
            writer.flush ();
         }
         scanner.close ();
         writer.close ();
         out.printf ("cli sender ending.. %n");
      }
   }

   // Accepts messages from the server. 
   static class receiver implements Runnable {
      chatter.options opts;
      Scanner scanner;
      receiver (chatter.options opts, Scanner scanner) {
         this.opts = opts;
         this.scanner = scanner;
      }

      public void run () {
         out.printf ("cli receiver starting..%n");
         while (scanner.hasNextLine ()) {
            out.printf ("%s%n", scanner.nextLine ());
         }   

         scanner.close ();
         out.printf ("cli receiver ending..%n");
      }
   }
}
