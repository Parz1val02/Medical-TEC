jQuery(document).ready(function($){

// cometchat widget initialization

    var init = function () {
        CometChatWidget.login({
            uid: usuarioId,
        }).then(
            (response) => {

                CometChatWidget.launch({
                    widgetID: "c18577ac-1245-4b10-9b91-dcd7f194cbb3",   //mi widgetID
                    target: "#cometchat",
                    roundedCorners: "true",
                    height: "600px",
                    width: "800px",
                    defaultID: "", //default UID (user) or GUID (group) to show,
                    defaultType: "user", //user or group
                });


            },
            (error) => {
                console.log("User login failed with error:", error);
            }
        );

    };

    CometChatWidget.init({
        appID: "24272539e9e89a98",  //mi appID
        appRegion: "us",  //region de mi appID
        authKey: "1b88d312bf176863d22000e0ed856a1717bc8c06",  //authkey de mi appID
    }).then(
        (response) => {
            console.log("Initialization (CometChatWidget) completed successfully");
            init();
        },
        (error) => {
            console.log("Initialization (CometChatWidget) failed with error:", error);
            //Check the reason for error and take appropriate action.
        }
    );



});





