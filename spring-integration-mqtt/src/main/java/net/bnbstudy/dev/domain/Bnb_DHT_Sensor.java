package net.bnbstudy.dev.domain;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "Bnb_DHT_Sensor")
public class Bnb_DHT_Sensor {

	@Column(name = "time")
	private String time;
	
	@Column(name = "Humidity")
	private String Humidity;
	
	@Column(name = "Temperature")
	private String Temperature;
	
	@Column(name = "device")
	private String device;
	
	@Column(name = "sensor")
	private String sensor;
	
	@Column(name = "sensor_id")
	private String sensor_id;
	
	@Column(name = "student_id")
	private String student_id;
	
	@Column(name = "student_name")
	private String student_name;


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getHumidity() {
		return Humidity;
	}


	public void setHumidity(String humidity) {
		Humidity = humidity;
	}


	public String getTemperature() {
		return Temperature;
	}


	public void setTemperature(String temperature) {
		Temperature = temperature;
	}


	public String getDevice() {
		return device;
	}


	public void setDevice(String device) {
		this.device = device;
	}


	public String getSensor() {
		return sensor;
	}


	public void setSensor(String sensor) {
		this.sensor = sensor;
	}


	public String getSensor_id() {
		return sensor_id;
	}


	public void setSensor_id(String sensor_id) {
		this.sensor_id = sensor_id;
	}


	public String getStudent_id() {
		return student_id;
	}


	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}


	public String getStudent_name() {
		return student_name;
	}


	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}


	@Override
	public String toString() {
		return "Bnb_DHT_Sendor [time=" + time + ", Humidity=" + Humidity + ", Temperature=" + Temperature + ", device="
				+ device + ", sensor=" + sensor + ", sensor_id=" + sensor_id + ", student_id=" + student_id
				+ ", student_name=" + student_name + "]";
	}
	
}
