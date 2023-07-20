package com.osckorea.openmsa.global.util.damo;

public interface DamoScpService {

    public String ScpEncB64 (String policyId, String strInputPlain);

    public String ScpDecB64 (String policyId, String strInputPlain);
    
    public String ScpHashB64 (String strInputPlain);
}
