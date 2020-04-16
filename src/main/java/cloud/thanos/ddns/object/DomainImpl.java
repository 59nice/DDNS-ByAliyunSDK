package cloud.thanos.ddns.object;

import cloud.thanos.ddns.common.utils.AliDnsUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 域名对象
 *
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
@Data
public class DomainImpl implements Domain {

    /**
     * ID
     */
    private String id;
    /**
     * 域名名
     */
    private String name;
    /**
     * 记录类型
     */
    private String type;
    /**
     * 主机记录
     */
    private String resolveRecord;
    /**
     * 解析线路
     */
    private String resolveLine;
    /**
     * 记录值
     */
    private String recordValue;
    /**
     * TTL
     */
    private Long ttl;

    public DomainImpl(String id, String name, String type,
                      String resolveRecord, String resolveLine, String recordValue,
                      Long ttl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.resolveRecord = resolveRecord;
        this.resolveLine = resolveLine;
        this.recordValue = recordValue;
        this.ttl = ttl;
    }

    /**
     * 保存
     *
     */
    @Override
    public void save() {
        if (StringUtils.isNotBlank(this.id)) {
            AliDnsUtils.updateResolveRecord(this);
        }
    }
}
