package cloud.thanos.ddns.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author leanderli
 * @see
 * @since 2020.04.09
 */
public class ResourceUtils {

    /**
     * 根据配置文件相对路径读取文件内容
     */
    public static String getStringContentFromFile(String fileRelativePath) {
        StringBuilder stringBuilder = null;
        try (
                InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(fileRelativePath);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
