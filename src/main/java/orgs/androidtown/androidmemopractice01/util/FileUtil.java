package orgs.androidtown.androidmemopractice01.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jisang on 2017-09-23.
 */

public class FileUtil {

    public static String read(Context context, String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            fis = context.openFileInput(filename);
            bis = new BufferedInputStream(fis);
            byte buffer[] = new byte[1024];

            int count = 0;
            while ((count = bis.read(buffer)) != -1) {
                String data = new String(buffer, 0, count);
                sb.append(data);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            try {
                if(fis != null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    public static void write(Context context, String filename, String content) throws IOException{
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, MODE_PRIVATE);
            fos.write(content.getBytes());
        } catch (Exception e) {
            throw e;
        } finally {
            if(fos != null)
            try {
                {
                    fos.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}

