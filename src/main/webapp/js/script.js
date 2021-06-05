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
                   setFormMessage(loginForm,"success", "");
                   console.log("Login succeeded")
                   window.location.href="home.html";
                   return response.json();
               } else {
                   setFormMessage(loginForm, "error", "Invalid username/password combination");
                   console.log("Login Failed");
               }
           })
           .then(myJson => window.sessionStorage.setItem("myJWT", myJson.JWT))
   });

    loginForm.addEventListener("submit", (e) => {

        var fetchOptions = { method: "GET",
            headers : {
                'Authorization' : 'Bearer ' + window.sessionStorage.getItem("myJWT")
            }}

        fetch("/restservices/shoppinglist/profile", fetchOptions)
            .then(function(response){
                if (response.ok) return response.json();
            }).then(myJson => console.log(myJson)).catch(error => console.log(error))
    })

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




