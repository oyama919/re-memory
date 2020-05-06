import React from 'react';
import { connect } from "react-redux";
import { countUpAction } from "../actions";
import { Action, Dispatch } from "redux";
import Store from '../states/index';

const mapStateToProps = (state: Store): Store.CountUp => (state.countUp);

interface DispatchToProps {
  countUpEvent: () => void;
}

const mapDispatchToProps = (dispatch: Dispatch<Action>): DispatchToProps => ({
  countUpEvent: () => dispatch(countUpAction())
});

type Props = ReturnType<typeof mapStateToProps> & ReturnType<typeof mapDispatchToProps>;
const CountUp = (props: Props): JSX.Element => {
  return (
    <div className="App">
      <p>{props.count}</p>
      <button onClick={()=>{props.countUpEvent()}}>Count UP</button>
    </div>
  );
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CountUp)
