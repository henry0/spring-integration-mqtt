package net.bnbstudy.dev.influxdb;

import org.springframework.beans.factory.annotation.Value;

public class InfluxDBConfig {

	@Value("${spring.influx.url:''}")
	private String influxDBUrl;
	
	@Value("${spring.influx.user:''}")
	private String userName;
	
	@Value("${spring.influx.password:''}")
	private String password;
	
	@Value("${spring.influx.database:''}")
	private String database;
	
	
}
