import java.util.Scanner;
public class Passwd {

    public static final String PROGRAM_NAME = "Passwd" ;

    /**
     * The size of the buffer to be used for reading from the
     * file.  A buffer of this size is filled before writing
     * to the output file.
     */
    public static final int BUF_SIZE = 4096 ;

    /**
     * The file mode to use when creating the output file.
     */
    public static final short OUTPUT_MODE = 0700 ;

    /**
     * Copies standard input to standard output and to a file.
     * @exception java.lang.Exception if an exception is thrown
     * by an underlying operation
     */
    public static void main( String[] argv ) throws Exception
    {
        // initialize the file system simulator kernel
        Kernel.initialize() ;

        // print a helpful message if the number of arguments is not correct
        if( argv.length != 1 )
        {
            System.err.println( PROGRAM_NAME + ": usage: java " + PROGRAM_NAME +
                    " output-file" ) ;
            Kernel.exit( 1 ) ;
        }

        // give the command line argument a better name
        String name = argv[0] ;

        //ask user for his old password
        String opw="";
        Scanner sc = new Scanner(System.in);
        System.out.println("enter your old  password  :");
        opw = sc.next();
        //open the file /etc/shadow
        int out_fd = Kernel.open( "/etc/shadow",OUTPUT_MODE) ;
        if( out_fd < 0 )
        {
            Kernel.perror( PROGRAM_NAME ) ;
            System.err.println( PROGRAM_NAME +
                    ": error during write to output file" ) ;
            Kernel.exit( 2 ) ;
        }

        //read the file in a table of string
        int rd;
        boolean exist=false;
        do {
            byte[] ch = new byte[1];
            String line = "";
            do {
                rd =Kernel.read(out_fd, ch, 1);
                String str = new String(ch);
                line += str;
            } while (ch[0] != 0x3A);
            String[] parts = line.split(":", 7);
            String user = parts[0];
            String password = parts[1];
            if(password==opw && user==name){ exist=true; break;}//as we find the user we  do  break
        }while(rd>0);
        if(exist) {
            String pw = "", pwc = "";
            Scanner s = new Scanner(System.in);
            System.out.println("enter your password  :");
            pw = s.next();
            System.out.println("confirm your password  :");
            pwc = s.next();
            if (!pw.equals(pwc)) System.out.println("please make sure you entered the same password");
            else{
                //when we did the break was the last position in the file!!!!!!!!!!!!
                Kernel.lseek(out_fd,0,1);
                String line =name+":x:0:0:0:0::";
                String newline =name+":"+pw+":0:0:0:0::";
                int wr_count = Kernel.write(out_fd, line.replace(line,newline).getBytes(), newline.getBytes().length);
                if (wr_count <= 0) {
                    Kernel.perror(PROGRAM_NAME);
                    System.err.println(PROGRAM_NAME +
                            ": error during write to output file");
                    Kernel.exit(3);
                }
                System.out.println("your password is successfully modified");}
        }
        else  System.out.println("this user doesn't exist or the password is not correct");

        // close the output file
        Kernel.close(out_fd);

         /* exit with success */
        Kernel.exit(0);
    }

}



