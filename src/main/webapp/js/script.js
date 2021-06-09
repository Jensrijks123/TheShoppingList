// wachtwoord is 3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303


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

   // Fetch sing up
    createAccountForm.addEventListener("submit", (e) => {
       e.preventDefault();

       var formSignupData = new FormData(document.querySelector("#createAccount"));
       var encSignupData = new URLSearchParams(formSignupData.entries());

       fetch("/restservices/LoginSignup/signup", {method: "POST", body: encSignupData})
           .then(function (response) {
              if (response.ok) {
                  setFormMessage(createAccountForm,"success", "");
                  console.log("Sign up succeeded");
                  setFormMessage(createAccountForm, "ok", "Your account has been registered");
                  alert("Your account has been registered");
                  window.location.href="home.html";
                  return response.json();
              } else {
                  setFormMessage(createAccountForm, "error", "Invalid username/password/email combination");
                  console.log("Sign up Failed");
              }
           })
           .then(myJson => console.log(myJson)).catch(error => console.log(error))
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

        fetch("/restservices/LoginSignup/profile", fetchOptions)
            .then(function(response){
                if (response.ok) return response.json();
            }).then(myJson => console.log(myJson)).catch(error => console.log(error))
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




