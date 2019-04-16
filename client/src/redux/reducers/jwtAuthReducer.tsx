import { JwtAuthActionTypes } from "../types/action/jwtAuthActionType";
import { StoreState } from "../types/system/storeState";


// Set initial state
const initialState: StoreState = {
    cookie: "none",
    loggedIn: false,
    userName: "guest"
};

export function loginReducer(
    state = initialState,
    action: JwtAuthActionTypes
    ): StoreState {
    switch (action.type) {
        case "SEND_LOGIN_REQUEST":
            return Object.assign({}, state, {
                cookie: action.payload.cookie,
                loggedIn: action.payload.loggedIn,
                userName: action.payload.userName,
            })
        default:
            return state
    }
};