import React, { useState, useEffect } from "react";
import { TableContainer, Table, Paper } from "@material-ui/core";
import UsersTableBody from "./UsersTableBody";
import UsersTableHead from "./UsersTableHead";
import { userService } from "../../service";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles({
  tableContainer: {
    width: "75%",
    marginTop: "10%",
    marginLeft: "15%"
  }
});

const cells = [
  { id: "email", label: "Email", selectable: false, editable: false },
  { id: "password", label: "Password", selectable: false, editable: false },
  { id: "firstName", label: "First name", selectable: false, editable: false },
  {
    id: "secondName",
    label: "Second name",
    selectable: false,
    editable: false
  },
  { id: "role", label: "Role", selectable: true, editable: false },
  { id: "discount", label: "Discount", selectable: false, editable: true }
];

const UsersTable = () => {
  const classes = useStyles();
  const [users, setUsers] = useState([]);
  const [adding, setAdding] = useState(false);
  const [rowEditing, setRowEditing] = useState(null);

  useEffect(() => {
    reloadUserList();
  });

  function reloadUserList() {
     userService.getAll().then(response => {
      setUsers(response.data);
    });
  }

  function deleteUserById(id) {
    userService.deleteById(id).then(response => {
      setUsers(users.filter(user => user.id !== id));
    });
  }

  function updateUserById(id, newUser) {
    userService.updateById(id, newUser).then(response => {
      setUsers(
        users.map(user => {
          return user.id === id ? newUser : user;
        })
      );
    });
    setRowEditing(null);
  }

  function addUser(user) {
    userService.create(user).then(response => {
      let newUsers = [...users, user];
      setUsers(newUsers);
    });
    stopAdding();
  }

  function startAdding() {
    setAdding(true);
  }
  function stopAdding() {
    setAdding(false);
  }
  return (
    <TableContainer className={classes.tableContainer} component={Paper}>
      <Table aria-label="a dense table">
        <UsersTableHead
          classes={classes}
          startAdding={startAdding}
          cells={cells}
        />
        <UsersTableBody
          classes={classes}
          users={users}
          deleteUserById={deleteUserById}
          adding={adding}
          stopAdding={stopAdding}
          addUser={addUser}
          cells={cells}
          rowEditing={rowEditing}
          setRowEditing={setRowEditing}
          updateUserById={updateUserById}
        />
      </Table>
    </TableContainer>
  );
};

export default UsersTable;
