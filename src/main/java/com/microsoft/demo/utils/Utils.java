package com.microsoft.demo.utils;

import com.microsoft.demo.model.User;

public class Utils {

    // validate user data
    public static boolean validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            return false;
        }
        if (user.getSurname() == null || user.getSurname().isEmpty()) {
            return false;
        }
        if (user.getAge() < 0) {
            return false;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return false;
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            return false;
        }
        return true;
    }
}
