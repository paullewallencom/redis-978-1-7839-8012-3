package org.redisch7.gossipserver.datahandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.redisch7.gossipserver.shell.CheckResult;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JedisUtil {
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * This function will check if the holder for a node exists or not. Holder
	 * over here means REGISTRATION-HOLDER,ACTIVATION-HOLDER, PASSIVATION-HOLDER
	 */
	public List<Boolean> doesExist(String nodename, List<String> holderList) {
		List<Boolean> resultList = new ArrayList<Boolean>();
		Jedis jedis = ConnectionManager.get();
		for (String holder : holderList) {
			resultList.add(jedis.sismember(holder, nodename));
		}
		ConnectionManager.set(jedis);
		return resultList;
	}
	
	/**
	 * This function will activate a node. For activation we add a new entry in
	 * the ACTIVATION-HOLDER. Apart from that it will add entry for create time
	 * and last access time.
	 */
	public CheckResult activateNode(String nodename) {
		Jedis jedis = ConnectionManager.get();
		CheckResult checkResult = new CheckResult();

		Long isAddSuccessful = jedis.sadd(ConstUtil.activationHolder, nodename);

		Map<String, String> keyvalue = new HashMap<String, String>();
		keyvalue.put(ConstUtil.createtime, ConstUtil.time.getCurrentTime());
		keyvalue.put(ConstUtil.lastupdate, ConstUtil.time.getCurrentTime());
		jedis.hmset(ConstUtil.helper.getConfigStoreName(nodename), keyvalue);

		ConnectionManager.set(jedis);
		if (isAddSuccessful == 1) {
			return checkResult.setTrue().appendReason("Activation Successful");
		} else {
			return checkResult.setFalse("Activation Failure").appendReason(
					" Could not add to Activation Holder");
		}
	}
	
	/**
	 * This function will register a node. For activation we add a new entry in
	 * the REGISTRATION-HOLDER.
	 */
	public CheckResult registerNode(String nodename) {
		Jedis jedis = ConnectionManager.get();
		CheckResult checkResult = new CheckResult();
		Long result = jedis.sadd(ConstUtil.registerationHolder, nodename);

		ConnectionManager.set(jedis);
		if (result == 1) {
			return checkResult.setTrue()
					.appendReason("Registration Successful");
		} else {
			return checkResult.setFalse("Registration Failure").appendReason(
					" Could not add to registration holder ");
		}
	}

	/**
	 * This function will passivate a node. For passivation we add a new entry
	 * in the PASSIVATION-HOLDER and remove an entry from the ACTIVATION-HOLDER.
	 * Apart from that it will archive the contents of the config store.
	 */
	public CheckResult passivateNode(String nodename) {
		Jedis jedis = ConnectionManager.get();
		CheckResult checkResult = new CheckResult();

		Long isAddSuccess = jedis.sadd(ConstUtil.passivationHolder, nodename);
		Long isRemSuccess = jedis.srem(ConstUtil.activationHolder, nodename);

		ConnectionManager.set(jedis);
		CheckResult archiveCheckResult = this.archiveNode(nodename);

		if (isAddSuccess == 1 && isRemSuccess == 1
				&& archiveCheckResult.getResult()) {
			return checkResult.setTrue().appendReason("Passivation Successful");
		} else {
			return checkResult.setFalse("Passivation Failure : "
					+ checkResult.getReason());
		}

	}
    
	/**
	 * This function will reactivate a node. For reactivation we add a new entry
	 * in the ACTIVATION-HOLDER and remove an entry from the PASSIVATION-HOLDER.
	 * Apart from that it will 'sync' the contents into the config store.
	 */
	public CheckResult reactivateNode(String nodename) {
		Jedis jedis = ConnectionManager.get();
		CheckResult checkResult = new CheckResult();

		Long isAddSuccess = jedis.sadd(ConstUtil.activationHolder, nodename);
		Long isRemSuccess = jedis.srem(ConstUtil.passivationHolder, nodename);
		CheckResult result = this.syncNode(nodename);
		if (isAddSuccess == 1 && isRemSuccess == 1 && result.getResult()) {
			checkResult.setTrue().appendReason("Reactivation sucess ..");
		} else {
			checkResult.setFalse("Reactivation failed ..");
		}
		ConnectionManager.set(jedis);
		return checkResult;
	}
   
	/**
	 * This function will store or set the name and value in the config store
	 * for a given node.
	 */
	public CheckResult setValuesInNode(String nodename,
			Map<String, String> setters) {
		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		setters.put(ConstUtil.lastupdate, ConstUtil.time.getCurrentTime());
		String result = jedis.hmset(
				ConstUtil.helper.getConfigStoreName(nodename), setters);
		if (result.equals("OK")) {
			checkResult.setTrue().appendReason("setting done in " + nodename);
		} else {
			checkResult.setFalse("setting failed in " + nodename);
		}
		ConnectionManager.set(jedis);
		return checkResult;
	}
     
	/**
	 * This function will get the values from the config store for a given node.
	 */
	public CheckResult getValuesFromNode(String nodename, List<String> getters) {
		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		Pipeline pipeline = jedis.pipelined();
		for (String get : getters) {
			pipeline.hget(ConstUtil.helper.getConfigStoreName(nodename), get);
		}
		List<Object> responses = pipeline.syncAndReturnAll();
		checkResult.setTrue().appendReason(responses.toString());
		ConnectionManager.set(jedis);
		return checkResult;
	}
    
	/**
	 * This function will delete the values from the config store for a given
	 * node.
	 */
	public CheckResult deleteValuesFromNode(String nodename,
			List<String> deleteList) {
		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		Pipeline pipeline = jedis.pipelined();
		for (String del : deleteList) {
			pipeline.hdel(ConstUtil.helper.getConfigStoreName(nodename), del);
		}
		pipeline.sync();
		checkResult.setTrue().appendReason("values deleted ");
		ConnectionManager.set(jedis);
		return checkResult;
	}
    
	/**
	 * This function will get the status of a node which includes the state of
	 * the node and the value in the config store. Note: If this function is
	 * called when the node is in PASSIVATE-NODE then the value returned will be
	 * empty as config store is empty and the data is in archived mode.
	 */
	public CheckResult getStatus(String nodename) {
		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		List<Boolean> result = this.doesExist(nodename, Arrays.asList(
				ConstUtil.registerationHolder, ConstUtil.activationHolder,
				ConstUtil.passivationHolder));
		checkResult
				.setTrue()
				.appendReason(
						ConstUtil.registerationHolder + " = " + (result.get(0)))
				.appendReason(
						ConstUtil.activationHolder + " = " + (result.get(1)))
				.appendReason(
						ConstUtil.passivationHolder + " = " + (result.get(2)));

		checkResult.appendReason(jedis.hgetAll(
				ConstUtil.helper.getConfigStoreName(nodename)).toString());
		ConnectionManager.set(jedis);
		return checkResult;
	}
    
	/**
	 * This function will publish message to a given channel. Here we are using
	 * the messaging capability of the Redis Server.
	 */
	public CheckResult publish(String channel, Map<String, String> value) {
		CheckResult checkResult = new CheckResult();
		String mapString = value.toString();
		int firstIndex = mapString.indexOf("{");
		int lastIndex = mapString.lastIndexOf("}");

		String santiziedMapString = mapString.substring(firstIndex + 1,
				lastIndex);
		System.out.println(santiziedMapString);
		Jedis jedis = ConnectionManager.get();
		Long result = jedis.publish(channel, santiziedMapString);
		if (result == 1) {
			checkResult.setTrue().appendReason("Sent to desired channel ");
		} else {
			checkResult.setFalse("Desired channel is down ");
		}
		ConnectionManager.set(jedis);
		return checkResult;
	}
    
	/**
	 * This function will kill the node by removing its entries.
	 */
	public CheckResult killNode(String nodename) {
		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		jedis.srem(ConstUtil.activationHolder, nodename);
		jedis.srem(ConstUtil.passivationHolder, nodename);
		jedis.srem(ConstUtil.registerationHolder, nodename);
		jedis.expire(ConstUtil.helper.getConfigStoreName(nodename), 0);
		ConnectionManager.set(jedis);
		return checkResult.setTrue();
	}
    
	/**
	 * This function will archive the node. This function deals with writing
	 * config data from the config store into a archive file in json format.
	 * 
	 */
	public CheckResult archiveNode(String nodename) {

		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		Map<String, String> configstore = jedis.hgetAll(ConstUtil.helper
				.getConfigStoreName(nodename));
		configstore.put(ConstUtil.lastupdate, ConstUtil.time.getCurrentTime());
		String json = gson.toJson(new ConfigVO(configstore));

		try {
			String path = System.getProperty("user.home") + "\\archive\\"
					+ nodename + ".json";
			// System.out.println(path);
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			checkResult.setFalse(e.getMessage());
		}
		jedis.expire(ConstUtil.helper.getConfigStoreName(nodename), 0);
		ConnectionManager.set(jedis);
		return checkResult.setTrue();
	}
    
	/**
	 * This function is a wrapper over syncNode function. This function is
	 * called when the node after shutdown once again comes up.
	 */
	public CheckResult reconnectNode(String nodename) {
		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		Long result = jedis.srem(ConstUtil.shutdownHolder, nodename);
		ConnectionManager.set(jedis);
		return checkResult.setTrue();
	}
    
	/**
	 * This function will sync the node data to the in memory config store (in
	 * Redis) from the file stored locally as a json file.
	 */
	public CheckResult syncNode(String nodename) {
		CheckResult checkResult = new CheckResult();
		ConfigVO config = null;
		try {
			String path = System.getProperty("user.home") + "\\archive\\"
					+ nodename + ".json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			config = gson.fromJson(br, ConfigVO.class);
			br.close();

		} catch (IOException e) {
			e.getMessage();
		}

		Jedis jedis = ConnectionManager.get();

		Map<String, String> configstore = config.store;

		if (configstore.size() != 0) {
			jedis.hmset(ConstUtil.helper.getConfigStoreName(nodename),
					configstore);
		}
		ConnectionManager.set(jedis);
		return checkResult.setTrue();
	}
    
	/**
	 * This function will clone data from source config store to target config
	 * store.
	 */
	public CheckResult clone(String target, String source) {
		CheckResult checkResult = new CheckResult();
		Jedis jedis = ConnectionManager.get();
		Map<String, String> dataValue = jedis.hgetAll(ConstUtil.helper
				.getConfigStoreName(source));
		checkResult.setTrue().appendReason(
				jedis.hmset(ConstUtil.helper.getConfigStoreName(target),
						dataValue));
		ConnectionManager.set(jedis);
		return checkResult;
	}
    
	/**
	 * This function will get a list of all the nodes from the
	 * REGISTRATION-HOLDER
	 */
	public List<String> getAllNodesFromRegistrationHolder() {
		Jedis jedis = ConnectionManager.get();
		Set<String> members = jedis.smembers(ConstUtil.registerationHolder);

		ConnectionManager.set(jedis);
		return new ArrayList<String>(members);
	}
  	
	/**
	 * This function will get a list of all the nodes from the ACTIVATION-HOLDER
	 */
	public List<String> getAllNodesFromActivatedHolder() {
		Jedis jedis = ConnectionManager.get();
		Set<String> members = jedis.smembers(ConstUtil.activationHolder);
		ConnectionManager.set(jedis);
		return new ArrayList<String>(members);
	}
    
	/**
	 * This function will get a list of all the nodes from the
	 * PASSIVATION-HOLDER
	 */
	public List<String> getAllNodesFromPassivatedHolder() {
		Jedis jedis = ConnectionManager.get();
		Set<String> members = jedis.smembers(ConstUtil.passivationHolder);
     	ConnectionManager.set(jedis);
		return new ArrayList<String>(members);
	}
    
	/**
	 * This function will get a list of all the nodes in a shutdown  state.
	 */
	public List<String> getAllNodesInInShutdownState() {
		Jedis jedis = ConnectionManager.get();
		Set<String> shutdownmembers = jedis.smembers(ConstUtil.shutdownHolder);
		ConnectionManager.set(jedis);
		return new ArrayList<String>(shutdownmembers);
	}
    	
	/**
	 * This function will add the name to  shutdown holder.
	 */
	public void addToShutdownNode(String name) {
		Jedis jedis = ConnectionManager.get();
		if (jedis.sismember(ConstUtil.registerationHolder, name)) {
			jedis.sadd(ConstUtil.shutdownHolder, name);
		}
		ConnectionManager.set(jedis);
	}

}
