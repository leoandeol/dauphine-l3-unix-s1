
public class touch {

    public static final String PROGRAM_NAME = "touch";

    /**
     * Copies standard input to standard output and to a file.
     *
     * @throws java.lang.Exception if an exception is thrown
     *                             by an underlying operation
     */
    public static void main(String[] argv) throws Exception {

        // print a helpful message if the number of arguments is not correct
        if (argv.length != 1) {
            System.err.println(PROGRAM_NAME + ": usage: java " + PROGRAM_NAME +
                    " filename");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String file = argv[0];
        File.createFile(file);

    }

}
