import {TicketServiceUrl, VehicleServiceUrl} from "../assets/js/urls.js";
import {showError, showSwalError} from "../assets/js/notifications.js";
import {Ticket} from "../model/Ticket.js";
const ticket_btns = $("#ticket_btns > button[type='button']");
let ownerEmail = null;
let entranceVal = null;
let departureVal = null;
let fineAmount = null;

// reset ticket form
ticket_btns.eq(1).on("click", async () => {
    $("#vehicle-select").empty();
    $("#entrance-select").val("0");
    $("#departure-select").val("0");
    $("#fine-amount").val('');
    entranceVal = null;
    departureVal = null;
    fineAmount = null;
    ownerEmail = localStorage.getItem('CurrentUser');
    loadVehicleData();
});

// load all vehicles to the select box
const loadVehicleData = () => {
    let title = $('<option>', { text: '- Set Vehicle -', value: "0" });
    $("#vehicle-select").append(title);
    const getAllURL = new URL(`${VehicleServiceUrl}/getAllByEmail?email=${ownerEmail}`);
    fetch(getAllURL, { method: 'GET' })
        .then(response => {
            if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data)) {
                data.forEach(vehicle => {
                    let option = $('<option>', { text: vehicle.make+" "+vehicle.model, value: vehicle.vehicleNo });
                    $("#vehicle-select").append(option);
                });
            } else { console.error('Error: Expected JSON array, but received: ', data); }
        })
        .catch(error => { console.error('Error: ', error); });
};

// when change the entrance-select
$("#entrance-select").on("change", function () {
    if (departureVal !== $(this).val() && $(this).val()!=="0") {
        entranceVal = $(this).val();
        if (departureVal!=="0" && $("#departure-select").val()!=="0") {
            calcFineAmount();
        }
    } else {
        entranceVal = "0";
        $("#entrance-select").val("0");
        $("#fine-amount").val('');
        showError('Invalid Entrance!');
    }
});

// when change the departure-select
$("#departure-select").on("change", function () {
    if (entranceVal !== $(this).val() && $(this).val()!=="0") {
        departureVal = $(this).val();
        if (entranceVal!=="0" && $("#entrance-select").val()!=="0") {
            calcFineAmount();
        } else {
            showError('Select valid Entrance!');
        }
    } else {
        departureVal = "0";
        $("#departure-select").val("0");
        $("#fine-amount").val('');
        showError('Invalid Departure!');
    }
});

// calc FineAmount
function calcFineAmount() {
    fineAmount = (entranceVal - departureVal) * 10;
    fineAmount = Math.abs(fineAmount);
    $("#fine-amount").val(fineAmount);
}

// get ticket
ticket_btns.eq(0).on("click", async () => {
    let date = new Date();
    let issueDate = date.toISOString();
    let status = "PENDING";
    let fineAmount = $("#fine-amount").val();
    let vehicleNo = $("#vehicle-select").val();
    if (vehicleNo && fineAmount && vehicleNo!=="0" && $("#entrance-select").val()!=="0" && $("#departure-select").val()!=="0") {
        let ticketObject = new Ticket(issueDate, status, fineAmount, vehicleNo, ownerEmail);
        let ticketJSON = JSON.stringify(ticketObject);
        $.ajax({
            url: `${TicketServiceUrl}/save`,
            type: "POST",
            data: ticketJSON,
            headers: {
                "Content-Type": "application/json"
            },
            success: function(res) {
                Swal.fire({width: '225px', position: 'center', icon: 'success',
                    title: 'Saved!', showConfirmButton: false, timer: 2000});
                ticket_btns.eq(1).click();
            },
            error: function(xhr, status, err) {
                showSwalError('Error', xhr.responseText);
            }
        });
    } else { showError('Fields can not be empty!');}
});