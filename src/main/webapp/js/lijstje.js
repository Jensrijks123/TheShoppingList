// Ready items
window.addEventListener('load', (e) => {

    function bobba(myJson) {
        let i = 0;
        for (;myJson[i];) {
            var tagButton = document.createElement("button");
            var mydiv = document.getElementById("openLijstID");
            tagButton.innerHTML = myJson[i];
            tagButton.classList.add("lijstButton");
            tagButton.setAttribute('id', myJson[i]);
            tagButton.onclick = verwijderItem;
            mydiv.appendChild(tagButton);
            i++;
        }
    }

    var fetchOptions = { method: "GET",
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}

    fetch("/restservices/item/loadItemsReady", fetchOptions)
        .then(function(response){
            if (response.ok) {
                return response.json();
            }
        }).then(myJson => bobba(myJson)).catch(error => console.log(error))

});

// Done items
window.addEventListener('load', (e) => {

    function bobba(myJson) {
        let i = 0;
        for (;myJson[i];) {
            var tagButton = document.createElement("button");
            var mydiv = document.getElementById("openLijstGedaanID");
            tagButton.innerHTML = myJson[i];
            tagButton.classList.add("lijstButtonGedaan");
            tagButton.onclick = undoVerwijder;
            tagButton.setAttribute('id',myJson[i]);
            mydiv.appendChild(tagButton);
            i++;
        }
    }

    var fetchOptions = { method: "GET",
        headers : {
            'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
        }}

    fetch("/restservices/item/loadItemsDone", fetchOptions)
        .then(function(response){
            if (response.ok) {
                return response.json();
            }
        }).then(myJson => bobba(myJson)).catch(error => console.log(error))

});




function goBack() {
    window.location.href="home.html";
}

function showDropdown() {
    document.getElementById("myDropdown").classList.toggle("show");
}


window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

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

function verwijderItem(btn) {

    var target = btn.target || btn.srcElement;
    var buttonRem = document.getElementById('' + target.innerHTML)

    var formlijstData = new FormData(document.querySelector("#createLijst"));
    var encLijstData = new URLSearchParams(formlijstData.entries());

    fetch("/restservices/item/createItemDone/" + target.innerHTML, {method: "POST", body: encLijstData})
        .then(function (response) {
            if (response.ok) {
                buttonRem.parentNode.removeChild(buttonRem);

                var itemNaamValue = target.innerHTML;
                var tagButton = document.createElement("button");
                var mydiv = document.getElementById("openLijstGedaanID");
                tagButton.innerHTML = itemNaamValue;
                tagButton.classList.add("lijstButtonGedaan");
                tagButton.onclick = undoVerwijder;
                tagButton.setAttribute('id',itemNaamValue);
                mydiv.appendChild(tagButton);
            }
        })
}


function undoVerwijder(btn) {

    var target = btn.target || btn.srcElement;
    var buttonRem = document.getElementById('' + target.innerHTML)

    var formlijstData = new FormData(document.querySelector("#createLijst"));
    var encLijstData = new URLSearchParams(formlijstData.entries());

    fetch("/restservices/item/createItemBackToReady/" + target.innerHTML, {method: "POST", body: encLijstData})
        .then(function (response) {
            if (response.ok) {

                buttonRem.parentNode.removeChild(buttonRem);

                var lijstNaamID = target.innerHTML;
                var tagButton = document.createElement("button");
                var mydiv = document.getElementById("openLijstID");
                tagButton.innerHTML = lijstNaamID;
                tagButton.classList.add("lijstButton");
                tagButton.onclick = verwijderItem;
                tagButton.setAttribute('id',lijstNaamID);
                mydiv.appendChild(tagButton);
            }
        })

}

function deleteSelectedItems() {

    var divParent = document.getElementById('openLijstGedaanID')

    var formlijstData = new FormData(document.querySelector("#createLijst"));
    var encLijstData = new URLSearchParams(formlijstData.entries());

    fetch("/restservices/item/deleteSelectedItems", {method: "POST", body: encLijstData})
        .then(function (response) {
            if (response.ok) {
                console.log("gelukt");
                divParent.querySelectorAll('*').forEach(n => n.remove());
                window.location.reload();
            }
        })
}

// add item
document.addEventListener("DOMContentLoaded", () => {
    const createLijstForm = document.querySelector('#addSubmit');

    // Fetch create lijst
    createLijstForm.addEventListener("click", (e) => {
        e.preventDefault();

        var formlijstData = new FormData(document.querySelector("#createLijst"));
        var encLijstData = new URLSearchParams(formlijstData.entries());

        fetch("/restservices/item/createItemReady", {method: "POST", body: encLijstData})
            .then(function (response) {
                if (response.ok) {
                    var lijstNaamID = document.querySelector("#lijstNaam").value;
                    var tagButton = document.createElement("button");
                    var mydiv = document.getElementById("openLijstID");
                    if (lijstNaamID !== "") {
                        tagButton.innerHTML = lijstNaamID;
                        tagButton.classList.add("lijstButton");
                        tagButton.onclick = verwijderItem;
                        tagButton.setAttribute('id',lijstNaamID);
                        mydiv.appendChild(tagButton);
                        document.getElementById("lijstNaam").value = "";
                    }
                } else {
                    console.log("Fout")
                    }
                })
    });
});