package redisch8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class ShardPoolConfig
{
    private static HashFunction hf = Hashing.md5();
    private Map<String, MasterConfig> masterConfigMap = new HashMap<String, MasterConfig>();
    private ConsistentHash<String> consistentHash;

    public ShardPoolConfig(List<MasterConfig> asList)
    {
        for ( MasterConfig masterConfig : asList )
        {
            masterConfigMap.put(masterConfig.getName(), masterConfig);
        }
        this.prepare();
    }

    private void prepare()
    {
        this.consistentHash = new ConsistentHash<>(hf, masterConfigMap.size(), masterConfigMap.keySet());
    }

    public ConnectionContext getWriteNode(String key)
    {
        String nodename = consistentHash.get(key);
        return new ConnectionContext().addNodeName(nodename).addNodeAddress(masterConfigMap.get(nodename).getAddress());
    }

    public ConnectionContext getReadNode(String key)
    {
        String mastername = consistentHash.get(key);
        MasterConfig masterConfig = masterConfigMap.get(mastername);
        String nodename = masterConfig.getNodeName(key);
        return new ConnectionContext().addNodeName(nodename).addNodeAddress(nodename);
    }
}
