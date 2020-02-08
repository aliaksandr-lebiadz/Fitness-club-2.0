import React, { useState } from "react";
import PropTypes from "prop-types";
import { TableCell, TableRow, TextField } from "@material-ui/core";
import DeleteIcon from "@material-ui/icons/Delete";
import EditIcon from "@material-ui/icons/Edit";
import CheckIcon from "@material-ui/icons/Check";
import CloseIcon from "@material-ui/icons/Close";

const User = props => {
  const {
    classes,
    deleteUserById,
    rowEditing,
    setRowEditing,
    cells,
    updateUserById
  } = props;
  const [user, setUser] = useState(props.user);

  function handleChange(e) {
    const { name, value } = e.target;
    setUser(prev => ({
      ...prev,
      [name]: value
    }));
  }

  return (
    <TableRow key={user.id}>
      {cells.map(cell => (
        <TableCell>
          {cell.editable && rowEditing === user.id ? (
            <TextField
              id={cell.id}
              value={user.discount}
              name={cell.id}
              onChange={handleChange}
            />
          ) : (
            user[cell.id]
          )}
        </TableCell>
      ))}
      {rowEditing === user.id ? (
        <TableCell>
          <CheckIcon
            onClick={() => {
              updateUserById(user.id, user);
            }}
          />
          <CloseIcon className={classes.cancelIcon} onClick={() => setRowEditing(false)} />
        </TableCell>
      ) : (
        <TableCell>
          <EditIcon onClick={() => setRowEditing(user.id)} />
          <DeleteIcon
            className={classes.deleteIcon}
            onClick={() => deleteUserById(user.id)}
          />
        </TableCell>
      )}
    </TableRow>
  );
};

User.propTypes = {
  classes: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired,
  deleteUserById: PropTypes.func.isRequired,
  rowEditing: PropTypes.number.isRequired,
  setRowEditing: PropTypes.func.isRequired
};

export default User;
