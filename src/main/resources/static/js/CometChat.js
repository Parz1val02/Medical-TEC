var logout = function () {
    // log user out of CometChat
    CometChat.logout().then(
        () => {
            //Logout completed successfully
            console.log("Logout completed successfully");
        },
        (error) => {
            //Logout failed with exception
            console.log("Logout failed with exception:", { error });
        }
    );
};


jQuery(document).ready(function($){

    (function () {
        // cometchat initialization
        var appID = "242035960420f9a5";   //mi appID
        var region = "us"; //region de mi appID
        var appSetting = new CometChat.AppSettingsBuilder()
            .subscribePresenceForAllUsers()
            .setRegion(region)
            .build();
        CometChat.init(appID, appSetting).then(
            () => {
                console.log("Initialization completed successfully");
                // You can now call login function.
            },
            (error) => {
                console.log("Initialization failed with error:", error);
                // Check the reason for error and take appropriate action.
            }
        );
    })();

    // cometchat widget initialization

    CometChatWidget.init({
        appID: "242035960420f9a5",  //mi appID
        appRegion: "us",  //region de mi appID
        authKey: "d923b0cc075aa911acef6415f775d893b5c75aa6",  //authkey de mi appID
    }).then(
        (response) => {
            console.log("Initialization (CometChatWidget) completed successfully");
        },
        (error) => {
            console.log("Initialization (CometChatWidget) failed with error:", error);
            //Check the reason for error and take appropriate action.
        }
    );

    var init = function () {
        CometChatWidget.login({
            uid: usuarioId,
        }).then(
            (response) => {

                CometChatWidget.launch({
                    widgetID: "6e163aef-cb84-40ba-a6fd-06b296018397",   //mi widgetID
                    target: "#cometchat",
                    roundedCorners: "true",
                    height: "600px",
                    width: "800px",
                    defaultID: "superhero1", //default UID (user) or GUID (group) to show,
                    defaultType: "user", //user or group
                });


            },
            (error) => {
                console.log("User login failed with error:", error);
            }
        );

    };

    init();


});





