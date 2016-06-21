/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.management;

/**
 *
 * @author Idan
 */
public class ResponseCode {
    private enum ResponseCodeResult { SUCCESS, FAIL }
    
    private ResponseCodeResult result = ResponseCodeResult.SUCCESS;
    private String message;
    private Object data;

    public ResponseCodeResult getResult() {
        return result;
    }

    public void setResult(ResponseCodeResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseCode success (String message)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.result = ResponseCodeResult.SUCCESS;
        responseCode.message = message;
        responseCode.data = null;
        return responseCode;
    }

    public static ResponseCode success (String message, Object data)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.result = ResponseCodeResult.SUCCESS;
        responseCode.message = message;
        responseCode.data = data;
        return responseCode;
    }
    
    public static ResponseCode fail (String errorMessage)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.result = ResponseCodeResult.FAIL;
        responseCode.message = errorMessage;
        return responseCode;
    }
}
