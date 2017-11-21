
public class adduser {
    /**
     * The name of this program.
     * This is the program name that is used
     * when displaying error messages.
     */
    public static final String PROGRAM_NAME = "adduser";

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
        if (argv.length < 2) {
            System.err.println(PROGRAM_NAME + ": usage: java " + PROGRAM_NAME +
                    " username group");
            Kernel.exit(1);
        }
        // give the command line argument a better name
        String name = argv[0];
        String group = argv[1];
        if (!File.checkUserExist(name)) {
            System.err.println("this user doesn't exist");
            Kernel.exit(-1);
        }
        if (!File.checkGroupExist(group)) {
            System.out.println("this group doesn't exit");
            Kernel.exit(-2);
        }
        String[][] data = File.readSystemFile(File.SystemFile.GROUP);
        int id = File.getLineNamedId(File.SystemFile.GROUP, group);
        if(data[id][3].contains(name)){
            System.err.println("this user is already in this group");
            Kernel.exit(-4);
        }
        if (data[id][3].equals(" "))
            data[id][3] = name;
        else
            data[id][3] += ("," + name);

        File.writeSystemFile(File.SystemFile.GROUP, data);

        Kernel.exit(0);
    }
}



























