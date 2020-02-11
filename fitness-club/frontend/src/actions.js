import Actions from "./constants";
import { userService } from "./service";

export const getUsers = () => {
  return dispatch => {
    userService.getAll().then(response => {
      return dispatch({
        type: Actions.FETCH_USERS_SUCCESS,
        payload: response.data
      });
    });
  };
};

export const addUser = user => {
  return dispatch => {
    userService.create(user).then(response => {
      return dispatch({
        type: Actions.ADD_USER,
        payload: user
      });
    });
  };
};

export const stopAddingUser = () => ({
  type: Actions.STOP_ADDING_USER
});

export const startAddingUser = () => ({
  type: Actions.START_ADDING_USER
});

export const deleteUserById = id => {
  return dispatch => {
    userService.deleteById(id).then(response => {
      return dispatch({ type: Actions.DELETE_USER, payload: id });
    });
  };
};

export const updateUserById = (id, user) => {
  return dispatch => {
    userService.updateById(id, user).then(response => {
      return dispatch({ type: Actions.UPDATE_USER, payload: { id, user } });
    });
  };
};

export const setRowEditing = row => ({
  type: Actions.SET_ROW_EDITING,
  payload: row
});
