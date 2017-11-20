
import java.util.Scanner;
public class passwd
{
  /**
   * The name of this program.  
   * This is the program name that is used 
   * when displaying error messages.
   */
  public static final String PROGRAM_NAME = "passwd" ;

  /**
   * The size of the buffer to be used for reading from the 
   * file.  A buffer of this size is filled before writing
   * to the output file.
   */
  public static final int BUF_SIZE = 4096 ;

  /**
   * Reads files and writes to standard output.
   * @exception java.lang.Exception if an exception is thrown
   * by an underlying operation
   */
  public static void main( String[] argv ) throws Exception
  {
    // initialize the file system simulator kernel
    Kernel.initialize() ;

    // display a helpful message if no arguments are given
    if( argv.length == 0 )
    {
      System.err.println( PROGRAM_NAME + ": usage: java " + PROGRAM_NAME + 
        " input-file ..." ) ;
      Kernel.exit( 1 ) ;
    }
    // give the command line argument a better name
      String name = argv[0] ;
    //read the file 
      String[][] data = File.readSystemFile(File.SystemFile.SHADOW);
      String opw="";
      String exist=checkUserExist(name);
      if(!exist) System.out.println("this user doesn't exist ");
      else {
        //ask user for his old password 
        Scanner sc = new Scanner(System.in);
        System.out.println("enter your old  password  :");
        opw = sc.nextLine();
        }
        boolean existopw=checkLineExist(File.SystemFile.SHADOW,data);//just to represente the fct that verify the existence of the line in the file to do !!!!!!
        if(existopw){
            String pw = "", pwc = "";
            Scanner s = new Scanner(System.in);
            System.out.println("enter your password  :");
            pw = s.next();
            System.out.println("confirm your password  :");
            pwc = s.next();
         if (!pw.equals(pwc)) System.out.println("please make sure you entered the same password");
         else{
          String[] newline = new String[9];
        newline[0] = name;
        newline[1] = pw;
        newline[2] = String.valueOf(0);
        newline[3] = String.valueOf(0);
        newline[4] = String.valueOf(0);
        newline[5] = String.valueOf(0);
        newline[6] = String.valueOf(0);
        newline[7] = String.valueOf(0);
        newline[8] = String.valueOf(0);
        String[][] newData = File.inflateArray(data, newline);
        File.writeSystemFile(File.SystemFile.SHADOW,newData);
        }
        }
        else break;    
    // exit with success if we read all the files without error
    Kernel.exit(0) ;
  }

}

