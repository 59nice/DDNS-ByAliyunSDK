package cloud.thanos.ddns.common.utils;

import cloud.thanos.ddns.config.DnsServerConfiguration;
import cloud.thanos.ddns.object.Domain;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
public class AliDnsUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(AliDnsUtils.class);
    public static IAcsClient client = null;

    static {
        //必填固定值，必须为“cn-hanghou”
        String regionId = DnsServerConfiguration.regionId;
        // your accessKey
        String accessKeyId = DnsServerConfiguration.accessKeyId;
        // your accessSecret
        String accessKeySecret = DnsServerConfiguration.accessKeySecret;
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
    }

    public static List<DescribeDomainRecordsResponse.Record> getDomainRecords(String rootDomain) {
        try {
            DescribeDomainRecordsRequest domainRecordsRequest = new DescribeDomainRecordsRequest();
            domainRecordsRequest.setDomainName(rootDomain);
            DescribeDomainRecordsResponse domainRecordsResponse = client.getAcsResponse(domainRecordsRequest);
            return domainRecordsResponse.getDomainRecords();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新解析记录
     *
     * @param domain 域名对象
     */
    public static void updateResolveRecord(Domain domain) {
        try {
            UpdateDomainRecordRequest updateRequest = new UpdateDomainRecordRequest();
            // 初始化更新域名解析的类
            updateRequest.setType(domain.getType());
            // 设置新的 IP
            updateRequest.setValue(domain.getRecordValue());
            // 域名
            updateRequest.setRR(domain.getResolveRecord());
            // recordId
            updateRequest.setRecordId(domain.getId());
            client.getAcsResponse(updateRequest);
        } catch (ClientException e) {
            LOGGER.error("Error!Update resolve record failed,errorMsg="
                    + e.getErrMsg() + ","
                    + domain.toString());
        }
    }
}
