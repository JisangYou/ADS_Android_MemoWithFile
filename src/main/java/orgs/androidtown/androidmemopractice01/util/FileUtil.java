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

    public static String read(Context context, String filename) throws IOException {// 전역으로 따로 class를 만들었으므로 어디서건 사용가능
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            fis = context.openFileInput(filename);
            // 버퍼를 달고
            bis = new BufferedInputStream(fis);
            // 한번에 읽어올 버퍼양을 설정
            byte buffer[] = new byte[1024];
            // 현재 읽은 양을 담는 변수설정
            int count = 0;
            while ((count = bis.read(buffer)) != -1) {
                String data = new String(buffer, 0, count); // public String(byte bytes[], int offset, int length), 즉 data란 변수의 정보를 설정하는 코드
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
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString(); // 이 메소드가 호출되면 다음과 같은 리턴타입을 돌려줌.
    }

    /**
     * 파일 쓰기 함수
     *
     * @param context
     * @param filename
     * @param content
     * @throws IOException
     */

    public static void write(Context context, String filename, String content) throws IOException {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, MODE_PRIVATE);
            fos.write(content.getBytes());
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null)
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

