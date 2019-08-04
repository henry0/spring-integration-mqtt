package net.bnbstudy.dev.domain;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "Bnb_DHT_Sensor")
public class Bnb_Soil_Moisture_Sensor {

	@Column(name = "time")
	private String time;
	
	@Column(name = "SoilMoisture_val")
	private String SoilMoisture_val;
	
	@Column(name = "SoilMoisture_per")
	private String SoilMoisture_per;
	
	@Column(name = "publish_time")
	private String publish_time;
	
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

	public String getSoilMoisture_val() {
		return SoilMoisture_val;
	}

	public void setSoilMoisture_val(String soilMoisture_val) {
		SoilMoisture_val = soilMoisture_val;
	}

	public String getSoilMoisture_per() {
		return SoilMoisture_per;
	}

	public void setSoilMoisture_per(String soilMoisture_per) {
		SoilMoisture_per = soilMoisture_per;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
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
		return "Bnb_Soil_Moisture_Sensor [time=" + time + ", SoilMoisture_val=" + SoilMoisture_val
				+ ", SoilMoisture_per=" + SoilMoisture_per + ", publish_time=" + publish_time + ", device=" + device
				+ ", sensor=" + sensor + ", sensor_id=" + sensor_id + ", student_id=" + student_id + ", student_name="
				+ student_name + "]";
	}
	
}
