// $Id: etclib.java,v 1.2 2013-04-25 16:26:14-07 - - $

import static java.lang.System.*;

class etclib {
   public static final int EXIT_SUCCESS = 0;
   public static final int EXIT_FAILURE = 1;
   public static int exit_status = EXIT_SUCCESS;
   public static boolean debug = false;

   //
   // Extract the basename of the jar file containing
   // the Java program, which appears as the class path.
   //
   public static String program_name() {
      String jarname = getProperty ("java.class.path");
      return jarname.substring (jarname.lastIndexOf ("/") + 1);
   }

   //
   // Print a warning and set exit status to failure.
   //
   public static void warn (Object... args) {
      exit_status = EXIT_FAILURE;
      out.flush();
      err.printf ("%s", program_name());
      for (Object arg: args) err.printf (": %s", arg);
      err.printf ("%n");
      err.flush();
   }

   //
   // Print a warning and exit program.
   //
   public static void die (Object... args) {
      warn (args);
      exit (exit_status);
   }

   //
   // Print a usage message and exit program.
   //
   public static void usage (Object... args) {
      exit_status = EXIT_FAILURE;
      out.flush();
      err.printf ("Usage: %s", program_name());
      for (Object arg: args) err.printf (" %s", arg);
      err.printf ("%n");
      err.flush();
      exit (exit_status);
   }


   //
   // Print debugging message to stderr.  Usage is
   // misclib.debug (format, args) similar to printf.
   //
   public static void debug (String format, Object... args) {
      if (debug) {
         Thread thread = Thread.currentThread();
         StackTraceElement caller = thread.getStackTrace()[2];
         err.printf ("%nDEBUG: %s:%n", caller);
         err.printf (format, args);
      }
   }

   //
   // Print STUB message.
   //
   public static void STUB() {
      debug = true;
      Thread thread = Thread.currentThread();
      StackTraceElement caller = thread.getStackTrace()[2];
      err.printf ("%nDEBUG: %s:%n", caller);
      err.printf ("STUB: NOT IMPLEMENTED.%n");
   }

}
