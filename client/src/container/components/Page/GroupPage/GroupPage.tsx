import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from "react-router";

// Creaate history variable to be able to go back and forth within routes
//  import createBrowserHistory from 'history/createBrowserHistory';
// const history = createBrowserHistory({ forceRefresh: true });

// Import store and types
import { store } from 'src/redux/store';
import { StoreState } from 'src/redux/types/storeState';
import { GroupSearchResult } from 'src/redux/types/userInterface/groupSearchResult';
import NavigationBar from '../../Navigation/NavigationBar';
import { LoginFormFields } from 'src/redux/types/userInterface/loginFormFields';
import { isNullOrUndefined } from 'util';
import GroupMemberTable from './GroupMemberTable';


/** CREATE Prop and State interfaces to use in the component */
// Set the default Props
export interface GroupProps{
    groupInfo: GroupSearchResult;
    loginFormFields: LoginFormFields;
}

export interface GroupState{
    groupInfo: GroupSearchResult;
    storeState: StoreState;
}

// These props are provided by the router
interface PathProps {
    history: any;
    location: any;
    match: any;
}


class GroupPage extends React.Component<GroupProps & RouteComponentProps < PathProps >, GroupState>{

    public state: GroupState;

    constructor(props: GroupProps& RouteComponentProps < PathProps >){

        super(props);
        const currAppState = store.getState();
        const localStorageSelectedGroup = window.localStorage.getItem('selectedGroupCard');

        // tslint:disable-next-line: no-console
        console.log("Local storage selected group:", localStorageSelectedGroup);
        let selectedGroup:GroupSearchResult= {
            id: '',
            name: '',
            groupDetails: {
                originCity: '',
                originZipCode: '',
                originRange: 2,
                destinationCity: '',
                destinationZipCode: '',
                destinationRange: 2 
            },
            members: {
                users: []
            },
            waitingList:{
                users: []
            }
        };

        if (!isNullOrUndefined(localStorageSelectedGroup)){
            selectedGroup = JSON.parse(localStorageSelectedGroup);
        }
        
        // tslint:disable-next-line: no-console
        console.log("Selected group JSON:", localStorageSelectedGroup);
        
        this.state = {
            groupInfo: selectedGroup,
            storeState: currAppState
        }
    }

    public componentDidUpdate(oldProps: GroupProps& RouteComponentProps < PathProps >) {
        
        const newProps = this.props;
        if(oldProps.groupInfo !== newProps.groupInfo) {
            this.setState({ 
                groupInfo: this.props.groupInfo 
            });
        }
    }

    public render() {
        return (
            <div className="GroupPage">

            <NavigationBar loginFormFields={this.props.loginFormFields} />
            <div className="container"/>
                <GroupMemberTable key={this.state.groupInfo.id} 
                                  groupInfo={this.state.groupInfo}
                                  userName={this.state.storeState.system.userName}/>
            </div>
        );
    }
}

// Create mapToState and mapDispatch for Redux
const mapStateToProps = (
    state: GroupState, 
    OwnProps: GroupProps & RouteComponentProps<PathProps>
    ) => {
    return {
        groupInfo: state.groupInfo,
        storeState: state.storeState
    }
}

// TODO: Add user search dispatch
/*const mapDispatchToProps = (dispatch: any) => {
    return {
        onSubmit: (
            e: React.FormEvent<HTMLFormElement>, 
            formFields: GroupSearchFormFields,
            existingGroups: GroupSearchResult[],
            page: number,
            token: string,
            ) => dispatch(SearchGroups(e, formFields, existingGroups, page, token))
    }
}*/

export default withRouter(connect(mapStateToProps, null)(GroupPage));
