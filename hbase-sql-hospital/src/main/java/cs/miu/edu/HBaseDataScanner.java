package cs.miu.edu;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;


public class HBaseDataScanner {
    private Configuration config;
    private final String hospitalTable;
    private final String hospitalInformation = "hospital_info";
    private final String address = "hospital_address";

    public HBaseDataScanner(String hospitalTableName) {
        this.config = HBaseConfiguration.create();
        hospitalTable = hospitalTableName;
    }

    public List<Hospital> fetchHospitalInfoFromHbase() throws IOException {
        List<Hospital> listOfHospital = new ArrayList<>();
        try (Connection connection = ConnectionFactory.createConnection(this.config)) {
            Table table = connection.getTable(TableName.valueOf(hospitalTable));
            Scan scan = new Scan();
            scan.setCacheBlocks(false);
            scan.setCaching(10000);
            scan.setMaxVersions(10);
            ResultScanner scanner = table.getScanner(scan);
            for (Result result = scanner.next(); result != null; result = scanner.next()) {
                Hospital hospital = new Hospital();
                hospital.setHospitalName(this.extractValue(result, hospitalInformation, "hospitalName"));
                hospital.setPhoneNo(this.extractValue(result, hospitalInformation, "phoneNo"));
                hospital.setHospitalType(this.extractValue(result, hospitalInformation, "hospitalType"));
                hospital.setHospitalOwnership(this.extractValue(result, hospitalInformation, "hospitalOwnership"));
                hospital.setReview(this.extractValue(result, hospitalInformation, "review"));
                hospital.setStreetAddress(this.extractValue(result,address,"streetAddress"));
                hospital.setCity(this.extractValue(result,address,"city"));
                hospital.setState(this.extractValue(result,address,"state"));
                hospital.setZip(this.extractValue(result,address,"zip"));
                hospital.setCountry(this.extractValue(result,address,"country"));
                listOfHospital.add(hospital);
            }

        }
        return listOfHospital;
    }

    public String extractValue(Result result, String header, String columnHeader) {
        return Bytes.toString(result.getValue(Bytes.toBytes(header), Bytes.toBytes(columnHeader)));
    }
}
