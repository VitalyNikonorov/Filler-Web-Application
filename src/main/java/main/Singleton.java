package main;

/**
 * Created by Виталий on 03.04.2015.
 */
public class Singleton {
    private static Singleton singleton;
    private Singleton(){};

    public static Singleton getInstance(){
        if ( singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
