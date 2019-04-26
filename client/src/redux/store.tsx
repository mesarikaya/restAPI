import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'react-router-redux';
import { applyMiddleware, createStore } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import logger from 'redux-logger'
import thunk from 'redux-thunk';
import { JwtAuthActionTypes } from './types/action/jwtAuthActionType';
import { rootReducer, AppState } from "./reducers/rootReducer";
import { SecurityState } from './types/system/SecurityState';

// Create history
export const history = createBrowserHistory();

// Set initial state
const system: SecurityState = {
    cookie: "none",
    loggedIn: false,
    userName: "guest"
};

const initialState: AppState = {
    system
};

// Build the middleware for intercepting and dispatching navigation actions
const myRouterMiddleware = routerMiddleware(history);

// Create the store with redicer, initial state and middleware
export const store = createStore<AppState, JwtAuthActionTypes, any,  any>(
        rootReducer,
        initialState,
        composeWithDevTools(applyMiddleware(myRouterMiddleware, thunk, logger))
);


