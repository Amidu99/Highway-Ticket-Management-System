export class Ticket {
    constructor(issueDate, status, fineAmount, vehicleNo, email) {
        this.issueDate = issueDate;
        this.status = status;
        this.fineAmount = fineAmount;
        this.vehicleNo = vehicleNo;
        this.email = email;
    }
}