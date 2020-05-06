import React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import { createStore,applyMiddleware,compose, } from "redux";
import logger from 'redux-logger'
import rootReducer from "./reducers";
import { CountUp } from "./components/index";

interface ExtendedWindow extends Window {
  __REDUX_DEVTOOLS_EXTENSION_COMPOSE__?: typeof compose;
}
declare var window: ExtendedWindow;
// eslint-disable-next-line
const composeReduxDevToolsEnhancers = typeof window === 'object' && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const middlewares = [logger];

const store = createStore(rootReducer,
  composeReduxDevToolsEnhancers(applyMiddleware(...middlewares)));

render(
  <Provider store={store}>
    <CountUp />
  </Provider>,
  document.getElementById("root")
);
