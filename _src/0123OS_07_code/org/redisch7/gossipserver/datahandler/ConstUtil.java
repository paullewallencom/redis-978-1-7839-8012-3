package org.redisch7.gossipserver.datahandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface ConstUtil {
	public String registerationHolder = "REGISTERATION-HOLDER";
	public String activationHolder = "ACTIVATION-HOLDER";
	public String passivationHolder = "PASSIVATION-HOLDER";
	public String shutdownHolder = "SHUTDOWN-HOLDER";
	public String config = "@configstore";
	public String messagebox = "@messagebox";

	public String createtime = "createtime";
	public String lastupdate = "lastupdatetime";
	public DateUtil time = new DateUtil();
	public Util helper = new Util();
	public SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

	public class DateUtil {

		public String getCurrentTime() {
			return simpleDateFormat.format(new Date());
		}

	}

	public class Util {

		public String getConfigStoreName(String nodename) {
			return nodename.concat(config);
		}
	}

}
