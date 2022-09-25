package miu.bdt.hospital_producer_kafka;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.List;

@SpringBootApplication
public class HospitalProducerKafkaApplication implements CommandLineRunner {
	@Autowired
	private HospitalProducer hospitalProducer;

	@Component
	@RequiredArgsConstructor
	class HospitalProducer{
		 private static final String TOPIC = "info_hospital";
		    private final KafkaTemplate<String, Hospital> kafkaTemplate;
		    public void produce(Hospital hospital)
		    {
		        System.out.println(" Producing -> {\n "+hospital+  "\n }");
		        this.kafkaTemplate.send(TOPIC, hospital);
		    }
	}

	public static void main(String[] args) {
		SpringApplication.run(HospitalProducerKafkaApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		String filename = args != null && args.length > 0 ? args[0] : "/Users/pg/Documents/MIU/Courses/bdt/bdtproject/hospital_producer_kafka/src/main/resources/HospInfo.csv";
		List<Hospital> hospitalList = new CsvToBeanBuilder(new FileReader(filename))
				.withType(Hospital.class)
				.build()
				.parse();
		//System.out.println(hospitalList);
		for(int i = 0; i<hospitalList.size();i++){
			try {
				Thread.sleep(10);
			}catch (InterruptedException ex){
				ex.printStackTrace();
			}
			hospitalProducer.produce(hospitalList.get(i));

		}

	}
}
