import React, { useState } from "react";
import PropTypes from "prop-types";
import { TextField, TableRow, TableCell, MenuItem } from "@material-ui/core";
import CheckIcon from "@material-ui/icons/Check";
import CloseIcon from "@material-ui/icons/Close";
import { stopAddingUser, addUser } from "../../actions";
import { bindActionCreators } from "redux";
import { connect } from "react-redux";

const roles = ["CLIENT", "TRAINER", "ADMIN"];

const AddUser = props => {
  const { cells, stopAddingUser, addUser } = props;
  const [user, setUser] = useState({
    email: "",
    password: "",
    firstName: "",
    secondName: "",
    role: "",
    discount: ""
  });

  function handleChange(e) {
    const { name, value } = e.target;
    setUser(prev => ({
      ...prev,
      [name]: value
    }));
  }

  return (
    <TableRow>
      {cells.map(cell => (
        <TableCell key={cell.id}>
          <TextField
            id={cell.id}
            select={cell.selectable}
            label={cell.selectable ? " " : cell.label}
            name={cell.id}
            onChange={handleChange}
          >
            {cell.selectable
              ? roles.map(role => (
                  <MenuItem key={role} value={role}>
                    {role}
                  </MenuItem>
                ))
              : null}
          </TextField>
        </TableCell>
      ))}
      <TableCell>
        <CheckIcon
          onClick={function() {
            addUser(user);
            stopAddingUser();
          }}
        />
        <CloseIcon onClick={stopAddingUser} />
      </TableCell>
    </TableRow>
  );
};

AddUser.propTypes = {
  cells: PropTypes.arrayOf(PropTypes.object).isRequired,
  stopAddingUser: PropTypes.func.isRequired,
  addUser: PropTypes.func.isRequired
};

const mapDispatchToProps = dispatch => {
  return bindActionCreators({ stopAddingUser, addUser }, dispatch);
};

export default connect(null, mapDispatchToProps)(AddUser);
