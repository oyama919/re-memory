import { combineReducers } from "redux";

const countUp = (
  initState = 0,
  action
) => {
  switch (action.type) {
    case "Count_Up":
      return action.count;
    default:
      return initState;
  }
};

export default combineReducers({countUp});