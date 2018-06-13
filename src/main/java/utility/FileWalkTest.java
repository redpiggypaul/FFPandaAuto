package utility;

/**
 * Created by appledev131 on 8/29/16.
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.io.Files;

public class FileWalkTest {

//        String folder = new String(System.getProperty("user.dir") + "/PageXML/And/");
static Collection<File> listFiles(File root){
    List<File> files = new ArrayList<File>();
    listFiles(files, root);
    return files;
}

    static void listFiles(List<File> files, File dir){
        File[] listFiles = dir.listFiles();
        for(File f: listFiles){
            if(f.isFile()){
                files.add(f);
            }else if(f.isDirectory()){
                listFiles(files, f);
            }
        }
    }


    static void run(Runnable task){
        long start = System.currentTimeMillis();
        task.run();
        System.out.printf("��ʱ %s ���롣\n", System.currentTimeMillis() - start);
    }

    public static void main(String[] args){
        String folder = new String(System.getProperty("user.dir") + "/PageXML/IOS/");
        final File dir = new File(folder);
        final Path path = Paths.get(folder);

        //listFiles()
        run(new Runnable(){
            public void run(){
                Collection<File> files = listFiles(dir);
                System.out.printf("ͨ�� listFiles() ������ %s ���ļ���", files.size());
            }
        });


        //plexus utils
        run(new Runnable(){
            public void run(){
                try{
                    List<File> files = org.codehaus.plexus.util.FileUtils.getFiles(dir, null, null);
                    System.out.printf("ͨ�� Plexus Utils ������ %s ���ļ���", files.size());
                }catch(IOException e){
                    //ignore
                }
            }
        });

        //guava
        run(new Runnable(){
            public void run(){
                int size = Files.fileTreeTraverser().breadthFirstTraversal(dir).filter(new Predicate<File>(){
                    public boolean apply(File input) {
                        return input.isFile();
                    }
                }).size();
                System.out.printf("ͨ�� Guava ������ %s ���ļ���", size);
            }
        });

        //commons io
        run(new Runnable(){
            public void run(){
                Collection<File> files = org.apache.commons.io.FileUtils.listFiles(dir, null, true);
                System.out.printf("ͨ�� Commons IO ������ %s ���ļ���", files.size());
            }
        });


        //java 7 nio.2
        run(new Runnable(){
            public void run(){
                final List<File> files = new ArrayList<File>();
                SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        files.add(file.toFile());
                        return super.visitFile(file, attrs);
                    }
                };
                try{
                    java.nio.file.Files.walkFileTree(path, finder);
                }catch(IOException e){
                    //ignore
                }
                System.out.printf("ͨ�� Java 7 NIO.2 ������ %s ���ļ���", files.size());
            }
        });
    }
}
