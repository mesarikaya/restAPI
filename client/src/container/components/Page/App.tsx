import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from "react-router";

// Add styling related imports
import '../../../stylesheets/css/App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import carouselPart1 from '../../../stylesheets/images/carouselImagePart1.png';
import carouselPart2 from '../../../stylesheets/images/carouselImagePart2.png';
import carouselPart3 from '../../../stylesheets/images/carouselImagePart3.png';
import { Carousel, CardColumns} from 'react-bootstrap';

// import child component
import GroupSearchForm from '../Forms/GroupSearchForm';
import NavigationBar from '../Navigation/NavigationBar';
import { LoginFormFields } from '../../../redux/types/userInterface/loginFormFields';
import GroupCard from '../Cards/GroupCard';

// import types
import { GroupSearchFormFields } from '../../../redux/types/userInterface/groupSearchFormFields';
import { store } from '../../../redux/store';
import { SecurityState } from '../../../redux/types/system/securityState';
import { SearchGroups } from 'src/redux/actions/groupSearchAction';

export interface Props {
    system: SecurityState;
    loginFormFields: LoginFormFields;
    groupSearchFormFields: GroupSearchFormFields;
    onSubmit: typeof SearchGroups;
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
            <div className="container">
                <div className="row align-items-center">
                    <div className="col-xs-12 col-sm-12 col-md-12 col-lg-8 col-xl-7 my-auto">  
                        <div className="mx-auto my-auto" >
                            <Carousel className="openingCarousel"
                                interval={5000}
                                nextIcon={<span aria-hidden="true"/>}
                                prevIcon={<span aria-hidden="true"/>}
                                fade={true}>
                                    <Carousel.Item>
                                        <img
                                        className="carouselImage d-block w-100"
                                        src={carouselPart1}
                                        alt="First slide"
                                        />
                                    </Carousel.Item>
                                    <Carousel.Item>
                                        <img
                                        className="carouselImage d-block w-100"
                                        src={carouselPart2}
                                        alt="Third slide"
                                        />
                                    </Carousel.Item>
                                    <Carousel.Item>
                                        <img
                                        className="carouselImage d-block w-100"
                                        src={carouselPart3}
                                        alt="Third slide"
                                        />
                                    </Carousel.Item>
                                </Carousel>
                        </div>
                    </div>

                    <div className="col-xs-12 col-sm-12 col-md-12 col-lg-4 col-xl-5 my-auto"> 
                        <div className="container mx-auto my-auto">
                            <div className="searchFormRow mx-auto my-auto">
                                <GroupSearchForm 
                                  token={this.state.system.token} 
                                  formFields={this.props.groupSearchFormFields}
                                  onSubmit={this.props.onSubmit}
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <br />

                <div className="container">
                    <CardColumns>
                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>

                        <GroupCard name="ArnhemToBolwerk" groupDetails={{"originCity":"Arnhem", "originZipCode":"2012Ed", 
                        "originRange":0.5, "destinationCity": "Bolwerk", "destinationZipCode":"2104ER", "destinationRange":0.5}}
                        members={[{"userName":"Ergin", "userEmail":"test@gm.com"}]}/>
                    </CardColumns>

                </div>

            </div>
            
        </div>);
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

const mapDispatchToProps = (dispatch: any) => {
    return {
        onSubmit: (
            e: React.FormEvent<HTMLFormElement>, 
            formFields: GroupSearchFormFields,
            token: string,
            ) => dispatch(SearchGroups(e, formFields, token))
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

export  default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));
