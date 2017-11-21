public class genproj {

    public static void main(String[] args) throws Exception {
        //Runtime.getRuntime().exec("rm filesys.dat").waitFor();
        Runtime.getRuntime().exec("java mkfs filesys.dat 128 1024").waitFor();

        final String[] folders = {"/etc","/bin", "/home"};
        final String[] files = {"/etc/passwd","/etc/shadow","/etc/group", "/bin/bash"};

        for(String folder : folders){
            File.createFolder(folder);
        }
        for(String file : files){
            File.createFile(file);
        }

        Runtime.getRuntime().exec("java useradd root");
    }

}
