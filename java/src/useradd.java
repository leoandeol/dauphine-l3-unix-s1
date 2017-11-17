
public class useradd {
    public static final String PROGRAM_NAME = "useradd";

    /**
     * The size of the buffer to be used for reading from the
     * file.  A buffer of this size is filled before writing
     * to the output file.
     */
    public static final int BUF_SIZE = 4096;

    /**
     * The file mode to use when creating the output file.
     */
    public static final short OUTPUT_MODE = 0700;

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
        String name = argv[0];

        // create the output file
        int out_fd = Kernel.open("/etc/passwd", OUTPUT_MODE);
        if (out_fd < 0) {
            Kernel.perror(PROGRAM_NAME);
            System.err.println(PROGRAM_NAME + ": unable to open output file /etc/passwd");
            Kernel.exit(2);
        }

        // on se positionne à la fin du fichier /etc/password
        Kernel.lseek(out_fd, 0, 2);
        int nb_lignes = File.countLines(out_fd);
        ///TODO groupe
        String newline = name + ":" + "x:" + nb_lignes + "::" + name + ":" + (name.equals("root") ? "/root" : "/home/" + name) + "/bin/bash\n";
        //TODO ajouter pass vide
        // on ajoute cette ligne
        int wr_count = Kernel.write(out_fd, newline.getBytes(), newline.getBytes().length);
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

}
