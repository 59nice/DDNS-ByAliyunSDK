package cloud.thanos.ddns.service.impl;

import cloud.thanos.ddns.common.utils.AliDnsUtils;
import cloud.thanos.ddns.object.Domain;
import cloud.thanos.ddns.object.DomainImpl;
import cloud.thanos.ddns.service.DomainService;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leanderli
 * @see
 * @since 2020.04.09
 */
@Service
public class DomainServiceImpl implements DomainService {

    @Override
    public ArrayList<Domain> getRequiresUpdateDomainRecordByResolveRule(String rootDomain, ArrayList<String> subDomainFilters) {
        List<DescribeDomainRecordsResponse.Record> domainRecords = AliDnsUtils.getDomainRecords(rootDomain);
        if (CollectionUtils.isEmpty(domainRecords)) {
            return null;
        }
        ArrayList<Domain> domains = new ArrayList<>();
        domainRecords.forEach(domainRecord -> {
            String rr = domainRecord.getRR();
            String domainName = domainRecord.getDomainName();
            if (subDomainFilters.contains(rr) && rootDomain.equals(domainName)) {
                Domain domain = new DomainImpl(
                        domainRecord.getRecordId(),
                        domainName,
                        domainRecord.getType(),
                        rr,
                        domainRecord.getLine(),
                        domainRecord.getValue(),
                        domainRecord.getTTL()
                );
                domains.add(domain);
            }
        });
        return domains;
    }

    @Override
    public void updateDomainRecord(Domain domain) {
        if (null != domain) {
            domain.save();
        }
    }
}
