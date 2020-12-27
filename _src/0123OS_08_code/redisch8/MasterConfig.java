package redisch8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class MasterConfig
{
    private String host;
    private int port;
    private Map<String, SlaveConfig> slaveConfigMap = new HashMap<String, SlaveConfig>();
    private ConsistentHash<String> consistentHash;
    private String name;
    private static HashFunction hf = Hashing.md5();

    public MasterConfig(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    private void prepare()
    {
        this.consistentHash = new ConsistentHash<>(hf, slaveConfigMap.size(), slaveConfigMap.keySet());
    }

    public MasterConfig withSlaves(List<SlaveConfig> asList)
    {
        for ( SlaveConfig slaveConfig : asList )
        {
            slaveConfigMap.put(slaveConfig.getName(), slaveConfig);
        }
        this.prepare();
        return this;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return host.concat("@").concat(new Integer(port).toString());
    }

    public String getNodeName(String key)
    {
        return consistentHash.get(key);
    }

    public MasterConfig as(String string)
    {
        this.name = string;
        return this;
    }
}
