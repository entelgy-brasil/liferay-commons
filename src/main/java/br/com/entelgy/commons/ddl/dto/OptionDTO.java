package br.com.entelgy.commons.ddl.dto;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OptionDTO {

	private String uuid;
	private String value;
	private HashMap<String, String> extras = new HashMap<>();

	public static OptionDTO getInstance(String uuid, String value) {
		OptionDTO optionDTO = new OptionDTO();
		optionDTO.setUuid(uuid);
		optionDTO.setValue(value);
		return optionDTO;
	}

	public void addExtra(String name, String value) {
		this.extras.put(name, value);
	}

	public Map<String, String> getExtras() {
		return extras;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}