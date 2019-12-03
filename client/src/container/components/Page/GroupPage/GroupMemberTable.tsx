import * as React from 'react';

// Import the store and types
import { GroupSearchResult } from 'src/redux/types/userInterface/groupSearchResult';
import { GroupUser } from 'src/redux/types/userInterface/groupUser';
import { Table, Card, Button } from 'react-bootstrap';


export interface Props {
    key: string;
    groupInfo: GroupSearchResult;
    userName: string;
}

export interface State {
    rows: Table;
}

class GroupMemberTable extends React.Component<Props,{}>{


    constructor(props:Props){
        super(props);
        this.createTable = this.createTable.bind(this);
        this.isUserInGroup = this.isUserInGroup.bind(this);
        this.isOwnerInGroup = this.isOwnerInGroup.bind(this);
    }

    public componentDidUpdate(oldProps: Props) {
        
        const newProps = this.props;
        if(oldProps.groupInfo !== newProps.groupInfo) {
            this.setState({ 
                groupInfo: this.props.groupInfo 
            });
        }
    }

    public createTable(data: GroupUser[]) {

        // tslint:disable-next-line: no-console
        console.log("Loading the data", data);
        const rows = [];
        const isUserInGroup = this.isUserInGroup(this.props.groupInfo.members.users);
        const isOwnerInGroup = this.isOwnerInGroup(this.props.groupInfo.members.users);
        for (const obj in data) {
            if (data.hasOwnProperty(obj)) {
                if(isUserInGroup){
                    if(isOwnerInGroup) {
                        if (this.props.userName === data[obj].userId){
                            rows.push(
                              <tr>
                                <td>{data[obj].userName}</td>
                                <td>Owner</td>
                                <td><Button variant="info" size="sm">Leave</Button></td>
                              </tr>
                            );
                        }else{
                            rows.push(
                                <tr>
                                  <td>{data[obj].userName}</td>
                                  <td>{data[obj].owner ? 'Owner' : 'Member'}</td>
                                  <td>{data[obj].owner ? null : <Button variant="info" size="sm">Remove</Button>}</td>
                                </tr>
                            );
                        }
                    } else {
                        if (this.props.userName === data[obj].userId){
                            rows.push(
                                <tr>
                                  <td>{data[obj].userName}</td>
                                  <td>{data[obj].owner ? 'Owner' : 'Member'}</td>
                                  <td><Button variant="info" size="sm">Leave</Button></td>
                                </tr>
                            );
                        }else{
                            rows.push(
                                <tr>
                                    <td>{data[obj].userName}</td>
                                    <td>{data[obj].owner ? 'Owner' : 'Member'}</td>
                                    <td/>
                                </tr>
                            );
                        }
                    }
                } else {
                    rows.push(
                        <tr>
                            <td>{data[obj].userName}</td>
                            <td>{data[obj].owner ? 'Owner' : 'Member'}</td>
                            <td/>
                        </tr>
                    );
                }
            }
        }

        return rows;
    }

    public isUserInGroup(data:GroupUser[]) {

        const isUserInGroup = data.some((obj:GroupUser) => obj.userId===this.props.userName);
        return isUserInGroup;
    } 
    

    public isOwnerInGroup(data:GroupUser[]) {

        const isOwnerInGroup = data.some((obj:GroupUser) => obj.owner===true && obj.userId===this.props.userName);
        return isOwnerInGroup;
    } 

    public render(){

        const tableRows = this.createTable(this.props.groupInfo.members.users);
        const isUserInGroup = this.isUserInGroup(this.props.groupInfo.members.users);
        const isOwnerInGroup = this.isOwnerInGroup(this.props.groupInfo.members.users);
        return (
            <React.Fragment>
                
                <div className="container p-4">
                    <div className="row align-items-center">
                        {/* <!- Group Member Overview Card design --> */}
                        <div className="col-xs-12 col-sm-12 col-md-12 col-lg-8 col-xl-7 my-auto">
                            <Card className="GroupMemberTableCard">
                                <Card.Header>
                                    <Card.Title className="card-header-text text-center">
                                        Group: {this.props.groupInfo.name}    
                                    </Card.Title>
                                    <Card.Subtitle className="card-header-text mx-auto text-center">
                                        {this.props.groupInfo.groupDetails.originCity}, {this.props.groupInfo.groupDetails.originZipCode}
                                         <i className="fas fa-angle-double-right fa-3x"/><i className="fas fa-angle-double-right fa-3x"/> 
                                         {this.props.groupInfo.groupDetails.destinationCity}, 
                                         {this.props.groupInfo.groupDetails.originZipCode} 
                                         {isOwnerInGroup && isUserInGroup ? <Button variant="info" size="sm">Delete Group</Button>: null}   
                                   
                                    </Card.Subtitle>
                                </Card.Header>
                                <Card.Body>
                                    <Table responsive={true}>
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Status</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                                {tableRows}
                                        </tbody> 
                                    </Table>
                                </Card.Body>
                            </Card>
                        </div>
                        {/* <!- Group Waiting List Design --> */}
                        <div className="col-xs-12 col-sm-12 col-md-12 col-lg-4 col-xl-5 my-auto">
                            Place for Waiting list Design
                        </div>
                    </div>
                </div>
                {/* <!- User Search Design --> */}

            </React.Fragment>
        );
    }
}

export default GroupMemberTable;



