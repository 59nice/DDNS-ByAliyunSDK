package cloud.thanos.ddns.schedule;

import cloud.thanos.ddns.common.AliDnsUtils;
import cloud.thanos.ddns.common.AppConfiguration;
import cloud.thanos.ddns.common.HttpClientUtil;
import cloud.thanos.ddns.domain.Domain;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 域名动态更新任务
 *
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
@Component
public class DDNSSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DDNSSchedule.class);

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void execute() {
        LOGGER.info("开始执行动态域名解析更新任务..." + new Date());
        // 获取当前机器的外网IP
        String currentIp = getCurrentIp();
        // 根据配置的根域名查询出所有需要更新的子域名(符合IPV4的子域名都需要更新)
        List<Domain> domains = listAllSubDomainNeedsUpdatedByRootDomain(AppConfiguration.rootDomain, currentIp);
        LOGGER.info("待更新的记录数：" + domains.size());
        // 更新记录
        if (domains.size() > 0) {
            domains.forEach(domain -> {
                boolean result = domain.save();
                LOGGER.info("更新记录：result(" + result + ")," + domain.toString());
            });
        }
        LOGGER.info("此次任务执行完成...");
    }

    /**
     * 获取所有需要更新的子级记录
     *
     * @param rootDomain 根域名
     * @param currentIp  当前IP
     * @return 需要更新的子级记录集合
     */
    private List<Domain> listAllSubDomainNeedsUpdatedByRootDomain(String rootDomain, String currentIp) {
        DescribeDomainRecordsRequest domainRecordsRequest = new DescribeDomainRecordsRequest();
        domainRecordsRequest.setDomainName(rootDomain);
        try {
            DescribeDomainRecordsResponse domainRecordsResponse = AliDnsUtils.client.getAcsResponse(domainRecordsRequest);
            List<DescribeDomainRecordsResponse.Record> records = domainRecordsResponse.getDomainRecords();
            List<Domain> domains = new ArrayList<>();
            if (null != records && records.size() > 0) {
                records.forEach(record -> {
                    String recordValue = record.getValue();
                    String recordIpValue = getIpAfterJudgment(recordValue);
                    boolean recordValueIsIp = StringUtils.isNotBlank(recordIpValue) && recordValue.equals(recordIpValue);
                    if (!recordValueIsIp) {
                        return;
                    }
                    boolean isSameIp = recordIpValue.equals(currentIp);
                    if (isSameIp) {
                        return;
                    }
                    Domain domain = new Domain();
                    domain.setId(record.getRecordId());
                    domain.setName(rootDomain);
                    domain.setRecordValue(currentIp);
                    domain.setResolveLine(record.getLine());
                    domain.setResolveRecord(record.getRR());
                    domain.setTtl(record.getTTL());
                    domain.setType(record.getType());
                    domains.add(domain);
                    LOGGER.info("需要更新的记录:" + domain.toString());
                });
            }
            return domains;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取到当前IP
     *
     * @return IP
     */
    private String getCurrentIp() {
        String response = HttpClientUtil.doGet(AppConfiguration.ipQueryServer);
        String ip = "";
        if (StringUtils.isNotBlank(response)) {
            ip = getIpAfterJudgment(response);
        }
        LOGGER.info("当前IP:" + ip);
        return ip;
    }

    /**
     * 传入一个字符串获取到其中对应的IP地址
     *
     * @param data 字符串
     * @return 正则匹配的ID
     */
    private String getIpAfterJudgment(String data) {
        Pattern pattern = compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)");
        Matcher m = pattern.matcher(data);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

}
