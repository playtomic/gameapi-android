package com.playtomic.android;

public class PResponse {

    private Boolean success;
    private int errorcode;
    
    public PResponse()
    {
    	
    }
    
    public PResponse(Boolean s, int e)
    {
    	success = s;
    	errorcode = e;
    }
    
    public Boolean getSuccess() {
        return success;
    }

    public int getErrorCode() {
        return errorcode;
    }

    public void setSuccess(Boolean s)
    {
    	success = s;
    }
    
    public void setErrorCode(int e)
    {
    	errorcode = e;
    }
}
