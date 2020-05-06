import React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import { createStore,applyMiddleware,compose, } from "redux";
import { BrowserRouter as Router, Route } from "react-router-dom";
import logger from 'redux-logger'
import rootReducer from "./reducers";
import { Layout, Login, CountUp } from "./components/index";

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
     <Router>
      <Layout>
        <Route exact path="/" component={CountUp}></Route>
        <Route path="/login" component={Login}></Route>
      </Layout>
    </Router>
  </Provider>,
  document.getElementById("root")
);
