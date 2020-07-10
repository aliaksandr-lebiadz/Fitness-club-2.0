import React, { useEffect } from "react";
import { TableContainer, Table, Paper, makeStyles } from "@material-ui/core";
import UsersTableBody from "./UsersTableBody";
import UsersTableHead from "./UsersTableHead";
import { connect } from "react-redux";
import { getUsers } from "../../actions";
import { bindActionCreators } from "redux";

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

const UsersTable = props => {
  const classes = useStyles();

  useEffect(() => {
    props.getUsers();
  });

  return (
    <TableContainer className={classes.tableContainer} component={Paper}>
      <Table aria-label="a dense table">
        <UsersTableHead cells={cells} />
        <UsersTableBody cells={cells} />
      </Table>
    </TableContainer>
  );
};

const mapDispatchToProps = dispatch => {
  return bindActionCreators({ getUsers }, dispatch);
};

export default connect(null, mapDispatchToProps)(UsersTable);
