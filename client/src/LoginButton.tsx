import * as React from 'react';
import { connect } from 'react-redux';
import { LoginFormFields } from './redux/types/userInterface/loginFormFields';
import Switch from "react-switch";
import { Button, Form, InputGroup, Modal } from 'react-bootstrap';

// Add styling related imports
import './stylesheets/css/App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import './stylesheets/css/Login.css';
import { UpdateAuth } from './redux/actions/jwtAuthAction';

export interface Props {
    loginFormFields: LoginFormFields;
    onLoginSubmit: typeof UpdateAuth
};

export interface State {
    loginFormFields: LoginFormFields;
};

class LoginButton extends React.Component<Props, State> {
    
    public state: State;

    constructor(props: Props) {
        super(props);

        this.state = {
            loginFormFields: {
                userName: '',
                password: '',
                rememberMe: false,
                show: false,
                validated: false
            }
        };

        this.handleModalShow = this.handleModalShow.bind(this);
        this.handleModalClose = this.handleModalClose.bind(this);
        this.handleRememberMeChange = this.handleRememberMeChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    public handleModalClose() {
        this.setState({ loginFormFields: {
                ...this.state.loginFormFields,
                show: false
            } 
        });
    }

    public handleModalShow() {
        this.setState({ loginFormFields: {
                ...this.state.loginFormFields,
                show: true
            } 
        });
    }

    public handleRememberMeChange(checked: boolean) {
        this.setState({ loginFormFields: {
                ...this.state.loginFormFields,
                rememberMe: checked
            }
        });
    }

    // TODO: Need to FIND A WAY TO USE THE RIGHT TYPE WITHOUT ERROR React.FormEvent<HTMLInputElement>
    public handleChange = async (event: any): Promise<void> => {

        // read the form input fields
        const loginFormFields = { ...this.state.loginFormFields };
        loginFormFields[event.currentTarget.name] = event.currentTarget.value;
        this.setState({
            loginFormFields
        });
    }

    public handleSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {

        // TODO: Add button disable
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }

        this.setState({ loginFormFields: {
            ...this.state.loginFormFields,
            validated: true
        }});

        // TODO: Deactivate Button disable
        
        // MAKE AN AJAX CALL
        this.props.onLoginSubmit(event, this.state.loginFormFields);

        // TODO: VALIDATE ON THE REST CONTROLLER AND RETURN ERROR OR THE LOGIN STATUS AND SAVE COOKIE

    };

    public render() {

        const validated = this.state.loginFormFields.validated;
        return (
          <React.Fragment>

              <Button className="navButton signInButton" variant="link" onClick={this.handleModalShow} >
                  <i className="fas fa-sign-in-alt">
                    <strong id="icons"> Sign in</strong>
                  </i>
              </Button>

              <Modal dialogClassName={"modalDialog"} show={this.state.loginFormFields.show} onHide={this.handleModalClose}>
                  <Modal.Header className={"modalHeader"} closeButton={true} />
                  <Modal.Body>

                      <div className="container mb-2 modalContainer">
                          <div className="row-fluid">
                              <Form name="loginForm" className="needsLoginFormValidation"
                                    noValidate={true}
                                    validated = {validated}
                                    onSubmit={this.handleSubmit}>
                                  <h2 className="loginHeader text-center">Login</h2>

                                  <div className="error" />

                                  <Form.Row className="mt-4">
                                      <InputGroup>
                                          <Form.Control
                                              required={true}
                                              type="email"
                                              id={"username"}
                                              name={"username"}
                                              placeholder="username"
                                              onChange={this.handleChange}
                                          />
                                          <InputGroup.Append>
                                              <InputGroup.Text>
                                                  <i className="fas fa-user-shield" />
                                              </InputGroup.Text>
                                          </InputGroup.Append>
                                          <Form.Control.Feedback type="invalid"
                                           className="invalid-feedback usernameError text-center">
                                              Invalid username!
                                          </Form.Control.Feedback>
                                      </InputGroup>
                                  </Form.Row>

                                  <Form.Row>
                                      <InputGroup  >
                                          <Form.Control
                                              required={true}
                                              type="password"
                                              id={"password"}
                                              name={"password"}
                                              placeholder="password"
                                              onChange={this.handleChange}
                                          />
                                          <InputGroup.Append>
                                              <div className="input-group-text">
                                                  <i className="far fa-eye" />
                                              </div>
                                          </InputGroup.Append>
                                          <Form.Control.Feedback type="invalid"
                                           className="invalid-feedback passwordError text-center">
                                              Invalid password!
                                          </Form.Control.Feedback>
                                      </InputGroup>
                                  </Form.Row>

                                  <div className="form-row mt-3 mb-3">
                                      <label className={"my-auto"}>
                                          <Switch
                                              onChange={this.handleRememberMeChange}
                                              checked={this.state.loginFormFields.rememberMe}
                                              offColor={"#f46950"}
                                          />
                                      </label>
                                      <span className="rememberMeText my-auto">Remember me</span>
                                      <a className="helpText ml-auto my-auto" href="/loginHelp">Need help?</a>
                                  </div>

                                  <div className="col text-center my-auto">
                                      <button className="btn btn-primary submitButton" type="submit">
                                          Submit
                                      </button>
                                  </div>
                              </Form>
                          </div>
                      </div>
                  </Modal.Body>
              </Modal>
          </React.Fragment>
        );
  }
}


const mapDispatchToProps = (dispatch: any) => {
    return {
        onLoginSubmit: (
            e: React.FormEvent<HTMLFormElement>, 
            formFields: LoginFormFields
            ) => dispatch(UpdateAuth(e, formFields))
    }
}

export default connect(null, mapDispatchToProps)(LoginButton);
