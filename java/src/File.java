public class File {

    public static String fileToString(String path) throws Exception {
        String s;
        int fd = Kernel.open(path, Kernel.O_RDONLY);
        if(fd<0){
            Kernel.perror("fileToString");
        }
        byte[] bytes = new byte[countBytes(fd)];
        Kernel.lseek(fd, 0, 0);
        Kernel.read(fd, bytes, bytes.length);
        s = bytes.toString();
        return s;
    }

    public static void stringToFile(String source, String path) throws Exception {

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
}
