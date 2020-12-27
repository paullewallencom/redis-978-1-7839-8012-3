package redisch8;

public class SlaveConfig
{
    private String host;
    private int port;

    public SlaveConfig(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public String getName()
    {
        return host.concat("@").concat(new Integer(port).toString());
    }
}
