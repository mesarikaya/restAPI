import * as React from 'react';
import {Button, Form, InputGroup, Modal } from 'react-bootstrap';
import './stylesheets/css/App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import './stylesheets/css/login.css';
import {RouteComponentProps} from "react-router";
import Switch from "react-switch";

export interface Props {
    rememberMe: boolean;
    show: boolean;
    validated: boolean;
};

export interface State {
    rememberMe: boolean;
    show: boolean;
    validated: boolean;
};

// These props are provided by the router
interface PathProps {
    history: any;
    location: any;
    match: any;
}

class App extends React.Component<Props & RouteComponentProps<PathProps>, State> {
    public state: State;

    constructor(props: Props & RouteComponentProps<PathProps>, context: any) {
        super(props, context);

        this.state = {
            rememberMe: false,
            show: false,
            validated: false
        };

        this.handleModalShow = this.handleModalShow.bind(this);
        this.handleModalClose = this.handleModalClose.bind(this);
        this.handleRememberMeChange = this.handleRememberMeChange.bind(this);
    }

    public handleModalClose() {
        this.setState({ show: false });
    }

    public handleModalShow() {
        this.setState({ show: true });
    }

    public handleRememberMeChange(checked: boolean) {
        this.setState({ rememberMe: checked });
    }

    public handleSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {

        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        this.setState({ validated: true });

        // TODO: MAKE AN AJAX CALL
        // TODO: VALIDATE ON THE REST CONTROLLER AND RETURN ERROR OR THE LOGIN STATUS AND SAVE COOKIE
    };


    public render() {

        const validated = this.state.validated;
        return (
          <div className="App">
              <Button variant="primary" onClick={this.handleModalShow}>
                  Launch demo modal
              </Button>

              <Modal dialogClassName={"modalDialog"} show={this.state.show} onHide={this.handleModalClose}>
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
                                          />
                                          <InputGroup.Append>
                                              <InputGroup.Text>
                                                  <i className="fas fa-user-shield" />
                                              </InputGroup.Text>
                                          </InputGroup.Append>
                                          <Form.Control.Feedback type="invalid" className="invalid-feedback usernameError text-center">
                                              Invalid username!
                                          </Form.Control.Feedback>
                                      </InputGroup>
                                  </Form.Row>

                                  <Form.Row>
                                      <InputGroup>
                                          <Form.Control
                                              required={true}
                                              type="password"
                                              id={"password"}
                                              name={"password"}
                                              placeholder="password"
                                          />
                                          <InputGroup.Append>
                                              <div className="input-group-text">
                                                  <i className="far fa-eye" />
                                              </div>
                                          </InputGroup.Append>
                                          <Form.Control.Feedback type="invalid" className="invalid-feedback passwordError text-center">
                                              Invalid password!
                                          </Form.Control.Feedback>
                                      </InputGroup>
                                  </Form.Row>

                                  <div className="form-row mt-3 mb-3">
                                      <label className={"my-auto"}>
                                          <Switch
                                              onChange={this.handleRememberMeChange}
                                              checked={this.state.rememberMe}
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
          </div>
        );
  }
}

export default App;
