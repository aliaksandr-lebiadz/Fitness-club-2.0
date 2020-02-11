import Actions from "../constants";
import { combineReducers } from "redux";

export const users = (state = [], action) => {
  switch (action.type) {
    case Actions.FETCH_USERS_SUCCESS: {
      return action.payload;
    }
    case Actions.ADD_USER: {
      return [...state, action.payload];
    }
    case Actions.DELETE_USER: {
      return state.filter(user => user.id !== action.payload);
    }
    case Actions.UPDATE_USER: {
      let newUsers = [...state];
      return newUsers.map(user => {
        return user.id === action.payload.id ? action.payload.user : user;
      });
    }
    default: {
      return state;
    }
  }
};

export const addingUser = (state = false, action) => {
  switch (action.type) {
    case Actions.START_ADDING_USER: {
      return true;
    }
    case Actions.STOP_ADDING_USER: {
      return false;
    }
    default: {
      return state;
    }
  }
};

export const rowEditing = (state = null, action) => {
  if (action.type === Actions.SET_ROW_EDITING) {
    return action.payload;
  }
  return state;
};

export default combineReducers({
  users,
  addingUser,
  rowEditing
});
