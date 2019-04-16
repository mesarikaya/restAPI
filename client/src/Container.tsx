import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from "react-router";
import { Route, Switch, withRouter } from 'react-router-dom';
import { AppState } from './redux/reducers/rootReducer';
import { StoreState } from './redux/types/system/storeState';
import { updateAuthAction } from './redux/actions/jwtAuthAction';

// Import the presentational components for this container
import App from './App';
import {store} from "./redux/store";

const mapStateToProps = (
    state: AppState, 
    OwnProps: AppProps & RouteComponentProps<PathProps>
    ) => ({
    system: state.system
})  

interface AppProps {
    updateSession: typeof updateAuthAction,
    system: StoreState
}

// These props are provided by the router
interface PathProps {
    history: any;
    location: any;
    match: any;
}

class Container extends React.Component<AppProps & RouteComponentProps<PathProps>, AppState> {
    public state: AppState;

    constructor(props: AppProps & RouteComponentProps<PathProps>) {
        super(props);
    
        this.state = store.getState();
    }

    public render() {

        return (
            <Switch>
                <Route exact={true} path="/" component={App}/>
                <Route path="/**" component={App} />
            </Switch>
        );
    }
}

export default withRouter(connect(mapStateToProps, null)(Container));