import { SecurityState } from "../system/securityState";

export const SEND_LOGIN_REQUEST = 'SEND_LOGIN_REQUEST';

interface SendLoginRequest {
    type: typeof SEND_LOGIN_REQUEST
    payload: SecurityState
}

export type JwtAuthActionTypes = SendLoginRequest;