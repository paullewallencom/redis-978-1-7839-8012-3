package redisch8;

import vinoo.util.common.ReturnValue;

public class JedisConnection implements ReadProperties, WriteProperties
{
    private ConnectionContext context;

    public JedisConnection(ConnectionContext context)
    {
        this.context = context;
    }

    @Override
    public ReturnValue hset(String hmapname, String key)
    {
        ReturnValue checkResult = new ReturnValue();
        System.out.println("-" + context.getNodeAddress());
        return checkResult;
    }

    @Override
    public ReturnValue hget(String string)
    {
        ReturnValue checkResult = new ReturnValue();
        System.out.println("-" + context.getNodeAddress());
        return checkResult;
    }
}
