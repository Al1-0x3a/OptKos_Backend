package client_api;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IPhoneApi {
    @WebMethod
    public void startCall(String phoneNumber);
    @WebMethod
    public String registerCall();
}
