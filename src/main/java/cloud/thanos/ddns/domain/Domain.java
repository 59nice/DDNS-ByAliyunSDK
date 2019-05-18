package cloud.thanos.ddns.domain;

import cloud.thanos.ddns.common.AliDnsUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 域名对象
 *
 * @author leanderli
 * @see
 * @since 2019/5/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domain {

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


    /**
     * 保存
     *
     * @return 是否成功
     */
    public boolean save() {
        if (StringUtils.isNotBlank(this.id)) {
            return AliDnsUtils.updateResolveRecord(this);
        }
        return false;
    }
}
