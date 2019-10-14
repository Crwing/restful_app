package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting") 
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name)  {
    	
    	try {
    	// access the RFC Destination "JCoDemoSystem"

        JCoDestination destination = JCoDestinationManager.getDestination("ldcisd4rfc");

        // make an invocation of STFC_CONNECTION in the backend;

        JCoRepository repo = destination.getRepository();

        JCoFunction stfcConnection = repo.getFunction("STFC_CONNECTION");

        JCoParameterList imports = stfcConnection.getImportParameterList();

        imports.setValue("REQUTEXT", "SAP Cloud Platform Connectivity runs with JCo");

        stfcConnection.execute(destination);

        JCoParameterList exports = stfcConnection.getExportParameterList();

        String echotext = exports.getString("ECHOTEXT");

        String resptext = exports.getString("RESPTEXT");
 	
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, echotext));
    	}
	    catch (JCoException e) {
	
	    	return new Greeting(counter.incrementAndGet(),
                    String.format(template, e.getMessage()));

	    }
    }
    
    @RequestMapping("/asset")
    public Asset postAsset(Asset createAsset) {
    	
    	try {
        	// access the RFC Destination "JCoDemoSystem"

            JCoDestination destination = JCoDestinationManager.getDestination("ldcisd4rfc");

            // make an invocation of STFC_CONNECTION in the backend;

            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction("ZBAPI_CREATE_ASSET");

            JCoParameterList imports = stfcConnection.getImportParameterList();

            imports.setValue("IV_COMPANYCODE", createAsset.getCompanyCode());
            
            imports.setValue("IV_ASSETCLASS", createAsset.getAssetClass());
            
            imports.setValue("IV_DESCRIPTION", createAsset.getDescription());

            stfcConnection.execute(destination);

            JCoParameterList exports = stfcConnection.getExportParameterList();

            String assetMainNo = exports.getString("EV_ASSETMAINO");

            String assetSubNo = exports.getString("EV_SUBNUMBER");
     	
            return new Asset(createAsset.getCompanyCode(), assetMainNo, createAsset.getDescription());
        	}
    	    catch (JCoException e) {
    	
    	    	return new Asset(null, null, null);

    	    }
    	  	
    }
}