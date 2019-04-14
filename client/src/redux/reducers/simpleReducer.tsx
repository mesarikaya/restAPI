import { SimpleActionTypes } from "../types/action/simpleActionType";
import StoreState from "../types/storeState";

export default (state: StoreState, action: SimpleActionTypes) => {
    switch (action.type) {
        case "SEND_LOGIN_REQUEST":
            return {
                ...state,
                cookie: action.payload.cookie
            }
        default:
            return state
    }
};