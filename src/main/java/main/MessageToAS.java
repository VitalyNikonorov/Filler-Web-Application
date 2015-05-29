package main;

import base.AccountService;
import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by Виталий on 29.05.2015.
 */
public abstract class MessageToAS extends Message {
    public MessageToAS(Address from, Address to) {
        super(from, to);
    }

    @Override
    public final void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountServiceImpl) abonent);
        }
    }

    protected abstract void exec(AccountServiceImpl service);
}
