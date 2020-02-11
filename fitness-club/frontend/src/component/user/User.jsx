import React, { useState } from "react";
import PropTypes from "prop-types";
import { TableCell, TableRow, TextField } from "@material-ui/core";
import DeleteIcon from "@material-ui/icons/Delete";
import EditIcon from "@material-ui/icons/Edit";
import CheckIcon from "@material-ui/icons/Check";
import CloseIcon from "@material-ui/icons/Close";
import { connect } from "react-redux";
import { deleteUserById, updateUserById, setRowEditing } from "../../actions";
import { bindActionCreators } from "redux";

const User = props => {
  const {
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
            onClick={function() {
              updateUserById(user.id, user);
              setRowEditing(null);
            }}
          />
          <CloseIcon onClick={() => setRowEditing(false)} />
        </TableCell>
      ) : (
        <TableCell>
          <EditIcon onClick={() => setRowEditing(user.id)} />
          <DeleteIcon onClick={() => deleteUserById(user.id)} />
        </TableCell>
      )}
    </TableRow>
  );
};

User.propTypes = {
  user: PropTypes.object.isRequired,
  deleteUserById: PropTypes.func.isRequired,
  updateUserById: PropTypes.func.isRequired,
  rowEditing: PropTypes.number.isRequired,
  setRowEditing: PropTypes.func.isRequired,
  cells: PropTypes.array.isRequired
};

const mapStateToProps = state => ({
  rowEditing: state.rowEditing
});

const mapDispatchToProps = dispatch => {
  return bindActionCreators(
    { deleteUserById, updateUserById, setRowEditing },
    dispatch
  );
};

export default connect(mapStateToProps, mapDispatchToProps)(User);
