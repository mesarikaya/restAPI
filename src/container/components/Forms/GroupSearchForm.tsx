import * as React from 'react';
import { Form, InputGroup, FormLabel, Button } from 'react-bootstrap';

// Add styling related imports
import '../../../stylesheets/css/App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import '../../../stylesheets/css/GroupSearch.css';

import { GroupSearchFormFields } from '../../../redux/types/userInterface/groupSearchFormFields';
import InputRange from '../Buttons/InputRange';
import { SearchGroups } from '../../../redux/actions/groupSearchAction';
import { submitForm } from '../../utilities/submitForm';

export interface Props {
    formFields: GroupSearchFormFields;
    token: string;
    onSubmit: typeof SearchGroups;
};

export interface State {
    formFields: GroupSearchFormFields;
};

const MAX_RANGES=1000;
const MIN_RANGES=0;

class GroupSearchForm extends React.Component<Props, State> {
    
    public state: State;

    constructor(props: Props) {
        super(props);

        this.state = {
            formFields: {
                origin: '',
                originRange: 2,
                destination: '',
                destinationRange: 2,
                validated: false
            }
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleInputRangeChange = this.handleInputRangeChange.bind(this);
        this.increase = this.increase.bind(this);
        this.decrease = this.decrease.bind(this);
    }

    // TODO: Need to FIND A WAY TO USE THE RIGHT TYPE WITHOUT ERROR React.ChangeEvent<HTMLInputElement>
    public handleChange = async (event: any): Promise<void> => {

        // tslint:disable-next-line: no-console
        console.log("Event handler on change: ", event.currentTarget.value);
        
        // read the form input fields
        const formFields = { ...this.state.formFields };
        formFields[event.currentTarget.name] = event.currentTarget.value;
        this.setState({
            formFields
        });
    }

    public handleSubmit = async (event: React.FormEvent<HTMLFormElement>): Promise<void> => {

        // TODO: Add button disable
        submitForm(event);
        
        this.setState({ formFields: {
            ...this.state.formFields,
            validated: true
        }});

        // TODO: Deactivate Button -- disable
        
        // MAKE AN AJAX CALL
        this.props.onSubmit(event, this.state.formFields, this.props.token);

        // TODO: VALIDATE ON THE REST CONTROLLER AND RETURN ERROR OR THE SEARCH STATUS AND SAVE COOKIE

    };

    public decrease = (name: string) => {
    
        if (name==="originRange"){

            const rangeValue = this.setRange(this.state.formFields.originRange);
            
                const formFields = {
                    ...this.state.formFields,
                    originRange: Math.max(Number(rangeValue) - 1, MIN_RANGES)
                };
                this.setState({formFields});
        }else if(name==="destinationRange"){

            const rangeValue = this.setRange(this.state.formFields.destinationRange);
            const formFields = {
                ...this.state.formFields,
                destinationRange: Math.max(Number(rangeValue) - 1, MIN_RANGES)
            };
            this.setState({formFields});
        }
    }
        
    public increase = (name:string) => {
        
        if (name==="originRange"){

            const rangeValue = this.setRange(this.state.formFields.originRange);
            const formFields = {
                ...this.state.formFields,
                originRange: Math.min(Number(rangeValue) + 1, MAX_RANGES)
            };
            this.setState({formFields});
        }else if(name==="destinationRange"){

            const rangeValue = Number(this.state.formFields.destinationRange);  
            const formFields = {
                ...this.state.formFields,
                destinationRange: Math.min(Number(rangeValue) + 1, MAX_RANGES)
            };
            this.setState({formFields});
        }
    }

    public handleInputRangeChange = async (name: string, value: number): Promise<void> => {
        
        const rangeValue = Math.max(0, Math.min(value,MAX_RANGES));
        if (name==="originRange"){
            this.setState({ formFields: {
                ...this.state.formFields,
                originRange: rangeValue
            }});
        }else if (name==="destinationRange") {
            this.setState({ formFields: {
                ...this.state.formFields,
                destinationRange: rangeValue
            }});
        }
    };

    public render() {

        const validated = this.state.formFields.validated;
        return (
          <React.Fragment>

            <div className="searchForm">
                                                
                <h1 className="joinAGroupText">Join<span> a group</span>?</h1>
                <p className="joinAGroupSubText">Search with ease based on the origin and destination radius</p>
                
                <Form name="groupSearchForm" className="groupSearchFormValidation" 
                noValidate={true} validated = {validated} onSubmit = {this.handleSubmit}>
                    <Form.Row> 
                        <InputGroup className="justify-content-center">
                            {/* <!- Origin Input Group --> */}
                            <Form.Group className="originInputFormGroup" controlId="originAdressInputControl">
                                {/* <!- Origin Input text --> */}
                                <Form.Label className="originLabel" ><strong>Origin</strong></Form.Label>
                                <Form.Control 
                                    type="input"
                                    id={"origin"}
                                    name={"origin"}
                                    placeholder="Address/zip code" 
                                    required={true}
                                    onChange={this.handleChange}
                                />
                            </Form.Group>
                            {/* <!- Origin Input Range Group --> */}
                            <Form.Group controlId="originAdressRangeControl">
                                {/* <!- Origin Range --> */}
                                <FormLabel className="originRangeDropdownLabel" htmlFor="originRangeDropDownMenuButton">
                                <strong>Range</strong>
                                </FormLabel>
                                <InputRange 
                                    name={"originRange"}
                                    value={this.state.formFields.originRange}
                                    onChangeValue={this.handleInputRangeChange}
                                    onIncrease={this.increase}
                                    onDecrease={this.decrease}
                                />
                            </Form.Group>
                        </InputGroup>
                    
                        <InputGroup className="justify-content-center">
                            {/* <!- Destination Input Group --> */}
                            <Form.Group className="destinationInputFormGroup" controlId="destinationAdressInputControl">
                                {/* <!- Destination Input text --> */}
                                <Form.Label className="destinationLabel" >
                                    <strong>Destination</strong>
                                </Form.Label>
                                <Form.Control 
                                    type="input"
                                    id={"destination"}
                                    name={"destination"}
                                    placeholder="Address/zip code" 
                                    required={true}
                                    onChange={this.handleChange}
                                />
                            </Form.Group>
                            {/* <!- Destination Input Range Group --> */}
                            <Form.Group controlId="destinationAdressRangeControl">
                                {/* <!- Destination Range --> */}
                                <FormLabel className="destinationRangeDropdownLabel" htmlFor="destinationRangeDropDownMenuButton">
                                    <strong>Range</strong>
                                </FormLabel>
                                <InputRange 
                                    name={"destinationRange"}
                                    value={this.state.formFields.destinationRange}
                                    onChangeValue={this.handleInputRangeChange}
                                    onIncrease={this.increase}
                                    onDecrease={this.decrease}
                                />
                            </Form.Group>
                        </InputGroup>
                    </Form.Row>
                    <Button size="lg" type="submit"> Search </Button>
                </Form>
            </div>
          </React.Fragment>
        );
  }

  private isValidEntry(entry: string|number){
    entry = Number(entry);
    return (isNaN(entry) || entry<0 || typeof entry !== 'undefined' || entry==='' || entry === null);
  }

  private setRange(entry: string|number){
    const rangeValue = Number(entry);
    return (this.isValidEntry(rangeValue)) ? rangeValue: 0;
  }
}

export default GroupSearchForm;
