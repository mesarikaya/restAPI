import { combineReducers } from 'redux';
import { loginReducer } from './jwtAuthReducer';

export const rootReducer = combineReducers({
    system: loginReducer
});

export type AppState = ReturnType<typeof rootReducer>

