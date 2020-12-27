package org.redisch7.gossipserver.commandhandlers;

import java.util.Arrays;
import java.util.List;

import org.redisch7.gossipserver.datahandler.ConstUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.StringListToken;
import org.redisch7.gossipserver.util.commandparser.Token;

public class DeleteCommandHandler extends AbstractCommandHandler {

	public DeleteCommandHandler(String nodename) {
		super(nodename);
	}

	public CheckResult process(List<Token> tokenList) {
		CheckResult checkResult = new CheckResult();
		JedisUtil jedisUtil = new JedisUtil();
		List<Boolean> result = jedisUtil
				.doesExist(this.getNodename(), Arrays
						.asList(ConstUtil.registerationHolder,
								ConstUtil.activationHolder,
								ConstUtil.passivationHolder, ConstUtil.shutdownHolder));

		if ((result.get(0) == true) && (result.get(1) == true)
				&& (result.get(2) == false)&& (result.get(3) == false)) {

			StringListToken stringList = (StringListToken) tokenList.get(1);
			checkResult = jedisUtil.deleteValuesFromNode(this.getNodename(),
					stringList.getValueAsList());
		} else {
			checkResult
					.setFalse("Activation Validation :")
					.appendReason(
							ConstUtil.registerationHolder + " = "
									+ ((Boolean) result.get(0)))
					.appendReason(
							ConstUtil.activationHolder + " = "
									+ ((Boolean) result.get(1)))
					.appendReason(
							ConstUtil.passivationHolder + " = "
									+ ((Boolean) result.get(2)));
		}

		return checkResult;
	}

}
