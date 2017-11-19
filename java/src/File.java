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

    public static int countLines(int fd) throws Exception {
        byte[] ch = new byte[1];
        int rd = 0;
        int count = 0;
        do {
            rd = Kernel.read(fd, ch, 1);
            if (ch[0] == 0x0A)
                count++;
        } while (rd > 0);
        return count;
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
}
