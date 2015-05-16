define([
    'backbone',
    'views/main',
    'views/scoreboard',
    'views/login',
    'views/game',
    'views/registration',
    'views/profile',
    'views/viewManager'
], function(
    Backbone,
    mainView,
    scoreView,
    loginView,
    gameView,
    registrationView,
    profile,
    viewManager
){
    var Router = Backbone.Router.extend({
        vm: viewManager,
        initialize: function() {
            this.vm = viewManager;
            this.vm.add({
                "mainView": mainView,
                "scoreView": scoreView,
                "loginView": loginView,
                "gameView": gameView,
                "registrationView": registrationView,
                "profileView": profile
            });
        },
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'back': 'defaultActions',
            'registration': 'registrationAction',
            'profile': 'profileAction',
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
        },
        profileAction: function () {
            profile.show();
        }

    });

    return new Router();
});