package redisch8;

import java.util.Arrays;
import vinoo.util.common.ReturnValue;

public class TestFaultTolerence
{
    public static void main(String[] args)
    {
        TestFaultTolerence faultTolerence = new TestFaultTolerence();
        faultTolerence.executetest1();
    }

    private void executetest1()
    {
        MasterConfig master0 = new MasterConfig("localhost", 6379).as("m1").withSlaves(Arrays.asList(new SlaveConfig("localhost", 6378),
                                                                                                     new SlaveConfig("localhost", 6377)));
        MasterConfig master1 = new MasterConfig("localhost", 7379).as("m2").withSlaves(Arrays.asList(new SlaveConfig("localhost", 7378),
                                                                                                     new SlaveConfig("localhost", 7377)));
        ShardPoolConfig shardConfig = new ShardPoolConfig(Arrays.asList(master0, master1));
        RedisConnectionPoolWrapper redisClient = new RedisConnectionPoolWrapper(shardConfig);
        for ( int i = 0; i < 10; i++ )
        {
            ReturnValue checkResult0 = redisClient.getReadConnection("lokiprofile".concat(new Integer(i).toString())).hget("name");
            ReturnValue checkResult1 = redisClient.getWriteConnection("lokiprofile".concat(new Integer(i).toString())).hset("status",
                                                                                                                            "married");
            System.out.println("---------------------------------------------------------------------------------------------------");
        }
    }
}
