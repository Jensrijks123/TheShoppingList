window.addEventListener('load', (e) => {

    function bobba(myJson) {
        let i = 0;
        for (;myJson[i];) {
            console.log(myJson[i]);

            var tagButton = document.createElement("button");
            var mydiv = document.getElementById("openLijstID");
            tagButton.innerHTML = myJson[i];
            tagButton.classList.add("lijstButton");
            mydiv.appendChild(tagButton);
            i++;
        }
    }

    var fetchOptions = { method: "GET",
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}

    fetch("/restservices/item/loadItems", fetchOptions)
        .then(function(response){
            if (response.ok) {

                return response.json();
            }
        }).then(myJson => bobba(myJson)).catch(error => console.log(error))

});

const openModalButtons = document.querySelectorAll('[data-modal-target]');
const closeModalButtons = document.querySelectorAll('[data-close-button]');
const overlay = document.getElementById('overlay');

openModalButtons.forEach(button => {
    button.addEventListener('click', () => {
        const modal = document.querySelector(button.dataset.modalTarget);
        openModal(modal);
    });
});

overlay.addEventListener('click', () => {
    const modals = document.querySelectorAll('.modal.active');
    modals.forEach(modal => {
        closeModal(modal);
    })
})

closeModalButtons.forEach(button => {
    button.addEventListener('click', () => {
        const modal = button.closest('.modal')
        closeModal(modal);
    });
});

function openModal(modal) {
    if (modal == null) return
    modal.classList.add('active')
    overlay.classList.add('active')
}

function closeModal(modal) {
    if (modal == null) return
    modal.classList.remove('active');
    overlay.classList.remove('active')
}

// add item
document.addEventListener("DOMContentLoaded", () => {
    const createLijstForm = document.querySelector('#addSubmit');

    // Fetch create lijst
    createLijstForm.addEventListener("click", (e) => {
        e.preventDefault();

        var formlijstData = new FormData(document.querySelector("#createLijst"));
        var encLijstData = new URLSearchParams(formlijstData.entries());

        fetch("/restservices/item/createItem", {method: "POST", body: encLijstData})
            .then(function (response) {
                if (response.ok) {
                    console.log("Werkt")

                    var lijstNaamID = document.querySelector("#lijstNaam").value;
                    var tagButton = document.createElement("button");
                    var mydiv = document.getElementById("openLijstID");
                    console.log(lijstNaamID);
                    if (lijstNaamID !== "") {
                        tagButton.innerHTML = lijstNaamID;
                        tagButton.classList.add("lijstButton");
                        mydiv.appendChild(tagButton);
                        document.getElementById("lijstNaam").value = "";
                    }
                } else {
                    console.log("Fout")
                    }
                })
    });
});