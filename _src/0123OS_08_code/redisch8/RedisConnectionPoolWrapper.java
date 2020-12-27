package redisch8;

public class RedisConnectionPoolWrapper
{
    private ShardPoolConfig shardPoolConfig;

    public RedisConnectionPoolWrapper(ShardPoolConfig shardConfig)
    {
        this.shardPoolConfig = shardConfig;
    }

    public ReadProperties getReadConnection(String key)
    {
        ConnectionContext context = shardPoolConfig.getReadNode(key);
        ReadProperties readProperties = new JedisConnection(context);
        return readProperties;
    }

    public WriteProperties getWriteConnection(String key)
    {
        ConnectionContext context = shardPoolConfig.getWriteNode(key);
        WriteProperties writeProperties = new JedisConnection(context);
        return writeProperties;
    }
}
