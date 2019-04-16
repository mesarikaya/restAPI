import { JwtAuthActionTypes, SEND_LOGIN_REQUEST } from "../types/action/jwtAuthActionType";
import { StoreState } from '../types/system/storeState';

export function updateAuthAction(newSession: StoreState): JwtAuthActionTypes {
    return {
        payload: newSession,
        type: SEND_LOGIN_REQUEST
    };
};