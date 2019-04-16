import { StoreState } from '../system/storeState';

export const SEND_LOGIN_REQUEST = 'SEND_LOGIN_REQUEST';

interface SendLoginRequest {
    type: typeof SEND_LOGIN_REQUEST
    payload: StoreState
}

export type JwtAuthActionTypes = SendLoginRequest;