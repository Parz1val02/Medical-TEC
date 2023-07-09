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




