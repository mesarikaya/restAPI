import * as React from 'react';
import { Button } from 'react-bootstrap';
import { LoginFormFields } from './redux/types/userInterface/loginFormFields';
import LoginButton from './LoginButton';
import Logo from '../src/logo.svg';

// Add styling related imports
import './stylesheets/css/App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import './stylesheets/css/NavBar.css';


export interface Props {
    loginFormFields: LoginFormFields;
};

class NavigationBar extends React.Component<Props> {
    

    constructor(props: Props) {
        super(props);
    }

    public render() {

        return (
            <React.Fragment>
                {/* <!- Navigation Bar --> */}
                <nav className="navbar fixed-top">

                    <div className="container">
                        {/* <!- Search Form --> */}
                        <div className="row box">
                            <div className="col-12 col-sm-5 branding">
                                <a href="/" className="navbar-brand" >
                                    <img className="img-fluid rounded-circle img_logo App-logo"
                                    src={Logo} alt="" style={{ maxWidth: '3rem', height: '3rem' }} />
                                       GreenerCommute
                                </a>
                            </div>
                            
                            <div className="col-12 col-sm-7 navButtonGroup">
                                <LoginButton loginFormFields={this.props.loginFormFields}/>
                                <Button className="navButton signOutButton" variant="link">
                                    <i className="fas fa-sign-out-alt">
                                        <strong id="icons"> Sign out</strong>
                                    </i>
                                </Button>
                                <Button className="navButton signUpButton" variant="link">
                                    <i className="fas fa-user-plus">
                                        <strong id="icons"> Sign up</strong>
                                    </i>
                                </Button>
                                <Button className="navButton aboutButton" variant="link">
                                    <i className="fas fa-info">
                                        <strong id="icons"> About</strong>
                                    </i>
                                </Button>
                            </div>
                        </div>
                    </div>
                </nav>
            </React.Fragment>
        );
  }
}

export default NavigationBar;
