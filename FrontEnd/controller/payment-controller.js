import {PaymentServiceUrl, TicketServiceUrl} from "../assets/js/urls.js";
import {showError, showSwalError} from "../assets/js/notifications.js";
import {digitsPattern} from "../assets/js/regex.js";
const payment_btns = $("#payment_btns > button[type='button']");
let ownerEmail = null;
let id = null;
let amount = null;

// reset ticket form
payment_btns.eq(1).on("click", async () => {
    $("#ticket-select").empty();
    $("#bank-select").val("0");
    $("#amount").val('');
    $("#card_digits").val('');
    id = null;
    amount = null;
    ownerEmail = localStorage.getItem('CurrentUser');
    loadTicketData();
});

// load all pending tickets to the select box
const loadTicketData = () => {
    let title = $('<option>', { text: '- Set Ticket -', value: "0" });
    $("#ticket-select").append(title);
    const getAllURL = new URL(`${TicketServiceUrl}/getAllPending?email=${ownerEmail}`);
    fetch(getAllURL, { method: 'GET' })
        .then(response => {
            if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data)) {
                let i = 1;
                data.forEach(ticket => {
                    let option = $('<option>', { text: "Ticket "+i, value: ticket.id+"|"+ticket.fineAmount });
                    console.log(ticket.id);
                    console.log(ticket.fineAmount);
                    $("#ticket-select").append(option);
                    i++;
                });
            } else { console.error('Error: Expected JSON array, but received: ', data); }
        })
        .catch(error => { console.error('Error: ', error); });
};

// when change the ticket-select
$("#ticket-select").on("change", function () {
    let ticket = $(this).val();
    if (ticket!=="0") {
        const values = ticket.split('|');
        id = values[0].trim();
        amount = values[1].trim();
        $("#amount").val(amount);
    } else { $("#amount").val(''); }
});

// pay ticket
payment_btns.eq(0).on("click", async () => {
    let bank = $("#bank-select").val();
    let digits = $("#card_digits").val();
    if (id && amount && $("#ticket-select").val()!=="0") {
        if (bank!=="0") {
            if (digitsPattern.test(digits)) {
                let payObject = { id: id, paidDate: "", amount: amount, payInfo: bank+" - "+digits };
                let payJSON = JSON.stringify(payObject);
                $.ajax({
                    url: `${PaymentServiceUrl}/save`,
                    type: "POST",
                    data: payJSON,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    success: function(res) {
                        Swal.fire({width: '225px', position: 'center', icon: 'success',
                            title: 'Payed!', showConfirmButton: false, timer: 2000});
                        payment_btns.eq(1).click();
                    },
                    error: function(xhr, status, err) {
                        showSwalError('Error', xhr.responseText);
                    }
                });
            } else { showError('Invalid Card Digits!');}
        } else { showError('Invalid Bank!');}
    } else { showError('Invalid Ticket!');}
});