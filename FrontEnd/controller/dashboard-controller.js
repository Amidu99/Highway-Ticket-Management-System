import { UserServiceUrl } from "../assets/js/urls.js";
export let userName;
const userProfileButton = document.getElementById("user-profile-btn");
const userVehiclesButton = document.getElementById("user-vehicles-btn");
const getTicketButton = document.getElementById("get-ticket-btn");
const paymentsButton = document.getElementById("payments-btn");
const userProfilePage = document.getElementById("user-profile");
const userVehiclesPage = document.getElementById("user-vehicles");
const getTicketPage = document.getElementById("get-ticket");
const paymentsPage = document.getElementById("payments");

// navigations
userProfileButton.onclick = function() {
    userProfilePage.style.display = "block";
    userVehiclesPage.style.display = "none";
    getTicketPage.style.display = "none";
    paymentsPage.style.display = "none";
};

userVehiclesButton.onclick = function() {
    if (!$("#vehicleNo").val()){ $("#vehicle_btns > button[type='button']").eq(3).click();}
    userProfilePage.style.display = "none";
    userVehiclesPage.style.display = "block";
    getTicketPage.style.display = "none";
    paymentsPage.style.display = "none";
};

getTicketButton.onclick = function() {
    if (!$("#fine-amount").val()){ $("#ticket_btns > button[type='button']").eq(1).click();}
    userProfilePage.style.display = "none";
    userVehiclesPage.style.display = "none";
    getTicketPage.style.display = "block";
    paymentsPage.style.display = "none";
};

paymentsButton.onclick = function() {
    if (!$("#amount").val()){ $("#payment_btns > button[type='button']").eq(1).click();}
    userProfilePage.style.display = "none";
    userVehiclesPage.style.display = "none";
    getTicketPage.style.display = "none";
    paymentsPage.style.display = "block";
};

document.addEventListener('DOMContentLoaded', async function () {
    $("#email").val(localStorage.getItem('CurrentUser'));
    userProfilePage.style.display = "block";
    userVehiclesPage.style.display = "none";
    getTicketPage.style.display = "none";
    paymentsPage.style.display = "none";
    await getUserByEmail()
});

// get user
async function getUserByEmail() {
    let email = localStorage.getItem('CurrentUser');
    const url = new URL(`${UserServiceUrl}/get`);
    try {
        const response = await fetch(url, {method: 'GET', headers: {"email": email}});
        if (response.status === 200) {
            const data = await response.json();
            userName = data.name;
            $("#current_user_name").text('Hi , '+userName);
        } else {console.error('Error:', response.status, response.statusText);}
    } catch (error) {console.error('Error:', error);}
}

// Logout
$("#btn-logout").on("click", () => {
    Swal.fire({
        width: '300px', title: 'Logout', icon: 'question',
        text: "Are you sure you want to logout?",  iconColor: '#FF7E00FF',
        showCancelButton: true, confirmButtonText: 'Logout'
    }).then((result) => {
        if (result.isConfirmed) {
            localStorage.removeItem('AuthToken');
            localStorage.removeItem('RefreshToken');
            localStorage.removeItem('CurrentUser');
            window.location.href = '../login/login.html';
            history.pushState(null, null, '../login/login.html');
            window.addEventListener('popstate', function(event) {
                history.pushState(null, null, '../login/login.html');
            });
        }
    });
});