package client_api;

import javax.jws.WebService;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IPhoneApi")
public class PhoneApi implements IPhoneApi {
    public static String phoneNumber ="";

    public PhoneApi() {
    }

    @Override
    public void startCall(String phoneNumber) {
        System.out.printf("%-50s", "Phone Call incoming...");
        System.out.println(phoneNumber);
    }

    @Override
    public String registerCall() {
        return "Hello Welt";
    }

}
