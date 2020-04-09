package cloud.thanos.ddns.object;

/**
 * @author leanderli
 * @see
 * @since 2019/7/9
 */
public interface Domain {

    void save();

    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getType();

    void setType(String type);

    String getResolveRecord();

    void setResolveRecord(String resolveRecord);

    String getResolveLine();

    void setResolveLine(String resolveLine);

    String getRecordValue();

    void setRecordValue(String recordValue);

    Long getTtl();

    void setTtl(Long ttl);
}
