
public class chmod {

    public static final String PROGRAM_NAME = "chmod";

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
                    " mode file");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String mode = argv[0];
        String path = argv[1];
        Integer i = Integer.parseInt(mode, 8);

        if(!File.fileExists(path)){
            System.err.println("This file doesn't exist");
            Kernel.exit(-1);
        }
        Stat stat = new Stat();
        Kernel.stat(path, stat);
        String bin = Integer.toBinaryString(i);
        String s = "";
        s+=((stat.getMode()&Kernel.S_IFMT)==Kernel.S_IFDIR)?"1":"";
        s+=bin;
        short fina = Short.parseShort(s, 2);
        Kernel.chmod(path, fina);

        // exit with success
        Kernel.exit(0);
    }

}
