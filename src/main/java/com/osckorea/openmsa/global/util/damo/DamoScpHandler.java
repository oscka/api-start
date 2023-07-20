package com.osckorea.openmsa.global.util.damo;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DamoScpHandler {

    private final DamoScpService damoScpService;


    public String ScpEncB64 (String policyId, String strInputPlain) {

        String strEnc = "";
        strEnc = damoScpService.ScpEncB64(policyId, strInputPlain);
        
        return strEnc;
    }


    public String ScpDecB64 (String policyId, String strInputPlain) {

        String strDec = "";
        strDec = damoScpService.ScpDecB64(policyId, strInputPlain);

        return strDec;
    }


    public String ScpHashB64 (String strInputPlain) {

        String strEnc = "";
        strEnc = damoScpService.ScpHashB64(strInputPlain);
        
        return strEnc;
    }
}
