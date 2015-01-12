// $Id: huffman.java,v 1.113 2013-05-11 15:02:07-07 - - $

import java.io.*;
import static java.lang.System.*;

class huffman {
   private static final String STDIN_NAME = "-";
   private static final String VISFMT = "%6d  %c  %s%n";
   private static final String HEXFMT = "%6d x%02X %s%n";

   //
   // Open a file by filename or use stdin.  Read bytes and
   // count frequencies.  Close the file if it was opened.
   // Return frequency array.
   // THROWS IOException if the file can not be opened or if
   // read throws an error.
   private static int[] read_frequencies (String filename)
                        throws IOException {
      boolean is_open_file = ! filename.equals (STDIN_NAME);
      InputStream stream = is_open_file
                         ? new FileInputStream (filename)
                         : System.in;
      etclib.debug ("%s: open: %s%n", filename, stream);
      int[] frequencies = new int [codetree.UBYTE_RANGE];
      for (;;) {
         int ubyte = stream.read();
         if (ubyte < 0) break;
         ++frequencies[ubyte];
      }
      if (is_open_file) stream.close();
        return frequencies;
   }

   //
   // Make a decoding tree from the frequencies.  Make an encoding
   // array from that.  Print the encoding array for non-null entries.
   //
   private static void print_encodings (int[] frequencies) {
     codetree tree = codetree.make_codetree(frequencies);
     String[] htree = tree.make_encoding();
     for(int i = 0; i < htree.length; i++){
       if(htree[i] != null){ 
         System.out.println((frequencies[i]<10?"  " + 
                             frequencies[i]:frequencies[i]<100?" " + 
                             frequencies[i]: frequencies[i]) + " " + 
                             (i==10?"x0A":i==32?"x20":" "+
                             (char)i + " ") + 
                             " " + htree[i]);
       }else{
          System.out.print("");
        }
     }
   }
   //
   // Read frequencies from stdin or a file and print encodings.
   // Error out if there is a problem.
   //
   private static void process_file (String filename) {
      try {
        int[] frequencies = read_frequencies (filename);
        out.printf ("%s:%n", filename);
        print_encodings(frequencies);
      }catch (IOException error) {
         out.printf ("%s: %s: %s%n", etclib.program_name(),
                     filename, error.getMessage());
         return;
       }
   }


   //
   // Process each file in turn or stdin, as needed.
   //
   public static void main (String[] args) {
      int firstarg = 0;
      if (args.length > 0 && args[0].equals ("-@")) {
         etclib.debug = true;
         ++firstarg;
      }
      if (args.length == firstarg) {
         process_file (STDIN_NAME);
      }else {
         for (int argi = firstarg; argi < args.length; ++argi) {
            process_file (args[argi]);
         }
      }
   }

}

