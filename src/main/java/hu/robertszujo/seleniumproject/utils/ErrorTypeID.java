package hu.robertszujo.seleniumproject.utils;

public enum ErrorTypeID {
    ELETKOR("eletkor_error"),
    INGATLAN_ERTEKE("ingatlan_erteke_error"),
    JOVEDELEM("mjovedelem_error"),
    MEGLEVO_TORLESZTO("meglevo_torleszto_error")
    ;

    final String id;

    ErrorTypeID(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}