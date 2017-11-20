
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

public class passwd {
    /**
     * The name of this program.
     * This is the program name that is used
     * when displaying error messages.
     */
    public static final String PROGRAM_NAME = "passwd";

    /**
     * The size of the buffer to be used for reading from the
     * file.  A buffer of this size is filled before writing
     * to the output file.
     */
    public static final int BUF_SIZE = 4096;

    /**
     * Reads files and writes to standard output.
     *
     * @throws java.lang.Exception if an exception is thrown
     *                             by an underlying operation
     */
    public static void main(String[] argv) throws Exception {
        // initialize the file system simulator kernel
        Kernel.initialize();

        // display a helpful message if no arguments are given
        if (argv.length == 0) {
            System.err.println(PROGRAM_NAME + ": usage: java " + PROGRAM_NAME +
                    " username");
            Kernel.exit(1);
        }
        // give the command line argument a better name
        String name = argv[0];

        //read the file
        String[][] data = File.readSystemFile(File.SystemFile.SHADOW);
        if (!File.checkUserExist(name)) {
            System.out.println("this user doesn't exist ");
            Kernel.exit(-1);
        }
        //ask user for his old password
        Scanner sc = new Scanner(System.in);
        System.out.println("enter your old  password  :");
        String oldpass;
        oldpass = sc.nextLine();
        String realoldpass = File.getLineNamed(File.SystemFile.SHADOW, name)[1];
        if (!oldpass.equals(realoldpass)) {
            System.err.println("the password does not match with the existing one");
            Kernel.exit(-1);
        }
        String pw, pwc;
        Scanner s = new Scanner(System.in);
        System.out.println("enter your password  :");
        pw = s.next();
        System.out.println("confirm your password  :");
        pwc = s.next();
        if (!pw.equals(pwc)) {
            System.out.println("the passwords do not match");
            Kernel.exit(-3);
        }
        int line = File.getLineNamedId(File.SystemFile.SHADOW, name);
        data[line][1]=pw;
        data[line][2]=Date.from(Instant.now()).toString();
        File.writeSystemFile(File.SystemFile.SHADOW, data);
        Kernel.exit(0);
    }

}

