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
        for (const obj in data) {
            if (data.hasOwnProperty(obj)) {
                if (this.props.userName === data[obj].userId){
                    rows.push(
                      <tr>
                        <td>{data[obj].userName}</td>
                        <td>{data[obj].owner ? 'Owner' : 'Member'}</td>
                        <td>{data[obj].owner ?  <Button>Leave</Button> : <Button>Remove</Button>}</td>
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
        }
        return rows;
    }

    public render(){
        const tableRows = this.createTable(this.props.groupInfo.members.users);
        return (
            <React.Fragment>
                
                <div className="container p-3">
                    <div className="row align-items-center">
                        {/* <!- Group Member Overview Card design --> */}
                        <div className="col-xs-12 col-sm-12 col-md-12 col-lg-8 col-xl-7 my-auto">
                            <Card className="GroupMemberTableCard">
                                <Card.Header>
                                    <Card.Title className="card-header-text text-center">
                                        Group: {this.props.groupInfo.name}    
                                    </Card.Title>
                                    <Card.Subtitle className="card-header-text mx-auto text-center">
                                        {this.props.groupInfo.groupDetails.originCity}, {this.props.groupInfo.groupDetails.originZipCode} <i className="fas fa-angle-double-right fa-3x"/><i className="fas fa-angle-double-right fa-3x"/> {this.props.groupInfo.groupDetails.destinationCity}, {this.props.groupInfo.groupDetails.originZipCode}   
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



