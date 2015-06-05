package main;

import base.dataSets.UserDataSet;
import mechanics.GameSession;
import mechanics.MessageRemove;
import mechanics.MessageToGM;
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

    private Address from;
    private Address to;

    private GameSession session;

    public MsgUpScore(Address from, Address to, GameSession session) {
        super(from, to);
        this.name1 = session.getFirst().getMyName();
        this.score1 = session.getFirst().getMyScore();

        this.name2 = session.getFirst().getEnemyName();
        this.score2 = session.getSecond().getMyScore();

        this.session = session;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void exec(AccountServiceImpl service) {
        UserDataSet result1 = service.getUserByName(name1);
        result1.setScore(result1.getScore() + score1);
        service.getDbService().updateScore(result1);
        Logger.getLogger("Account").warning("\nUpdate for" + name1 + " +score: " + score1);
        System.out.print("Update for" + name1);

        UserDataSet result2 = service.getUserByName(name2);
        result2.setScore(result2.getScore() + score2);
        service.getDbService().updateScore(result2);
        Logger.getLogger("Account").warning("\nUpdate for " + name2 + " +score: " + score2);
        System.out.print("Update for" + name2);

        MessageToGM back = new MessageRemove(to, from, session);
        service.getMessageSystem().sendMessage(back);
    }
}
