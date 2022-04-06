package hr.cizmic.seebanking.links;

import androidx.lifecycle.ViewModel;

import hr.cizmic.seebanking.repositories.Repository;

public class RegisterLink extends ViewModel {

    public void postRegistrationValues(String... s) {
        Repository.instance().postNewUserInfo(s);
    }

    public static boolean isInvalidName(String name) {
        return (name.length() == 0 || name.length() > 30);
    }


}
