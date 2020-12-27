package org.redisch7.gossipserver.shell;

import java.util.ArrayList;
import java.util.List;

public class CheckResult {
	private Boolean result = true;
	private final List<String> reason = new ArrayList<String>();
	private Boolean state = false;

	public CheckResult() {

	}

	public CheckResult setTrue() {
		this.state = true;
		return this;
	}

	public CheckResult setFalse(String reason) {
		this.state = true;
		this.result = false;
		this.reason.add(reason);
		return this;
	}

	public Boolean getResult() {
		if (state) {
			return result;
		} else {
			return null;
		}
	}

	public String getReason() {
		if (state) {
			StringBuffer reasonBuffer = new StringBuffer();
			for (int index = 0; index < reason.size(); index++) {
				reasonBuffer.append(reason.get(index));
			}
			return reasonBuffer.toString();
		} else {
			return "";
		}
	}

	public CheckResult appendReason(String reason) {
		this.reason.add(reason + "\n");
		return this;
	}
}
