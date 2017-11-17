public class genproj {

    public static void main(String[] args) throws Exception {
        //Runtime.getRuntime().exec("rm filesys.dat").waitFor();
        Runtime.getRuntime().exec("java mkfs filesys.dat 128 1024").waitFor();

        final String[] folders = {"/etc","/bin", "/home", "/root"};
        final String[] files = {"/etc/passwd","/etc/shadow"};

        for(String folder : folders){
            createFolder(folder);
        }
        for(String file : files){
            createFile(file);
        }
        System.out.println(File.fileToString("/etc/passwd"));
    }

    public static void createFolder(String path) throws Exception{
        Runtime.getRuntime().exec("java mkdir "+path).waitFor();
    }

    public static void createFile(String path) throws Exception{
        Process a = Runtime.getRuntime().exec("java tee "+path);
        a.getOutputStream().write(125);
        a.getOutputStream().close();
        a.getInputStream().close();
        a.waitFor();
    }

}
