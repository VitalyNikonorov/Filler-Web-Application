package mechanics;

import messageSystem.Address;


/**
 * Created by Виталий on 29.05.2015.
 */
public final class MessageRemove extends MessageToGM {
    private GameSession session;

    public MessageRemove(Address from, Address to, GameSession session) {
        super(from, to);
        this.session = session;
    }

    @Override
    protected void exec(GameMechanicsImpl gameMechanics) {
        gameMechanics.removeGame(session);
    }
}
