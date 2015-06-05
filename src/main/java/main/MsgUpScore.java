package main;

import base.dataSets.UserDataSet;
import mechanics.MessageUser;
import messageSystem.Address;
import messageSystem.Message;

import java.util.logging.Logger;

/**
 * Created by Виталий on 29.05.2015.
 */
public final class MsgUpScore extends MessageToAS {
    private String name1;
    private int score1;

    private String name2;
    private int score2;

    public MsgUpScore(Address from, Address to, String name1, int score1, String name2, int score2) {
        super(from, to);
        this.name1 = name1;
        this.score1 = score1;

        this.name1 = name2;
        this.score2 = score2;
    }

    @Override
    protected void exec(AccountServiceImpl service) {
        UserDataSet result1 = service.getUserByName(name1);
        result1.setScore(result1.getScore()+score1);
        service.getDbService().updateScore(result1);
        Logger.getLogger("Account").warning("\nUpdate for" + name1 +"+ score: " + score1);
        System.out.print("Update for" + name1);

        UserDataSet result2 = service.getUserByName(name2);
        result2.setScore(result2.getScore()+score2);
        service.getDbService().updateScore(result2);
        Logger.getLogger("Account").warning("\nUpdate for " + name2 +"+ score: " + score2 );
        System.out.print("Update for" + name2);


    }
}
