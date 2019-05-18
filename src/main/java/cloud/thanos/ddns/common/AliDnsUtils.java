package cloud.thanos.ddns.common;

import cloud.thanos.ddns.domain.Domain;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
public class AliDnsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliDnsUtils.class);

    public static IAcsClient client = null;

    static {
        //必填固定值，必须为“cn-hanghou”
        String regionId = AppConfiguration.regionId;
        // your accessKey
        String accessKeyId = AppConfiguration.accessKeyId;
        // your accessSecret
        String accessKeySecret = AppConfiguration.accessKeySecret;
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
    }

    /**
     * 更新解析记录
     *
     * @param domain 域名对象
     * @return 是否成功
     */
    public static boolean updateResolveRecord(Domain domain) {
        UpdateDomainRecordRequest updateRequest = new UpdateDomainRecordRequest();
        // 初始化更新域名解析的类
        updateRequest.setType(domain.getType());
        // 设置新的 IP
        updateRequest.setValue(domain.getRecordValue());
        // 域名
        updateRequest.setRR(domain.getResolveRecord());
        // recordId
        updateRequest.setRecordId(domain.getId());
        try {
            UpdateDomainRecordResponse updateResponse = client.getAcsResponse(updateRequest);
            LOGGER.info("更新解析记录完成：recordId(" + updateResponse.getRecordId() + ")");
            return true;
        } catch (ClientException e) {
            LOGGER.error("更新解析记录失败..." + e);
            e.printStackTrace();
        }
        return false;
    }
}
