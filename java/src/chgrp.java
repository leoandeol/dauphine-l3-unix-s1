
public class chgrp {

    public static final String PROGRAM_NAME = "chgrp";

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
                    " chgrp path");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String group = argv[0];
        String path = argv[1];
        if(!File.fileExists(path)){
            System.err.println("This file doesn't exist");
            Kernel.exit(-1);
        }
        if(!File.checkGroupExist(group)){
            System.err.println("This user doesn't exist");
            Kernel.exit(-2);
        }
        Kernel.chgrp(path,group);

        // exit with success
        Kernel.exit(0);
    }

}
