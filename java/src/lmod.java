
public class lmod {

    public static final String PROGRAM_NAME = "lmod";

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
                    " filename");
            Kernel.exit(1);
        }

        // give the command line argument a better name
        String path = argv[0];

        if(!File.fileExists(path)){
            System.err.println("This file doesn't exist");
            Kernel.exit(-1);
        }
        Stat stat = new Stat();
        int fd = Kernel.open(path, Kernel.O_RDONLY);
        Kernel.fstat(fd,stat);
        Kernel.close(fd);
        long i = Long.valueOf(Integer.toBinaryString(stat.getMode()));
        String s = "";
        s+=(i%10==1)?"x":"-";
        i/=10;
        s+=(i%10==1)?"w":"-";
        i/=10;
        s+=(i%10==1)?"r":"-";
        i/=10;
        s+=(i%10==1)?"x":"-";
        i/=10;
        s+=(i%10==1)?"w":"-";
        i/=10;
        s+=(i%10==1)?"r":"-";
        i/=10;
        s+=(i%10==1)?"x":"-";
        i/=10;
        s+=(i%10==1)?"w":"-";
        i/=10;
        s+=(i%10==1)?"r":"-";
        s+=((stat.getMode()&Kernel.S_IFMT)==Kernel.S_IFDIR)?"d":"-";
        String rights = (new StringBuilder(s)).reverse().toString();

        String[][] data = File.readSystemFile(File.SystemFile.PASSWD);
        String name = data[stat.getUid()][0];
        String[][] ndata = File.readSystemFile(File.SystemFile.GROUP);
        String group = ndata[stat.getGid()][0];

        String fina = rights+" "+name+" "+group;
        System.out.println(fina);

        // exit with success
        Kernel.exit(0);
    }

}
