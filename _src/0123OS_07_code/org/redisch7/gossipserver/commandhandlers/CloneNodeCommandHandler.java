package org.redisch7.gossipserver.commandhandlers;

import java.util.Arrays;
import java.util.List;

import org.redisch7.gossipserver.datahandler.ConstUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.Token;

public class CloneNodeCommandHandler extends AbstractCommandHandler {

	public CloneNodeCommandHandler(String nodename) {
		super(nodename);
	}

	public CheckResult process(List<Token> tokenList) {
		CheckResult checkResult = new CheckResult();
		MapListToken maptokens = (MapListToken) tokenList.get(1);
		String target = maptokens.getNValue("target");
		String source = maptokens.getNValue("source");

		JedisUtil jedisUtil = new JedisUtil();
		List<Boolean> target_validity_result = jedisUtil
				.doesExist(target, Arrays
						.asList(ConstUtil.registerationHolder,
								ConstUtil.activationHolder,
								ConstUtil.passivationHolder, ConstUtil.shutdownHolder));

		List<Boolean> source_validity_result = jedisUtil
				.doesExist(source, Arrays
						.asList(ConstUtil.registerationHolder,
								ConstUtil.activationHolder,
								ConstUtil.passivationHolder, ConstUtil.shutdownHolder));

		if ((target_validity_result.get(0) == true)
				&& (target_validity_result.get(1) == true)
				&& (target_validity_result.get(2) == false)&& (target_validity_result.get(3) == false)) {
			if (((Boolean) source_validity_result.get(0) == true)
					&& (source_validity_result.get(1) == true)
					&& (source_validity_result.get(2) == false)&& (source_validity_result.get(3) == false)) {
				checkResult = jedisUtil.clone(target, source);
			} else {
				checkResult.setFalse("The source =" + source
						+ " is not in a proper state to clone");
			}
		} else {
			checkResult.setFalse("The target =" + target
					+ " is not in a proper state to clone");
		}

		return checkResult;
	}

}
