import {Vehicle} from "../model/Vehicle.js";
import {VehicleServiceUrl} from "../assets/js/urls.js";
import {showError, showSwalError} from "../assets/js/notifications.js";
import {modelPattern, vehicleNoPattern} from "../assets/js/regex.js";
const vehicle_btns = $("#vehicle_btns > button[type='button']");
let ownerEmail = null;
let vehicle_row_index = null;

// save vehicle
vehicle_btns.eq(0).on("click", async () => {
    let vehicleNo = $("#vehicleNo").val();
    let make = $("#make_select").val();
    let type = $("#type_select").val();
    let model = $("#model").val();
    if (vehicleNo && model) {
        if (vehicleNoPattern.test(vehicleNo)) {
            if (make!=="0") {
                if (type!=="0") {
                    if (modelPattern.test(model)) {
                        let vehicleObject = new Vehicle(vehicleNo, make, type, model, ownerEmail);
                        let vehicleJSON = JSON.stringify(vehicleObject);
                        $.ajax({
                            url: `${VehicleServiceUrl}/save`,
                            type: "POST",
                            data: vehicleJSON,
                            headers: {
                                "Content-Type": "application/json"
                            },
                            success: function(res) {
                                Swal.fire({width: '225px', position: 'center', icon: 'success',
                                    title: 'Saved!', showConfirmButton: false, timer: 2000});
                                vehicle_btns.eq(3).click();
                            },
                            error: function(xhr, status, err) {
                                showSwalError('Error', xhr.responseText);
                            }
                        });
                    } else { showError('Invalid model input!');}
                } else { showError('Select Type!');}
            } else { showError('Select Make!');}
        } else { showError('Invalid vehicleNo input!');}
    } else { showError('Fields can not be empty!');}
});

// update vehicle
vehicle_btns.eq(1).on("click", async () => {
    let vehicleNo = $("#vehicleNo").val();
    let make = $("#make_select").val();
    let type = $("#type_select").val();
    let model = $("#model").val();
    if (vehicleNo && model) {
        if (vehicleNoPattern.test(vehicleNo)) {
            if (make!=="0") {
                if (type!=="0") {
                    if (modelPattern.test(model)) {
                        let vehicleObject = new Vehicle(vehicleNo, make, type, model, ownerEmail);
                        let vehicleJSON = JSON.stringify(vehicleObject);
                        $.ajax({
                            url: `${VehicleServiceUrl}/update`,
                            type: "PUT",
                            data: vehicleJSON,
                            headers: {
                                "Content-Type": "application/json"
                            },
                            success: function(res, status, xhr) {
                                if (xhr.status === 200) {
                                    Swal.fire({width: '225px', position: 'center', icon: 'success',
                                        title: 'Updated!', showConfirmButton: false, timer: 2000});
                                    vehicle_btns.eq(3).click();
                                } else if (xhr.status === 204) { showSwalError('Error',`${vehicleNo} : Vehicle does not exist.`);}
                            },
                            error: function(xhr, status, err) { showSwalError('Error', 'An error occurred while proceeding. Please try again later.');}
                        });
                    } else { showError('Invalid model input!');}
                } else { showError('Select Type!');}
            } else { showError('Select Make!');}
        } else { showError('Invalid vehicleNo input!');}
    } else { showError('Fields can not be empty!');}
});

// delete vehicle
vehicle_btns.eq(2).on("click", async () => {
    let vehicleNo = $("#vehicleNo").val();
    if (vehicleNo) {
        if (vehicleNoPattern.test(vehicleNo)) {
            Swal.fire({
                width: '300px', titleText: 'Delete Vehicle', icon: 'question',
                text: "Are you sure you want to permanently remove this vehicle?", iconColor: '#FF7E00FF',
                showCancelButton: true, confirmButtonText: 'Yes, delete!'
            }).then((result) => {
                if (result.isConfirmed) {
                    const url = new URL(`${VehicleServiceUrl}/delete/${vehicleNo}`);
                    $.ajax({
                        url: url,
                        type: "DELETE",
                        success: function(res, status, xhr) {
                            if (xhr.status === 200) {
                                Swal.fire({width: '225px', position: 'center', icon: 'success',
                                    title: 'Deleted!', showConfirmButton: false, timer: 2000});
                                vehicle_btns.eq(3).click();
                            } else if (xhr.status === 204) { showSwalError('Error',`${vehicleNo} : Vehicle does not exist.`);}
                        },
                        error: function(xhr, status, err) { showSwalError('Error', 'An error occurred while proceeding. Please try again later.');}
                    });
                }
            });
        } else { showError('Invalid vehicleNo input!');}
    } else { showError('VehicleNo can not be empty!');}
});

// reset vehicle form
vehicle_btns.eq(3).on("click", async () => {
    $("#vehicleNo").val('');
    $("#make_select").val("0");
    $("#type_select").val("0");
    $("#model").val('');
    ownerEmail = localStorage.getItem('CurrentUser');
    loadVehicleData();
});

// load all user's vehicle details to the table
const loadVehicleData = () => {
    const getAllURL = new URL(`${VehicleServiceUrl}/getAllByEmail?email=${ownerEmail}`);
    fetch(getAllURL, { method: 'GET' })
        .then(response => {
            if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data)) {
                $('#vehicle_tbl_body').empty();
                data.forEach(vehicle => {
                    let record = `<tr><td class="vehicleNo">${vehicle.vehicleNo}</td>
                                  <td class="make">${vehicle.make}</td>
                                  <td class="type">${vehicle.type}</td>
                                  <td class="model">${vehicle.model}</td></tr>`;
                    $("#vehicle_tbl_body").append(record);
                });
            } else { console.error('Error: Expected JSON array, but received: ', data); }
        })
        .catch(error => { console.error('Error: ', error); });
};

// retrieve vehicle by table click
$("#vehicle_tbl_body").on("click", "tr", function() {
    vehicle_row_index = $(this).index();
    let vehicleNo = $(this).find(".vehicleNo").text();
    let make = $(this).find(".make").text();
    let type = $(this).find(".type").text();
    let model = $(this).find(".model").text();
    $("#vehicleNo").val(vehicleNo);
    $("#make_select").val(make);
    $("#type_select").val(type);
    $("#model").val(model);
});