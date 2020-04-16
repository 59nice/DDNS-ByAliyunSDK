package cloud.thanos.ddns.schedule;

import cloud.thanos.ddns.common.utils.NetworkUtils;
import cloud.thanos.ddns.common.utils.RemoteShellUtils;
import cloud.thanos.ddns.config.DomainResolveRules;
import cloud.thanos.ddns.object.Domain;
import cloud.thanos.ddns.service.DomainService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

import static cloud.thanos.ddns.config.DomainResolveRules.getRootDomains;

/**
 * @author leanderli
 * @see
 * @since 2020.04.09
 */
@Component
public class IpChangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpChangeListener.class);
    private static String currentIP;

    @Autowired
    private DomainService service;

    @Scheduled(cron = "0/30 * * * * ? ")
    public void execute() {
        LOGGER.info("IpChangeListener is time to execute");
        if (isIpChanged()) {
            updateDomainRecords();
        }
    }

    private void updateDomainRecords() {
        LOGGER.info("Ip was changed and start to update domain records,current v4 ip[" + currentIP + "]");
        ArrayList<String> rootDomains = getRootDomains();
        if (CollectionUtils.isEmpty(rootDomains)) {
            return;
        }
        rootDomains.forEach(rootDomain -> {
            LOGGER.info("Update [" + rootDomain + "] records start");
            ArrayList<Domain> domains = service
                    .getRequiresUpdateDomainRecordByResolveRule(rootDomain,
                            DomainResolveRules.getResolveSubDomains(rootDomain));
            if (CollectionUtils.isEmpty(domains)) {
                return;
            }
            domains.forEach(domain -> {
                if (domain.getRecordValue().equals(currentIP)) {
                    return;
                }
                domain.setRecordValue(currentIP);
                LOGGER.info("Update domain record:" + domain.toString());
                service.updateDomainRecord(domain);
            });
        });
        LOGGER.info("Current update task execute success.\n");
    }

    private boolean isIpChanged() {
        if (StringUtils.isBlank(currentIP)) {
            currentIP = NetworkUtils.getV4Ip();
            return true;
        } else {
            String newIp = NetworkUtils.getV4Ip();
            if (!currentIP.equals(newIp)) {
                currentIP = newIp;
                return true;
            }
        }
        return false;
    }
}
