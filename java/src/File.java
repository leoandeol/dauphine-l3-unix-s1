import java.util.Arrays;

public class File {

    public enum SystemFile {
        PASSWD("/etc/passwd"),
        SHADOW("/etc/shadow"),
        GROUP("/etc/group");

        private String path;

        SystemFile(String p){
            this.path=p;
        }

        public String toString(){
            return this.path;
        }
    }

    public static String fileToString(String path) throws Exception {
        String s;
        int fd = Kernel.open(path, Kernel.O_RDONLY);
        if(fd<0){
            Kernel.perror("fileToString");
        }
        byte[] bytes = new byte[countBytes(fd)];
        Kernel.lseek(fd, 0, 0);
        Kernel.read(fd, bytes, bytes.length);
        s = new String(bytes, "UTF-8");
        Kernel.close(fd);
        return s;
    }

    public static void stringToFile(String path, String source) throws Exception {
        int fd = Kernel.creat(path, Kernel.S_IRWXU);
        if(fd<0){
            Kernel.perror("stringToFile");
        }
        byte[] bytes = source.getBytes();
        int nb = Kernel.write(fd, bytes, bytes.length);
        if(nb!=bytes.length){
            Kernel.perror("stringToFile");
        }
        Kernel.close(fd);
    }

    public static String[][] readSystemFile(SystemFile f) throws Exception {
        String s = fileToString(f.toString());
        String[] ss = s.split("\n");
        String[][] sss = new String[ss.length][];
        for(int i = 0; i < ss.length; i++){
            sss[i] = ss[i].split(":");
        }
        return sss;
    }

    public static void writeSystemFile(SystemFile f, String[][] s) throws Exception {
        String[] ss = new String[s.length];
        for(int i = 0; i < s.length; i++){
            ss[i] = "";
            for(int j = 0; j < s[i].length; j ++){
                ss[i]+=s[i][j];
                if(j<(s[i].length-1))
                    ss[i]+=":";
            }
        }
        String res = "";
        for(int i = 0; i < ss.length; i++){
            res += ss[i];
            if(i<(ss[i].length()-1))
                res+="\n";
        }
        stringToFile(f.toString(), res);
    }

    public static int countLines(SystemFile file) throws Exception {
        int fd= Kernel.open(file.toString(), Kernel.O_RDONLY);
        if(countBytes(fd)==0) return 0;
        Kernel.close(fd);
        String s = File.fileToString(file.toString());
        String[] tmp = s.split("\n");
        return tmp.length;
    }

    public static int countBytes(int fd) throws Exception {
        byte[] ch = new byte[1];
        int rd = 0;
        int count = 0;
        do {
            rd = Kernel.read(fd, ch, 1);
            count+=rd;
        } while (rd > 0);
        return count;
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static <T> T[] inflateArray(T[] first, T second) throws Exception{
        T[] result = Arrays.copyOf(first, first.length + 1);
        //TODO cloner l'element second
        result[result.length-1] = second;
        return result;
    }

    public static boolean checkUserExist(String user) throws Exception {
        String[][] s = File.readSystemFile(SystemFile.PASSWD);
        for(String[] v : s){
            if(v[0].equals(user))
                return true;
        }
        return false;
    }

    public static void createFolder(String path) throws Exception{
        Runtime.getRuntime().exec("java mkdir "+path).waitFor();
    }

    public static void createFile(String path) throws Exception{
        Process a = Runtime.getRuntime().exec("java tee "+path);
        a.getOutputStream().close();
        a.getInputStream().close();
        a.waitFor();
    }

    public static String[] getLineNamed(SystemFile file, String name) throws Exception{
        String[][] s = File.readSystemFile(file);
        for(String[] a : s){
            if(a[0].equals(name))
                return a;
        }
        return null;
    }

    public static int getLineNamedId(SystemFile file, String name) throws Exception{
        String[][] s = File.readSystemFile(file);
        for(int i = 0; i < s.length; i++){
            if(s[i][0].equals(name))
                return i;
        }
        return -1;
    }
}
