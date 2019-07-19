import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from "react-router";
import { Route, Switch, withRouter } from 'react-router-dom';
import { UpdateAuth } from '../redux/actions/jwtAuthAction';

// Import the presentational components for this container
import App from './components/Page/App';
import {store} from "../redux/store";
import { SecurityState } from '../redux/types/system/securityState';


interface AppProps {
    updateSession: typeof UpdateAuth,
    system: SecurityState
}

// These props are provided by the router
interface PathProps {
    history: any;
    location: any;
    match: any;
}

export interface State {
    system: SecurityState;
};

class Container extends React.Component<AppProps & RouteComponentProps<PathProps>, State> {
    public state: State;

    constructor(props: AppProps & RouteComponentProps<PathProps>) {
        
        super(props);

        // tslint:disable-next-line:no-console
        console.log('Container - Current Store State is: ', store.getState());
        const currentState = store.getState().system;

        this.state = {
            system: currentState.system
        };
    }

    public componentDidUpdate(oldProps: AppProps) {
        
        const newProps = this.props;
        if(oldProps.system !== newProps.system) {
          this.setState({ system: this.props.system });
        }
    }

    public render() {

        return (
            <Switch>
                <Route exact={true} path="/" component={App} />
                <Route path="/**" component={App} />
            </Switch>
        );
    }
}

const mapStateToProps = (
    state: State, 
    OwnProps: AppProps & RouteComponentProps<PathProps>
    ) => ({
    system: state.system
})  

export default withRouter(connect(mapStateToProps, null)(Container));