import {AuthServiceUrl} from "../assets/js/urls.js";
import {emailPattern, passwordPattern} from "../assets/js/regex.js";
import {showError} from "../assets/js/notifications.js";

const container = $('#container');

$('#signUpToggle').on('click', () => {
    container.addClass('active');
});

$('#signInToggle').on('click', () => {
    container.removeClass('active');
});

// signIn
$("#signin-btn").on("click", async () => {
    let email = $("#signIn-email").val();
    let password = $("#signIn-password").val();
    if ( email && password ) {
        if (emailPattern.test(email)) {
            const signInObject = { email: email, password: password };
            const signInJSON = JSON.stringify(signInObject);
            const signinURL = new URL(`${AuthServiceUrl}/signIn`);
            try {
                const response = await fetch(signinURL, {
                    method: 'POST',
                    body: signInJSON,
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                if (response.ok) {
                    const token = await response.text();
                    setTokens(token, email);
                } else if (response.status===403) {Swal.fire({width: '215px', icon: "error", title: "Login failed", footer: '<b>Check your Email & Password!</b>', showConfirmButton: false, timer: 3000 });}
            } catch (error) { console.error('Error:', error); }
        } else { showError('Invalid email input!');}
    } else { showError('Fields can not be empty!');}
});

// signUp
$("#signup-btn").on("click", async () => {
    let name = $("#signUp-name").val();
    let email = $("#signUp-email").val();
    let phone = $("#signUp-phone").val();
    let address = $("#signUp-address").val();
    let password = $("#signUp-password").val();
    let re_password = $("#signUp-rePassword").val();
    if ( name && email && phone && address && password && re_password ) {
        if (emailPattern.test(email)) {
            if (password===re_password) {
                if (passwordPattern.test(password)) {
                    const signUpObject = { name: name, email: email, phone: phone, address: address, password: password };
                    const signUpJSON = JSON.stringify(signUpObject);
                    const signupURL = new URL(`${AuthServiceUrl}/signUp`);
                    try {
                        const response = await fetch(signupURL, {
                            method: 'POST',
                            body: signUpJSON,
                            headers: {
                                "Content-Type": "application/json"
                            }
                        });
                        if (response.status === 200) {
                            const token = await response.text();
                            setTokens(token, email);
                        } else if (response.status===204) {Swal.fire({width: '240px', icon: "error", title: "Signup failed", footer: '<b>This email does not belong to an employee!</b>', showConfirmButton: false, timer: 3000 });
                        } else if (response.status===205) {Swal.fire({width: '240px', icon: "error", title: "Signup failed", footer: '<b>This email is already associated with a user!</b>', showConfirmButton: false, timer: 3000 });}
                    } catch (error) { console.error('Error:', error); }
                } else { Swal.fire({width: "400px", title: "Invalid Password pattern!", text: "Password must be 8+ characters, with uppercase, lowercase, digit, and special character (e.g., !@#$%^&*).", icon: "warning", iconColor: "red"});}
            } else { showError('Passwords did not match!');}
        } else { showError('Invalid email input!');}
    } else { showError('Fields can not be empty!');}
});

function setTokens(token, email) {
    const parsedData = JSON.parse(token);
    const tokenString = parsedData.token;
    const tokens = tokenString.split('|');
    const firstToken = tokens[0].trim();
    const secondToken = tokens[1].trim();
    localStorage.setItem('AuthToken', firstToken);
    localStorage.setItem('RefreshToken', secondToken);
    localStorage.setItem('CurrentUser', email);
    window.location.href = '../dashboard/dashboard.html';
    history.pushState(null, null, '../dashboard/dashboard.html');
    window.addEventListener('popstate', function(event) {
        history.pushState(null, null, '../dashboard/dashboard.html');
    });
}