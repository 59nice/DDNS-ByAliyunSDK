package cloud.thanos.ddns.service;

import cloud.thanos.ddns.object.Domain;

import java.util.ArrayList;

/**
 * @author leanderli
 * @see
 * @since 2020.04.09
 */
public interface DomainService {

    /**
     * 根据配置的过滤规则查询获取所有需要更新的记录
     *
     * @param rootDomain       根域名
     * @param subDomainFilters 过滤规则
     * @return 域名信息
     */
    ArrayList<Domain> getRequiresUpdateDomainRecordByFilterRule(String rootDomain, ArrayList<String> subDomainFilters);

    /**
     * 更新域名信息
     *
     * @param domain 域名
     */
    void updateDomainRecord(Domain domain);
}
