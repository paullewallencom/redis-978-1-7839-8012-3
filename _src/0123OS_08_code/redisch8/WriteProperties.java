package redisch8;

import vinoo.util.common.ReturnValue;

public interface WriteProperties
{
    ReturnValue hset(String hmapname, String key);
}
