package cloud.thanos.ddns.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 应用配置类
 *
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
public class ServiceProviderConfiguration {

    private static final String FILE_NAME = "service_provider/aliyun.properties";

    public static String regionId = "";
    public static String accessKeyId = "";
    public static String accessKeySecret = "";

    static {
        PropertiesConfiguration configuration = null;
        try {
            configuration = new PropertiesConfiguration(FILE_NAME);
            regionId = configuration.getString("regionId");
            accessKeyId = configuration.getString("accessKeyId");
            accessKeySecret = configuration.getString("accessKeySecret");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }


}
