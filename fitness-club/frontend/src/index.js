import React from "react";
import ReactDOM from "react-dom";
import * as serviceWorker from "./serviceWorker";
import { Provider } from "react-redux";
import storeFactory from "./store";
import App from "./App";
import sampleData from "./initialState";

const initialState = localStorage["redux-store"]
  ? JSON.parse(localStorage["redux-store"])
  : sampleData;

const saveState = () => {
  localStorage["redux-store"] = JSON.stringify(store.getState());
};

const store = storeFactory(initialState);
store.subscribe(saveState);

window.React = React;
window.store = store;

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);

serviceWorker.unregister();
