const container = document.querySelector(".container");
const btnSignIn = document.getElementById("signIn");
const btnSignUp = document.getElementById("signUp");

btnSignIn.addEventListener("click", () => {
    container.classList.remove("toggle");
})

btnSignUp.addEventListener("click", () => {
    container.classList.add("toggle");
})