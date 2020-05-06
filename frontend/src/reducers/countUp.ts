import { Reducer } from "redux";
import { countUpAction } from "../actions";
import Store from "../states";

type Action = ReturnType<typeof countUpAction>
const countUp:Reducer<Store.CountUp, Action> = (
  initialState = { count: 0 },
  action
) => {
  switch (action.type) {
    case "Count_Up":
      return {count: action.count}
    default:
      return initialState
  }
};

export default countUp;
