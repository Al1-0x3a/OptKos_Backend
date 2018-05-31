package client_api;

import javax.jws.WebService;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IPhoneApi")
public class PhoneApi implements IPhoneApi {
    public static String phoneNumber ="";

    @Override
    public void startCall(String phoneNumber) {

    }

    @Override
    public String registerCall() {
        return null;
    }
}
