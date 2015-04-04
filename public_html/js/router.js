define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/login',
    'views/game',
    'views/registration',
    'views/viewManager'
], function(
    Backbone,
    mainView,
    scoreView,
    loginView,
    gameView,
    registrationView,
    VM
){
    $(".page").append(mainView.el);
    $(".page").append(scoreView.el);
    $(".page").append(loginView.el);
    $(".page").append(gameView.el);
    $(".page").append(registrationView.el);
    VM.add(mainView);
    VM.add(scoreView);
    VM.add(loginView);
    VM.add(gameView);
    VM.add(registrationView);

    var Router = Backbone.Router.extend({
        vm: VM,
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'back': 'defaultActions',
            'registration': 'registrationAction',
            '*default': 'defaultActions'
        },


        defaultActions: function () {
            mainView.show();
        },
        scoreboardAction: function () {
            scoreView.show();
        },
        gameAction: function () {
            gameView.show();
        },
        loginAction: function () {
            loginView.show();
        },
        registrationAction: function () {
            registrationView.show();
        }

    });

    return new Router();
});