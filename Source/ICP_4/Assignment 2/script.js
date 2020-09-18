function getGithubInfo(user) {
    var url = "https://api.github.com/users/" + user;
    const xhttp = new XMLHttpRequest();
    xhttp.open('GET', url);
    xhttp.send();
    xhttp.onload = () =>{
        //if the response is successful show the user's details
        if (xhttp.status === 200) {
            showUser(JSON.parse(xhttp.response));
        }
        //else return no such user
        else {
            noSuchUser(user);
        }
    }
}

function showUser(user) {
    var userAva = $(user).attr("login");
    var userURL = $(user).attr("avatar_url")
    $('.avatar').text(userAva);
    $('.information').text(userURL);
}

function noSuchUser(username) {
    $('.avatar').text(username);
    $('.information').text(" : No such user.");
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information
            getGithubInfo(username);
        }
    })
});
