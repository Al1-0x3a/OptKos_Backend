package client_api;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IPhoneApi {
    @WebMethod
    void startCall(String phoneNumber);
    @WebMethod
    String registerCall();
}
