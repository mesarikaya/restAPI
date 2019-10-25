import * as React from 'react';

// Import types
import { GroupUser } from 'src/redux/types/userInterface/groupUser';
// import { GroupCardFields } from 'src/redux/types/userInterface/groupCardFields';

// Styling imports
import { Card } from 'react-bootstrap';
import '../../../stylesheets/css/cards/groupCard.css';

interface Props {
    key: string,
    name: string;
    groupDetails: any;
    members: GroupUser;
}


class GroupCard extends React.Component<Props>{

    constructor(props: Props){
        super(props);
    }

    public render(){
        return (
            <React.Fragment>
                        
                {/* <!- Group Card design --> */}
                <Card className="groupCard" border="warning" style={{ width: '18rem' }}>
                    <Card.Header><Card.Title className="card-header-text">Group: {this.props.name}</Card.Title></Card.Header>
                    <Card.Body>
                        <Card.Text className="card-text">
                        {this.props.groupDetails.originCity}, {this.props.groupDetails.originZipCode} (+/-{this.props.groupDetails.originRange} km)
                        </Card.Text>
                        <Card.Text className="card-text">
                            <i className="fas fa-angle-double-down fa-7x"/>
                        </Card.Text>            
                        <Card.Text className="card-text">
                        {this.props.groupDetails.destinationCity} {this.props.groupDetails.originZipCode} (+/-{this.props.groupDetails.destinationRange} km)
                        </Card.Text>
                    </Card.Body>
                </Card>
                <br />

            </React.Fragment>
        );
    }
}

export default GroupCard;