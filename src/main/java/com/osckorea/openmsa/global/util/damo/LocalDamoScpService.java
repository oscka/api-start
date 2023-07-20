package com.osckorea.openmsa.global.util.damo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("local")
public class LocalDamoScpService implements DamoScpService{

    public String ScpEncB64 (String policyId, String strInputPlain) {
        
        return strInputPlain;
    }

    public String ScpDecB64 (String policyId, String strInputPlain) {

        return strInputPlain;
    }
    
    public String ScpHashB64 (String strInputPlain) {
        
        return strInputPlain;
    }
}
