
public class chown {

    public static final String PROGRAM_NAME = "chown";

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
        if (argv.length != 2) {
            System.err.println(PROGRAM_NAME + ": usage: java " + PROGRAM_NAME +
                    " user path");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String user = argv[0];
        String path = argv[1];
        if(!File.fileExists(path)){
            System.err.println("This file doesn't exist");
            Kernel.exit(-1);
        }
        if(!File.checkUserExist(user)){
            System.err.println("This user doesn't exist");
            Kernel.exit(-2);
        }
        Kernel.chown(path,user);

        // exit with success
        Kernel.exit(0);
    }

}
