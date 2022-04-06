package hr.cizmic.seebanking.links;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import hr.cizmic.seebanking.models.Login;
import hr.cizmic.seebanking.repositories.Repository;

public class LoginLink extends ViewModel {
    private static final String TAG = "BANKA";

    private MutableLiveData<Login> mutLogin;

    public LoginLink() {
        mutLogin = new MutableLiveData<>();
        Repository.instance().getLiveLoginInfo().observeForever(login -> mutLogin.setValue(login));
    }

    public static boolean isValidPass(String pass) {
        return pass.length() >= 4 && pass.length() <= 6;
    }

    public LiveData<Login> get_login_info() {
        return mutLogin;
    }

    public LiveData<Boolean> isCorrectPass(String pass) {
        MutableLiveData<Boolean> bool = new MutableLiveData<>();
        bool.setValue(Repository.instance().checkPassword(pass));

        return bool;
    }

    public void removeUserInfo() {
        Repository.instance().removeUserInfo();
    }

}
