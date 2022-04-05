package hr.cizmic.seebanking.links;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import hr.cizmic.seebanking.models.Login;
import hr.cizmic.seebanking.repositories.Repository;

public class LoginLink extends ViewModel {
    private MutableLiveData<Login>   _login = new MutableLiveData<>();

    public LiveData<Login> get_login_info() {
        Repository.instance().getLiveLoginInfo().observeForever(new Observer<Login>() {
            @Override
            public void onChanged(Login login) {
                _login.postValue(login);
            }
        });
        return _login;
    }


    private static boolean isValidEmail() {
        return true;
    }
}
