/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milad
 */
public class jjFile {
    ///\:*?"<>|

    /**
     *
     * @param path is path of folder or file for delete
     * @return true if after act file or folder not exists
     */
    public static boolean delete(File path) {
        return delete(path.getAbsolutePath());
    }

    /**
     *
     * @param path is path of folder or file for delete
     * @return true if after act file or folder not exists
     */
    public static boolean delete(String path) {
        File file = new File(path);
        try {
            if (file.exists()) {
                if (file.canWrite()) {
                    if (file.isFile()) {
                        System.out.println(file.delete() ? "delete :" + file : "No Delete");
                    } else {
                        File[] files = file.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            if (files[i].isFile()) {
                                System.out.println(files[i].delete() ? "delete :" + files[i] : "No Delete");
                            } else {
                                System.out.println(jjFile.delete(files[i].getAbsolutePath()) ? "delete :" + files[i] : "No Delete");
                            }
                        }
                        System.out.println(file.delete() ? "delete :" + file : "No Delete");
                    }
                } else {
                    System.out.println("Is read only");
                }
            } else {
                System.out.println("Not Exists");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return !file.exists();
    }

   
    /**
     *
     * Create new empty file
     */
    public static void CreateEmptyFile(String path) {
        try {
            File f1 = new File(path);
            File fParent = f1.getParentFile();
            fParent.mkdirs();
            f1.createNewFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void doCopyDirectory(File srcPath, File dstPath) throws Exception {
        if (srcPath.isDirectory()) {
            if (!dstPath.exists()) {
                dstPath.mkdir();
            }
            String files[] = srcPath.list();
            for (int i = 0; i < files.length; i++) {
                doCopyDirectory(new File(srcPath, files[i]),
                        new File(dstPath, files[i]));
            }
        } else {
            if (!srcPath.exists()) {
                System.out.println("File or directory does not exist.");
                return;
            } else {
                InputStream in = new FileInputStream(srcPath);
                OutputStream out = new FileOutputStream(dstPath);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }
        }
        System.out.println("Directory copied.");
    }

    /**
     *
     * Copy File from srcFrom and past that in srcTo
     *
     */
    public static void doCopyFileByInputOutputStream(String srcFrom, String srcTo) throws Exception {
        InputStream fReaded = new FileInputStream(srcFrom);
        FileOutputStream fOutput = new FileOutputStream(srcTo);
        try {
            File fTo = new File(srcTo);
            fTo.getParentFile().mkdirs();
            fOutput = new FileOutputStream(srcTo);
            byte buffer[] = new byte[fReaded.available()];
            int n = fReaded.read(buffer);
            while (n != -1) {
                fOutput.write(buffer, 0, n);
                n = fReaded.read(buffer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fOutput.close();
                fReaded.close();
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
    }

    public static void doCopyFileByInputOutputStream(InputStream readedInputStream, String pathWriteTo) {
        FileOutputStream fOutput = null;
        try {
            fOutput = new FileOutputStream(pathWriteTo);
            File fTo = new File(pathWriteTo);
            fTo.getParentFile().mkdirs();
            fOutput = new FileOutputStream(pathWriteTo);
            byte buffer[] = new byte[readedInputStream.available()];
            int n = readedInputStream.read(buffer);
            while (n != -1) {
                fOutput.write(buffer, 0, n);
                n = readedInputStream.read(buffer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fOutput.close();
                readedInputStream.close();
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
    }

    /**
     *
     * Copy File from strFrom and past that in strTo if doRename be true and
     * srcFrom and srcTo be in same folder do Rename
     */
    public static void doCopyFileByChannel(String srcFrom, String srcTo, boolean doRename) {
        FileChannel fochan = null;
        FileChannel fIchan = null;
        FileInputStream fIn = null;
        FileOutputStream fout = null;
        try {
            if (srcFrom.toLowerCase().trim().equals(srcTo.toLowerCase().trim())) {
                return;
            }
            File fromFile = new File(srcFrom);
            File toFile = new File(srcTo);
            if (doRename) {
                if (fromFile.getParent().equalsIgnoreCase(toFile.getParent())) {
                    fromFile.renameTo(toFile);
                    return;
                }
            }
            if (!toFile.exists()) {
                toFile.getParentFile().mkdirs();
                long fsize;
                fout = new FileOutputStream(toFile);
                fochan = fout.getChannel();
                fIn = new FileInputStream(fromFile);
                fIchan = fIn.getChannel();
                fsize = fIchan.size();
                MappedByteBuffer mBuf = fIchan.map(FileChannel.MapMode.READ_ONLY, 0, fsize);
                fochan.write(mBuf);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        } finally {
            try {
                fochan.close();
                fIchan.close();
                fIn.close();
                fout.flush();
                fout.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
    }

    public static List<File> getAllFileList(String path) {
        return getAllFileList(new File(path), "", "");
    }

    public static List<File> getAllFileList(File path) {
        return getAllFileList(path, "", "");
    }

    public static List<File> getAllFileList(String path, String extention) {
        return getAllFileList(new File(path), extention, "");
    }

    public static List<File> getAllFileList(String path, String extention, String before) {
        return getAllFileList(new File(path), extention, before);
    }

    public static List<File> getAllFileList(File path, String extention, String before) {
        List<File> allFile = new ArrayList<File>();
        if (path.exists() && path.isDirectory()) {
            File[] listFiles = path.listFiles();
            boolean extentionIsEmpty = extention == null && extention.equals("");
            boolean beforeIsEmpty = before == null || before.equals("");
            if (extentionIsEmpty && beforeIsEmpty) {
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isFile()) {
                        allFile.add(listFiles[i]);
                    } else {
                        allFile.addAll(getAllFileList(listFiles[i], extention, before));
                    }
                }
            } else if (!extentionIsEmpty && beforeIsEmpty) {
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isFile() && listFiles[i].getAbsolutePath().toLowerCase().endsWith(extention.toLowerCase())) {
                        allFile.add(listFiles[i]);
                    } else {
                        allFile.addAll(getAllFileList(listFiles[i], extention, before));
                    }
                }
            } else if (extentionIsEmpty && !beforeIsEmpty) {
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isFile() && listFiles[i].getName().toLowerCase().startsWith(before.toLowerCase())) {
                        allFile.add(listFiles[i]);
                    } else {
                        allFile.addAll(getAllFileList(listFiles[i], extention, before));
                    }
                }
            } else {
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isFile()
                            && listFiles[i].getName().toLowerCase().endsWith(extention.toLowerCase())
                            && listFiles[i].getName().toLowerCase().startsWith(before.toLowerCase())) {
                        allFile.add(listFiles[i]);
                    } else {
                        allFile.addAll(getAllFileList(listFiles[i], extention, before));
                    }
                }
            }
        }
        return allFile;
    }

    public static List<File> getAllFolderList(String path) {
        return getAllFolderList(new File(path));
    }

    public static List<File> getAllFolderList(File path) {
        List<File> allFolder = new ArrayList<File>();
        if (path.exists() && path.isDirectory()) {
            File[] listFiles = path.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    allFolder.add(listFiles[i]);
                    allFolder.addAll(getAllFolderList(listFiles[i]));
                }
            }
        }
        return allFolder;
    }
}
