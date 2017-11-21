
public class umask {

    public static final String PROGRAM_NAME = "umask";

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
                    " umask");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String umask = argv[0];
        int i = Integer.parseInt(umask);
        if(i<0||i>777){
            System.err.println("The new umask must be between 000 and 777");
            Kernel.exit(-1);
        }
        Kernel.umask(umask);

        // exit with success
        Kernel.exit(0);
    }

}
