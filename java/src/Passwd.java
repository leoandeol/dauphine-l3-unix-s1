import java.util.Scanner;

import static java.util.Collections.replaceAll;

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

            //ask user for his new password
            String mp="",mpc="";
            do {
                Scanner sc = new Scanner(System.in);
                System.out.println("enter you password  :");
                mp = sc.next();
                System.out.println("confirm your password  :");
                mpc = sc.next();
            }while(!mp.equals(mpc));
            //open the file /etc/shadow
                int out_fd = Kernel.open( "/etc/shadow",OUTPUT_MODE) ;
                if( out_fd < 0 )
                {
                    Kernel.perror( PROGRAM_NAME ) ;
                    System.err.println( PROGRAM_NAME +
                            ": error during write to output file" ) ;
                    Kernel.exit( 2 ) ;
                }
                //upload the password of user
                int i = Kernel.searchword(out_fd,name);
                int count=0;
                if(i!=-1){
                    do {
                        int rd=0;
                        byte[] ch=new byte[0];
                        rd = Kernel.read(out_fd, ch, 1);
                        if(ch[0]==0x0A)
                            count++;
                    } while (i!=count);
                    Kernel.lseek(out_fd,0,1);
                    String line =name+"::0:0:0:0::";
                    String newline =name+":"+mp+":0:0:0:0::";
                    int wr_count = Kernel.write(out_fd, line.replace(line,newline).getBytes(), newline.getBytes().length);
                    if (wr_count <= 0) {
                        Kernel.perror(PROGRAM_NAME);
                        System.err.println(PROGRAM_NAME +
                                ": error during write to output file");
                        Kernel.exit(3);
                    }

                    // close the output file
                    Kernel.close(out_fd);

                    // exit with success
                    Kernel.exit(0);
                }

    }}


