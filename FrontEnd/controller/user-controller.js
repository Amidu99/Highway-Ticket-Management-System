import {UserServiceUrl} from "../assets/js/urls.js";
import {passwordPattern, namePattern, contactPattern, addressPattern} from "../assets/js/regex.js";
import {showError, showSwalError} from "../assets/js/notifications.js";
const user_btns = $("#user_btns > button[type='button']");

// update user profile
user_btns.eq(0).on("click", async () => {
    let email = $("#email").val();
    let name = $("#name").val();
    let phone = $("#phone").val();
    let address = $("#address").val();
    let password = $("#password").val();

    if ( email && name && phone && address && password ) {
        if (passwordPattern.test(password)) {
            if (namePattern.test(name)) {
                if (contactPattern.test(phone)) {
                    if (addressPattern.test(phone)) {
                        let userObject = {
                            name: name,
                            email: email,
                            phone: phone,
                            address: address,
                            password: password
                        };
                        let userJSON = JSON.stringify(userObject);
                        $.ajax({
                            url: `${UserServiceUrl}/update`,
                            type: "PUT",
                            data: userJSON,
                            headers: {"Content-Type": "application/json"},
                            success: (res) => {
                                Swal.fire({width: '225px', position: 'center', icon: 'success', title: 'Updated!', showConfirmButton: false, timer: 2000});
                                user_btns.eq(1).click();
                            },
                            error: (err) => {
                                showSwalError('Error', 'An error occurred while proceeding. Please try again later.');
                            }
                        });
                    } else { showError('Invalid address input!');}
                } else { showError('Invalid contact number input!');}
            } else { showError('Invalid name input!');}
        } else {
            Swal.fire({
                width: "325px", title: "Invalid Password pattern!",
                text: "Password must be 8+ characters, with uppercase, lowercase, digit, and special character (e.g., !@#$%^&*).",
                icon: "warning", iconColor: "red"
            });
        }
    } else { showError('Fields can not be empty!');}
});

// reset user form
user_btns.eq(1).on("click", async () => {
    $("#email").val(localStorage.getItem('CurrentUser'));
    $("#name").val('');
    $("#phone").val('');
    $("#address").val('');
    $("#password").val('');
});