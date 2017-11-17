package me.fahimfarook.ms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "prop")
public class Config {

	private String date;

	public String getDate() {
		return this.date;
	}

	public void setDate(final String date) {
		this.date = date;
	}	
}