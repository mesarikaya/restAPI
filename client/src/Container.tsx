import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from "react-router";
import { Route, Switch, withRouter } from 'react-router-dom';
import StoreState from './redux/types/storeState';

// Import the presentational components for this container
import App from './App';
import {store} from "./redux/store";

export interface Props {
    cookie: string
};

// These props are provided by the router
interface PathProps {
    history: any;
    location: any;
    match: any;
}

class Container extends React.Component<Props & RouteComponentProps<PathProps>, StoreState> {
    public state: StoreState;

    constructor(props: Props & RouteComponentProps<PathProps>) {
        super(props);

        const currAppState = store.getState();
        this.state = {
            cookie: currAppState.cookie
        };
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

export function mapStateToProps(state: StoreState, OwnProps: Props & RouteComponentProps<PathProps>) {
    return {
        cookie: state.cookie
    }
}

export default withRouter(connect(mapStateToProps, null)(Container));