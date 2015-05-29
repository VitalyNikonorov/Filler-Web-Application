package mechanics;

import base.GameMechanics;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by Виталий on 29.05.2015.
 */
public abstract class MessageToGM extends Message {
    public MessageToGM(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof GameMechanics) {
            exec((GameMechanicsImpl) abonent);
        }
    }

    protected abstract void exec(GameMechanicsImpl gameMechanics);
}
