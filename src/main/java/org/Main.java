package org;
import org.core.Engine;
import org.entities.user.User;
import org.entities.wallet.StandardWallet;
import org.utils.SerializationUtils;

import java.io.IOException;
import java.util.Currency;


public class Main {
    public static void main(String[] args) throws IOException {
        Engine engine = new Engine();
        engine.run();


        User user = new User("user1", "123456");
        SerializationUtils.serialize(user, "user.ser");

        // ser for standard test
        StandardWallet wallet = new StandardWallet(user.getId(), user.getUsername(), Currency.getInstance("BGN"));
        SerializationUtils.serialize(wallet, "wallet.ser");

    }

}