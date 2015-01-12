import static java.lang.System.*;
import static java.lang.Integer.*;

public final class stacktrace{
  public static final String PROGNAME = 
                basename (getProperty("java.class.path"));
  public static final int EXIT_SUCCESS = 0;
  public static int exitvalue = EXIT_SUCCESS;


  private stacktrace(){
    throw new UnsupportedOperationException();
  }

  public static String basename (String pathname){
    if(pathname == null || pathname.length() == 0) return ".";
    String[] paths = pathname.split("/");
    for(int index = paths.length - 1; index>=0; --index){
      if(paths[index].length() >0) return paths[index];
    }
    return "/";
  }


}
