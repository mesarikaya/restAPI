import * as React from 'react';
import '../../../stylesheets/css/inputRange.css';

export interface Props {
  value: string|number;
};

export interface State {
  value: string|number;
};

class InputRange extends React.Component<Props, State> {

  public state: State;

  constructor(props:Props){
    super(props);

    this.state={
      value: 2
    };

    this.increase = this.increase.bind(this);
    this.decrease = this.decrease.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  public decrease = () => {
    
    const rangeValue = Number(this.state.value);
    
    if(this.isValidEntry(rangeValue)){
      if (rangeValue-1<0){
        this.setState({ value: 0 }); // Set to 0 for negatives
      }else{
        this.setState({ value: rangeValue - 1 });
      }
    } else{
      this.setState({ value: 0 }); // Set to default: 0
    }
  }

  public handleInputChange = async (event: any): Promise<void> => {
    // tslint:disable-next-line: no-console
    console.log("Event details:", Number(event.currentTarget.value));
    const enteredRange = this.setRange(Number(event.target.value));
    this.setState({ value: enteredRange});
  }

  public increase = () => {

    const rangeValue = Number(this.state.value);
    
    if(this.isValidEntry(rangeValue)){
      if (rangeValue+1>1000){
        this.setState({ value: 1000 }); // Set to a default upper limit
      }else{
        this.setState({ value: rangeValue + 1 });
      }
    }else{
      this.setState({ value: 0 }); // Set to default: 0
    }
  }
  
  public render() {

    const rangeValue = this.setRange(this.state.value).toString();

    return (
        <div className="def-number-input number-input">
          <button onClick={this.decrease} className="minus" type="button"/>
          <input onChange={this.handleInputChange}
            className="quantity" name="quantity" value={rangeValue} type="number"/>
          <button onClick={this.increase} className="plus" type="button" />
        </div>
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

export default InputRange;