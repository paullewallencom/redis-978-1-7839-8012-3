package redisch8;

import java.util.HashMap;
import java.util.Map;

public class ConnectionContext
{
    Map<String, String> context = new HashMap<String, String>();

    public ConnectionContext addNodeName(String nodename)
    {
        context.put("nodename", nodename);
        return this;
    }

    public String getNodeName()
    {
        return context.get("nodename");
    }

    public String getNodeAddress()
    {
        return context.get("address");
    }

    public ConnectionContext addNodeAddress(String string)
    {
        context.put("address", string);
        return this;
    }
}
