// const formLogin = document.getElementById('login');
// const formSignin = document.getElementById('createAccount');
// const usernameOrEmail = document.getElementById('userEmail');
// const username = document.getElementById('username');
// const email = document.getElementById('email');
// const password = document.getElementById('password');
// const password1 = document.getElementById('password1');
// const password2 = document.getElementById('password2');


// delete user :

// document.querySelector("#delete").addEventListener("click", function (){
//    var id = document.querySelector("#deleteid").value;
//    var fetchoptions = {
//        method: "DELETE",
//        headers : {
//            'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")
//        }
//    }
//
//    fetch("/restservices/customer/"+id, fetchoptions)
//        .then(function (response) {
//           if (response.ok) console.log("Customer deleted")
//            else if (response.status === 404) console.log("Customer not found")
//            else if (response.status === 401) console.log("Unauthorized")
//        }).catch(error => console.log(error));
// });


// code les

// document.addEventListener("load", event => {
//    document.querySelector("#loginFormSubmit").addEventListener("submit", login);
// });
//
// async function login() {
//     console.log("Login");
//     var formData = new FormData(document.querySelector("#login"));
//     var encData = new URLSearchParams(formData.entries());
//
//     let response = await fetch("restservices/authentication", { method: "POST", body : encData})
//
//     if (response.ok) {
//         let myJson = await response.json();
//         window.sessionStorage.setItem("myJWT", myJson.JWT);
//     } else {
//         console.log("Login failed");
//     }
// }



// Message bij login en sign form als er iets gebeurt
function setFormMessage(formElement, type, message) {
    const messageElement = formElement.querySelector(".form__message");

    messageElement.textContent = message;
    messageElement.classList.remove("form__message--success", "form__message--error");
    messageElement.classList.add(`form__message--${type}`);
}

// Error versie displayen
function setInputError(inputElement, message) {
    inputElement.classList.add("formInputError");
    inputElement.parentElement.querySelector(".formInputErrorMessage").textContent = message;
}

// Error versie weghalen
function clearInputError(inputElement) {
    inputElement.classList.remove("formInputError")
    inputElement.parentElement.querySelector(".formInputErrorMessage").textContent = "";
}

// Login form en signin form verwisselen
document.addEventListener("DOMContentLoaded", () => {
   const loginForm = document.querySelector('#login');
   const createAccountForm = document.querySelector('#createAccount');

   document.querySelector("#linkCreateAccount").addEventListener("click", e => {
       e.preventDefault();
       loginForm.classList.add("formHidden");
       createAccountForm.classList.remove("formHidden");
   });

   document.querySelector('#linkLogin').addEventListener("click", e => {
       e.preventDefault();
       loginForm.classList.remove("formHidden");
       createAccountForm.classList.add("formHidden");
   });

   // Fetch login

   loginForm.addEventListener("submit", (e) => {
       e.preventDefault()

       var formData = new FormData(document.querySelector("#login"));
       var encData = new URLSearchParams(formData.entries());

       fetch("/restservices/authentication", {method: "POST", body: encData})
           .then(function (response) {
               if (response.ok) {
                   let myJson = response.json();
                   window.sessionStorage.setItem("myJWT", myJson.JWT);
               } else {
                   setFormMessage(loginForm, "error", "Invalid username/password combination");
                   console.log("Login Failed")
               }
           })
   });

   document.querySelectorAll(".formInput").forEach(inputElement => {
       inputElement.addEventListener("blur", e => {
           if (e.target.id === "signupUsername" && e.target.value.length > 0 && e.target.value.length < 4) {
               setInputError(inputElement, "Username must be at least 4 characters long")
           }
       });

       inputElement.addEventListener("input", e => {
          clearInputError(inputElement);
       });
   });

});


