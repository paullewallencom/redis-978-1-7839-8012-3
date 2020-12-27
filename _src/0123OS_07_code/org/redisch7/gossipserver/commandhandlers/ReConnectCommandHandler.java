package org.redisch7.gossipserver.commandhandlers;

import java.util.Arrays;
import java.util.List;

import org.redisch7.gossipserver.datahandler.ConstUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.Token;

public class ReConnectCommandHandler extends AbstractCommandHandler {

	public ReConnectCommandHandler(String nodename) {
		super(nodename);
	}

	@Override
	public CheckResult process(List<Token> tokenList) {
		CheckResult checkResult = new CheckResult();
		JedisUtil jedisUtil = new JedisUtil();
		List<Boolean> result = jedisUtil.doesExist(this.getNodename(), Arrays
				.asList(ConstUtil.registerationHolder,
						ConstUtil.activationHolder,
						ConstUtil.passivationHolder, ConstUtil.shutdownHolder));

		if ((result.get(0) == true)
				&& ((result.get(1) == false) || (result.get(2) == false))
				&& (result.get(3) == true)) {

			checkResult = jedisUtil.reconnectNode(this.getNodename());
		} else {
			checkResult
					.setFalse("Reconnect Failed :")
					.appendReason(
							ConstUtil.registerationHolder + " = "
									+ (result.get(0)))
					.appendReason(
							ConstUtil.activationHolder + " = "
									+ (result.get(1)))
					.appendReason(
							ConstUtil.passivationHolder + " = "
									+ (result.get(2)));
		}

		return checkResult;
	}

}
