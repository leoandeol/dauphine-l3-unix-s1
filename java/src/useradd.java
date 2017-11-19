
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

        String[][] data = File.readSystemFile(File.SystemFile.PASSWD);
        ///TODO creer groupe
        //String newline = name + ":" + "x:" + nb_lignes + "::" + name + ":" + (name.equals("root") ? "/root" : "/home/" + name) + "/bin/bash\n";
        //TODO ajouter pass vide Date.from(Instant.now()).toString()
        ///TODO meilleur generateur d'ID
        String[] newline = new String[7];
        newline[0] = name;
        newline[1] = "x";
        newline[2] = String.valueOf(data.length);
        newline[3] = String.valueOf(data.length);
        newline[4] = name;
        newline[5] = (name.equals("root") ? "/root" : "/home/" + name);
        newline[6] = "/bin/bash";
        ///TODO creer dossier utilisateur

        String[][] newData = File.inflateArray(data, newline);

        // exit with success
        Kernel.exit(0);
    }

}
