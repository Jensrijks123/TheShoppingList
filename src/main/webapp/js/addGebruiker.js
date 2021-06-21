function goBack() {
    window.location.href="lijstje.html";
}

window.addEventListener('load', (e) => {

    function bobba(myJson) {
        let i = 0;
        for (;myJson[i];) {
            console.log(myJson[i]);

            var tagButton = document.createElement("button");
            var mydiv = document.getElementById("openLijstID");
            tagButton.innerHTML = myJson[i];
            tagButton.classList.add("lijstButton");
            // tagButton.onclick = openLijst;
            mydiv.appendChild(tagButton);
            i++;
        }
    }

    var fetchOptions = { method: "GET",
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}

    fetch("/restservices/user/loadUsers", fetchOptions)
        .then(function(response){
            if (response.ok) {
                return response.json();
            }
        }).then(myJson => bobba(myJson)).catch(error => console.log(error))

});

// Add user
document.addEventListener("DOMContentLoaded", () => {
    const createLijstForm = document.querySelector('#addUserForm');

    // Fetch Add user
    createLijstForm.addEventListener("submit", (e) => {
        e.preventDefault();
        var formlijstData = new FormData(document.querySelector("#addUserForm"));
        var encLijstData = new URLSearchParams(formlijstData.entries());

        fetch("/restservices/user/voegUserToe", {method: "POST", body: encLijstData})
            .then(function (response) {
                if (response.ok) {


                    var lijstNaamID = document.querySelector("#addUserInput").value;
                    var tagButton = document.createElement("button");
                    var mydiv = document.getElementById("openLijstID");
                    console.log(lijstNaamID);
                    if (lijstNaamID !== "") {
                        tagButton.innerHTML = lijstNaamID;
                        tagButton.setAttribute("id", 'lijstButtonClick')
                        tagButton.classList.add("lijstButton");
                        // tagButton.onclick = function(){ openLijst(this); };
                        mydiv.appendChild(tagButton);
                        window.location.reload();
                    }
                } else {
                }
            })
    });
});