import React from "react";
import { TableBody } from "@material-ui/core";
import PropTypes from "prop-types";
import User from "./User";
import AddUser from "./AddUser";

const UsersTableBody = props => {
  const {
    classes,
    users,
    deleteUserById,
    updateUserById,
    adding,
    cells,
    stopAdding,
    addUser,
    rowEditing,
    setRowEditing
  } = props;

  return (
    <TableBody>
      {users.map(user => (
        <User
          key={user.id}
          classes={classes}
          user={user}
          deleteUserById={deleteUserById}
          cells={cells}
          rowEditing={rowEditing}
          setRowEditing={setRowEditing}
          updateUserById={updateUserById}
        />
      ))}
      {adding ? (
        <AddUser
          classes={classes}
          cells={cells}
          stopAdding={stopAdding}
          addUser={addUser}
        />
      ) : null}
    </TableBody>
  );
};

UsersTableBody.propTypes = {
  classes: PropTypes.object.isRequired,
  users: PropTypes.arrayOf(PropTypes.object).isRequired,
  deleteUserById: PropTypes.func.isRequired,
  adding: PropTypes.bool.isRequired,
  cells: PropTypes.arrayOf(PropTypes.object).isRequired,
  stopAdding: PropTypes.func.isRequired,
  addUser: PropTypes.func.isRequired,
  rowEditing: PropTypes.number.isRequired,
  setRowEditing: PropTypes.func.isRequired
};

export default UsersTableBody;
