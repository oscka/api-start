package com.osckorea.openmsa.global.util.damo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.penta.scpdb.ScpDbAgent;
import com.penta.scpdb.ScpDbAgentException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("prod")
public class ProdDamoScpService implements DamoScpService{

    /* DAMO SCP : config file full path */
    String iniFilePath = "/opt/bascp/scpdb_agent.ini"; /* scpdb_agent.ini fullpath */
    
    /* DAMO SCP : Create ScpDbAgent object */      
    ScpDbAgent agt = new ScpDbAgent();

    public String ScpEncB64 (String policyId, String strInputPlain) {

        String strEnc = "";
        try {
            /*  보안 정책 (policyId)
                OSCKOREA-POL-1
                OSCKOREA-POL-2  
            */ 
			log.info("DevDamoScpService.ScpEncB64 >> policyId : " + policyId);
			log.info("DevDamoScpService.ScpEncB64 >> strInputPlain : " + strInputPlain);
			strEnc = agt.ScpEncB64( iniFilePath, policyId, strInputPlain );
			log.info("DevDamoScpService.ScpEncB64 >> strEnc : " + strEnc);
		}
		catch (ScpDbAgentException e1) {
			log.error(e1.getMessage());
		}
		catch (Exception e) {
            log.error(e.getMessage());  
		}
        
        return strEnc;
    }

    public String ScpDecB64 (String policyId, String strInputPlain) {
        
        String strDec = "";
        try {
            /*  보안 정책 (policyId)
                OSCKOREA-POL-1
                OSCKOREA-POL-2
            */ 
			log.info("DevDamoScpService.ScpDecB64 >> policyId : " + policyId);
			log.info("DevDamoScpService.ScpDecB64 >> strInputPlain : " + strInputPlain);
			strDec = agt.ScpDecB64( iniFilePath, policyId, strInputPlain );
			log.info("DevDamoScpService.ScpDecB64 >> strDec : " + strDec);
		}
		catch (ScpDbAgentException e1) {
			log.error(e1.getMessage());
		}
		catch (Exception e) {
            log.error(e.getMessage());  
		}

        return strDec;
    }
    
    public String ScpHashB64 (String strInputPlain) {

        String strEnc = "";
        try {
            /*  DAMO SCP API 
                HASH function
                HASH Algorithm ID :
                SHA1 = 70
                SHA256 = 71
                SHA384 = 72
                SHA512 = 73
                HAS160 = 74
                MD5 = 75  
            */ 
			log.info("DevDamoScpService.ScpHashB64 >> strInputPlain : " + strInputPlain);
            strEnc = agt.ScpHashB64( iniFilePath, 71, strInputPlain );
			log.info("DevDamoScpService.ScpHashB64 >> strEnc : " + strEnc);
		}
		catch (ScpDbAgentException e1) {
			log.error(e1.getMessage());
		}
		catch (Exception e) {
            log.error(e.getMessage());  
		}
        
        return strEnc;
    }
}
