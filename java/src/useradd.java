import java.time.Instant;
import java.util.Date;

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
        if(File.checkUserExist(name)){
            System.err.println(PROGRAM_NAME + ": user \""+name+"\" already exists");
            Kernel.exit(-1);
        }

        // /etc/group entry
        String[][] groupdata = File.readSystemFile(File.SystemFile.GROUP);
        String gid = String.valueOf(File.countLines(File.SystemFile.GROUP));
        String[] newgroupline = {name,"",gid,name};
        String[][] newgroupdata = File.inflateArray(groupdata, newgroupline);
        File.writeSystemFile(File.SystemFile.GROUP, newgroupdata);

        // /etc/passwd entry
        String[][] passwddata = File.readSystemFile(File.SystemFile.PASSWD);
        String uid = String.valueOf(File.countLines(File.SystemFile.PASSWD));
        String home = (name.equals("root") ? "/root" : "/home/" + name);

        String[] newpasswdline = {name,"x",uid,gid,name,home,"/bin/bash"};
        String[][] newpasswdata = File.inflateArray(passwddata, newpasswdline);
        File.writeSystemFile(File.SystemFile.PASSWD, newpasswdata);

        //new user folder
        File.createFolder(home);

        // /etc/shadow entry
        String[][] shadowdata = File.readSystemFile(File.SystemFile.SHADOW);
        String[] newshadowline = {name, "", Date.from(Instant.now()).toString(),"0","99999"};
        String[][] newshadowdata = File.inflateArray(shadowdata, newshadowline);
        File.writeSystemFile(File.SystemFile.SHADOW, newshadowdata);

        // exit with success
        Kernel.exit(0);
    }
}
