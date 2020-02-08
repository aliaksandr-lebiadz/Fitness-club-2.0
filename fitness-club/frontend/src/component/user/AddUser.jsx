import React, { useState } from "react";
import PropTypes from "prop-types";
import { TextField, TableRow, TableCell, MenuItem } from "@material-ui/core";
import CheckIcon from "@material-ui/icons/Check";
import CloseIcon from "@material-ui/icons/Close";

const roles = ["CLIENT", "TRAINER", "ADMIN"];

const AddUser = props => {
  const { classes, cells, stopAdding, addUser } = props;
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
          onClick={() => {
            addUser(user);
          }}
        />
        <CloseIcon className={classes.cancelIcon} onClick={stopAdding} />
      </TableCell>
    </TableRow>
  );
};

AddUser.propTypes = {
  classes: PropTypes.object.isRequired,
  cells: PropTypes.arrayOf(PropTypes.object).isRequired,
  stopAdding: PropTypes.func.isRequired,
  addUser: PropTypes.func.isRequired
};

export default AddUser;
