import * as React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps, withRouter } from "react-router";

// Creaate history variable to be able to go back and forth within routes
//  import createBrowserHistory from 'history/createBrowserHistory';
// const history = createBrowserHistory({ forceRefresh: true });

// Add styling related imports
import '../../../../stylesheets/css/cards/GroupPage.css';

// Import store and types
import { store } from 'src/redux/store';
import { StoreState } from 'src/redux/types/storeState';
import { GroupSearchResult } from 'src/redux/types/userInterface/groupSearchResult';
import NavigationBar from '../../Navigation/NavigationBar';
import { LoginFormFields } from 'src/redux/types/userInterface/loginFormFields';
import { isNullOrUndefined } from 'util';
import GroupMemberTable from './GroupMemberTable';
import { GroupUser } from '../../../../redux/types/userInterface/groupUser';
import GroupWaitingList from './GroupWaitingList';
import { CardDeck, Button } from 'react-bootstrap';


/** CREATE Prop and State interfaces to use in the component */
// Set the default Props
export interface GroupProps{
    groupInfo: GroupSearchResult;
    loginFormFields: LoginFormFields;
}

export interface GroupState{
    groupInfo: GroupSearchResult;
    storeState: StoreState;
    isUserInGroup: boolean;
    isUserOwnerInGroup: boolean;
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
        
        const groupUser = this.isUserInGroup(selectedGroup.members.users);
        const groupOwner = this.isOwnerInGroup(selectedGroup.members.users);
        // tslint:disable-next-line: no-console
        console.log("Group page user status:", groupUser, groupOwner);
        this.state = {
            groupInfo: selectedGroup,
            storeState: currAppState,
            isUserInGroup:groupUser,
            isUserOwnerInGroup: groupOwner
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

    public isUserInGroup(data:GroupUser[]) {
        const isUserInGroup = data.some((obj:GroupUser) => obj.userId===store.getState().system.userName);
        return isUserInGroup;
    } 
    

    public isOwnerInGroup(data:GroupUser[]) {
        
        const isOwnerInGroup = data.some((obj:GroupUser) => obj.owner && obj.userId===store.getState().system.userName);
        return isOwnerInGroup;
    } 

    public render() {
        return (
            <div className="GroupPage">
                <NavigationBar loginFormFields={this.props.loginFormFields} />
                <div className="container px-0 mx-auto">
                    <h2 className="text-center">                                
                        {this.state.groupInfo.name}: <span>
                                                        {this.state.groupInfo.groupDetails.originCity}, {this.state.groupInfo.groupDetails.originZipCode}
                                                        <i className="fas fa-angle-double-right fa-3x"/><i className="fas fa-angle-double-right fa-3x"/> 
                                                        {this.state.groupInfo.groupDetails.destinationCity},    
                                                        {this.state.groupInfo.groupDetails.originZipCode} 
                                                        {this.state.isUserOwnerInGroup && this.state.isUserInGroup ? <Button variant="info" size="sm">Delete Group</Button>: null}
                                                     </span>
                    </h2>
                    <CardDeck>
                        <GroupMemberTable key={this.state.groupInfo.id} 
                                          groupInfo={this.state.groupInfo}
                                          userName={this.state.storeState.system.userName}
                                          isUserInGroup={this.state.isUserInGroup}
                                          isUserOwnerInGroup={this.state.isUserOwnerInGroup}/>

                        <GroupWaitingList key={this.state.groupInfo.id} 
                                          groupInfo={this.state.groupInfo}
                                          userName={this.state.storeState.system.userName}
                                          isUserInGroup={this.state.isUserInGroup}
                                          isUserOwnerInGroup={this.state.isUserOwnerInGroup}/>
                    </CardDeck>
                </div>
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
