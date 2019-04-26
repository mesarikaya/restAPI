import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from "react-router";
// Add styling related imports
import './stylesheets/css/App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import { store } from './redux/store';
import { SecurityState } from './redux/types/system/SecurityState';
import { LoginFormFields } from './redux/types/userInterface/loginFormFields';
import NavigationBar from './NavigationBar';


export interface Props {
    system: SecurityState;
    loginFormFields: LoginFormFields;
};

export interface State {
    system: SecurityState;
};

// These props are provided by the router
interface PathProps {
    history: any;
    location: any;
    match: any;
}

class App extends React.Component<Props & RouteComponentProps<PathProps>, State> {
    
    public state: State;

    constructor(props: Props & RouteComponentProps<PathProps>) {
        super(props);

        const currentState = store.getState();

        // tslint:disable-next-line:no-console
        console.log('App - Current Store State is: ', currentState);

        this.state = {
            system: currentState.system
        };
    }

    public componentDidUpdate(oldProps: Props) {
        
        const newProps = this.props;
        if(oldProps.system !== newProps.system) {
          this.setState({ system:this.props.system });
        }
    }

    public render() {

        return (
          <div className="App">

            <NavigationBar loginFormFields={this.props.loginFormFields} />
            

          </div>
        );
  }
}

// Create mapToState and mapDispatch for Redux
const mapStateToProps = (
    state: State, 
    OwnProps: Props & RouteComponentProps<PathProps>
    ) => {
    return {
        system: state.system
    }
}

/*const mapDispatchToProps = (dispatch: any) => {
    return {
        onLoginSubmit: (
            e: React.FormEvent<HTMLFormElement>, 
            formFields: LoginFormFields
            ) => dispatch(UpdateAuth(e, formFields))
    }
}*/

export  default withRouter(connect(mapStateToProps, null)(App));
