import { SecurityState } from "../system/SecurityState";

export const SEND_LOGIN_REQUEST = 'SEND_LOGIN_REQUEST';

interface SendLoginRequest {
    type: typeof SEND_LOGIN_REQUEST
    payload: SecurityState
}

export type JwtAuthActionTypes = SendLoginRequest;