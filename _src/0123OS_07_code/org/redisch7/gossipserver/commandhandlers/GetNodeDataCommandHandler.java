package org.redisch7.gossipserver.commandhandlers;

import java.util.Arrays;
import java.util.List;

import org.redisch7.gossipserver.datahandler.ConstUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.StringListToken;
import org.redisch7.gossipserver.util.commandparser.Token;

public class GetNodeDataCommandHandler extends AbstractCommandHandler {

	public GetNodeDataCommandHandler(String nodename) {
		super(nodename);
	}

	@Override
	public CheckResult process(List<Token> tokenList) {

		CheckResult checkResult = new CheckResult();

		StringListToken gettersstringListToken = (StringListToken) tokenList
				.get(1);
		StringListToken nodesstringListToken = (StringListToken) tokenList
				.get(5);
		List<String> nodeList = nodesstringListToken.getValueAsList();
		JedisUtil jedisUtil = new JedisUtil();
		for (String nodename : nodeList) {

			List<Boolean> result = jedisUtil.doesExist(nodename, Arrays.asList(
					ConstUtil.registerationHolder, ConstUtil.activationHolder,
					ConstUtil.passivationHolder, ConstUtil.shutdownHolder));

			if ((result.get(0) == true) && (result.get(1) == true)
					&& (result.get(2) == false)&& (result.get(3) == false)) {

				CheckResult chkresult = jedisUtil.getValuesFromNode(nodename,
						gettersstringListToken.getValueAsList());
				checkResult.setTrue()
						.appendReason("The results for " + nodename + " :")
						.appendReason(chkresult.getReason());
			} else {
				checkResult
						.appendReason("The node where the GET didn't work is as follows: ");
				checkResult
						.setFalse(
								"Activation Validation for " + nodename + " :")
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
		}
		return checkResult;

	}

}
