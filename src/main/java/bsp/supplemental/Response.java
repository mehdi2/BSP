package bsp.supplemental;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 26-Dec-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */


public class Response {
    private String status;
    private Object data;

    public Response(){

    }

    public Response(String status, Object data){
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
