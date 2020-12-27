package org.redisch7.gossipserver.commandhandlers;

import java.util.Arrays;
import java.util.List;

import org.redisch7.gossipserver.datahandler.ConstUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.StringListToken;
import org.redisch7.gossipserver.util.commandparser.Token;

public class SetNodeDataCommandHandler extends AbstractCommandHandler {

	public SetNodeDataCommandHandler(String nodename) {
		super(nodename);
	}

	@Override
	public CheckResult process(List<Token> tokenList) {
		MapListToken mapListToken = (MapListToken) tokenList.get(1);
		StringListToken stringListToken = (StringListToken) tokenList.get(5);
		List<String> nodeList = stringListToken.getValueAsList();

		CheckResult checkResult = new CheckResult();
		JedisUtil jedisUtil = new JedisUtil();
		for (String nodename : nodeList) {

			List<Boolean> result = jedisUtil.doesExist(nodename, Arrays.asList(
					ConstUtil.registerationHolder, ConstUtil.activationHolder,
					ConstUtil.passivationHolder, ConstUtil.shutdownHolder));

			if ((result.get(0) == true) && (result.get(1) == true)
					&& (result.get(2) == false)&& (result.get(3) == false)) {

				jedisUtil.setValuesInNode(nodename,
						mapListToken.getValueAsMap());
			} else {
				checkResult
						.appendReason("The node where the SET didn't work is as follows: ");
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
