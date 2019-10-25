import { combineReducers } from 'redux';
import { loginReducer } from './jwtAuthReducer';
import { groupSearchReducer } from './groupSearchReducer';

export const rootReducer = combineReducers({
    system: loginReducer,
    groupSearchResults: groupSearchReducer
});

export type AppState = ReturnType<typeof rootReducer>

