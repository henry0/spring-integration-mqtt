package net.bnbstudy.dev;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.bnbstudy.dev.SpringIntegrationMqttApplication.MyGate;
import net.bnbstudy.dev.domain.Bnb_DHT_Sensor;
import net.bnbstudy.dev.domain.Bnb_Soil_Moisture_Sensor;

@Controller
public class RestController {

	@Autowired
	private MyGate gateway;
	
	@Autowired
	private InfluxService influxService;
	
	@RequestMapping("/dummy")
	public @ResponseBody String welcome() {
		
		String src = "[{\"measurement\":\"dummy\",\"tags\":{\"location\":\"seoul\",\"season\":\"summer\"},\"fields\":{\"temperature\":\"\",\"humidity\":\"\"}}]";
		
		byte[] payload = src.getBytes();
		
		MqttMessage message = new MqttMessage(payload);
		
		gateway.send(message.toString(), "bnbstudy/test");
		
		return src;
	}
	
	@RequestMapping("/bnbstudy/command/{deviceName}/{deviceType}/{studentName}/{value}")
	public @ResponseBody String command(
										@PathVariable String deviceName,
										@PathVariable String deviceType,
										@PathVariable String studentName,
										@PathVariable String value
										) {
		
		String src = "[{\"measurement\":\"sensor\",\"tags\":{\"device_name\":\"$(0)\",\"device_type\":\"$(1)\",\"student_name\":\"$(2)\"},\"fields\":{\"value\":\"$(3)\"}}]";
		
		src.replace("$(0)", deviceName);
		src.replace("$(1)", deviceType);
		src.replace("$(2)", studentName);
		src.replace("$(3)", value);
				
		byte[] payload = src.getBytes();
		
		MqttMessage message = new MqttMessage(payload);
		
		gateway.send(message.toString(), "bnbstudy/command");
		
		return src;
	}
	
	@RequestMapping("/measurements")
	public @ResponseBody String measurements() {
		
		//String result = influxService.listMeasurements();
		List<String> result = influxService.listMeasurements();
		
		System.out.println("/measurements ["+ result +"]");
		
		return String.join(",", result);
	}
	
	@RequestMapping("/monitor/Bnb_DHT_Sensor")
	public @ResponseBody String monitorBnb_DHT_Sensor() {
		
		List<Bnb_DHT_Sensor> last = influxService.lastBnb_DHT_Sensor();
		
		System.out.println("/Bnb_DHT_Sensor ["+ last.get(0).toString() +"]");
		
		return last.get(0).toString();
	}
	
	@RequestMapping("/monitor/Bnb_Soil_Moisture_Sensor")
	public @ResponseBody String monitorBnb_Soil_Moisture_Sensor() {
		
		List<Bnb_Soil_Moisture_Sensor> last = influxService.lastBnb_Soil_Moisture_Sensor();
		
		System.out.println("/Bnb_Soil_Moisture_Sensor ["+ last.get(0).toString() +"]");
		
		return last.get(0).toString();
	}
	
}
