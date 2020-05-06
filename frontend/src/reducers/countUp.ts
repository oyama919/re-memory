import { Reducer } from "redux";
import { countUpAction } from "../actions";
// import store from "../states";

type Action = ReturnType<typeof countUpAction>
const countUp:Reducer<{count: number;}, Action> = (
  initState = {count: 0},
  action
) => {
  switch (action.type) {
    case "Count_Up":
      return {count: action.count}
    default:
      return initState
  }
};

export default countUp;