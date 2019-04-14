import StoreState from '../storeState';

export const SEND_LOGIN_REQUEST = 'SEND_LOGIN_REQUEST';

interface SendLoginRequest {
    type: typeof SEND_LOGIN_REQUEST
    payload: StoreState
}

export type SimpleActionTypes = SendLoginRequest;