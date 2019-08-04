package net.bnbstudy.dev.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;

public class InfluxDbUtils {

	private String userName;
	
	private String password;
	
	private String url;
	
	private String database;
	
	private String retentionPolicy;
	
	private InfluxDB influxDB;
	
	public InfluxDbUtils(String userName,
						String password,
						String url,
						String database,
						String retentionPolicy
			) {
		this.userName = userName;
		this.password = password;
		this.url = url;
		this.database = database;
		this.retentionPolicy = retentionPolicy == null || "".equals(retentionPolicy) ? "autogen" : retentionPolicy;
	}
	
	private InfluxDB build() {
		if(influxDB == null) {
			influxDB = InfluxDBFactory.connect(url);
			//influxDB = InfluxDBFactory.connect(url, userName, password);
		}
		try {
			//influxDB.createDatabase(database);
			influxDB.setDatabase(database);
		} catch(Exception e) {
			System.out.println("create influx db failed, error : "+ e.getMessage());
		} finally {
			influxDB.setRetentionPolicy(retentionPolicy);
		}
		return influxDB;
	}
}
