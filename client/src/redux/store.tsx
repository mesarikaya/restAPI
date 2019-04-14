import createHistory from 'history/createBrowserHistory';
import { routerMiddleware } from 'react-router-redux';
import { createStore, applyMiddleware } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import logger from 'redux-logger'
import thunk from 'redux-thunk';
import StoreState from '../redux/types/storeState';
import { SimpleActionTypes } from '../redux/types/action/simpleActionType';
import simpleReducer from "./reducers/simpleReducer";

// Create history
export const history = createHistory();

// Set initial state
const initialState = {
    cookie: "none"
};

// Build the middleware for intercepting and dispatching navigation actions
const myRouterMiddleware = routerMiddleware(history);

export const store = createStore<StoreState, SimpleActionTypes, any,  any>(
        simpleReducer,
        initialState,
        composeWithDevTools(applyMiddleware(myRouterMiddleware, thunk, logger))
);


