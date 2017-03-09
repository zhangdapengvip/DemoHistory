package com.zero.library.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import android.content.Context;
import android.util.Log;

public class UtilsZip {
    /**
     * unzip a input byte buffer
     *
     * @param buf
     * @return
     */
    public static byte[] unzip(byte[] buf) {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(buf, 0, buf.length);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] result = new byte[1024];
            int size = -1;
            while (size != 0) {
                size = inflater.inflate(result, 0, result.length);
                output.write(result, 0, size);
            }

            inflater.end();

            result = output.toByteArray();

            return result;
        } catch (DataFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zip a input byte buffer.
     *
     * @param input
     * @return
     */
    public static byte[] zip(byte[] input) {
        try {
            Deflater deflater = new Deflater();
            deflater.setInput(input);
            deflater.finish();

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            while (!deflater.finished()) {
                int byteCount = deflater.deflate(buf);
                output.write(buf, 0, byteCount);
            }
            deflater.end();

            return output.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void zipFiles(ZipOutputStream zos, String path,
                                 File... srcFiles) {
        if (null != path) {
            if (!path.endsWith(File.separator)) {
                path += File.separator;
            }
        } else {
            path = "";
        }
        byte[] buf = new byte[1024];
        try {
            for (File file : srcFiles) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    String srcPath = file.getName();
                    if (!srcPath.endsWith(File.separator)) {
                        srcPath += File.separator;
                    }
                    zipFiles(zos, path + srcPath, files);
                } else {
                    FileInputStream fis = new FileInputStream(file);
                    zos.putNextEntry(new ZipEntry(path + file.getName()));
                    int len;
                    while ((len = fis.read(buf)) > 0) {
                        zos.write(buf, 0, len);
                    }
                    zos.closeEntry();
                    fis.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * zip the file/folder
     *
     * @param zip      the zipped file, this file should be created before call this
     *                 method.
     * @param path     the top path of the zipped file, can be null
     * @param srcFiles the files want to be zipped
     * @throws IOException
     */
    public static void zipFiles(File zip, String path, File... srcFiles)
            throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
        zipFiles(zos, path, srcFiles);
        zos.close();
    }

    private static List<String> unZipFiles(File zipFile, String dstDir)
            throws IOException {
        File pathFile = new File(dstDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        List<String> paths = new ArrayList<String>();
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry ze = entries.nextElement();
            String name = ze.getName();
            InputStream is = zip.getInputStream(ze);
            String outPath = dstDir + name;
            paths.add(outPath);
            File file = new File(outPath.substring(0,
                    outPath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }

            if (new File(outPath).isDirectory()) {
                continue;
            }

            OutputStream os = new FileOutputStream(outPath);
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            os.flush();
            is.close();
            os.close();
        }
        return paths;
    }

    public static String unZipFile(InputStream is, String target, String dstDir) {
        String path = null;
        if (!dstDir.endsWith(File.separator)) {
            dstDir += File.separator;
        }
        ZipInputStream zis = new ZipInputStream(is);
        try {
            File file = null;
            byte[] buffer = new byte[1024];
            int count = 0;
            ZipEntry entry = zis.getNextEntry();
            while (null != entry) {
                String name = entry.getName();
                if (target.equals(name)) {
                    if (entry.isDirectory()) {
                        file = new File(dstDir + name);
                        file.mkdirs();
                    } else {
                        file = new File(dstDir + name);
                        file.createNewFile();
                        FileOutputStream os = new FileOutputStream(file);
                        while ((count = zis.read(buffer)) > 0) {
                            os.write(buffer, 0, count);
                        }
                        os.flush();
                        os.close();
                        path = file.getAbsolutePath();
                        Log.d("Unzip from stream",
                                "unzip file:" + file.getAbsolutePath());
                        break;
                    }
                }
                entry = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != zis) {
                    zis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return path;
    }

    /**
     * Unzip the inputStream and close it
     *
     * @param is
     * @param dstDir
     * @return
     */
    public static List<String> unZipFiles(InputStream is, String dstDir) {
        List<String> files = new ArrayList<String>();
        if (!dstDir.endsWith(File.separator)) {
            dstDir += File.separator;
        }
        ZipInputStream zis = new ZipInputStream(is);
        try {
            File file = null;
            byte[] buffer = new byte[1024];
            int count = 0;
            ZipEntry entry = zis.getNextEntry();
            while (null != entry) {
                FileOutputStream os = null;
                try {
                    String name = entry.getName();
                    if (entry.isDirectory()) {
                        file = new File(dstDir + name);
                        file.mkdirs();
                    } else {
                        file = new File(dstDir + name);
                        file.createNewFile();
                        os = new FileOutputStream(file);
                        while ((count = zis.read(buffer)) > 0) {
                            os.write(buffer, 0, count);
                        }
                        os.flush();
                        os.close();
                        os = null;
                        Log.d("Unzip from stream",
                                "unzip file:" + file.getAbsolutePath());
                        files.add(file.getAbsolutePath());
                    }
                    entry = zis.getNextEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (os != null) {
                        os.close();
                        os = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != zis) {
                    zis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return files;
    }

    /**
     * Get the target file from the zip file
     *
     * @param zipFile
     * @param target
     * @param dstDir
     * @return
     * @throws IOException
     */
    public static String unZipFile(String zipFile, String target, String dstDir)
            throws IOException {
        String ret = null;
        if (!dstDir.endsWith(File.separator)) {
            dstDir += File.separator;
        }
        File pathFile = new File(dstDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry ze = entries.nextElement();
            String name = ze.getName();
            if (name.equals(target)) {
                InputStream is = zip.getInputStream(ze);
                String outPath = dstDir + name;
                File file = new File(outPath.substring(0,
                        outPath.lastIndexOf(File.separator)));
                if (!file.exists()) {
                    file.mkdirs();
                }

                if (new File(outPath).isDirectory()) {
                    continue;
                }

                OutputStream os = new FileOutputStream(outPath);
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    os.write(buf, 0, len);
                }
                os.flush();
                is.close();
                os.close();
                ret = outPath;
                break;
            }
        }

        return ret;
    }

    /**
     * unzip the file to the dstDir
     *
     * @param zip    the zipped file
     * @param dstDir the destination directory
     * @throws IOException
     */
    public static List<String> unZipFiles(String zip, String dstDir)
            throws IOException {
        if (!dstDir.endsWith(File.separator)) {
            dstDir += File.separator;
        }
        return unZipFiles(new File(zip), dstDir);
    }

    public static List<String> getZipFileList(Context context, String zipFile) {
        List<String> files = new ArrayList<String>();
        ZipInputStream zis = new ZipInputStream(UtilsFile.getFileInputStream(
                context, zipFile));
        ZipEntry entry;
        try {
            entry = zis.getNextEntry();
            while (null != entry) {
                files.add(entry.getName());
                entry = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != zis) {
                try {
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return files;
    }

    public static Map<String, Long> getZipFiles(Context context, String zipFile) {
        Map<String, Long> maps = new HashMap<String, Long>();
        ZipInputStream zis = new ZipInputStream(UtilsFile.getFileInputStream(
                context, zipFile));
        ZipEntry entry;
        try {
            entry = zis.getNextEntry();
            while (null != entry) {
                maps.put(entry.getName(), entry.getSize());
                entry = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != zis) {
                try {
                    zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return maps;
    }
}
