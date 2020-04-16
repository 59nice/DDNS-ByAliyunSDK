package cloud.thanos.ddns.config;

import cloud.thanos.ddns.common.utils.ResourceUtils;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author leanderli
 * @see
 * @since 2020.04.09
 */
public class DomainResolveRules {

    private static final String CONFIG_FILE = "domain_resolve_rules.json";

    private static ArrayList<String> rootDomains = new ArrayList<>();
    private static HashMap<String, ArrayList<String>> rules = new HashMap<>();

    static {
        String rulesJson = ResourceUtils.getStringContentFromFile(CONFIG_FILE);
        if (StringUtils.isNotBlank(rulesJson)) {
            JsonArray domains = new JsonParser().parse(rulesJson).getAsJsonObject().get("domain").getAsJsonArray();
            if (null != domains) {
                for (JsonElement domain : domains) {
                    if (domain.isJsonObject()) {
                        JsonObject object = domain.getAsJsonObject();
                        object.keySet().forEach((rootDomain) -> {
                            rootDomains.add(rootDomain);
                            JsonArray array = object.get(rootDomain).getAsJsonArray();
                            ArrayList<String> subDomains = new ArrayList<>();
                            array.forEach((value) -> {
                                subDomains.add(value.getAsString());
                            });
                            rules.put(rootDomain, subDomains);
                        });
                    }
                }
            }
        }
    }

    public static ArrayList<String> getRootDomains() {
        return rootDomains;
    }

    public static ArrayList<String> getResolveSubDomains(String rootDomain) {
        return rules.get(rootDomain);
    }


}
