import { SEND_LOGIN_REQUEST, SimpleActionTypes } from '../types/action/simpleActionType';

export function simpleAction(doNothing:any): SimpleActionTypes {
    return {
        payload: {
            cookie: 'SIMPLE_ACTION'
        },
        type: SEND_LOGIN_REQUEST
    };
};