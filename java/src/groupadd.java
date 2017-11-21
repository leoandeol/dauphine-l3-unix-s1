
public class groupadd {

    public static final String PROGRAM_NAME = "groupadd";

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
                    " username");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String name = argv[0];  //nom de groupe

        String[][] data = File.readSystemFile(File.SystemFile.GROUP);
        if(File.checkGroupExist(name)){
            System.err.println("This group already exists");
            Kernel.exit(-1);
        }
        String[] newline = {name,"",String.valueOf(File.countLines(File.SystemFile.GROUP))," "};
        String[][] newdata = File.inflateArray(data, newline);
        File.writeSystemFile(File.SystemFile.GROUP, newdata);

        // exit with success
        Kernel.exit(0);
    }

}
