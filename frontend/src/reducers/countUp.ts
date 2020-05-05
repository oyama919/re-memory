import { Reducer } from "redux";
import { countUpAction } from "../actions";
import store from "../states";

type Action = ReturnType<typeof countUpAction>
const countUp:Reducer<store["countUp"], Action> = (
  initState: number = 0,
  action
) => {
  switch (action.type) {
    case "Count_Up":
      return action.count;
    default:
      return initState;
  }
};

export default countUp;