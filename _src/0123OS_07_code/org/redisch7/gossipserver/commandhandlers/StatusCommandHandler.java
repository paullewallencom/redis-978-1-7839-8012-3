package org.redisch7.gossipserver.commandhandlers;

import java.util.List;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.datahandler.JedisUtil;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.Token;

public class StatusCommandHandler extends AbstractCommandHandler {

	public StatusCommandHandler(String nodename) {
		super(nodename);
	}

	@Override
	public CheckResult process(List<Token> tokenList) {
		CheckResult checkResult = new CheckResult();
		JedisUtil jedisUtil = new JedisUtil();
		if (this.getNodename().equals("master")) {
			List<String> registerednames = jedisUtil
					.getAllNodesFromRegistrationHolder();
			checkResult.setTrue().appendReason(
					"The following nodes are registered ");
			checkResult.appendReason(registerednames.toString());

			List<String> activenodenames = jedisUtil
					.getAllNodesFromActivatedHolder();
			checkResult.setTrue().appendReason(
					"The following nodes are activated ");
			checkResult.appendReason(activenodenames.toString());

			List<String> passivenodenames = jedisUtil
					.getAllNodesFromPassivatedHolder();
			checkResult.setTrue().appendReason(
					"The following nodes are passivated ");
			checkResult.appendReason(passivenodenames.toString());

			List<String> shutdownstate = jedisUtil
					.getAllNodesInInShutdownState();
			checkResult.setTrue().appendReason(
					"The following nodes are not in SHUTDOWN mode ");
			checkResult.appendReason(shutdownstate.toString());

		} else {
			checkResult = jedisUtil.getStatus(this.getNodename());
		}

		return checkResult;
	}
}
