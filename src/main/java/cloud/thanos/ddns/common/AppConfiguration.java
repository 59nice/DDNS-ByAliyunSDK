package cloud.thanos.ddns.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 应用配置类
 *
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
public class AppConfiguration {

    private static final String fileName = "domain.properties";

    public static String ipQueryServer = "";
    public static String regionId = "";
    public static String accessKeyId = "";
    public static String accessKeySecret = "";
    public static String rootDomain = "";

    /**
     * 初始化加载配置
     */
    static {
        PropertiesConfiguration configuration = null;
        try {
            configuration = new PropertiesConfiguration(fileName);
            ipQueryServer = configuration.getString("ipQueryServer");
            regionId = configuration.getString("regionId");
            accessKeyId = configuration.getString("accessKeyId");
            accessKeySecret = configuration.getString("accessKeySecret");
            rootDomain = configuration.getString("rootDomain");

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }


}
