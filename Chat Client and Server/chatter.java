import static java.lang.System.*;
class chatter {
  static class options {
    final String usage = " [-g] [-@flags] [hostname:]port username";
    boolean is_server = true; 
    String server_hostname;
    int server_portnumber;
    String username;

    options (String[] args) {
      try {
        if (args.length < 1 || args.length > 4)  
          throw new NumberFormatException ();
            for (String arg: args) {
              if (arg.contains (":")) {
                String[] hostnm_prt = arg.split (":", 2);
                server_hostname = hostnm_prt[0];
                server_portnumber = Integer.parseInt (hostnm_prt[1]);
                is_server = false;
              }else {
                 if (is_server == false) break;
                   server_portnumber = Integer.parseInt (arg); 
                   is_server = true;
                 }
              }
              username = args [args.length - 1];
           }catch (NumberFormatException exn) {
           System.err.println(usage);
           exit(1);
         }
      }
   }
   public static void main (String[] args) {
      options opts = new options (args);

      if (opts.is_server == true) {
         Thread server = new Thread (new server.server_listener (opts));
         server.start ();
      }else if (opts.is_server == false) {
         Thread client = new Thread (new client.client_starter (opts));
         client.start ();
      }
   }
}
