package mechanics;

import base.dataSets.UserDataSet;
import messageSystem.Address;


/**
 * Created by Виталий on 29.05.2015.
 */
public final class MessageUser extends MessageToGM {
    private UserDataSet user;
    private UserDataSet varUser;

    public MessageUser(Address from, Address to, UserDataSet user, UserDataSet varUser) {
        super(from, to);
        this.user = user;
        this.varUser = varUser;
    }

    @Override
    protected void exec(GameMechanicsImpl gameMechanics) {
        varUser = user;
    }
}
