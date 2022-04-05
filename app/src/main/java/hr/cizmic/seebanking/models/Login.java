package hr.cizmic.seebanking.models;

public class Login {
    private String _name;
    private String _surname;
    private String _password;

    public Login(String _name, String _surname, String _password) {
        this._name = _name;
        this._surname = _surname;
        this._password = _password;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_surname() {
        return _surname;
    }

    public void set_surname(String _surname) {
        this._surname = _surname;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
