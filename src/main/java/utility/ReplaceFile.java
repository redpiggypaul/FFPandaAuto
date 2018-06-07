package utility;

/**
 * Created by appledev131 on 8/29/16.
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

//操作查找文件的类
public class ReplaceFile {
    static int countFiles = 0;// 声明统计文件个数的变量
    static int countFolders = 0;// 声明统计文件夹的变量

    public static StringBuilder deleteSpace(String income) {
        StringBuilder result = new StringBuilder("");

        result.append(income.replaceAll(" +"," "));

        return result;

    }

    public static void main(String[] args) {// java程序的主入口处

        System.out.println(deleteSpace("ada 1asd   aisdjao  ||    isjf39  aisjd asd as"));

    }

    public static List<File> run4files(Path path) {
        final List<File> files = new ArrayList<File>();
        SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files.add(file.toFile());
                return super.visitFile(file, attrs);
            }
        };
        try {
            java.nio.file.Files.walkFileTree(path, finder);
        } catch (IOException e) {
            //ignore
        }
        System.out.printf("ͨ�� Java 7 NIO.2 ������ %s ���ļ���", files.size());
        return files;
    }
}