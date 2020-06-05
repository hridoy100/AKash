package github.mjksabit.akash.app.Netword;

import github.mjksabit.akash.app.Controller.Controller;

public abstract class Request <TypeC extends Controller> {
    protected static final String REQUEST_TYPE = "requestType";
    protected static final String RESPONSE_SUCCESS = "success";

    protected static final String REQUEST_BALANCE = "balance";
    protected static final String REQUEST_LOGIN = "login";
    protected static final String REQUEST_SIGNUP = "signup";
    protected static final String REQUEST_CHANGE_PASSWORD = "changepassword";
    
    protected TypeC requester = null;

    public Request(TypeC requester) {
        this.requester = requester;
    }
}
