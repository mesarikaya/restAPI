import * as React from 'react';
import '../../../stylesheets/css/inputRange.css';


class InputRange extends React.Component {

  public state = {
    value: 0
  }

  public decrease = () => {

    if (this.state.value-1<0){
      this.setState({ value: 0 });
    }else{
      this.setState({ value: this.state.value - 1 });
    }
    
  }

  public increase = () => {
    if(this.state.value+1>20){
      this.setState({ value: 20});
    }else{
      this.setState({ value: this.state.value + 1 });
    }
  }

  public render() {
    return (
        <div className="def-number-input number-input">
          <button onClick={this.decrease} className="minus"/>
          <input className="quantity" name="quantity" value={this.state.value}
          type="number"/>
          <button onClick={this.increase} className="plus" />
        </div>
      );
  }
}

export default InputRange;