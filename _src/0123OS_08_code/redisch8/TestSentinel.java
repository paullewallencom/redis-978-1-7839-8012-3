package redisch8;

public class TestSentinel
{
    public static void main(String[] args)
    {
        TestSentinel testSentinel = new TestSentinel();
        testSentinel.evaluate();
    }

    private void evaluate()
    {
        System.out.println("-- start the test -------------------");
        this.writeToMaster();
        this.readFromMaster();
        this.readFromSlave();
        this.writeToSlave();
        this.stopMaster();
        this.sentinelKicks();
        this.readFromMaster();
        this.readFromSlave();
        this.writeToSlave();
        System.out.println("-- end of test -------------------");
    }

    private void sentinelKicks()
    {
        try
        {
            Thread.currentThread().sleep(9000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void stopMaster()
    {
    }

    private void writeToSlave()
    {
    }

    private void readFromSlave()
    {
    }

    private void readFromMaster()
    {
    }

    private void writeToMaster()
    {
    }
}
