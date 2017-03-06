package arm.davsoft.staffmanager.utils;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.domain.UploadFile;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 7/22/15 <br/>
 * <b>Time:</b> 1:42 AM <br/>
 */
public class Utils {

    public static String concatStrings(Collection<String> strings) {
        return concatObjects(strings, ", ");
    }

    public static <T> String concatObjects(Collection<T> strings, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        if (strings != null) {
            Iterator<T> iterator = strings.iterator();
            while (iterator.hasNext()) {
                T item = iterator.next();
                if (item != null) {
                    stringBuilder.append(item);
                    if (iterator.hasNext()) {
                        stringBuilder.append(delimiter);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String joinIntegers(Collection<Integer> items) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Integer> integerIterator = items.iterator();
        while (integerIterator.hasNext()) {
            Integer item = integerIterator.next();
            stringBuilder.append(item);
            if (integerIterator.hasNext()) {
                stringBuilder.append(',');
            }
        }
        return stringBuilder.toString();
    }

    public static String toJsonArray(List<String> items) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int itemsCount = items.size();
        for (int i = 0; i < itemsCount - 1; i++) {
            stringBuilder.append("\"").append(items.get(i)).append("\",");
        }
        stringBuilder.append("\"").append(items.get(itemsCount - 1)).append("\"");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static File byteArrayToFile(byte[] bytes, String fileName) {
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("temp_", fileName, ResourceManager.getAppTempDir());
            tmpFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tmpFile);
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpFile;
    }

    public static byte[] fileToByteArray(File file) {
        try {
            return inputStreamToByteArray(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] inputStreamToByteArray(InputStream is) {
        ByteArrayOutputStream bos = null;
        try {
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = is.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    public static void showAttachment(UploadFile attachment) {
        Thread t = new Thread(() -> {
            try {
                Desktop.getDesktop().open(Utils.byteArrayToFile(attachment.getBytes(), attachment.getName()));
            } catch (IOException e) {
                Main.LOGGER.error("Error occurred while showing the attachment.", e);
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
