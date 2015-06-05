package mechanics;

import base.dataSets.UserDataSet;
import messageSystem.Address;


/**
 * Created by Виталий on 29.05.2015.
 */
public final class MessageUser extends MessageToGM {
    private UserDataSet user;

    public MessageUser(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }

    @Override
    protected void exec(GameMechanicsImpl gameMechanics) {
        gameMechanics.upScore(user);
    }
}
