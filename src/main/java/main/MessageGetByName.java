package main;

import base.dataSets.UserDataSet;
import mechanics.MessageUser;
import messageSystem.Address;
import messageSystem.Message;

/**
 * Created by Виталий on 29.05.2015.
 */
public final class MessageGetByName extends MessageToAS {
    private String name;
    private UserDataSet varUser;

    public MessageGetByName(Address from, Address to, String name, UserDataSet varUser) {
        super(from, to);
        this.name = name;
        this.varUser = varUser;
    }

    @Override
    protected void exec(AccountServiceImpl service) {
        UserDataSet result = service.getUserByName(name);
        final Message back = new MessageUser(getTo(), getFrom(), result, varUser);
        service.getMessageSystem().sendMessage(back);
    }
}
