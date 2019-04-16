import createHistory from 'history/createBrowserHistory';
import { routerMiddleware } from 'react-router-redux';
import { applyMiddleware, createStore } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import logger from 'redux-logger'
import thunk from 'redux-thunk';
import { StoreState } from './types/system/storeState';
import { JwtAuthActionTypes } from './types/action/jwtAuthActionType';
import { rootReducer, AppState } from "./reducers/rootReducer";

// Create history
export const history = createHistory();

// Set initial state
const system: StoreState = {
    cookie: "none",
    loggedIn: false,
    userName: "guest"
};

const initialState: AppState = {
    system
};

// Build the middleware for intercepting and dispatching navigation actions
const myRouterMiddleware = routerMiddleware(history);

export const store = createStore<AppState, JwtAuthActionTypes, any,  any>(
        rootReducer,
        initialState,
        composeWithDevTools(applyMiddleware(myRouterMiddleware, thunk, logger))
);


