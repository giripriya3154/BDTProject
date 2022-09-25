package cs523.bigdata;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;

public class HospitalHBaseTable {

	private static final String hospitalTable = "hospital";
	private static final String hospitalInformation = "hospital_info";
	private static final String address = "hospital_address";
	private Configuration config = HBaseConfiguration.create();
	public void addRecord(Hospital hospital, String state) throws IOException {
		String fullTableName = hospitalTable;
		if(state.equals("")){
			 fullTableName = hospitalTable;
		}
		else {
			 fullTableName = hospitalTable+"-"+ state;
		}

		try (Connection connection = ConnectionFactory.createConnection(config);
				Admin admin = connection.getAdmin()) {

			HTableDescriptor hTableDescriptor = new HTableDescriptor(
					TableName.valueOf(fullTableName));
			hTableDescriptor.addFamily(new HColumnDescriptor(hospitalInformation));
			hTableDescriptor.addFamily(new HColumnDescriptor(address));


			if (!admin.tableExists(hTableDescriptor.getTableName())) {
				admin.createTable(hTableDescriptor);
				System.out.println("Table is created");
			}
			
			// Get Connection to Table
			Table hospitalTable = connection.getTable(TableName
					.valueOf(fullTableName));
			
			Put row = new Put(Bytes.toBytes(hospital.getId().toString()));

			row.addColumn(Bytes.toBytes(hospitalInformation),
					Bytes.toBytes("hospitalName"), Bytes.toBytes(hospital.getHospitalName()));
			
			row.addColumn(Bytes.toBytes(hospitalInformation),
					Bytes.toBytes("hospitalType"), Bytes.toBytes(hospital.getHospitalType()));
			
			row.addColumn(Bytes.toBytes(hospitalInformation),
					Bytes.toBytes("hospitalOwnership"), Bytes.toBytes(hospital.getHospitalOwnership()));

			row.addColumn(Bytes.toBytes(hospitalInformation),
					Bytes.toBytes("isEmergencyService"), Bytes.toBytes(hospital.getEmergencyService().toString()));

			row.addColumn(Bytes.toBytes(hospitalInformation),
					Bytes.toBytes("review"), Bytes.toBytes(hospital.getReview()));

			row.addColumn(Bytes.toBytes(hospitalInformation),
					Bytes.toBytes("phoneNo"), Bytes.toBytes(hospital.getPhoneNo()));

			row.addColumn(Bytes.toBytes(address),
					Bytes.toBytes("streetAddress"), Bytes.toBytes(hospital.getStreetAddress()));

			row.addColumn(Bytes.toBytes(address),
					Bytes.toBytes("city"), Bytes.toBytes(hospital.getCity()));

			row.addColumn(Bytes.toBytes(address),
					Bytes.toBytes("state"), Bytes.toBytes(hospital.getState()));

			row.addColumn(Bytes.toBytes(address),
					Bytes.toBytes("county"), Bytes.toBytes(hospital.getCountry()));

			row.addColumn(Bytes.toBytes(address),
					Bytes.toBytes("zip"), Bytes.toBytes(hospital.getZip()));

			hospitalTable.put(row);

		}

	}

}
