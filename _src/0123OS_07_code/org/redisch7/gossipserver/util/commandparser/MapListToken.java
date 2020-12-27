package org.redisch7.gossipserver.util.commandparser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.redisch7.gossipserver.shell.CheckResult;

public class MapListToken extends Token {
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	private char dilimiter = ',';

	public MapListToken() {

	}

	public MapListToken(List<MapToken> mapTokenList) {
		for (MapToken mapToken : mapTokenList) {
			map.put(mapToken.getName(), mapToken.getValue());
		}
	}

	public MapListToken(String namevaluelist) {
		this(namevaluelist, ',');
	}

	public MapListToken(String namevaluelist, char dilim) {
		dilimiter = dilim;
		String santizedString = namevaluelist.replaceAll("\\s{2,}", "")
				.replaceAll("( ,)", ",").replaceAll("(, )", ",")
				.replaceAll("( =)", "=");

		if (santizedString.contains(String.valueOf('='))) {
			StringTokenizer stringTokenizer = new StringTokenizer(
					santizedString, String.valueOf(dilim));
			while (stringTokenizer.hasMoreElements()) {
				MapToken mapToken = new MapToken(stringTokenizer.nextToken());
				this.add(mapToken);
			}
		}
	}

	public void add(MapToken mapToken) {
		map.put(mapToken.getName(), mapToken.getValue());
	}

	public Map<String, String> getAll() {
		return map;
	}

	@Override
	public String getValue() {
		return map.toString();
	}

	public int getSize() {
		return map.size();
	}

	@Override
	public CheckResult validate(String input) {
		CheckResult checkResult = new CheckResult();
		MapListToken targetMapListToken = (MapListToken) this
				.convertToToken(input);
		if ((map.size() == 0 || this.equalsTo(targetMapListToken).getResult())
				&& targetMapListToken.getAll().size() != 0) {
			return checkResult.setTrue();
		} else {
			return checkResult.setFalse(
					"Data in target map = " + targetMapListToken.getValue())
					.appendReason(
							" data is configured map = " + this.getValue());
		}
	}

	public CheckResult equalsTo(MapListToken targetmapListToken) {
		CheckResult checkResult = new CheckResult();
		checkResult.setTrue();
		ArrayList<String> keyList = new ArrayList<String>(map.keySet());
		for (String key : keyList) {
			if (!targetmapListToken.containsKey(key)
					&& !targetmapListToken.getNValue(key).equals(map.get(key))) {
				new CheckResult().setFalse("name value dont match");
				break;
			}
		}
		return checkResult;
	}

	public String getNValue(String key) {
		return map.get(key);
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	@Override
	public Token convertToToken(String input) {
		return new MapListToken(input);
	}

	public Map<String, String> getValueAsMap() {
		return map;
	}

	public MapListToken removeElement(String commandValue) {
		map.remove(commandValue);

		return new MapListToken(this.getValueAsSantizedString(map));
	}

	public String getValueAsSantizedString() {
		int firstIndex = this.map.toString().indexOf("{");
		int lastIndex = this.map.toString().lastIndexOf("}");

		String santiziedMapString = this.map.toString().substring(
				firstIndex + 1, lastIndex);
		return santiziedMapString;
	}

	public String getValueAsSantizedString(Map<String, String> map) {
		int firstIndex = map.toString().indexOf("{");
		int lastIndex = map.toString().lastIndexOf("}");

		String santiziedMapString = map.toString().substring(firstIndex + 1,
				lastIndex);
		return santiziedMapString;
	}

}
