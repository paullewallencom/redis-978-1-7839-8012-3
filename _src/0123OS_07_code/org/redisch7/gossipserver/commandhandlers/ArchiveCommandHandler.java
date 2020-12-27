package org.redisch7.gossipserver.commandhandlers;

import java.util.Arrays;
import java.util.List;

import org.redisch7.gossipserver.datahandler.ConstUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.Token;

public final class ArchiveCommandHandler extends AbstractCommandHandler {

	public ArchiveCommandHandler(String nodename) {
		super(nodename);
	}

	@Override
	public CheckResult process(List<Token> tokenList) {
		CheckResult checkResult = new CheckResult();
		JedisUtil jedisUtil = new JedisUtil();
		List<Boolean> result = jedisUtil
				.doesExist(this.getNodename(), Arrays
						.asList(ConstUtil.registerationHolder,
								ConstUtil.activationHolder,
								ConstUtil.passivationHolder, ConstUtil.shutdownHolder));

		if ((result.get(0) == true)
				&&  (result.get(3) == false) &&((result.get(1) == true) || (result.get(2) == true))) {
			checkResult = jedisUtil.archiveNode(this.getNodename());
		} else {
			checkResult
					.setFalse("Activation Validation :")
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
