import { JwtAuthActionTypes, SEND_LOGIN_REQUEST } from "../types/action/jwtAuthActionType";
import { SecurityState } from '../types/system/securityState';


// Set initial state
const initialState: SecurityState   = {
    cookie: "none",
    loggedIn: false,
    userName: "guest",
    token: ''
};

export function loginReducer(
    state = initialState,
    action: JwtAuthActionTypes
    ): SecurityState {

    switch (action.type) {

        case SEND_LOGIN_REQUEST:

            // tslint:disable-next-line:no-console
            console.log('Inside SEND_LOGIN_REQUEST REDUCER, PAYLOAD IS: ', action.payload);

            return Object.assign({}, state, {
                cookie: action.payload.cookie,
                loggedIn: action.payload.loggedIn,
                userName: action.payload.userName,
                token: action.payload.token
            });
        default:
            return state
    }
};