package org.redisch7.gossipserver.commandhandlers;

import java.util.Arrays;
import java.util.List;

import org.redisch7.gossipserver.datahandler.ConstUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.Token;

public class KillNodeCommandHandler extends AbstractCommandHandler {

	public KillNodeCommandHandler(String nodename) {
		super(nodename);
	}

	public CheckResult process(List<Token> tokenList) {
		CheckResult checkResult = new CheckResult();
		JedisUtil jedisUtil = new JedisUtil();
		List<Boolean> result = jedisUtil.doesExist(this.getNodename(),
				Arrays.asList(ConstUtil.registerationHolder,ConstUtil.shutdownHolder));

		if ((result.get(0)) && (result.get(1) == false)) {
			checkResult = jedisUtil.killNode(this.getNodename());
		} else {
			checkResult.setFalse("Kill node failed ");

		}

		return checkResult;
	}
}
