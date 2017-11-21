import java.io.Console;
import java.util.Scanner;

public class switchuser {

    public static final String PROGRAM_NAME = "switchuser";

    /**
     * Copies standard input to standard output and to a file.
     *
     * @throws java.lang.Exception if an exception is thrown
     *                             by an underlying operation
     */
    public static void main(String[] argv) throws Exception {
        // initialize the file system simulator kernel
        Kernel.initialize();

        // print a helpful message if the number of arguments is not correct
        if (argv.length != 1) {
            System.err.println(PROGRAM_NAME + ": usage: java " + PROGRAM_NAME +
                    " user");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String user = argv[0];

        if(!File.checkUserExist(user)){
            System.err.println("This user doesn't exist");
            Kernel.exit(-2);
        }
        String pass = File.getLineNamed(File.SystemFile.SHADOW, user)[1];
        if(!pass.equals("")){
            System.out.println("password :");
            String s = (new Scanner(System.in)).next();
            if(!s.equals(pass)){
                System.err.println("wrong password");
                Kernel.exit(-1);
            }
        }
        Kernel.switchUser(user);

        // exit with success
        Kernel.exit(0);
    }

}
