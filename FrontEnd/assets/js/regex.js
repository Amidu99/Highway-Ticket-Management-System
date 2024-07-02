export const addressPattern = /^[a-zA-Z0-9 ',.-]{3,}$/;
export const digitsPattern = /^\d{4}$/;
export const modelPattern = /^[a-zA-Z0-9 ',.-]{2,}$/;
export const contactPattern = /^(?:[0-9] ?){6,14}[0-9]$/;
export const vehicleNoPattern = /^.*-\d{4}$/;
export const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
export const namePattern = /^[A-Za-z\s]{3,}$/;
export const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;